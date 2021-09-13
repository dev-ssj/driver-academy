package kr.consulting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.consulting.dao.ConsultingDAO;
import kr.controller.Action;

public class UpdateAdminAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		int consulting_num = Integer.parseInt(request.getParameter("consulting_num"));
		int con_state = Integer.parseInt(request.getParameter("con_state"));
		//���ǿ� ����� ���� ������ ������ �α��� ������ ȣ��
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}
		
		ConsultingDAO dao = ConsultingDAO.getIntance();
		dao.UpdateConsultingAdmin(consulting_num, con_state);
		
		return "/WEB-INF/views/consulting/admin/UpdateConsultingAdmin.jsp";
	}
	
}
