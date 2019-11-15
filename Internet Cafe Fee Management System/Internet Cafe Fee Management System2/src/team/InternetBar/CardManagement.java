package team.InternetBar;

import java.sql.*;

import java.util.Scanner;

public class CardManagement {
	public void addCard() throws SQLException {
		String password, balance;
		Scanner sc = Input.getInput();
		System.out.println("���������루����10���ַ�����");
		password = sc.next();
		System.out.println("�����뿪����");
		balance = sc.next();
		cards card = new cards(password, balance);
		System.out.println("���Ŀ����ǣ�");
		System.out.println(card.getAccount());
		System.out.println("�����ɹ�");
	}

	public void searchCard() throws SQLException {
		String account, password, balance;
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("���������ѯ�Ŀ���");
			account = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			account = rs.getString("account");
			password = rs.getString("password");
			balance = rs.getString("balance");
			System.out.println("��ѯ�������");
			System.out.println("����                                ����                 ���");
			System.out.println(account + "  " + password + "   " + balance);
		} finally {
			Tools.release(preStmt, rs);
		}
	}

	public void cancellationCard() throws SQLException {
		String account, inputtPassword, searchPassword;
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("�������ע���Ŀ��ţ�");
			account = sc.next();
			System.out.println("���������룺");
			inputtPassword = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql1 = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql1);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			if (inputtPassword.equals(searchPassword)) {
				String sql2 = "DELETE FROM cards WHERE account=?";
				preStmt = conn.prepareStatement(sql2);
				preStmt.setString(1, account);
				preStmt.executeUpdate();
				System.out.println("ע���ɹ�");
			} else {
				System.out.println("�����������");
			}
		} finally {
			//Tools.release(preStmt, rs);
		}
	}
}
