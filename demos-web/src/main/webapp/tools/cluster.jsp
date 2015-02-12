<%@page import="java.util.*,java.net.*,java.io.*,java.lang.reflect.*"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    //
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			String qs = request.getQueryString();
			String serverIp = request.getLocalAddr();
			int serverPort = request.getLocalPort();
			String contextPath = request.getContextPath();
			String sessionId = session.getId();
			String server = serverIp + ":" + serverPort;

			if ("serverInfo".equals(qs)) {
				out.print("{\"serverIp\":\"");
				out.print(serverIp);
				out.print("\",\"serverPort\":");
				out.print(serverPort);
				out.print(",\"contextPath\":\"");
				out.print(contextPath);
				out.print("\",\"sessionId\":\"");
				out.print(sessionId);
				out.print("\"}");
				return;
			}
%>
<html>
<head>
<style type="text/css">
div {
	padding: 5px;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

th, td {
	border: 1px solid #ccc;
	padding: 5px 10px;
}
</style>
<script type="text/javascript">
	var server="<%=server%>";
	var sessionId="<%=sessionId%>";
	var ajaxTimeOut = 1000;
	var loop = true;
	var handle = null;
	var count = 1;
</script>
</head>
<body>
	<h2>集群验证工具</h2>
	<%--	<div>
		<label for="jsessionname">存储SessionID的Cookie名称：</label><input
			id="jsessionname" type="text" value="JSESSIONID">
	</div>--%>
	<div>
		<input id="loopbox" type="checkbox" checked><label
			for="loopbox">循环检测</label>
	</div>
	<div>
		<table id="logTable">
			<tr>
				<th></th>
				<th>时间</th>
				<th>服务节点</th>
				<th>会话</th>
				<th>Cookie</th>
			</tr>
		</table>
	</div>
	<div>
		<label>检测总次数：</label><span id="count"></span> <label
			style="margin-left: 30px">最后检测时间：</label><span id="lasttime"></span>
	</div>
	<script src="jquery.min.js"></script>
	<script type="text/javascript">
		function log(time, server, sessionStr) {
			var newRow = "<tr><td>" + count + "</td><td>" + time + "</td><td>"
					+ server + "</td><td>" + sessionStr + "</td><td>"
					+ document.cookie + "</td></tr>";
			$("#logTable tr:last").after(newRow);
		}
		function getTimeStr() {
			var now = new Date();
			return now.getHours() + ":" + now.getMinutes() + ":"
					+ now.getSeconds() + "," + now.getMilliseconds();
		}
		function getServerInfo() {
			$.get("?serverInfo", function(data) {
				console.debug(data);
				var time = getTimeStr();
				count++;
				$("#count").html(count);
				$("#lasttime").html(time);
				data = $.parseJSON(data);
				var changed = false;
				var newServer = data.serverIp + ":" + data.serverPort;
				var newSessionId = data.sessionId;
				if (server != newServer) {
					changed = true;
					server = newServer;
					newServer = "<span style=\"color: red\">" + server
							+ "</span>";
				}
				if (sessionId != newSessionId) {
					changed = true;
					sessionId = newSessionId;
					newSessionId = "<span style=\"color: red\">" + sessionId
							+ "</span>";
				}
				if (changed)
					log(time, newServer, newSessionId);
			});
		}
		function checkLoopbox() {
			if ($("#loopbox").is(":checked")) {
				if (!handle) {
					handle = window.setInterval(getServerInfo, ajaxTimeOut);
				}
			} else {
				if (handle) {
					window.clearInterval(handle);
					handle = null;
				}
			}
		}
		$(document).ready(function() {
			log(getTimeStr(), server, sessionId);
			$("#loopbox").click(checkLoopbox);
			checkLoopbox();
		});
	</script>
</body>
</html>
