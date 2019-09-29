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
			System.out.println("请输入待充值账号：");
			account = sc.next();
			System.out.println("请输入充值金额：");
			money = sc.nextFloat();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			oldBalance = rs.getFloat("balance");
			sql = "UPDATE cards SET balance=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);//可能要两个对象操作
			curBalance = oldBalance + money;
			preStmt.setFloat(1, curBalance);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("充值成功，当前余额为：" + curBalance);
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
			System.out.println("请输入退费账号：");
			account = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			balance = rs.getFloat("balance");
			sql = "UPDATE cards SET balance=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);//可能要两个对象操作
			preStmt.setFloat(1, 0);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("可退" + balance + "元，退费成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
