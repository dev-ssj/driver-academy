package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		if(admin_num==null) {//�α����� ���� ���� ���
			return "redirect:/admin/adminLoginForm.do";
		}
		
		
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO notice = dao.getNotice(notice_num);
		if(admin_num != notice.getAdmin_num()) {
			//�α����� �����ڹ�ȣ�� �ۼ��� �����ڹ�ȣ�� ����ġ
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//�α����� �����ڹ�ȣ�� �ۼ��� �����ڹ�ȣ�� ��ġ
		dao.deleteNotice(notice_num);
		//���� ����
		FileUtil.removeFile(request, notice.getFilename());
		
		return "redirect:/notice/list.do";
	}

}
