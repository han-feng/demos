package com.cvicse.refactor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.cvicse.refactor.ast.RefactorASTVisitor;
import com.cvicse.refactor.util.StringRewriter;

/**
 * "for -> forEache 重构工具"
 *
 * @author han_feng
 *
 */
public class Refactor {

	public static final Charset[] CHARSETS = { StandardCharsets.UTF_8, Charset.forName("GBK") };

	private static CompilationUnit getAST(String source) {
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest()); // 设置Java语言规范版本
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);

		Map<String, String> compilerOptions = JavaCore.getOptions();
		compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8); // 设置Java语言版本
		compilerOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(compilerOptions); // 设置编译选项

		parser.setSource(source.toCharArray());
		return (CompilationUnit) parser.createAST(null);
	}

	public static void main(String[] args) {
		refactor(new File("/Users/hanfeng/codes/V9/"));
//		refactor(new File("src/test"));
	}

	private static String readFile(String filePath, Charset charset) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		return new String(bytes, charset);
//		StringBuilder string = new StringBuilder();
//		Files.readAllLines(Paths.get(filePath), charset).forEach(line -> {
//			string.append(line);
//			string.append(System.lineSeparator());
//		});
//		return string.toString();
	}

	/**
	 * 对一个目录下的所有Java源文件进行重构.
	 *
	 * @param root 待扫描目录
	 * @return
	 */
	public static void refactor(File root) {
		List<File> files = Arrays.asList(root.listFiles());
		files.parallelStream().forEach(file -> {
			if (file.isDirectory()) {
				refactor(file);
			} else {
				String name = file.getName();
				if (name.endsWith(".java")) {
					String path = file.getAbsolutePath();
					String source = null;
					boolean ok = false;
					// 检测文件编码格式
					for (int i = 0, len = CHARSETS.length; i < len; i++) {
						try {
							source = readFile(path, CHARSETS[i]);
							ok = true;
							break;
						} catch (IOException e) {
						}
					}
					if (!ok) {
						System.out.println("文件编码格式异常：" + path);
						return;
					}
					if (source != null) {
						String src = refactor(source);
						if (src != null) {
							System.out.println(src);
						}
					}
				}
			}
		});
	}

	private static String refactor(String source) {
		CompilationUnit cu = getAST(source);
		List<StringRewriter.Info> context = new ArrayList<>();
		cu.accept(new RefactorASTVisitor(context));
		return StringRewriter.rewrite(source, context);
	}
}
