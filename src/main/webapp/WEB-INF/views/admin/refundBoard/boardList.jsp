<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<title>Insert title here</title>
<script>
$(function(){
	console.log("${refund}")
	console.log("${pi}")
});
	function refundUpdate(refundNo){
		let url = "refunUpdate"
			$.ajax({
				url : url,
				type : "POST",
				dataType : "text",
				data : {
					refundNo : refundNo
				},
				success : function(data) {
					if(data == "success"){
						location.reload();
					}
				}
			});
	}

</script>
</head>
<body>
	<jsp:include page="../../managerHeader.jsp"></jsp:include>
	<div class="container">
		<h4 class="fw-bold py-3 mb-4">
			<span class="text-muted fw-light">관리자 /</span> 환불상품
		</h4>

		<div class="card">
			<h5 class="card-header">환불상품</h5>
			<table class="table">
				<thead>
					<tr>
						<th>게시글번호</th>
						<th>주문번호</th>
						<th>구매자</th>
						<th>환불신청일</th>
						<th>환불사유</th>
						<th>환불처리날짜</th>
						<th>환불상태</th>
						<th>비고</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="Refund" items="${refund }" varStatus="status">
						<tr>
							<td><c:out value="${Refund.refundNo}" /></td>
							<td><c:out value="${Refund.orderNo}" /></td>
							<td><c:out value="${Refund.userId}" /></td>
							<td><fmt:formatDate value="${Refund.refundDate}"
									pattern="yyyy-MM-dd" /></td>
							<td><c:choose>
									<c:when test="${Refund.refundReason == 1}">배송지연</c:when>
									<c:when test="${Refund.refundReason == 2}">단순변심</c:when>
									<c:when test="${Refund.refundReason == 3}">상품파손</c:when>
								</c:choose></td>
							<td><fmt:formatDate value="${Refund.refundProcessdate}"
									pattern="yyyy-MM-dd" /></td>

							<c:choose>
								<c:when test="${Refund.isRefund !='yes' }">
									<td>환불대기</td>
									<td><button type="button" class="btn btn-secondary"
											id="refunUpdate" onclick="refundUpdate(${Refund.refundNo})">환불</button></td>
								</c:when>
								<c:otherwise>
									<td>환불완료</td>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<nav class="blog-pagination justify-content-center d-flex">
			<ul class="pagination">
				<c:if test="${param.pageNo>1 }">
				
							<li class="page-item">
							<a class="page-link"
								href="/admin/refundBoard/boardList?pageNo=1"><<</a></li>
							<li class="page-item">
							<a class="page-link"
								href="/admin/refundBoard/boardList?pageNo=${param.pageNo-1}"><</a></li>
				</c:if>

				<c:forEach var="i" begin="${pi.startNoOfCurPagingBlock}"
					end="${pi.endNoOfCurPagingBlock }" step="1">
					
						
							<c:choose>
								<c:when test="${param.pageNo== i}">
									<li class="page-item"><a class="page-link"
										href="/admin/refundBoard/boardList?pageNo=${i}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item "><a class="page-link"
										href="/admin/refundBoard/boardList?pageNo=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
	
				</c:forEach>
				<c:if test="${param.pageNo < pi.totalPage }">
				
							<li class="page-item">
							<a class="page-link"
								href="/admin/refundBoard/boardList?pageNo=${param.pageNo+1}">></a></li>
								<li class="page-item">
							<a class="page-link"
								href="/admin/refundBoard/boardList?pageNo=${pi.totalPage}">>></a></li>
	
				</c:if>
			</ul>
		 </nav>
	</div>

	<jsp:include page="../../managerFooter.jsp"></jsp:include>
</body>
</html>