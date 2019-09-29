package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BillingManagement {
	public void reCharge() throws SQLException {
		String account, sql;
		float oldBalance, curBalance, money;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("���������ֵ�˺ţ�");
			account = sc.next();
			System.out.println("�������ֵ��");
			money = sc.nextFloat();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			oldBalance = rs.getFloat("balance");
			sql = "UPDATE cards SET balance=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);//����Ҫ�����������
			curBalance = oldBalance + money;
			preStmt.setFloat(1, curBalance);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("��ֵ�ɹ�����ǰ���Ϊ��" + curBalance);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
	public void refund() throws SQLException {
		String account, sql;
		float balance;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("�������˷��˺ţ�");
			account = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			balance = rs.getFloat("balance");
			sql = "UPDATE cards SET balance=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);//����Ҫ�����������
			preStmt.setFloat(1, 0);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("����" + balance + "Ԫ���˷ѳɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
