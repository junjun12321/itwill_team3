package com.itwillbs.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import member.ReviewDAO;
import member.ReviewDTO;

public class MemberReviewModifyPro implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("MemberReviewModifyPro execute()");
		
		// 파일
		String uploadPath = request.getRealPath("/upload");
		System.out.println(uploadPath);
		int maxSize = 10*1024*1024;
		MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		int ano = Integer.parseInt(multi.getParameter("ano"));
		
		int rno = Integer.parseInt(multi.getParameter("rno"));
		String rtitle = multi.getParameter("title");
		String rstar = multi.getParameter("rstar"); 
		String rcontent = multi.getParameter("content");
		String rpic1 = multi.getParameter("rpic1");
		// 수정할 업로드 파일이 없으면 기존 파일 유지
		if(rpic1 == null) {
			rpic1 = multi.getParameter("oldrpic1");
		}
		
		// ReviewDTO
		ReviewDTO rdto = new ReviewDTO();
		rdto.setRno(rno);
		rdto.setRtitle(rtitle);
		rdto.setRstar(rstar); 
		rdto.setRcontent(rcontent);
		rdto.setRpic1(rpic1);
		
		// ReviewDAO
		ReviewDAO rdao = new ReviewDAO();
		rdao.updateReview(rdto);
		
		// response.sendRedirect("myList.jsp");
		ActionForward forward = new ActionForward();
		forward.setPath("MemberMyList.me");
		forward.setRedirect(true);
		
		return forward;
	}
	
}
