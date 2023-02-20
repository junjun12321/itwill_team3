package com.itwillbs.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qna.QnaDAO;

public class AdminAnswerdelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AdminAnswerdelete execute()");
		
int qno=Integer.parseInt(request.getParameter("qno"));
		
		// QnaDAO 객체생성
		QnaDAO dao=new QnaDAO();
		dao.deleteQna(qno);
		
		// list.jsp 이동
		ActionForward forward = new ActionForward();
		forward.setPath("QnaList.qa");
		forward.setRedirect(true);
		
		return forward;
	}

}
