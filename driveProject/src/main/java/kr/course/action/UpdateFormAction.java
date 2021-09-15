package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		request.setCharacterEncoding("utf-8");
		
		//세션에 저장된 값이 없으면 관리자 로그인 페이지 호출
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}
		
		int course_num = Integer.parseInt(request.getParameter("course_num"));
		CourseDAO dao = CourseDAO.getInstance();
		CourseVO course = dao.getCourse(course_num);
		
		request.setAttribute("course", course);
		return "/WEB-INF/views/course/updateForm.jsp";
	}

}
