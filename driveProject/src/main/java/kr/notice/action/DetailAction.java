package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�۹�ȣ ��ȯ
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
				
		NoticeDAO dao = NoticeDAO.getInstance();
		//��ȸ�� ����
		dao.updateReadcount(notice_num);
				
		NoticeVO notice = dao.getNotice(notice_num);
				
		//HTML�� ������� ����
		notice.setTitle(StringUtil.useNoHtml(notice.getTitle()));
		//HTML�� ������� �����鼭 �ٹٲ� ó��
		notice.setContent(StringUtil.useBrNoHtml(notice.getContent()));
				
		request.setAttribute("notice", notice);
				
		return "/WEB-INF/views/notice/detail.jsp";
	}

}
