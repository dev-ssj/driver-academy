package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class WriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		if(admin_num == null) {//�α��� ���� ���� ���
			return "redirect:/admin/adminLoginForm.do";
		}
		
		//�α��� �� ���
		return "/WEB-INF/views/notice/writeForm.jsp";
	}
}
