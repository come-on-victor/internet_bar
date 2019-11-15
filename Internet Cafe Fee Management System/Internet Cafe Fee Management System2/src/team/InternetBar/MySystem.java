package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySystem {
	public void signIn() throws SQLException {
		int temp, level;
		String account, inputPassword, searchPassword;
		CardManagement cmg = new CardManagement();
		BillingStandardManagement bsm = new BillingStandardManagement();
		AccountingManagement amg = new AccountingManagement();
		BillingManagement bm = new BillingManagement();
		QueryStatistics qs = new QueryStatistics();
		PriviligeManagement pm = new PriviligeManagement();
		Menu menu = new Menu();
		Scanner sc = Input.getInput();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			System.out.println("请输入管理员账号：");
			account = sc.next();
			System.out.println("请输入密码：");
			inputPassword = sc.next();
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "SELECT * FROM privilege_management WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			level = rs.getInt("level");
			if (inputPassword.equals(searchPassword)) {
				if (level == 0) {
					// 普通管理员的操作
					System.out.println("欢迎您，管理员");
					menu.administratorMenu();
					temp = sc.nextInt();
					while (temp != 0) {
						switch (temp) {
						case 1:
							cmg.addCard();
							break;
						case 2:
							cmg.searchCard();
							break;
						case 3:
							cmg.cancellationCard();
							break;
						case 4:
							bsm.queryBillingStandard();
							break;
						case 5:
							amg.useTheComputer();
							break;
						case 6:
							amg.stop();
							break;
						case 7:
							bm.reCharge();
							break;
						case 8:
							bm.refund();
							break;
						case 9:
							qs.queryConsumptionRecords();
							break;
						default:
							System.out.println("无此选项");
							break;
						}
						menu.administratorMenu();
						temp = sc.nextInt();
					}
				} else if (level == 1) {
					// 超级管理员的操作
					System.out.println("欢迎您，超级管理员");
					menu.superAdministratorMenu();
					temp = sc.nextInt();
					while (temp != 0) {
						switch (temp) {
						case 1:
							cmg.addCard();
							break;
						case 2:
							cmg.searchCard();
							break;
						case 3:
							cmg.cancellationCard();
							break;
						case 4:
							bsm.newBillingStandard();
							break;
						case 5:
							bsm.queryBillingStandard();
							break;
						case 6:
							bsm.deleteBillingStandard();
							break;
						case 7:
							bsm.modifyTheChargingStandard();
							break;
						case 8:
							amg.useTheComputer();
							break;
						case 9:
							amg.stop();
							break;
						case 10:
							bm.reCharge();
							break;
						case 11:
							bm.refund();
							break;
						case 12:
							qs.queryConsumptionRecords();
							break;
						case 13:
							qs.statisticalTotalTurnover();
							break;
						case 14:
							qs.statisticalMonthlyTurnover();
							break;
						case 15:
							pm.addAdministrator();
							break;
						case 16:
							pm.deleteAdministrators();
							break;
						case 17:
							pm.configurationAuthority();
							break;
						default:
							System.out.println("无此选项");
							break;
						}
						menu.superAdministratorMenu();
						temp = sc.nextInt();
					}
				}
			} else {
				System.out.println("输入密码错误");
			}
		} finally {
			Tools.release(preStmt, rs);
		}
	}
}
