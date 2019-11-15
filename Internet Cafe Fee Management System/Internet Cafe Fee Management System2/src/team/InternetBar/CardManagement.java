package team.InternetBar;

import java.sql.*;

import java.util.Scanner;

public class CardManagement {
	public void addCard() throws SQLException {
		String password, balance;
		Scanner sc = Input.getInput();
		System.out.println("请输入密码（限制10个字符）：");
		password = sc.next();
		System.out.println("请输入开卡金额：");
		balance = sc.next();
		cards card = new cards(password, balance);
		System.out.println("您的卡号是：");
		System.out.println(card.getAccount());
		System.out.println("开卡成功");
	}

	public void searchCard() throws SQLException {
		String account, password, balance;
		Scanner sc = Input.getInput();
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
			System.out.println("请输入待注销的卡号：");
			account = sc.next();
			System.out.println("请输入密码：");
			inputtPassword = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
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
				System.out.println("注销成功");
			} else {
				System.out.println("输入密码错误");
			}
		} finally {
			//Tools.release(preStmt, rs);
		}
	}
}
