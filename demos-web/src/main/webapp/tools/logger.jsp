<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!public static boolean checkClass(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    boolean log4j = checkClass("org.apache.log4j.Logger");
    boolean log4j2 = checkClass("org.apache.logging.log4j.LogManager");
    boolean jul = checkClass("java.util.logging.Logger");
    boolean slf4j = checkClass("org.slf4j.LoggerFactory");
    boolean acl = checkClass("org.apache.commons.logging.LogFactory");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logger</title>
<style>
table {
	border-collapse: collapse;
	border: 1px solid black;
}

th {
	text-align: center;
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
	<table style="width: 100%">
		<tr>
			<th class="caption">Log4j 1.x <%=log4j ? "可用" : "不可用"%></th>
		</tr>
		<%
		    if (log4j) {
		%><tr>
			<td align="center"><jsp:include page="logger-log4j.jsp"></jsp:include></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption">Log4j 2.x <%=log4j2 ? "可用" : "不可用"%></th>
		</tr>
		<%
		    if (log4j2) {
		%><tr>
			<td><jsp:include page="logger-log4j2.jsp"></jsp:include></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption">Slf4j <%=slf4j ? "可用" : "不可用"%></th>
		</tr>
		<%
		    if (slf4j) {
		%><tr>
			<td><jsp:include page="logger-slf4j.jsp"></jsp:include></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption">Apache Commons Logging <%=acl ? "可用" : "不可用"%></th>
		</tr>
		<%
		    if (acl) {
		%><tr>
			<td><jsp:include page="logger-acl.jsp"></jsp:include></td>
		</tr>
		<%
		    }
		%>
		<tr>
			<th class="caption">Java Util Logging<%=jul ? "可用" : "不可用"%></th>
		</tr>
		<%
		    if (jul) {
		%><tr>
			<td><jsp:include page="logger-jul.jsp"></jsp:include></td>
		</tr>
		<%
		    }
		%>
	</table>
</body>
</html>