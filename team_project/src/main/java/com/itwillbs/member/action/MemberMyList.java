package com.itwillbs.member.action;

import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.ReviewDAO;
import member.UserDAO;
import member.UserDTO;
import products.AppointmentDAO;
import products.AppointmentDTO;
import products.ProductDAO;
import products.ProductDTO;
import products.SalesDAO;
import products.SalesDTO;

public class MemberMyList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception, ParseException {
		
		System.out.println("MemberMyList execute()");
		
		// 아이디 세션 값
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");

		// 유저정보 no 값
		UserDAO udao = new UserDAO();
		UserDTO udto = udao.getUser(id);
		int no = udto.getNo();
		
		// 판매정보 => 입실일 / 퇴실일
		SalesDAO salesdao = new SalesDAO();
		SalesDTO salesdto = salesdao.getSales(no);
		
//		try {
//			
//			Date Indate = Indate = salesdto.getIndate(); 
//			LocalDate appointIndate = LocalDate.ofInstant(Indate.toInstant(), ZoneId.systemDefault()); // 체크인 날짜
//			Date Outdate = Outdate = salesdto.getOutdate(); 
//			LocalDate appointOutdate = LocalDate.ofInstant(Outdate.toInstant(), ZoneId.systemDefault()); // 체크아웃 날짜
//			LocalDateTime now = LocalDateTime.now(); 
//			LocalDate today = now.toLocalDate();	// 오늘 날짜
//			
//			request.setAttribute("appointIndate", appointIndate);
//			request.setAttribute("appointOutdate", appointOutdate);
//			request.setAttribute("today", today);
//			
//		} catch (UnsupportedOperationException e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
		
		request.setAttribute("salesdto", salesdto);
		
		// 펜션정보 => 펜션 이름, 체크인/체크아웃 시간
		ProductDAO pdao = new ProductDAO();
		ProductDTO pdto = pdao.getProduct(salesdto.getPno());
		
		String houseName = pdto.getPname(); // 펜션 이름
		int houseInTime = pdto.getCheckin(); // 체크인 시간
		int houseOutTime = pdto.getCheckout(); // 체크아웃 시간
		
		request.setAttribute("houseName", houseName);
		request.setAttribute("houseInTime", houseInTime);
		request.setAttribute("houseOutTime", houseOutTime);

		
		// 페이징
		AppointmentDAO adao = new AppointmentDAO();
		
		int pageSize = 2;
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum="1";
		}

		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1) * pageSize+1; 
		int endRow = startRow + pageSize-1;
		
		ArrayList<AppointmentDTO> userappointmentlist = new ArrayList<>(adao.getAppointmentList(no, startRow, pageSize));
		
		// 한 화면에 보여줄 '페이지 개수' 구하기
		int pageBlock = 5;
		
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		
		int endPage = (startPage+pageBlock)-1;
		
		ReviewDAO rdao = new ReviewDAO();
		int count = rdao.getReviewCount();
		
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
		// request에 가져온 데이터 담기
		request.setAttribute("userappointmentlist", userappointmentlist);
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		
		// 이동
		ActionForward forward = new ActionForward();
		forward.setPath("member/myList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
}
