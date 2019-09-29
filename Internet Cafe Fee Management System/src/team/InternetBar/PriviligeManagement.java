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
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		try {
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "INSERT INTO privilege_management(account,password,level)" + "VALUES(?,?,?)";
			System.out.println("请输入账号：");
			account = sc.next();
			System.out.println("请输入密码（限制10个字符）：");
			password = sc.next();
			System.out.println("请输入权限（“1”代表超级管理员，“0”代表普通管理员）：");
			level = sc.nextInt();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.setString(2, password);
			preStmt.setInt(3, level);
			preStmt.executeUpdate();
			System.out.println("添加成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn);
		}
	}

	public void deleteAdministrators() throws SQLException {
		String account;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("请输入要删除的管理员账号：");
			account = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "DELETE FROM privilege_management WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.executeUpdate();
			System.out.println("删除成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}

	public void configurationAuthority() throws SQLException {
		String account; 
		int level;
		String sql;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("请输入需要配置的管理员账号：");
			account = sc.next();
			System.out.println("请输入配置后的权限：");
			level = sc.nextInt();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			sql = "UPDATE privilege_management SET level=? WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, level);
			preStmt.setString(2, account);
			preStmt.executeUpdate();
			System.out.println("配置成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
