<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리 시스템 회원가입 페이지</title>
<style>
form {margin:20px auto; width:500px;}
b {width:100%;display:block}
input{height:20px; border:1px solid lightgray;}
input[type=text],input[type=password] {background-color:#EAEAEA;}
select {height:25px;}
div {border:1px solid lightgray;}
input[name=id], input[name=email]{width:60%;}
button[type=submit] {width:49.3%;height:30px;color:white;
                        background-color:skyblue;}
button[type=reset] {width:49.3%;height:30px;color:white;
                        background-color:lavender;}
</style>
<script src="resources/js/jquery-3.4.1.js"></script> 
<script>

$(document).ready(function(){
 $("input:eq(0)").on('keyup',function(){
	 $("#message").empty();
	   var id = $('input:eq(0)').val();
	   // \w는 [A-Za-z0-9]의 의미
	   var pattern = /^\w{5,12}$/;
	   if(!pattern.test(id)){
		   $("#message").css('color','red')
		               .html("영문자 숫자 _로 5~12자 가능합니다.")
		   checkid=false; 
		   return;
	   }
	   
	   $.ajax({
		   type: "post",
			  url: "idcheck.net", 
			  data: {"id": id},  
			  success: function(resp){
				 if(resp==-1){
					 $("#message").css('color','green')
					              .html("사용 가능한 아이디입니다.");
				    checkid=true;
				 }else{
					 $("#message").css('color','blue')
					              .html("사용 중인 아이디입니다.");
				    checkid=false;
				 }
		     } //success end
	   }); // ajax end
    }); // keyup end
    
    
    
    
    var checkid=false;
	 var checkmail=false;
	 $('form').submit(function(){
		 if(!checkid){
			 alert("사용 가능한  id로 입력하세요.");
			 $("input:eq(0)").val('').focus();
			 $("#message").text('');
			 return false;
		 }
		 
		 if(!$.isNumeric($("input[name='age']").val())){
			 alert("나이는 숫자를 입력하세요.");
			 $("input[name='age']").val('');
			 $("input[name='age']").focus();
			 return false;
		 }
		 
		 if(!checkemail){
			 alert("email 형식을 확인하세요.");
			 $("input:eq(6)").focus();
			 return false;
		 }
	 }); //submit end
	 
	 
    $("input:eq(6)").on('keyup',function(){
   	 $("#email_message").empty();
   	   var email = $('input:eq(6)').val();
   	   //정규식: \w:대소문자,뭐어쩌구 그런거 +:플러스 [.]:점
   	   var pattern = /^\w+@\w+[.]\w{3}$/; //@랑 .들어가게 꼭 적어야함!
   	   
   	   if(!pattern.test(email)){
   		   $("#email_message").css('color','red')
   		                      .html("이메일 형식이 맞지 않습니다.");
   	   }else{
   		   $("#email_message").css('color','green')
   		                      .html("이메일 형식에 맞습니다.");
   	   }
    }); // keyup end
   }); //ready end
</script>
</head>
<body>
<form name="joinform" action="joinProcess.net" method="post">
  <h1>회원가입 페이지</h1>
  <hr>
  <b>아이디</b>
  <input type="text" name="id" placeholder="Enter id"
       required maxLength="12">
  <span id="message"></span>
  <b>비밀번호</b>
  <input type="password" name="password" placeholder="Enter password"
       required>
  <b>이름</b>
  <input type="text" name="name" placeholder="Enter name"
       required maxLength="15">
  <b>나이</b>
  <input type="text" name="age" placeholder="Enter age"
       required maxlength="2">
  <b>성별</b>
  <div>
  <input type="radio" name="gender" value="남" checked><span>남자</span>
  <input type="radio" name="gender" value="여"><span>여자</span>
  </div>
  <b>이메일 주소</b>
  <input type="text" name="email" placeholder="Enter email"
       required><span id="email_message"></span>
  <div class="clearfix">
  <button type="submit" class="submitbtn">확인</button>
  <button type="reset" class="cancelbtn" onClick="history.back()">취소</button>
  </div>
</form>
</body>
</html>