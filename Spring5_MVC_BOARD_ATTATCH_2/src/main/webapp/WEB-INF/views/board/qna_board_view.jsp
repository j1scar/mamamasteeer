<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
   <head>
      <jsp:include page = "header.jsp"/>      
      <script src = "resources/js/jquery-3.0.0.js"></script>
     
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
      <style>
         #myModal {
            display : none;
         }
         
         #count {
            position : relative;
            top : -10px;
            left : -10px;
            background : orange;
            color : white;
            border-radius : 30%;
         }
      </style>
      <title>MVC 게시판 - view</title>
   </head>
   <body>
      <input type = "hidden" id = "loginid" value = "${id}" name = "loginid">
      <div class = "container">
         <table class = "table table-striped">
            <tr>
               <th colspan = "2">MVC 게시판 - view 페이지</th>
            </tr>
            <tr>
               <td><div>글쓴이</div></td>
               <td><div id = "BOARD_NAME">${boarddata.BOARD_NAME}</div></td>
            </tr>
            <tr>
               <td><div>제목</div></td>
               <td><div>${boarddata.BOARD_SUBJECT}</div></td>
            </tr>
            <tr>
               <td><div>내용</div></td>
               <td>
                  <textarea class = "form-control" rows="8" cols="80" readOnly style = "width : 102%">${boarddata.BOARD_CONTENT}
                  </textarea>
               </td>
            </tr>
            <c:if test = "${!empty boarddata.BOARD_FILE}">
            <tr>
               <td>
                  <div>첨부파일</div>
               </td>
               <td>
                  <img src = "resources/image/down.png" width = "10px">
                  <a href = "BoardFileDown.bo?filename=${boarddata.BOARD_FILE}&original=${boarddata.BOARD_ORIGINAL}" >${boarddata.BOARD_ORIGINAL}</a>
               </td>
            </tr>
            </c:if>
            <tr>
               <td colspan = "2" class = "center">
                  <button class = "btn btn-primary start">댓글</button>
                  <!-- BoardDetailAction.java에서 41번 보면 count가 있다. -->
                  <!-- count는 댓글의 개수를 의미한다. -->
                  <span id = "count">${count}</span>
                  <c:if test = "${boarddata.BOARD_NAME == id || id == 'admin'}">
                     <a href = "./BoardModifyView.bo?num=${boarddata.BOARD_NUM}">
                        <button class = "btn btn-info">수정</button>
                     </a>
                     <a href = "#">
                        <button class = "btn btn-danger" data-toggle = "modal" data-target = "#myModal">삭제</button>
                     </a>
                  </c:if>
                  <a href = "BoardList.bo">
                     <button type = "button" class = "btn btn-dark">목록</button>
                  </a>
                  <a href = "BoardReplyView.bo?num=${boarddata.BOARD_NUM}">
                     <button type = "button" class = "btn btn-success">답변</button>
                  </a>
               </td>
            </tr>
         </table>
         <%-- 게시판 수정 end --%>
         
         <%-- 모달 시작 --%>
         <!-- The Modal -->
         <div class="modal" id="myModal">
            <div class="modal-dialog">
               <div class="modal-content">
                  <!-- Modal body -->
                  <div class="modal-body">
                     <form name = "deleteForm" action = "BoardDeleteAction.bo" method = "post">
                        <input type = "hidden" name = "num" value = "${param.num}">
                        <%--
                           주소를 보면 num을 파라미터로 넘기고 있다.
                           이 값을 가져와서 ${param.num}을 사용
                           또는 ${boarddata.BOARD_NUM}
                         --%>
                        <input type = "hidden" name = "num" value = "${param.num}" id = "BOARD_RE_REF">
                        <input type = "hidden" name = "BOARD_FILE" value = "${boarddata.BOARD_FILE}"> 
                        <div class = "form-group">
                           <label for = "pwd">비밀번호</label> 
                           <input type = "password" class = "form-control" placeholder = "Enter password"
                                 name = "BOARD_PASS" id = "board_pass">
                        </div>
                        <button type = "submit" class = "btn btn-primary">Submit</button>
                        <button type = "button" class = "btn btn-danger" data-dismiss = "modal">Close</button>
                     </form>
                  </div>
               </div>
            </div>
         </div>
         
         <div id = "comment">
            <button class = "btn btn-info float-left">총 50자까지 가능합니다.</button>
            <button id = "write" class = "btn btn-info float-right">등록</button>
            <textarea rows = 3 class = "form-control" id = "content" maxLength = "50"></textarea>
            <table class = "table table_striped">
               <thead>
                  <tr>
                     <td>아이디</td>
                     <td>내용</td>
                     <td>날짜</td>
                  </tr>   
               </thead>
               <tbody>
               <!-- 여기에 ajax 결과에 따른 내용 추가 -->
               </tbody>   
            </table>
            <div id = "message"></div>
         </div>
      </div>
       <script src = "resources/js/view3.js"></script>
   </body>
</html>