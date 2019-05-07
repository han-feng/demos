package com.cvicse.refactor.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import com.cvicse.refactor.util.StringRewriter;

public class RefactorASTVisitor extends ASTVisitor {

	private List<StringRewriter.Info> context;

	public RefactorASTVisitor(List<StringRewriter.Info> context) {
		this.context = context;
	}

	/**
	 * 对导入进行排序和合并
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void endVisit(CompilationUnit node) {
		if (node.imports().size() <= 0)
			return;

		int start = node.getLength();
		int end = 0;
		List<ImportDeclaration> imports = new ArrayList<>();
		imports.addAll((List<ImportDeclaration>) node.imports());
		// 排序
		imports.sort((i1, i2) -> i1.getName().getFullyQualifiedName().compareTo(i2.getName().getFullyQualifiedName()));
		// 合并
		Map<String, AtomicInteger> counter = new HashMap<>();
		for (ImportDeclaration i : imports) {
			// 记录 imports 整体 start 和 end
			final int is = i.getStartPosition();
			final int ie = is + i.getLength();
			start = start < is ? start : is;
			end = end > ie ? end : ie;
			String name = i.getName().getFullyQualifiedName();
			int p = name.lastIndexOf('.');
			if (p > 0) {
				String packageName = name.substring(0, p);
				if (counter.containsKey(packageName)) {
					counter.get(packageName).incrementAndGet();
				} else {
					counter.put(packageName, new AtomicInteger(1));
				}
			}
		}
		List<String> newImports = new ArrayList<>();
		StringBuilder importBuilder = new StringBuilder();
		for (ImportDeclaration i : imports) {
			String name = i.getName().getFullyQualifiedName();
			int p = name.lastIndexOf('.');
			if (p > 0) {
				String packageName = name.substring(0, p);
				if (counter.containsKey(packageName)) {
					if (counter.get(packageName).get() > 2) {
						importBuilder.append("import ");
						if (i.isStatic())
							importBuilder.append("static ");
						importBuilder.append(packageName).append(".*;");
						newImports.add(importBuilder.toString());
						importBuilder.delete(0, importBuilder.length());
						counter.remove(packageName);
					} else {
						newImports.add(i.toString().trim());
					}
				}
			} else {
				newImports.add(i.toString().trim());
			}
		}
		StringBuilder code = new StringBuilder();
		for (int i = 0, len = newImports.size(); i < len; i++) {
			if (i != 0)
				code.append(System.lineSeparator());
			code.append(newImports.get(i));
		}
		context.add(new StringRewriter.Info(start, end, code.toString()));
	}

	/**
	 * 将 EnhancedFor 重构为 forEach() 方法调用，该重构可能会造成语法错误，需要手工进行修复。
	 */
	@Override
	public void endVisit(EnhancedForStatement node) {
		final int start = node.getStartPosition();
		final int length = node.getLength();
		final SingleVariableDeclaration parameter = node.getParameter();
		final Expression expression = node.getExpression();

		// 头部替换
		final StringBuilder code = new StringBuilder();
		code.append(expression.toString()).append(".forEach(" + parameter.getName()).append(" -> ");
		context.add(new StringRewriter.Info(start, node.getBody().getStartPosition(), code.toString()));
		// 结尾追加 “);”
		context.add(new StringRewriter.Info(start + length, start + length, ");"));
	}
}
