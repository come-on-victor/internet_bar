package team.InternetBar;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class CardManagement {
//��Ҫ����������ͬ�Ŀ���
	public void addCard() throws SQLException {
		final int MAX =999999999;
		final int MIN =100000000;
		String account, password, balance, begin, randNumber;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		Random rand = new Random();
		randNumber = Integer.toString(rand.nextInt(MAX - MIN + 1) + MIN);
		try {
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "INSERT INTO cards(account,password,balance)" + "VALUES(?,?,?)";
			System.out.println("���Ŀ����ǣ�");
			System.out.println(randNumber);
			account = randNumber;
			System.out.println("���������루����10���ַ�����");
			password = sc.next();
			System.out.println("�����뿪����");
			balance = sc.next();
			begin = "";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.setString(2, password);
			preStmt.setString(3, balance);
			// preStmt.setString(4, begin);
			preStmt.executeUpdate();
			System.out.println("�����ɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn);
		}
	}

	public void searchCard() throws SQLException {
		String account, password, balance, begin;
		Scanner sc = new Scanner(System.in);
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}

	public void cancellationCard() throws SQLException {
		String account, intPassword, searchPassword, balance, begin;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("�������ע���Ŀ��ţ�");
			account = sc.next();
			System.out.println("���������룺");
			intPassword = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql1 = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql1);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			if(intPassword.equals(searchPassword))
			{				
				String sql2 = "DELETE FROM cards WHERE account=?";
				preStmt = conn.prepareStatement(sql2);
				preStmt.setString(1, account);
				preStmt.executeUpdate();
				System.out.println("ע���ɹ�");
			}else {
				System.out.println("�����������");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
