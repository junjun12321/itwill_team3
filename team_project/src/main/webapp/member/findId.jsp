<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        /* 전체 레이아웃 스타일 */
        body {
            margin: 0;
            padding: 0;
        }
        #wrap {
            width: 100%;
            max-width: 800px;
            margin: 60px auto;
            padding: 20px;
        }
        .form-container {
            background-color: #fff;
            margin: 20px auto;
            max-width: 600px;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin: 0;
            font-size: 24px;
        }

        /* 폼 스타일 */
        form {
            width: 100%;
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-size: 16px;
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            font-size: 16px;
            border: 1px solid #ccc;
        }
        input[type="button"] {
            background-color: #99b19c;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-right: 10px;
            font-size: 16px;
            cursor: pointer;
        }
        input[type="button"]:hover {
            background-color: #113000;
        }
    </style>
    
<script type="text/javascript">
function id_search() { 
	 	var frm = document.idfindscreen;

	 	if (frm.uname.value.length < 1) {
		  alert("이름을 입력해주세요");
		  return;
		 }

		 if (frm.email.value.length < 1) {
			  alert("이메일을 입력해주세요");
			  return;
		 }

	 frm.method = "post";
	 frm.action = "MemberFindIdPro.me"; //넘어간화면
	 frm.submit();  
	 }
</script>
</head>
<body>
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/header.jsp" />


<div id="wrap">

<form name="idfindscreen" method = "POST" >

		<h2>아이디찾기</h2>
		<div class="form-container">
		<section class = "form-search">
			<div class = "find-name">
				<label>이름</label>
				<input type="text" name="uname" class = "btn-name" placeholder = "등록한 이름">
			<br>
			</div>
			<div class = "find-email">
				<label>이메일</label>
				<input type="text" name="email" class = "btn-email" placeholder = "등록한 이메일">
			</div>
			<br>
	</section>
	<div class ="btnSearch">
		<input type="button" name="enter" value="찾기"  onClick="id_search()">
		<input type="button" name="cancle" value="취소" onClick="history.back()">
		</div>
 	</div>
 </form>
</div>


 <jsp:include page="../inc/footer.jsp" />
</body>
</html>