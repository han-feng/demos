<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.*,java.net.*,java.io.*" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
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

		public URL[] getURLs() {
			if (classLoader instanceof URLClassLoader) {
				return ((URLClassLoader) classLoader).getURLs();
			} else {
				return new URL[0];
			}
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
		URL[] urls = node.getURLs();
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
	<%
	    printClassLoaderNodes(new Node().getRoot(), out);
	%>
</body>
</html>
