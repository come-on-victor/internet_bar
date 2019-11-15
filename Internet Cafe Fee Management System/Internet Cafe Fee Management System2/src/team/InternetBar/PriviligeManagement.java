package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PriviligeManagement {
	public void addAdministrator() throws SQLException {
		String account, password;
		int level;
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		try {
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "INSERT INTO privilege_management(account,password,level)" + "VALUES(?,?,?)";
			System.out.println("�������˺ţ�");
			account = sc.next();
			System.out.println("���������루����10���ַ�����");
			password = sc.next();
			System.out.println("������Ȩ�ޣ���1��������������Ա����0��������ͨ����Ա����");
			level = sc.nextInt();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.setString(2, password);
			preStmt.setInt(3, level);
			preStmt.executeUpdate();
			System.out.println("���ӳɹ�");
		} finally {
			Tools.release(preStmt);
		}
	}

	public void deleteAdministrators() throws SQLException {
		String account;
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("������Ҫɾ���Ĺ���Ա�˺ţ�");
			account = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "DELETE FROM privilege_management WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.executeUpdate();
			System.out.println("ɾ���ɹ�");
		} finally {
			Tools.release(preStmt, rs);
		}
	}

	public void configurationAuthority() throws SQLException {
		String account; 
		int level;
		String sql;
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("��������Ҫ���õĹ���Ա�˺ţ�");
			account = sc.next();
			System.out.println("���������ú��Ȩ�ޣ�");
			level = sc.nextInt();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			sql = "UPDATE privilege_management SET level=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, level);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("���óɹ�");
		} finally {
			Tools.release(preStmt, rs);
		}
	}
}