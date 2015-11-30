<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@page
	import="java.net.UnknownHostException,java.net.InetAddress,java.util.Enumeration"%>
<%!public static String getLocalIP() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr = ipAddrStr + ".";
            }
            ipAddrStr = ipAddrStr + (ipAddr[i] & 0xFF);
        }
        return ipAddrStr;
    }%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
	    String ipAddress = request.getHeader("x-forwarded-for");
	    out.println("<p>1: " + ipAddress + "</p>");
	    if ((ipAddress == null) || (ipAddress.length() == 0)
	            || ("unknown".equalsIgnoreCase(ipAddress))) {
	        ipAddress = request.getHeader("Proxy-Client-IP");
	        out.println("<p>2: " + ipAddress + "</p>");
	    }
	    if ((ipAddress == null) || (ipAddress.length() == 0)
	            || ("unknown".equalsIgnoreCase(ipAddress))) {
	        ipAddress = request.getHeader("WL-Proxy-Client-IP");
	        out.println("<p>3: " + ipAddress + "</p>");
	    }
	    if ((ipAddress == null) || (ipAddress.length() == 0)
	            || ("unknown".equalsIgnoreCase(ipAddress))) {
	        ipAddress = request.getRemoteAddr();
	        out.println("<p>4: " + ipAddress + "</p>");
	        if ((ipAddress.equals("127.0.0.1"))
	                || (ipAddress.equals("0:0:0:0:0:0:0:1"))) {
	            ipAddress = getLocalIP();
	            out.println("<p>5: " + ipAddress + "</p>");
	        }
	    }
	    out.println("<p>6: " + ipAddress + "</p>");
	%>
</body>
</html>