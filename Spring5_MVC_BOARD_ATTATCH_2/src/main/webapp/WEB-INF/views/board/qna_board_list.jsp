<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
           <jsp:include page="header.jsp"/>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style>
 .center-block {display:flex;
                justify-content:center; /* 가운데 정렬 */}
 .rows {padding:5px;width:80px;text-align:center;float:right;}

</style>
<script>
 $(function(){
	 $("button").click(function(){
		 location.href="BoardWrite.bo";
	 })
 })
</script>

<script src="resources/js/list.js"></script>
<title>MVC 게시판</title>
</head>
<body>
<div class="container">
 <%-- 게시글이 있는 경우 --%>
 <c:if test="${listcount>0 }">
  <div class="rows">
   <span>줄보기</span>
   <select class="form-control" id="viewcount">
    <option value="1">1</option>
    <option value="3">3</option>
    <option value="5">5</option>
    <option value="7">7</option>
    <option value="10" selected>10</option>
   </select>
  </div>
   <table class="table table-striped">
   <thead>
     <tr>
       <th colspan="3">MVC 게시판 - list</th>
       <th colspan="2">
          <font size=3>글 개수: ${listcount }</font>
       </th>
     </tr>
     <tr>
       <th width="8%"><div>번호</div></th> <!-- 글번호 아님 -->
       <th width="50%"><div>제목</div></th>
       <th width="14%"><div>글쓴이</div></th>
       <th width="17%"><div>날짜</div></th>
       <th width="11%"><div>조회수</div></th>
     </tr>
    </thead>
    <tbody>
    <c:set var="num" value="${listcount-(page-1)*10 }"/>
    <c:forEach var="b" items="${boardlist }">
      <tr>
        <td>
        <c:out value="${num }"/> <%-- num 출력 --%>
        <c:set var="num" value="${num-1 }"/> <%--num=num-1 --%>
        </td>
        <td>
           <div>
             <%-- 답변글 제목 앞에 여백 처리 부분
                  BOARD_RE_LEV, BOARD_RE_LEV, BOARD_NUM,
                  BOARD_SUBJECT, BOARD_NAME, BOARD_DATE,
                  BOARD_READCOUNT : property 이름 --%>
             <c:if test="${b.BOARD_RE_LEV != 0 }"> <!-- 답글인 경우 -->
               <c:forEach var="a" begin="0"
                          end="${b.BOARD_RE_LEV*2 }" step="1">
               &nbsp;
               </c:forEach>
             </c:if>
             <c:if test="${b.BOARD_RE_LEV ==0 }"> <!-- 원문인 경우 -->
              &nbsp;
             </c:if>
             
             <a href="BoardDetailAction.bo?num=${b.BOARD_NUM }">
                ${b.BOARD_SUBJECT}
             </a>
           </div>
        </td>
        <td>
          <div>${b.BOARD_NAME }</div>
        </td>
        <td>
          <div>${b.BOARD_DATE }</div>
        </td>
        <td>
          <div>${b.BOARD_READCOUNT }</div>
        </td>
      </tr>
    </c:forEach>
   </table>
   </tbody>
   <div class="center-block">
     <div class="row">
       <div class="col">
          <ul class="pagination">
          <c:if test="${page <= 1 }">
            <li class="page-item">
            <a class="page-link" href="#">이전&nbsp;</a>
            </li>
          </c:if>
          <c:if test="${page > 1 }">
            <li class="page-item">
            <a href="BoardList.bo?page=${page-1 }"
                class="page-link">이전</a>&nbsp;
            </li>
          </c:if>
          <c:forEach var="a" begin="${startpage }" end="${endpage }">
            <c:if test="${a == page }">
              <li class="page-item">
              <a class="page-link" href="#">${a }</a>
              </li>
            </c:if>
            <c:if test="${a != page }">
              <li class="page-item">
              <a href="BoardList.bo?page=${a }"
                 class="page-link">${a }</a>
              </li>
            </c:if>
          </c:forEach>
          <c:if test="${page >= maxpage }">
            <li class="page-item">
            <a class="page-link" href="#">&nbsp;다음</a>
            </li>
          </c:if>
          <c:if test="${page < maxpage }">
            <li class="page-item">
            <a href="BoardList.bo?page=${page+1 }"
                class="page-link">&nbsp;다음</a>
            </li>
          </c:if>
          </ul>
       </div>
     </div>
   </div>
 </c:if>
 <%-- 게시글이 없는 경우 --%>
 <c:if test="${listcount==0 }">
     <font size=5>등록된 글이 없습니다.</font>
 </c:if><br>
 <button type="button"
         class="btn btn-info float-right">글쓰기</button>
</div>
</body>
</html>