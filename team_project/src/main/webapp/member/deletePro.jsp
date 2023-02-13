<%@page import="java.sql.Timestamp"%>
<%@page import="member.UserDTO"%>
<%@page import="member.UserDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/deletePro.jsp</title>
</head>
<body>
<h1>member/deletePro.jsp</h1>
<%
//request 태그이름에 해당하는 값을 가져오기s
String id=request.getParameter("id");
String pass=request.getParameter("pass");

UserDAO dao=new UserDAO();
UserDTO dto = dao.userCheck(id,pass);

if(dto!=null){	
	// id,pass 일치
	dao.deleteUser(id); 
	// => 세션값 초기화
	session.invalidate();
	response.sendRedirect("../main/main.jsp"); 
}else{
	// id,pass 틀림
%>
    <script type="text/javascript">
    		alert("아이디 또는 비밀번호를 잘 못 입력했습니다");
			history.back();
    </script>
    <%
}
%>
</body>
</html>