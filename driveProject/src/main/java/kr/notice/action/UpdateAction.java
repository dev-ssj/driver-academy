package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		if(admin_num==null) {//�α����� ���� ���� ���
			return "redirect:/admin/adminLoginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int notice_num = Integer.parseInt(multi.getParameter("notice_num"));
		String filename = multi.getFilesystemName("filename");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		//������ ������
		NoticeVO dbNotice = dao.getNotice(notice_num);
		if(admin_num != dbNotice.getAdmin_num()) {//�α����� �����ڿ� �ۼ��� �����ڹ�ȣ�� ����ġ
			FileUtil.removeFile(request, filename);//���ε�� ������ ������ ���� ����
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//�α����� �����ڿ� �ۼ��� �����ڹ�ȣ�� ��ġ
		NoticeVO notice = new NoticeVO();
		notice.setNotice_num(notice_num);
		notice.setTitle(multi.getParameter("title"));
		notice.setContent(multi.getParameter("content"));
		notice.setFilename(filename);
		
		dao.updateNotice(notice);
		
		if(filename!=null) {//�� ���Ϸ� ��ü�� �� ���� ���� ����
			FileUtil.removeFile(request, dbNotice.getFilename());			
		}
		
		return "redirect:/notice/list.do";
	}

}
