<%@page import="products.ProductDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="products.ProductDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.class{

}
</style>
</head>
<body>
    <div class="breadcrumb-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="breadcrumb-text">
                        <h2>검색 결과</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
 
<%
request.setCharacterEncoding("utf-8");
//ProductDAO 객체생성
ProductDAO dao=new ProductDAO();
//한페이지에 보여줄 글개수 설정
int pageSize=9;
//현 페이지 번호 가져오기 => 페이지번호가 없으면 1페이지 설정
//http://localhost:8080/webProject/board/list.jsp
//http://localhost:8080/webProject/board/list.jsp?pageNum=2
String pageNum=request.getParameter("pageNum");
if(pageNum==null){
	//=> 페이지번호가 없으면 1페이지 설정
	pageNum="1";
}
//pageNum => 숫자변경
int currentPage=Integer.parseInt(pageNum);
//시작하는 행번호 구하기
//pageNum(currentPage)	   pageSize	=> startRow
//	1						10		=> 	(1-1)*10+1 => 0*10+1 =>  0+1 -> 1  ~10
//	2						10		=> 	(2-1)*10+1 => 1*10+1 => 10+1 -> 11 ~20
//	3						10		=> 	(3-1)*10+1 => 2*10+1 => 20+1 -> 21 ~30
int startRow=(currentPage-1)*pageSize+1;
//끝나는 행번호 구하기
//startRow	pageSize	=>	endRow
//		1		10		=>	1+10-1	=>	10
//		11		10		=>	11+10-1	=>	20
//		21		10		=>	21+10-1	=>	30
int endRow = startRow+pageSize-1;





String indate=request.getParameter("indate");
String outdate=request.getParameter("outdate");
int guest=Integer.parseInt(request.getParameter("guest"));
String region=request.getParameter("region");
String indated=indate.substring(0, 10);
String outdated=outdate.substring(0, 10);
String indatet=indate.substring(11, 13);
String outdatet=outdate.substring(11, 13);
System.out.println(indated);
System.out.println(outdated);
System.out.println(indatet);
System.out.println(outdatet);
System.out.println(guest);
System.out.println(region);

ArrayList<ProductDTO> ProductList=dao.getSearchProductList(indated, outdated, indatet, outdatet, guest, region, startRow, pageSize);

	for(int i=0;i<ProductList.size();i++){
 		//배열 한칸에 내용 가져오기 
 		ProductDTO dto=ProductList.get(i);
%>
   <section class="rooms-section spad">
        <div class="container">
            <div class="row">

                <div class="col-lg-4 col-md-6">
                    <div class="room-item">
						<!--  펜션 사진들어 가는 곳 -->
						<!-- 사진없으면 샘플사진 뜨게 설정 -->
						<%if(dto.getPpic1()==null){%>
                        <img src="../img/room/room-6.jpg" width="370px" height="240px"> 
                        <%}else{%>
                        	 <img src="<%=dto.getPpic1()%>" width="370px" height="240px"> 
                      <% }%>
                        <div class="ri-text">
                            <h4><%=dto.getPname() %></h4>
                            <h3>
                            
                            <script type="text/javascript">
                            var num = <%=dto.getPprice()%>;
                            document.write(num.toLocaleString()+"원");
                            </script>
                            <span>/1박</span></h3>
                            <table>
                                <tbody>
                                    <tr>
                                        <td class="r-o">주소 :</td>
                                        <td><%=dto.getPaddress2() %></td>
                                    </tr>
                                    <tr>
                                        <td class="r-o">체크인 :</td>
                                        <td><%=dto.getCheckin() %>시</td>
                                    </tr>
                                    <tr>
                                        <td class="r-o">체크아웃:</td>
                                        <td><%=dto.getCheckout() %>시</td>
                                    </tr>
                                   
                                </tbody>
                            </table>
					<!-- 	펜션 상세 정보 페이지로 이동   -->
                            <a href="ProductContent.pr?pno=<%=dto.getPno()%>" class="primary-btn">More Details</a>
                        </div>
                    </div>
                </div>
            </div>
         </div>    
     </section>         
<%
}
	
// 한 화면에 보여줄 페이지 개수 설정
int pageBlock=10;
// 시작하는 페이지 번호 구하기
// currentPage			pageBlock	=> startPage
//		1~  10(0~9)			10		=>	(-1)/10*10+1 => 0*10+1 => 0+1 => 1
//		11~ 20(10~19)		10		=>	(-1)/10*10+1 => 1*10+1 => 10+1 => 11
//		21~ 30(20~29)		10		=>	(-1)/10*10+1 => 2*10+1 => 20+1 => 21

int startPage=(currentPage-1)/pageBlock*pageBlock+1;
// 끝나는 페이지 번호 구하기
// startPage pageBlock => endPage
//	1			10	   =>	1+10-1  => 10
//	11			10	   =>	11+10-1 => 20
//	21			10	   =>	21+10-1 => 30
int endPage=startPage+pageBlock-1;
//전체글 개수 select count(*) from board
// int 리턴할형 getBoardCount() 메서드 정의
// getBoardCount() 메서드 호출
int count = dao.getSearchProductCount(indated, outdated, indatet, outdatet, guest, region);
//끝나는 페이지(endPage) = 10  <=  전체페이지(pageCount) = 2
//전체페이지(pageCount) 구하기
//=> 전체글의 개수 13 /글개수 10 => 1 페이지 +(0.3 글 남아있으면 1페이지 추가)
//
int pageCount=count/pageSize+(count%pageSize==0?0:1);
if(endPage > pageCount){
	endPage = pageCount;
}
//10페이지 이전
if(startPage > pageBlock){
	%>
	<a href="result.jsp?pageNum=<%=startPage-pageBlock%>">Prev</a>
	<%
}

for(int i=startPage;i<=endPage;i++){
	%>
	<a href="result.jsp?pageNum=<%=i%>"><%=i%></a>
	<%
}

//10페이지 다음
if(endPage < pageCount){
	%>
	<a href="result.jsp?pageNum=<%=startPage+pageBlock%>">Next</a>
	<%
}
%>


</body>
</html>