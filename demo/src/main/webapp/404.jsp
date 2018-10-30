<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function seturl() {
		var urlHref = location.href;
		var newHref = urlHref, urlDom = document.getElementById("url");
		if (urlHref.length > 100) {
			newHref = urlHref.substring(0, 100) + "...";
		}

		urlDom.setAttribute("title", urlHref);
		urlDom.innerHTML = newHref;

	}
	window.onload = function() {
		seturl();
	}
</script>
</head>
<body>
	<h1>404</h1>
	您请求的页面
	<label id="url"></label>不存在，它可能已经转到其他地址，或者您输入的URL有错误
</body>
</html>