<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.4.1.js"></script> 
<style>
* {
   margin: 0 auto;
   box-sizing: border-box;
}

body {
   color: gray;
   font-size:10pt;
}

form {
   padding: 25px;
}

fieldset {padding: 20px;
   width: 480px; 
   border:2px solid lightblue;
   background:linen;
   border-radius:20px;
   border: 2px solid linen;
}

legend {
   font-size: 20pt;
   font-weight: bold;
}

label {
   margin-top: 10px;
   display: block;
   font-weight: bold;
}

input, textarea {
border:0px;
}

input:not(#hobby1,#hobby2){
height:50px;
}

#id, #post, #pass, #address {
   width: 300px; height: 20px;
}

input[type=button] {
   width: 20%;
   background: lightblue;
   border: 0px;
   color: gray;
}

button {
   width: 40%;
   height: 50px;
   line-height: 50px;
   color: gray;
   font-weight: bold;
   border: 2px solid linen;
}

button[type=submit], button[type=reset] {
   background: lightblue;
}
button:hover {
   opacity:0.5;
}

span {color:thistle; font-size:15pt;}

</style>
<script>
  $(function(){
	  $(".join").click(function(){
		 location.href="join.net"; 
	  });
  })
</script>
</head>
<body>
   <form name="loginform" method=post 
         action="loginProcess.net">
      <fieldset>
         <legend><span></span> 로그인 <span></span></legend>
         <label>ID</label>
         <input type="text" name="id" id="id" required
            <c:if test="${!empty saveid}">value=${saveid}</c:if> 
         >
         <br> 
         <label>비밀번호</label>
         <input type="password" name="password" id="password" required> 
         <br><br><br>
         
         <input type="checkbox" name="remember"
             <c:if test="${!empty saveid}">checked</c:if> 
         >Remember me
        
      <div class=cleaffix>
         <button type="submit" class="submitbtn">로그인</button>
         <button type="button" class="join">회원가입</button>
      </div>
      </fieldset>

   </form>
</body>
</html>