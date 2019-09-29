package team.InternetBar;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class CardManagement {
//需要避免生成相同的卡号
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
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "INSERT INTO cards(account,password,balance)" + "VALUES(?,?,?)";
			System.out.println("您的卡号是：");
			System.out.println(randNumber);
			account = randNumber;
			System.out.println("请输入密码（限制10个字符）：");
			password = sc.next();
			System.out.println("请输入开卡金额：");
			balance = sc.next();
			begin = "";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.setString(2, password);
			preStmt.setString(3, balance);
			// preStmt.setString(4, begin);
			preStmt.executeUpdate();
			System.out.println("开卡成功");
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
			System.out.println("请输入待查询的卡号");
			account = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			account = rs.getString("account");
			password = rs.getString("password");
			balance = rs.getString("balance");
			System.out.println("查询结果如下");
			System.out.println("卡号                                密码                 余额");
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
			System.out.println("请输入待注销的卡号：");
			account = sc.next();
			System.out.println("请输入密码：");
			intPassword = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
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
				System.out.println("注销成功");
			}else {
				System.out.println("输入密码错误");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
