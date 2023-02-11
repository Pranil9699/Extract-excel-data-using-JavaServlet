<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String filename = request.getParameter("filename");
%>
<a href="<%=request.getContextPath()%>/<%=filename%>"download>DownLoad</a>
<!-- Your HTML table -->
<h3 style="text-align:center;color:rgba(0, 255, 0, 0.1)">Excel File Contents:</h3>
	<%= (String) request.getAttribute("html") %>
	<br>
</body>
</html>