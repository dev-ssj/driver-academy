package kr.consulting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.consulting.dao.ConsultingDAO;
import kr.controller.Action;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		HttpSession session = request.getSession();
		Integer member_num = (Integer)session.getAttribute("member_num");
		int consulting_num = Integer.parseInt(request.getParameter("consulting_num"));
		
		//���ǿ� ����� ���� ������ �α��� ������ ȣ��
		if(member_num == null) {
			return "redirect:/member/memberLoginForm.do";
		}
		
		ConsultingDAO dao = ConsultingDAO.getIntance();
		dao.DeleteConsulting(consulting_num);
		
		return "/WEB-INF/views/consulting/member/DeleteConsultingMember.jsp";
	}

}
