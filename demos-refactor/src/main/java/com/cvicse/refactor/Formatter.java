package com.cvicse.refactor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

/**
 * 代码格式化工具
 * 
 * @author han_feng
 *
 */
public class Formatter {

	private static CodeFormatter formatter;

	public static CodeFormatter getFormatter() {
		if (formatter == null) {
			synchronized (Formatter.class) {
				if (formatter == null) {
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put(JavaCore.COMPILER_SOURCE, "1.8");
					hashMap.put(JavaCore.COMPILER_COMPLIANCE, "1.8");
					hashMap.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, "1.8");
					formatter = ToolFactory.createCodeFormatter(hashMap);
				}
			}
		}
		return formatter;
	}

	public static String format(String source) {
		TextEdit edit = getFormatter().format(CodeFormatter.K_COMPILATION_UNIT, source, 0, source.length(), 0, null);
		if (edit == null) {
			return source;
		}
		IDocument doc = new Document();
		doc.set(source);
		try {
			edit.apply(doc);
		} catch (Exception e) {
			return source;
		}
		return doc.get();
	}

	public static void main(String[] args) {
		String src = "package com.cvicse.refactor;" + "import java.util.HashMap;" + "import java.util.Map;"
				+ "import org.eclipse.jdt.core.JavaCore;" + "import org.eclipse.jdt.core.ToolFactory;"
				+ "import org.eclipse.jdt.core.formatter.CodeFormatter;" + "import org.eclipse.jface.text.Document;"
				+ "import org.eclipse.jface.text.IDocument;" + "import org.eclipse.text.edits.TextEdit;" + "/**\n"
				+ " * 代码格式化工具\n" + " *\n" + " * @author han_feng\n" + " *\n" + " */" + "public class Formatter {"
				+ "private static CodeFormatter formatter;" + "public static CodeFormatter getFormatter() {"
				+ "if (formatter == null) {" + "synchronized (Formatter.class) {" + "if (formatter == null) {"
				+ "Map<String, String> hashMap = new HashMap<>();" + "hashMap.put(JavaCore.COMPILER_SOURCE, \"1.8\");"
				+ "hashMap.put(JavaCore.COMPILER_COMPLIANCE, \"1.8\");"
				+ "hashMap.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, \"1.8\");"
				+ "formatter = ToolFactory.createCodeFormatter(hashMap);" + "}" + "}" + "}" + "return formatter;" + "}"
				+ "public static String format(String source) {"
				+ "TextEdit edit = getFormatter().format(CodeFormatter.K_COMPILATION_UNIT, source, 0, source.length(), 0, null);"
				+ "if (edit == null) {" + "return source;" + "}" + "IDocument doc = new Document();"
				+ "doc.set(source);" + "try {" + "edit.apply(doc);" + "} catch (Exception e) {" + "return source;" + "}"
				+ "return doc.get();" + "}" + "public static void main(String[] args) {" + "String src = \"\";"
				+ "System.out.println(Formatter.format(src));" + "}" + "}";
		System.out.println(Formatter.format(src));
	}
}
