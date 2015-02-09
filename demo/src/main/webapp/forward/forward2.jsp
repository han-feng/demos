<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table>
	<tr>
		<th>ContextPath</th>
		<td><%=request.getContextPath()%></td>
	</tr>
	<tr>
		<th><%=RequestDispatcher.FORWARD_CONTEXT_PATH%></th>
		<td><%=request
                    .getAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH)%></td>
	</tr>
	<tr>
		<th><%=RequestDispatcher.FORWARD_REQUEST_URI%></th>
		<td><%=request
                    .getAttribute(RequestDispatcher.FORWARD_REQUEST_URI)%></td>
	</tr>
</table>
