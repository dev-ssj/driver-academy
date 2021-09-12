package kr.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.application.vo.ApplicationVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class ApplicationDAO {
	//�̱��� ����
	private static ApplicationDAO instance = new ApplicationDAO();
	public static ApplicationDAO getinstance() {
		return instance;
	}
	private ApplicationDAO() {}
	
	//������û ���
	public void insertApp(ApplicationVO app) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO application VALUES(Application_seq.nextval,?,?,SYSDATE,0)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, app.getMember_num());
			pstmt.setInt(2, app.getCourse_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//������û �ߺ� üũ
	public int checkApp(ApplicationVO app) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int check = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(*) FROM application WHERE member_num=? AND course_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, app.getMember_num());
			pstmt.setInt(2, app.getCourse_num());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				check=rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return check;
	}
	
	//������û ����
		public ApplicationVO getApp(int app_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			ApplicationVO app = null;
			
			try {
				conn = DBUtil.getConnection();
				
				sql = "SELECT * FROM application WHERE app_num=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, app_num);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					//������û ����
					app = new ApplicationVO();
					app.setApp_num(rs.getInt("app_num"));
					app.setApp_date(rs.getDate("app_date"));
					app.setApp_result(rs.getInt("app_result"));
					app.setCourse_num(rs.getInt("course_num"));
					app.setMember_num(rs.getInt("member_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//�ڿ�����
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return app;
		}
	
	//�� ���ڵ� �� - ȸ����
	public int getAppCount(int member_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) FROM application WHERE member_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//������û ��� - ȸ����
	public List<ApplicationVO> getAppList(int member_num, int start, int end) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<ApplicationVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT X.*, ROWNUM rnum FROM (SELECT * FROM application A "
					+ "JOIN course C ON A.course_num=C.course_num "
					+ "JOIN teacher T ON C.teacher_num=T.teacher_num "
					+ "WHERE member_num=? ORDER BY app_num DESC) X) WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<ApplicationVO>();
			while(rs.next()) {
				ApplicationVO app = new ApplicationVO();
				app.setApp_num(rs.getInt("app_num"));
				app.setApp_date(rs.getDate("app_date"));
				app.setApp_result(rs.getInt("app_result"));
				app.setCourse_num(rs.getInt("course_num"));
				app.setMember_num(rs.getInt("member_num"));
				app.setCourse_name(rs.getString("course_name"));
				app.setTeacher_name(rs.getString("teacher_name"));
				
				list.add(app);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//������û ����
	public void deleteApp(int app_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM application WHERE app_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, app_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//������ ���
	//�� ���ڵ� �� - ��ü ȸ��
	public int getAppCountAll() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) FROM application";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//������û ��� - ��ü ȸ��
	public List<ApplicationVO> getAppListAll(int start, int end) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<ApplicationVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT X.*, ROWNUM rnum FROM (SELECT * FROM application A "
					+ "JOIN course C ON A.course_num=C.course_num "
					+ "JOIN teacher T ON C.teacher_num=T.teacher_num "
					+ "JOIN member_detail D ON A.member_num=D.member_num "
					+ "ORDER BY app_num DESC) X) WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<ApplicationVO>();
			while(rs.next()) {
				ApplicationVO app = new ApplicationVO();
				app.setApp_num(rs.getInt("app_num"));
				app.setApp_date(rs.getDate("app_date"));
				app.setApp_result(rs.getInt("app_result"));
				app.setCourse_num(rs.getInt("course_num"));
				app.setMember_num(rs.getInt("member_num"));
				app.setName(rs.getString("name"));
				app.setCourse_name(rs.getString("course_name"));
				app.setTeacher_name(rs.getString("teacher_name"));
				list.add(app);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//������û �� - ������, �����, ȸ���� JOIN
	public HashMap<String,Object> getAppDetail(int app_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		HashMap<String,Object> hmap = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM application A "
					+ "JOIN course C ON A.course_num=C.course_num "
					+ "JOIN teacher T ON C.teacher_num=T.teacher_num "
					+ "JOIN member_detail D ON A.member_num=D.member_num WHERE app_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, app_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//������û ����
				ApplicationVO app = new ApplicationVO();
				app.setApp_num(rs.getInt("app_num"));
				app.setApp_date(rs.getDate("app_date"));
				app.setApp_result(rs.getInt("app_result"));
				app.setCourse_num(rs.getInt("course_num"));
				app.setMember_num(rs.getInt("member_num"));
				app.setName(rs.getString("name"));
				app.setCourse_name(rs.getString("course_name"));
				app.setTeacher_name(rs.getString("teacher_name"));
				
				//ȸ�� ����
				MemberVO member = new MemberVO();
				app.setMember_num(rs.getInt("member_num"));
				member.setName(rs.getString("name"));
				member.setBirth(rs.getString("birth"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setZipcode(rs.getString("zipcode"));
				
				hmap = new HashMap<String,Object>();
				hmap.put("app", app);
				hmap.put("member", member);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return hmap;
	}
	
	//������û ��� �ο�
	public void setAppResult(int app_num, int app_result) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE application SET app_result=? WHERE app_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, app_result);
			pstmt.setInt(2, app_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}