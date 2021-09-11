package kr.application.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.application.vo.ApplicationVO;
import kr.controller.Action;

public class CancelAppAction implements Action{

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
		
		int app_num = Integer.parseInt(request.getParameter("app_num"));
		
		ApplicationDAO dao = ApplicationDAO.getinstance();
		ApplicationVO app = dao.getApp(app_num);
		
		//�α����� ȸ���� ������û ȸ�� ��ġ ���� Ȯ��
		if(member_num != app.getMember_num()) {
			return "redirect:/application/listApp.do";
		}
		
		//������û ����
		dao.deleteApp(app_num);
		
		return "/WEB-INF/views/application/cancelApp.jsp";
	}
}