package kr.application.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.application.vo.ApplicationVO;
import kr.controller.Action;
import kr.util.PaginationUtil;

public class ListAllAppAction implements Action{

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
		
		//��������ȣ ����
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		if(keyfield == null) keyfield = "";
		
		String keyword = "";
		if(keyfield.equals("r")) {
			keyword = request.getParameter("re_keyword");
		}else {
			keyword = request.getParameter("keyword");
		}
		if(keyword == null) keyword = "";
		
		//�� ���ڵ� ��
		ApplicationDAO dao = ApplicationDAO.getinstance();
		int count = dao.getAppCountAll(keyfield, keyword);
		
		//������ ó��
		PaginationUtil page = new PaginationUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 5, 5, "listAllApp.do");
		
		//��ü ������û ����(������)
		List<ApplicationVO> list = null;
		if(count>0) {
			list = dao.getAppListAll(page.getStartCount(), page.getEndCount(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/application/listAllApp.jsp";
	}
}