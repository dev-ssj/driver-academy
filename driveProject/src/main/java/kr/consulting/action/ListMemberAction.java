package kr.consulting.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.consulting.dao.ConsultingDAO;
import kr.consulting.vo.ConsultingVO;
import kr.controller.Action;
import kr.util.PaginationUtil;


public class ListMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer member_num = (Integer)session.getAttribute("member_num");
		
		//���ǿ� ����� ���� ������ �α��� ������ ȣ��
		if(member_num == null) {
			return "redirect:/member/memberLoginForm.do";
		}
		
		
		//����¡ ó��
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		ConsultingDAO dao = ConsultingDAO.getIntance();
		int count = dao.getMemberConsulting(member_num);	//�� ���ڵ� ��
		
		PaginationUtil page = new PaginationUtil(Integer.parseInt(pageNum), count, 5, 5, "memberList.do");
		
		List<ConsultingVO>list = null;
		if(count > 0) {
			list=dao.ListConsulting(member_num,page.getStartCount(),page.getEndCount());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/consulting/member/ListConsultingMember.jsp";
	}

}
