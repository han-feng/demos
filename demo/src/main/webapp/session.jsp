<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String value = request.getParameter("setValue");
    String name = request.getContextPath();
    ServletContext root = session.getServletContext().getContext(
            "/demo");
    if (value != null && !"".equals(value)) {
        session.setAttribute(name, value);
        root.setAttribute(name, value);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form action="">
		<input type="text" name="setValue" value=""><input
			type="submit" value="设置">
	</form>
	<table>
		<tr>
			<td>ContextPath</td>
			<td><%=request.getContextPath()%></td>
		</tr>
		<%
		    Enumeration names = session.getAttributeNames();
		    while (names.hasMoreElements()) {
		        name = (String) names.nextElement();
		%>
		<tr>
			<td><%=name%></td>
			<td><%=session.getAttribute(name)%></td>
		</tr>
		<%
		    }
		    names = root.getAttributeNames();
		    while (names.hasMoreElements()) {
		        name = (String) names.nextElement();
		%>
		<tr>
			<td><%=name%></td>
			<td><%=root.getAttribute(name)%></td>
		</tr>
		<%
		    }
		%>
	</table>

</body>
</html>