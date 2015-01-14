<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application Server Info</title>
<style>
table {
	border-collapse: collapse;
	border: 1px solid black;
}

th {
	text-align: right;
}

th, td {
	padding: 5px;
}

th.caption {
	text-align: left;
	background-color: #ccc;
}
</style>
</head>
<body>
	<table>
		<tr>
			<th class="caption" colspan="2">Server</th>
		</tr>
		<tr>
			<th>Server Info:</th>
			<td><%=application.getServerInfo()%></td>
		</tr>
		<tr>
			<th>Servlet API Version:</th>
			<td><%=application.getMajorVersion() + "."
                    + application.getMinorVersion()%></td>
		</tr>
		<tr>
			<th>JSP API Version:</th>
			<td><%=JspFactory.getDefaultFactory().getEngineInfo()
                    .getSpecificationVersion()%></td>
		</tr>
		<tr>
			<th>Web Root Real Path:</th>
			<td><%=application.getRealPath("/")%></td>
		</tr>
		<tr>
			<th class="caption" colspan="2">Java</th>
		</tr>
		<tr>
			<th>Java Home:</th>
			<td><%=System.getProperty("java.home")%></td>
		</tr>
		<tr>
			<th>Java Version:</th>
			<td><%=System.getProperty("java.version") + " ("
                    + System.getProperty("java.vendor") + ")"%></td>
		</tr>
		<tr>
			<th class="caption" colspan="2">Properties</th>
		</tr>
		<%
		    String key;
		    Properties prop = System.getProperties();
		    for (Iterator<?> itr = prop.keySet().iterator(); itr.hasNext();) {
		        key = itr.next().toString();
		%>
		<tr>
			<th><%=key%>:</th>
			<td><%=prop.get(key)%></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption" colspan="2">Env</th>
		</tr>
		<%
		    Map<String, String> env = System.getenv();
		    for (Iterator<String> itr = env.keySet().iterator(); itr.hasNext();) {
		        key = itr.next().toString();
		%>
		<tr>
			<th><%=key%>:</th>
			<td><%=env.get(key)%></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption" colspan="2">ServletContext</th>
		</tr>
		<%
		    for (Enumeration<?> names = application.getAttributeNames(); names
		            .hasMoreElements();) {
		        key = names.nextElement().toString();
		%>
		<tr>
			<th><%=key%>:</th>
			<td><%=application.getAttribute(key)%></td>
		</tr>
		<%
		    }
		%>
	</table>
</body>
</html>