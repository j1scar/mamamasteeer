<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<jsp:include page="header.jsp"/>
<script src="resources/js/writeform.js" charset="utf-8"></script>
<style>
  tr.center-block {text-align:center;}
  h1 {font-size:1.5rem; text-align:center; color:#1a92b9;}
  .container {width:60%;}
  label {font-weight:bold;}
  #upfile {display:none;}
  img {width:20px;}
</style>
</head>
<body>
<div class="container">
 <form action="BoardReplyAction.bo" method="post"
       name="boardform">
   <%-- 답변을 추가하기 위해서는 답변의 원문글에 대한
        BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ 정보 필요 --%>
   <input type="hidden" name="BOARD_RE_REF"
          value="${boarddata.BOARD_RE_REF }">
   <input type="hidden" name="BOARD_RE_LEV"
          value="${boarddata.BOARD_RE_LEV }">
   <input type="hidden" name="BOARD_RE_SEQ"
          value="${boarddata.BOARD_RE_SEQ }">              
  <h1>MVC 게시판 - reply 페이지</h1>
  <div class="form-group">
    <label for="board_name">글쓴이</label>
     <input name="BOARD_NAME" id="board_name" value="${id }"
            readOnly type="text" size="10" maxlength="30"
            class="form-control" placeholder="Enter board_name">
  </div>
  <div class="form-group">
    <label for="board_subject">제목</label>
     <input name="BOARD_SUBJECT" id="board_subject" 
            type="text" size="10" maxlength="100"
            class="form-control" value="Re: ${boarddata.BOARD_SUBJECT}"/>
  </div> 
  <div class="form-group">
    <label for="board_content">내용</label>
    <textarea name="BOARD_CONTENT" id="board_content" 
            cols="67" rows="10" class="form-control" ></textarea>
  </div>   
  <div class="form-group">
    <label for="board_pass">비밀번호</label>
    <input name="BOARD_PASS" id="board_pass" 
            type="password" class="form-control" placeholder="Enter board_pass">
  </div> 
   <div class="form-group">
    <input type="submit" class="btn btn-primary" value="등록">
    <input type="button" class="btn btn-danger" 
           value="취소" onClick="history.go(-1)">
   </div>  
 </form>
</div>
</body>
</html>