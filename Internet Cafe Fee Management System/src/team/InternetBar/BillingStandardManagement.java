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
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "INSERT INTO charge_standards(begin,end,standard)" + "VALUES(?,?,?)";
			System.out.println("请输入起始时间：");
			begin = sc.nextFloat();
			System.out.println("请输入终止时间：");
			end = sc.nextFloat();
			System.out.println("请输入该时间段一小时的收费标准：");
			standard = sc.nextFloat();
			preStmt = conn.prepareStatement(sql);
			preStmt.setFloat(1, begin);
			preStmt.setFloat(2, end);
			preStmt.setFloat(3, standard);
			preStmt.executeUpdate();
			System.out.println("添加成功");
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
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
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
			System.out.println("请输入待修改计费标准的id号：");
			id = sc.nextInt();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "DELETE FROM charge_standards WHERE id=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, id);
			preStmt.executeUpdate();
			System.out.println("删除成功");
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
			System.out.println("请输入待删除计费标准的id号：");
			id = sc.nextInt();
			System.out.println("请输入新的起始时间：");
			begin = sc.nextFloat();
			System.out.println("请输入新的终止时间：");
			end = sc.nextFloat();
			System.out.println("请输入该段时间内每小时的收费标准：");
			standard = sc.nextFloat();
			
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			sql = "UPDATE charge_standards SET begin=?, end=?, standard=? WHERE id=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setFloat(1, begin);
			preStmt.setFloat(2, end);
			preStmt.setFloat(3, standard);
			preStmt.setInt(4, id);
			preStmt.executeUpdate();
			System.out.println("修改成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Tools.release(preStmt, conn, rs);
		}
	}
}
