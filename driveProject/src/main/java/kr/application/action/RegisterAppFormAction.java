package kr.application.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.util.PaginationUtil;

public class RegisterAppFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		//��������ȣ ����
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		//�� ���ڵ� ��
		CourseDAO dao = CourseDAO.getInstance();
		int count = dao.getCourseCount();
		
		//������ ó��
		PaginationUtil page = new PaginationUtil(Integer.parseInt(pageNum), count, 5, 5, "listApp.do");
		
		//���� ���
		List<CourseVO> list = null;
		if(count>0) {
			list = dao.getListCourse(page.getStartCount(),page.getEndCount());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/application/registerAppForm.jsp";
	}
}