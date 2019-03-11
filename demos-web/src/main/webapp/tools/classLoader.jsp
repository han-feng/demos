<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.*,java.net.*,java.io.*,java.lang.reflect.*"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    //
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
%>
<%!//
	static class Node {

		private static int MAX_ID = 0;

		private final int id = ++MAX_ID;
		private String type;
		private Node parent;
		private Set children = new HashSet();
		private ClassLoader classLoader;
		private Node root;

		public Node() {
			this(Node.class.getClassLoader());
		}

		public Node(ClassLoader classloader) {
			this.classLoader = classloader;
			this.type = classloader.getClass().getName();

			ClassLoader p = classloader.getParent();

			if (p != null && p != classloader) {
				this.parent = new Node(p);
				this.parent.children.add(this);
				this.root = this.parent.getRoot();
			} else {
				this.root = this;
			}

			// children

		}

		public int getId() {
			return this.id;
		}

		public String getType() {
			return this.type;
		}

		public Node getParent() {
			return this.parent;
		}

		public ClassLoader getClassLoader() {
			return this.classLoader;
		}

		public Node getRoot() {
			return this.root;
		}

		public Node[] getChildren() {
			return (Node[]) this.children.toArray(new Node[children.size()]);
		}

		public String[] getURLs() {
			String[] urls;
			int len;
			if (classLoader instanceof URLClassLoader) {
				URL[] urlObjs = ((URLClassLoader) classLoader).getURLs();
				if (urlObjs == null)
					return new String[0];
				len = urlObjs.length;
				urls = new String[len];
				for (int i = 0; i < len; i++) {
					urls[i] = urlObjs[i].toString();
				}
			} else {
				try {
					Method method = classLoader.getClass().getMethod(
							"getFinderClassPath");
					String classpath = (String) method.invoke(classLoader);
					urls = classpath.split(";");
				} catch (Exception e) {
					return new String[0];
				}
			}
			return urls;
		}
	}

	private static void printClassLoaderNodes(Node node, JspWriter out)
			throws IOException {
		Node[] children = node.getChildren();
		int size = children.length;

		out.println("<table>");
		out.print("<tr><th");
		if (size > 1)
			out.print(" colspan='" + size + "'");
		out.print(">");
		out.print(node.type);
		String[] urls = node.getURLs();
		int urlsize = urls.length;
		out.print(" &nbsp; <a href=\"javascript:displayNode('node_urls_"
				+ node.id + "')\">详细信息</a>");
		out.println("<ol id='node_urls_" + node.id
				+ "' style='display: none;'>");
		out.println(node.classLoader.toString());
		for (int i = 0; i < urlsize; i++) {
			out.println("<li>" + urls[i] + "</li>");
		}
		out.print("</ol>");
		out.println("</th></tr>");

		if (size > 0) {
			out.print("<tr>");
			for (int i = 0; i < size; i++) {
				out.println("<td>");
				printClassLoaderNodes(children[i], out);
				out.println("</td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
	}%>
<html>
<head>
<title>类加载器结构</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
li {
	color: blue;
	font-weight: normal;
}

table {
	border-collapse: collapse;
	border: 0px solid black;
	margin: 0;
}

th {
	text-align: left;
	border: 1px solid black;
	padding: 5px;
}

td {
	padding: 20px 0;
}
</style>
<script type="text/javascript">
	function displayNode(nodeId) {
		var node = document.getElementById(nodeId);
		if (node.style.display == "none")
			node.style.display = "block";
		else
			node.style.display = "none";
	}
</script>
</head>
<body>
	<h1>JSP ClassLoader</h1>
	<%
	    printClassLoaderNodes(new Node().getRoot(), out);
	%>
	<h1>Context ClassLoader</h1>
	<%
	    printClassLoaderNodes(new Node(Thread.currentThread()
	            .getContextClassLoader()).getRoot(), out);
	%>
</body>
</html>
