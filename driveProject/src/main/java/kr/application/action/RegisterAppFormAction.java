package kr.application.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class RegisterAppFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���� ��� ----- ���� ��Ʈ �ϼ��Ǹ� �߰�
		
		
		
		return "/WEB-INF/views/application/registerAppForm.jsp";
	}
}