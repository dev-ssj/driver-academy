package kr.application.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.application.vo.ApplicationVO;
import kr.controller.Action;

public class RegisterAppAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		//���ǿ� ����� ȸ����ȣ �о����
		HttpSession session = request.getSession();
		Integer member_num = (Integer)session.getAttribute("member_num");
		
		//�α��ε��� ���� ���, ȸ�� �α��� ������ ȣ��
		if(member_num == null) {
			return "redirect:/member/memberLoginForm.do";
		}
		
		int course_num = Integer.parseInt(request.getParameter("course_num"));
		
		//������û �ߺ� üũ
		ApplicationDAO dao = ApplicationDAO.getinstance();
		boolean check = false;
		if(dao.checkApp(course_num)==null) {
			check = true;
			
			//������û ���
			ApplicationVO app = new ApplicationVO();
			app.setMember_num(member_num);
			app.setCourse_num(course_num);
			dao.insertApp(app);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/application/registerApp.jsp";
	}
}