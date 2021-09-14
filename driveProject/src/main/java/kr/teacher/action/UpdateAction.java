package kr.teacher.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.teacher.dao.TeacherDAO;
import kr.teacher.vo.TeacherVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer admin_num = (Integer)session.getAttribute("admin_num");
		
		//���ǿ� ����� ���� ������ ������ �α��� ������ ȣ��
		if(admin_num == null) {
			return "redirect:/admin/adminLoginForm.do";
		}

		request.setCharacterEncoding("utf-8");
		MultipartRequest multi =FileUtil.createFile(request);
		int teacher_num=Integer.parseInt(multi.getParameter("teacher_num"));
		String photo = multi.getFilesystemName("photo");
		
		TeacherDAO dao = TeacherDAO.getInstance();
		
		
		if( photo == null || photo.equals("") || photo.equals("null") ) {	//������ ���� ���� ���� ��
			TeacherVO teacher = new TeacherVO();
			teacher.setTeacher_num(Integer.parseInt(multi.getParameter("teacher_num")));
			teacher.setTeacher_name(multi.getParameter("name"));
			teacher.setTeacher_phone(multi.getParameter("phone"));
			teacher.setTeacher_email(multi.getParameter("email"));
			
			dao.updateTeacher(teacher);
			
		}else {	//������ ���� ���� ��
			//���� ������ ���� �ҷ���
			TeacherVO teacher_old = dao.getTeacher(teacher_num);
			
			TeacherVO teacher = new TeacherVO();
			teacher.setTeacher_num(Integer.parseInt(multi.getParameter("teacher_num")));
			teacher.setTeacher_name(multi.getParameter("name"));
			teacher.setTeacher_profile(multi.getFilesystemName("photo"));
			teacher.setTeacher_phone(multi.getParameter("phone"));
			teacher.setTeacher_email(multi.getParameter("email"));
			
			//���������� ���� ����
			FileUtil.removeFile(request, teacher_old.getTeacher_profile());
			
			//������ ������Ʈ
			dao.allUpdateTeacher(teacher);
			
		}
		
		return "/WEB-INF/views/teacher/UpdateTeacher.jsp";
	}

}
