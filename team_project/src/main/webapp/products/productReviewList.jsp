<%@page import="member.ReviewDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="member.ReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="https://hangeul.pstatic.net/hangeul_static/css/nanum-square-neo.css" rel="stylesheet">
<head>
<meta charset="UTF-8">
<title>reviewList.JSP</title>
</head>
<style>
  	article {
  		font-family: 'NanumSquareNeo';
 		max-width: 750px;
 		margin: 0 auto;
 		height: 100%;
 		padding: 20px;
 		box-sizing: border-box;
 	}
 	
 	ul {
 		margin: 0px 10px 0px 10px;
		padding: 0px 10px 0px 10px;
 	}
 	
	ul li {
		list-style: none;
	}
	ul h3 {
		font-family: 'NanumSquareNeo';
		margin: 0px 20px 10px 20px;
		display: inline-block;
	}
	ul #img {
 		margin: 0px 20px 10px 0px;
 		color : black;
        display: inline-block;
	}
</style>
<body>
<%
	// 	해당 펜션 리뷰 리스트 출력
	ArrayList<ReviewDTO> reviewList = (ArrayList<ReviewDTO>)request.getAttribute("reviewList");
	
	int currentPage = (Integer)request.getAttribute("currentPage");
	int startPage = (Integer)request.getAttribute("startPage");
	int pageBlock = (Integer)request.getAttribute("pageBlock");
	int endPage = (Integer)request.getAttribute("endPage");
	int pageCount = (Integer)request.getAttribute("pageCount");
%>
<jsp:include page="../inc/header.jsp" />

<article>
<h2>Reviews</h2>
<%
	for(int i = 0; i < reviewList.size(); i++) {
		ReviewDTO rdto = reviewList.get(i);
%>
	<!-- 후기 리스트  -->
	<ul>
		<img src="img/review/quote-left.png"> <h3><%=rdto.getRtitle() %></h3> <img src="img/review/get-quote.png">
		<li id="stars"> 별점 <%=rdto.getRstar() %>
		<%
			String star = rdto.getRstar();
			int getStar = Integer.parseInt(star);
			for(int j = 1; j <= getStar; j++) {
				%>
				<img src="img/review/yellowStar.png" width="25" height="25">
				<%
			}
		%>
		</li>
		<li id="contents"><%=rdto.getRcontent() %></li>
		
		<!-- 사진 1이 없을 때 안보이게 하기 -->
		<% if(rdto.getRpic1() == null) { %>
			<input type="hidden" name="rpic1" value="<%=rdto.getRpic1() %>">
		<% } else { %>
			<li id="img"><img src="upload/<%=rdto.getRpic1()%>" width="200" height="150"></li>
		<% } %>
		
		<!-- 사진 2가 없을 때 안보이게 하기 -->
		<% if(rdto.getRpic2() == null) { %>
			<input type="hidden" name="rpic2" value="<%=rdto.getRpic2() %>">
		<% } else { %>
			<li id="img"><img src="upload/<%=rdto.getRpic2()%>" width="200" height="150"></li>
		<% } %>
		
		<!-- 사진 3이 없을 때 안보이게 하기 -->
		<% if(rdto.getRpic3() == null) { %>
			<input type="hidden" name="rpic3" value="<%=rdto.getRpic3() %>">
		<% } else { %>
			<li id="img"><img src="upload/<%=rdto.getRpic3()%>" width="200" height="150"></li>
		<% } %>
	</ul>
	<% } %>
	
<%
	if(startPage > pageBlock) {
		%>
		<a href="ProductReviewList.pr?pageNum=<%=startPage-pageBlock%>">[5 페이지 이전]</a>
		<%
	}

	if(currentPage > 1) {
		%>
		<a href="ProductReviewList.pr?pageNum=<%=currentPage-1%>">[1 페이지 이전]</a>
		<%
	}
	
	for(int i = startPage; i <= endPage; i++) {
		%>
		<a href="ProductReviewList.pr?pageNum=<%=i%>"><%=i %></a>
		<%
	}
	
	if(currentPage < pageCount) {
		%>
		<a href="ProductReviewList.pr?pageNum=<%=currentPage+1%>">[1 페이지 다음]</a>
		<%
	}
	
	if(endPage < pageCount) {
		%>
		<a href="ProductReviewList.pr?pageNum=<%=startPage+pageBlock%>">[5 페이지 다음]</a>
		<%
	}
%>
</article>

<footer>
	<jsp:include page="../inc/footer.jsp" />
</footer>
	
</body>
</html>