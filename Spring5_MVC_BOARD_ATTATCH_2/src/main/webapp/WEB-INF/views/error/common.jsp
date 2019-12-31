<%--board.xml에서 한 문장 주석달아서 오류 발생시켜보기 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>common.jsp</title>
<style>
  body {background-color:#f7bfbf; text-align:center}
</style>
</head>
<body>
Sorry<br>
<img src="resources/image/error.png" width="150px"><br>
<b>${url}</b> error 
<hr>
${exception} 
</body>
</html>