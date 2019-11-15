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
			System.out.println("���������Ա�˺ţ�");
			account = sc.next();
			System.out.println("���������룺");
			inputPassword = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM privilege_management WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			level = rs.getInt("level");
			if (inputPassword.equals(searchPassword)) {
				if (level == 0) {
					// ��ͨ����Ա�Ĳ���
					System.out.println("��ӭ��������Ա");
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
							System.out.println("�޴�ѡ��");
							break;
						}
						menu.administratorMenu();
						temp = sc.nextInt();
					}
				} else if (level == 1) {
					// ��������Ա�Ĳ���
					System.out.println("��ӭ������������Ա");
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
							System.out.println("�޴�ѡ��");
							break;
						}
						menu.superAdministratorMenu();
						temp = sc.nextInt();
					}
				}
			} else {
				System.out.println("�����������");
			}
		} finally {
			Tools.release(preStmt, rs);
		}
	}
}
