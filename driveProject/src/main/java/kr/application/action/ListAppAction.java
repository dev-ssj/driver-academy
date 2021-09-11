package kr.application.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.application.vo.ApplicationVO;
import kr.controller.Action;
import kr.util.PaginationUtil;

public class ListAppAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		//���ǿ� ����� ȸ����ȣ �о����
		HttpSession session = request.getSession();
		Integer member_num = (Integer)session.getAttribute("member_num");
		
		//�α��ε��� ���� ���, ȸ�� �α��� ������ ȣ��
		if(member_num==null) {
			return "redirect:/member/memberLoginForm.do";
		}
		
		//��������ȣ ����
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		//�� ���ڵ� ��
		ApplicationDAO dao = ApplicationDAO.getinstance();
		int count = dao.getAppCount(member_num);
		
		//������ ó��
		PaginationUtil page = new PaginationUtil(Integer.parseInt(pageNum), count, 5, 5, "listApp.do");
		
		//������û ����(ȸ��)
		List<ApplicationVO> list = null;
		if(count>0) {
			list = dao.getAppList(member_num, page.getStartCount(), page.getEndCount());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/application/listApp.jsp";
	}
}