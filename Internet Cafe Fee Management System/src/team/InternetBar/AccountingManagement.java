package team.InternetBar;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class AccountingManagement {
	public void useTheComputer() throws SQLException  {
		String account, inputPassword, searchPassword;
		long curtime;
		float balance;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("请输入卡号：");
			account = sc.next();
			System.out.println("请输入密码：");
			inputPassword = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			balance = rs.getFloat("balance");
			if (inputPassword.equals(searchPassword)) {
				if (balance > 0) {
					System.out.println("该卡余额为 " + balance);
					curtime = System.currentTimeMillis();
					//sql = "UPDATE cards SET begin=?, online=? WHERE account=?";
					sql = "UPDATE cards SET begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// 可能出错 sql语句赋值两次
					preStmt.setLong(1, curtime);
//					preStmt.setInt(2, 1);
//					preStmt.setString(3, account);
					preStmt.setString(2, account);
					preStmt.executeUpdate();
					//记录账号的消费时间
					String datatime = simpleDateFormat.format(date); 
					sql = "INSERT INTO query_statistics(account,datetime)" + "VALUES(?,?)";
					preStmt = conn.prepareStatement(sql);
					preStmt.setString(1, account);
					preStmt.setString(2, datatime);
					preStmt.executeUpdate();
					System.out.println("开始上机");
				} else {
					System.out.println("该卡余额为" + balance + "，请先充值");
				}
			} else {
				System.out.println("输入密码错误");
			}
		} finally {
			Tools.release(preStmt, rs);
		}
	}

	public void stop() throws SQLException {
		String account, inputPassword, searchPassword, datetime;
		long beginTime, curTime;
		float sBeginTime, sEndTime, standard, balance, payment, pendingPayment, time;
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("请输入卡号：");
			account = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			balance = rs.getFloat("balance");
			beginTime = rs.getLong("begin");
			curTime = System.currentTimeMillis();
			time = (curTime - beginTime) / 1000;
			time = time / 60 / 60;
			if (time == 0)
				System.out.println("该账号不在上机状态，请检查账号输入是否有误");
			else {
				//计算需要支付的金额
				sql = "SELECT * FROM charge_standards";
				preStmt = conn.prepareStatement(sql);
				rs = preStmt.executeQuery();
				payment = 0;
				while(rs.next()) {
					sBeginTime = rs.getFloat("begin");
					sEndTime = rs.getFloat("end");
					standard = rs.getFloat("standard");
					if(time < sEndTime) {
						payment = payment + time * standard;
						break;
					}else {
						payment = payment + (sEndTime - standard) * standard;
					}
				}
				payment = Float.parseFloat(String.format("%.1f", payment));
				if(payment < 1)
					payment = 1;
				//记下消费记录
				sql = "SELECT * FROM query_statistics WHERE account=?";
				preStmt = conn.prepareStatement(sql);
				preStmt.setString(1, account);
				rs = preStmt.executeQuery();
				rs.afterLast();
				rs.previous();
				datetime = rs.getString("datetime");
				sql = "UPDATE query_statistics SET money=? WHERE datetime=?";
				preStmt = conn.prepareStatement(sql);// 可能出错 sql语句赋值两次
				preStmt.setFloat(1, payment);
				preStmt.setString(2, datetime);
				preStmt.executeUpdate();
				
				System.out.println("账号：" + account + "下机成功");
				System.out.println("该卡原余额为 " + balance);
				System.out.println("本次上机时长为" + time + "小时");
				System.out.println("需扣费" + payment + "元（不足1元则按1元扣费）");
				pendingPayment = balance - payment;
				if(pendingPayment >= 0) {
					sql = "UPDATE cards SET balance=? , begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// 可能出错 sql语句赋值两次
					preStmt.setFloat(1, pendingPayment);
					preStmt.setLong(2, 0);
					preStmt.setString(3, account);
					preStmt.executeUpdate();
					System.out.println("现余额为" + pendingPayment + "，结算完毕，欢迎下次光临");
				}else {
					sql = "UPDATE cards SET balance=? , begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// 可能出错 sql语句赋值两次
					preStmt.setFloat(1, 0);
					preStmt.setLong(2, 0);
					preStmt.setString(3, account);
					preStmt.executeUpdate();
					System.out.println("您的余额不足以支付本次上机费用，请补交" 
							+ Math.abs(pendingPayment) + "元");
				}
			}
		} finally {
			Tools.release(preStmt, rs);
		}
	}
}
