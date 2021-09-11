package kr.teacher.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.teacher.dao.TeacherDAO;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		
		//���ǿ� ����� ���� ������ ������ �α��� ������ ȣ��
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}
		
		int teacher_num = Integer.parseInt(request.getParameter("teacher_num"));
		
		TeacherDAO dao = TeacherDAO.getInstance();
		dao.deleteTeacher(teacher_num);
		
		
		return "/WEB-INF/views/teacher/DeleteTeacher.jsp";
	}

}
