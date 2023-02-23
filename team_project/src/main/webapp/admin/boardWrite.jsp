<%@page import="member.UserDTO"%>
<%@page import="member.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>섬집 관리자 페이지</title>
</head>
<body>

<!-- 헤더파일들어가는 곳 -->
<jsp:include page="../inc/ad_header.jsp" />
<!-- Backgrounds -->
	<div class="container">
    	<div class="row">
        	<div class="col-lg-2">
				
			</div>
			
			<div class="col-lg-10">
				<div class="content-main">		
			
					<%
					String id=(String)session.getAttribute("id");
					
					if(id==null){
						response.sendRedirect("AdminLogin.ad");
					}else if(id.equals("admin")){
					
					}else{
						%>
					<script type="text/javascript">
					alert("글쓰기 권한이 없습니다.");
					history.back();
					</script>
					<%
					}
					%>
					
					<h2>admin/boardWrite.jsp</h2>
					
					
					<!-- 부트스트랩 사용하기 위해 -->
					
					
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
					
					<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
					
					
					<!-- 게시판 글쓰기 부분 -->
					
						<form action="AdminBoardWritePro.ad" method="post" >
						
							<div class="container">
							
							<h2>게시판 글쓰기</h2>          
							
								<table class="table table-hover">
								
									<tbody>
									
										<tr>
										
											<td><input type="text" class="form-control" placeholder="유저번호" name="id" value=<%=id %> maxlength="40" readonly></td>
										
										</tr>
										
										<tr>
										
											<td><input type="text" class="form-control" placeholder="글 제목" name="btitle" maxlength="40"></td>
										
										</tr>
										
										<tr>
										
										<td><textarea type="text" class="form-control" placeholder="글 내용을 작성하세요" name="bcontent" maxlength="1024" style="height: 400px;"></textarea></td>
										
										</tr>
									
									</tbody>
								
								</table>
							
							<input type="submit" class="btn btn-primary pull-right" value="글쓰기">
							
							</div>
						</form>


				</div>
			</div>
		</div>
	</div>
</body>
</html>