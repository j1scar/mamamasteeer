<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../board/header.jsp" />
<style>
 caption {caption-side:top; text-align:center;}
 table {text-align:center;}
 body .container {width:60%;}
 tr:last-child{text-align:center;}
</style>
<title>회원 정보</title>
</head>
<body>
<c:set var="m" value="${memberinfo }" />
<div class="container">
<table class="table table-striped">
  <caption>회원 정보</caption>
  <thead>
   <tr>
     <td>아이디 </td>
     <td>정보</td>
   </tr>
   </thead>
   <tbody>
   <tr>
     <td>아이디</td>
     <td>${m.id }</td>
   </tr>
   <tr>
     <td>이름</td>
     <td>${m.name }</td>
   </tr>
   <tr>
     <td>비밀번호</td>
     <td>${m.password }</td>
   </tr>
   <tr>
     <td>나이</td>
     <td>${m.age }</td>
   </tr>
   <tr>
     <td>성별</td>
     <td>${m.gender }</td>
   </tr>
   <tr>
     <td>이메일</td>
     <td>${m.email }</td>
   </tr>
   <tr>
   <td colspan=2>
      <a href="member_list.net">목록으로 돌아가기</a>
   </td>
   </tr>   
   </tbody>
</table>
</div>
</body>
</html>