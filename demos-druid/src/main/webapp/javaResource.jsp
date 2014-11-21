<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<html>
<head>
<style>
* {
	font-family: 宋体, Arial;
	font-size: 9pt
}

label {
	color: red;
}

li {
	color: blue;
}
</style>
</head>
<body>
<%
	String resUri = request.getParameter("resourceUri");
%>
<form method="post"><label>请输入资源类路径 (例:
java/lang/String.class )</label> <br />
<input type="text" name="resourceUri" size="100"
	value="<%//
			if (resUri == null)
				out.print("java/lang/String.class");
			else
				out.print(resUri);%>" />
<input type="submit" value="搜索" /></form>
<%
	//搜索
	if (resUri != null && !resUri.equals("")) {
%>
<hr />
<label>搜索结果</label>
<ol>
	<%
		resUri = resUri.trim();
			java.util.Enumeration resources = Thread.currentThread()
					.getContextClassLoader().getResources(resUri);
			Object value;
			while (resources.hasMoreElements()) {
				value = resources.nextElement();
				out.print("<li>" + value + "</li>");
			}
		}
	%>
</ol>
</body>
</html>
