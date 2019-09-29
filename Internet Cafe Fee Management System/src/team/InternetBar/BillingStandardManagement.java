package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BillingStandardManagement {
	public void newBillingStandard() throws SQLException {
		float begin, end, standard;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		try {
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "INSERT INTO charge_standards(begin,end,standard)" + "VALUES(?,?,?)";
			System.out.println("��������ʼʱ�䣺");
			begin = sc.nextFloat();
			System.out.println("��������ֹʱ�䣺");
			end = sc.nextFloat();
			System.out.println("�������ʱ���һСʱ���շѱ�׼��");
			standard = sc.nextFloat();
			preStmt = conn.prepareStatement(sql);
			preStmt.setFloat(1, begin);
			preStmt.setFloat(2, end);
			preStmt.setFloat(3, standard);
			preStmt.executeUpdate();
			System.out.println("��ӳɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn);
		}
	}

	public void queryBillingStandard() throws SQLException {
		float begin, end, standard;
		int id;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM charge_standards";
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			System.out.println("id" + "   " + "begin" + "   " + "end" + "   " + "standard");
			while (rs.next()) {
				id = rs.getInt("id");
				begin = rs.getFloat("begin");
				end = rs.getFloat("end");
				standard = rs.getFloat("standard");
				System.out.println(id + "   " + begin + "   " + end + "   " + standard);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}

	public void deleteBillingStandard() throws SQLException {
		int id;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			queryBillingStandard();
			System.out.println("��������޸ļƷѱ�׼��id�ţ�");
			id = sc.nextInt();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "DELETE FROM charge_standards WHERE id=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, id);
			preStmt.executeUpdate();
			System.out.println("ɾ���ɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}

	public void modifyTheChargingStandard() throws SQLException {
		int id;
		float begin, end, standard;
		String sql;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			queryBillingStandard();
			System.out.println("�������ɾ���Ʒѱ�׼��id�ţ�");
			id = sc.nextInt();
			System.out.println("�������µ���ʼʱ�䣺");
			begin = sc.nextFloat();
			System.out.println("�������µ���ֹʱ�䣺");
			end = sc.nextFloat();
			System.out.println("������ö�ʱ����ÿСʱ���շѱ�׼��");
			standard = sc.nextFloat();
			
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			sql = "UPDATE charge_standards SET begin=?, end=?, standard=? WHERE id=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setFloat(1, begin);
			preStmt.setFloat(2, end);
			preStmt.setFloat(3, standard);
			preStmt.setInt(4, id);
			preStmt.executeUpdate();
			System.out.println("�޸ĳɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
