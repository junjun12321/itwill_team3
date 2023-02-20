<%@page import="board.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin/boardList.jsp</title>
</head>
<body>
<!-- 헤더파일들어가는 곳 -->
<jsp:include page="../inc/header.jsp" />

<!-- 한페이지에 보여줄 글 개수 -->
<%
// BoardDAO dao= new BoardDAO();

// // 한페이지에 보여줄 글 개수
// int pageSize=10;

// String pageNum=request.getParameter("pageNum");
// if(pageNum==null){
// 	 pageNum="1";
// 	}

// int currentPage=Integer.parseInt(pageNum);

// int startRow=(currentPage-1)*pageSize+1;

// int endRow= startRow+pageSize-1;

// ArrayList<BoardDTO> boardList=dao.getBoardList(startRow, pageSize);

ArrayList<BoardDTO> boardList = (ArrayList<BoardDTO>)request.getAttribute("boardList");
int currentPage =(Integer)request.getAttribute("currentPage");
int pageBlock =(Integer)request.getAttribute("pageBlock");
int startPage =(Integer)request.getAttribute("startPage");
int endPage =(Integer)request.getAttribute("endPage");
int pageCount =(Integer)request.getAttribute("pageCount");


%>

<!-- 글쓰기 버튼 부분 -->
<h3>admin/boardList.jsp</h3>

<table border="0" width="50%">
<tbody><tr align="right">
<td>
<button type="button" onclick="location.href='AdminBoardWriteForm.ad'">
글쓰기</button></td>
</tr>
</tbody></table>

<!-- 공지사항 리스트 부분 -->
<table border="1" width="50%">
<tbody>
<tr>
<td width="5%" align="center">번호</td><td width="5%" align="center">유저번호</td>
<td width="15%" align="center">제목</td><td width="20%" align="center">작성날짜</td>
<td width="5%" align="center">조회수</td>
</tr>

<tr><td colspan="5" align="center"></td></tr>

<%
for(int i=0; i<boardList.size(); i++){
	BoardDTO dto= boardList.get(i);
%>
<tr><td align="center"><%=dto.getBno() %></td>
<td align="center"><%=dto.getNo() %></td>
    <td align="center">
    <a href="AdminBoardContent.ad?bno=<%=dto.getBno() %>">
    <%=dto.getBtitle() %>
    </a></td>
<td align="center"><%=dto.getBdate() %></td>
<td align="center"><%=dto.getBcount() %></td></tr>

<% 
}
%>

<!-- 게시판 글 검색부분 -->
<form action="AdminBoardList.ad"  method="get">
<table border="1" width="50%"> 
<tbody><tr>
<td align="center">
<select name="serch1">
<option value="btitle">제목</option>
<option value="bcontent">내용</option>
</select>
<input type="text" name="search2">
<input type="submit" value="검색">
</td>
</tr>
</tbody></table>
</form>

<!-- 페이징 처리 -->
<% 
// // 한 화면에 보여줄 페이지개수
// int pageBlock=10;

// // 시작하는 페이지 번호
// int startPage=(currentPage-1)/pageBlock*pageBlock+1;

// // 끝나는 페이지 번호
// int endPage=startPage+pageBlock-1;

// // 전체글 개수를 정의한 getBoardCount()메서드 호출
// int count= dao.getBoardCount();

// // 전체페이지
// int pageCount=count/pageSize+(count%pageSize==0?0:1);
// if(endPage > pageCount){
// 	endPage = pageCount;
// }


if(startPage > pageBlock){
	%>
<a href="AdminBoardList.ad?pageNum=<%=startPage-pageBlock%>">[10페이지 이전]</a>
	<%
}

for(int i=startPage;i<=endPage;i++){
	%>
	<a href="AdminBoardList.ad?pageNum=<%=i%>"><%=i %></a> 
	<%
}

if(endPage < pageCount){
	%>
<a href="BoardList.bo?pageNum=<%=startPage+pageBlock%>">[10페이지 다음]</a>
	<%
}
%>

<!-- 푸터 들어가는 곳 -->
<%-- <jsp:include page="../inc/footer.jsp" /> --%>

</body>
</html>