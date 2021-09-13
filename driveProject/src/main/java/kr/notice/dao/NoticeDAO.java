package kr.notice.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.notice.vo.NoticeVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class NoticeDAO {
	//�̱��� ����
	private static NoticeDAO instance = new NoticeDAO();
	
	public static NoticeDAO getInstance() {
		return instance;
	}
	private NoticeDAO() {}
	
	//�� ���
	public void insertNotice(NoticeVO notice)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "INSERT INTO notice (notice_num,title,content,filename,admin_num) VALUES (notice_seq.nextval,?,?,?,?)";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getContent());
			pstmt.setString(3, notice.getFilename());
			pstmt.setInt(4, notice.getAdmin_num());
			
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//�� ���ڵ� �� 
	public int getNoticeCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM notice n JOIN admin a ON n.admin_num = a.admin_num";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//SQL�� �����ϰ� ������� ResultSet�� ����
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
	//�� ���
	public List<NoticeVO> getListNotice(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeVO> list = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM notice n JOIN admin a ON n.admin_num = a.admin_num "
				+ "ORDER BY n.notice_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			//SQL�� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			while(rs.next()) {
				NoticeVO notice = new NoticeVO();
				notice.setNotice_num(rs.getInt("notice_num"));
				notice.setTitle(StringUtil.useNoHtml(rs.getString("title")));//HTML�� ������� ����
				notice.setHit(rs.getInt("hit"));
				notice.setReg_date(rs.getDate("reg_date"));
				notice.setFilename(rs.getString("filename"));
				notice.setContent(rs.getString("content"));
				notice.setAdmin_num(rs.getInt("admin_num"));
				
				list.add(notice);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//�� ��
	public NoticeVO getNotice(int notice_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeVO notice = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "SELECT * FROM notice n JOIN admin a ON n.admin_num=a.admin_num WHERE n.notice_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� �����͸� ���ε�
			pstmt.setInt(1, notice_num);
			//SQL���� �����ؼ� ������� ResultSet ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				notice = new NoticeVO();
				notice.setNotice_num(rs.getInt("notice_num"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setHit(rs.getInt("hit"));
				notice.setReg_date(rs.getDate("reg_date"));
				notice.setFilename(rs.getString("filename"));
				notice.setAdmin_num(rs.getInt("admin_num"));
				notice.setId(rs.getString("id"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return notice;
	}
	
	//��ȸ�� ����
	public void updateReadcount(int notice_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//sql�� �ۼ�
			sql = "UPDATE notice SET hit=hit+1 WHERE notice_num=?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� �����͸� ���ε�
			pstmt.setInt(1, notice_num);
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//�� ����
	
	//�� ����
}
