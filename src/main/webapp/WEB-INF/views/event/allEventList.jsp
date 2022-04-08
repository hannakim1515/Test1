<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="<%=request.getContextPath() %>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 이벤트 리스트</title>
</head>
<style>
#listTable thead {
	background-color: #dcdcdc;
}
</style>
<body>
	<jsp:include page="../userHeader.jsp"></jsp:include>
	<div class="container">
		<h2 style="margin: 30px; ">전체 이벤트 리스트</h2>
		<table class="table table-hover">
			<thead>
				<tr>
					<th colspan="2">글번호</th>
					<th>제목</th>
					<th>작성일자</th>
					<th>이벤트 기간</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eventBoardList }" var="eventBoardList">
					<tr onclick="location.href='${contextPath}/event/detailEvent?boardno=${eventBoardList.boardNo }';"> <!-- 클릭시 글넘버를 GET으로 넘기며 이동-->
						<td>${eventBoardList.boardNo }</td>
						<td><img src="${eventBoardList.mainImg }" /></td>
						<td>${eventBoardList.title }</td>
						<td>${eventBoardList.date }</td>
						<td>${eventBoardList.eventStart } ~ ${eventBoardList.eventEnd } </td>
						<td>${eventBoardList.readcount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<!-- 이하 if문으로 감싸 관리자만 해당 버튼이 보이게 만드는 처리를 할 것 -->
			<!-- <c:if test="${sessionScope.loginUser == 'admin' }"></c:if> -->
			<button class="button button-header" onclick="location.href='${contextPath}/event/showinsertEventPage';" style="margin: 10px;">글 작성</button>
		</div>
	</div>
	
	<jsp:include page="../userFooter.jsp"></jsp:include>
</body>
</html>