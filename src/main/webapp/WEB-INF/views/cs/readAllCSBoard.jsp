<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
$(function(){
	
});

function showContent(obj){
	let no = $(obj).attr("id");
	location.href="/notice/viewContent?no="+no;
}

</script>
<style>
.container{
margin-top:20px;
margin-bottom: 20px;
}

</style>
</head>
<body>
<jsp:include page="../userHeader.jsp"></jsp:include>
<div class="container mt-3">
	<h1 style="margin:20px; margin-bottom:20px;">고객센터</h1>
  <table class="table table-hover">
    <thead style="background-color:#fafaff; color:#777">
      <tr>
    		<th>No</th>
    		<th>말머리</th>
        	<th>제목</th>
        	<th>작성자</th>
         	<th>작성일자</th>  	
      </tr>
    </thead>
    <tbody>
    <c:forEach var="notice" items="${notice }">
      <tr id="${notice.no}" onclick="showContent(this);">
       
        <td>${notice.title}</td>
        <td>${notice.writer}</td>
         <td>${notice.writedDate}</td>
        <td>${notice.viewCount}</td>
        <td>${notice.reply}</td>
      </tr>
     </c:forEach>
    </tbody>
  </table>
  <button class="button button-header" onclick="" style="" >글 작성</button>
</div>

	<jsp:include page="../userFooter.jsp"></jsp:include>
</body>
</html>