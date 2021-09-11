package kr.teacher.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.teacher.dao.TeacherDAO;
import kr.teacher.vo.TeacherVO;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		
		//���ǿ� ����� ���� ������ ������ �α��� ������ ȣ��
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}
		
		TeacherDAO dao = TeacherDAO.getInstance();
		int count = dao.getTeacherCount();
		List<TeacherVO>list = null;
		list=dao.ListTeacher();
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/teacher/ListTeacher.jsp";
	}

}
