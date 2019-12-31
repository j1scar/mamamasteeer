<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 페이지</title>
<link href="resources/css/join.css" type="text/css" rel="stylesheet">
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
<jsp:include page="../board/header.jsp" />
<script src="resources/js/jquery-3.4.1.js"></script>
<script>

$(document).ready(function(){
	 var checkmail=true;
	 $('form').submit(function(){
		
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
    
    var pandan = "${memberinfo.gender}";
    if(pandan=="남"){
    	$("input:radio").eq(0).attr("checked","checked");
    }else{
    	$("input:radio").eq(1).attr("checked","checked");
    }
    $(".cancelbtn").click(function(){
    	history.back();
    })
   }); //ready end
</script>
</head>
<body>
<form name="updateform" action="updateProcess.net" method="post">
  <h1>정보 수정 페이지</h1>
  <hr>
  <b>아이디</b>
  <input type="text" name="id" readOnly
       value=${id }>
  <span id="message"></span>
  <b>비밀번호</b>
  <input type="password" name="password" 
       required value=${memberinfo.password }>
  <b>이름</b>
  <input type="text" name="name" required 
  maxLength="15" value=${memberinfo.name }>
  <b>나이</b>
  <input type="text" name="age" required 
  maxlength="2" value=${memberinfo.age }>
  <b>성별</b>
  <div>
  <input type="radio" name="gender" value="남"><span>남자</span>
  <input type="radio" name="gender" value="여"><span>여자</span>
  </div>
  <b>이메일 주소</b>
  <input type="text" name="email" required value=${memberinfo.email }><span id="email_message"></span>
  <div class="clearfix">
  <button type="submit" class="submitbtn">수정</button>
  <button type="reset" class="cancelbtn">취소</button>
  </div>
</form>
</body>
</html>