package kr.application.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.application.dao.ApplicationDAO;
import kr.application.vo.ApplicationVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;

public class DetailAppAction implements Action{

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
		
		//������û ��
		ApplicationDAO dao = ApplicationDAO.getinstance();
		HashMap<String,Object> hmap = dao.getAppDetail(app_num);
		
		ApplicationVO app = (ApplicationVO) hmap.get("app");
		MemberVO member = (MemberVO) hmap.get("member");
		
		request.setAttribute("app", app);
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/application/detailApp.jsp";
	}
}