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
		if(admin_num == null) {//로그인 되지 않은 경우
			return "redirect:/admin/loginForm.do";
		}
		
		//로그인 된 경우
		return "/WEB-INF/views/notice/writeForm.jsp";
	}

}
