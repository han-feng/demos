<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="demo.web.TestJTA"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@page
	import="java.net.UnknownHostException,java.net.InetAddress,java.util.Enumeration"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
		TestJTA test = new TestJTA();
		if (test.testTM()) {
	%>OK !<%
		} else {
	%>Error !<%
		}
	%>
</body>
</html>