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
			System.out.println("请输入查询的账号");
			account = sc.nextLine();
			System.out.println("请输入查询的起始时间（yyyy-mm-dd hh:mm:ss）");
			beginTime = sc.nextLine();
			System.out.println("请输入查询的终止时间（yyyy-mm-dd hh:mm:ss）");
			endTime = sc.nextLine();
			System.out.println("账号：" + account);
			System.out.println("起始时间" + beginTime);
			System.out.println("终止时间" + endTime);
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
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
			System.out.println("请输入统计的起始时间（yyyy-mm-dd hh:mm:ss）");
			beginTime = sc.nextLine();
			System.out.println("请输入统计的终止时间（yyyy-mm-dd hh:mm:ss）");
			endTime = sc.nextLine();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM query_statistics";
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while(rs.next()) {
				datetime = rs.getString("datetime");
				money = rs.getFloat("money");
				if(datetime.compareTo(beginTime) >= 0 && datetime.compareTo(endTime) <= 0)
					sum = sum + money;
			}
			System.out.println(beginTime + "~" + endTime + "的营业额为" + sum);
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
			System.out.println("请输入统计年份（yyyy）");
			year = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM query_statistics";
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while(rs.next()) {
				datetime = rs.getString("datetime");
				money = rs.getFloat("money");
				curYear = datetime.substring(0, 4);
				month = datetime.substring(5, 7);
				//System.out.println("当前的年份和月份为" + curYear +" "+ month);
				if (year.compareTo(curYear) == 0) {
					if (flag == 1) {
						tMonth = month;
						flag = 0;
					}
					if (tMonth.compareTo(month) == 0) {
						count++;
						sum = sum + money;
					} else {
						System.out.println(month + "月的上机次数为" + count + "，营业额为" + sum + "元");
						count = 0;
						flag = 1;
						rs.previous();
					} 
				}
				if(rs.next() == false) {
					System.out.println(month + "月的上机次数为" + count + "，营业额为" + sum + "元");
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
