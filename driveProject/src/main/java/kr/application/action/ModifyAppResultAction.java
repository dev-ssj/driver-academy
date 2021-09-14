package kr.application.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.controller.Action;

public class ModifyAppResultAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		//���ǿ� ����� �����ڹ�ȣ �о����
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		
		//�α��ε��� ���� ���, ������ �α��� ������ ȣ��
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}
		
		int app_num = Integer.parseInt(request.getParameter("app_num"));
		int app_result = Integer.parseInt(request.getParameter("app_result"));
		
		//������û ��� ����
		ApplicationDAO dao = ApplicationDAO.getinstance();
		dao.setAppResult(app_num, app_result);
		
		request.setAttribute("app_num", app_num);
		
		return "/WEB-INF/views/application/modifyAppResult.jsp";
	}
}