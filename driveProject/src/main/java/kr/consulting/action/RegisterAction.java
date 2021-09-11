package kr.consulting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.consulting.dao.ConsultingDAO;
import kr.consulting.vo.ConsultingVO;
import kr.controller.Action;

public class RegisterAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer member_num = (Integer)session.getAttribute("member_num");
		
		//���ǿ� ����� ���� ������ �α��� ������ ȣ��
		if(member_num == null) {
			return "redirect:/member/memberLoginForm.do";
		}
		
		ConsultingVO consulting = new ConsultingVO();
		consulting.setMember_num(member_num);
		//StringŸ������ ���� ��¥�� java.sql.Date�������� �ٲ㼭 VO�� ����
		java.sql.Date consulting_date = java.sql.Date.valueOf(request.getParameter("consulting_date"));
		consulting.setConsulting_date(consulting_date);
		
		//09:30 ���� �������� VO�� ����
		consulting.setConsulting_time(request.getParameter("consulting_time_hour")+":"+request.getParameter("consulting_time_min"));
		
		ConsultingDAO dao = ConsultingDAO.getIntance();
		dao.InsertConsulting(consulting);
		
		return "/WEB-INF/views/consulting/member/InsertConsultingMember.jsp";
	}

}
