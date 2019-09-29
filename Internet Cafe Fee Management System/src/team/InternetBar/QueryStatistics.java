package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QueryStatistics {
	public void queryConsumptionRecords() throws SQLException {
		String account, datetime, beginTime, endTime;
		float money;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("�������ѯ���˺�");
			account = sc.nextLine();
			System.out.println("�������ѯ����ʼʱ�䣨yyyy-mm-dd hh:mm:ss��");
			beginTime = sc.nextLine();
			System.out.println("�������ѯ����ֹʱ�䣨yyyy-mm-dd hh:mm:ss��");
			endTime = sc.nextLine();
			System.out.println("�˺ţ�" + account);
			System.out.println("��ʼʱ��" + beginTime);
			System.out.println("��ֹʱ��" + endTime);
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM query_statistics WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			while(rs.next()) {
				datetime = rs.getString("datetime");
				money = rs.getFloat("money");
				if(datetime.compareTo(beginTime) >= 0 && datetime.compareTo(endTime) <= 0) {
					System.out.println(account + "  " + money + "  " + datetime);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
	public void statisticalTotalTurnover() throws SQLException {
		String account, datetime, beginTime, endTime;
		float money, sum = 0;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("������ͳ�Ƶ���ʼʱ�䣨yyyy-mm-dd hh:mm:ss��");
			beginTime = sc.nextLine();
			System.out.println("������ͳ�Ƶ���ֹʱ�䣨yyyy-mm-dd hh:mm:ss��");
			endTime = sc.nextLine();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM query_statistics";
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while(rs.next()) {
				datetime = rs.getString("datetime");
				money = rs.getFloat("money");
				if(datetime.compareTo(beginTime) >= 0 && datetime.compareTo(endTime) <= 0)
					sum = sum + money;
			}
			System.out.println(beginTime + "~" + endTime + "��Ӫҵ��Ϊ" + sum);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
	public void statisticalMonthlyTurnover() throws SQLException {
		String datetime, year, curYear, month, tMonth = " ";
		float money, sum = 0;
		int count = 0, flag = 1;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("������ͳ����ݣ�yyyy��");
			year = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM query_statistics";
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while(rs.next()) {
				datetime = rs.getString("datetime");
				money = rs.getFloat("money");
				curYear = datetime.substring(0, 4);
				month = datetime.substring(5, 7);
				//System.out.println("��ǰ����ݺ��·�Ϊ" + curYear +" "+ month);
				if (year.compareTo(curYear) == 0) {
					if (flag == 1) {
						tMonth = month;
						flag = 0;
					}
					if (tMonth.compareTo(month) == 0) {
						count++;
						sum = sum + money;
					} else {
						System.out.println(month + "�µ��ϻ�����Ϊ" + count + "��Ӫҵ��Ϊ" + sum + "Ԫ");
						count = 0;
						flag = 1;
						rs.previous();
					} 
				}
				if(rs.next() == false) {
					System.out.println(month + "�µ��ϻ�����Ϊ" + count + "��Ӫҵ��Ϊ" + sum + "Ԫ");
				}else {
					rs.previous();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
