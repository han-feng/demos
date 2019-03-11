<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.*,java.net.URL" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    String resUri = request.getParameter("resourceUri");
%>
<html>
<head>
<title>Java资源查找</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
label {
	color: red;
	font-weight: bold;
}

li {
	color: blue;
}
</style>
</head>
<body>
	<form method="post">
		<label>请输入资源类路径 (例: java/lang/String.class )</label> <br /> <input
			type="text" name="resourceUri" size="100"
			value="<%//
            if (resUri == null)
                out.print("java/lang/String.class");
            else
                out.print(resUri);%>" />
		<input type="submit" value="搜索" />
	</form>
	<%
	    //搜索
	    if (resUri != null && !resUri.equals("")) {
	%>
	<hr />
	<%
	    //
	        resUri = resUri.trim();

	        ClassLoader classLoader = Thread.currentThread()
	                .getContextClassLoader();
	        URL url = classLoader.getResource(resUri);
	        if (url != null) {
	%>
	<label>搜索结果</label>
	<ol>
		<%
		    //
		            Enumeration resources = classLoader.getResources(resUri);
		            Object value;
		            while (resources.hasMoreElements()) {
		                value = resources.nextElement();
		                out.print("<li>" + value);
		                if (url.equals(value)) {
		                    out.print("<label>有效</label>");
		                }
		                out.print("</li>");
		            }
		%>
	</ol>
	<%
	    //
	        } else {
	%>
	<label>搜索结果：未找到 <%=resUri%></label>
	<%
	    //
	        }
	    }
	%>
</body>
</html>
