<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form action="/myapp/LoginUserServlet" method="post">
	用户名:<input name="Email" type="text"><br><br>
	密码:<input name="userPassword" type="password"><br>
	<input type="submit">
	</form>
</body>
</html>