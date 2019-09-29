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
			System.out.println("�����뿨�ţ�");
			account = sc.next();
			System.out.println("���������룺");
			inputPassword = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
			String sql = "SELECT * FROM cards WHERE account=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			rs = preStmt.executeQuery();
			rs.next();
			searchPassword = rs.getString("password");
			balance = rs.getFloat("balance");
			if (inputPassword.equals(searchPassword)) {
				if (balance > 0) {
					System.out.println("�ÿ����Ϊ " + balance);
					curtime = System.currentTimeMillis();
					//sql = "UPDATE cards SET begin=?, online=? WHERE account=?";
					sql = "UPDATE cards SET begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// ���ܳ��� sql��丳ֵ����
					preStmt.setLong(1, curtime);
//					preStmt.setInt(2, 1);
//					preStmt.setString(3, account);
					preStmt.setString(2, account);
					preStmt.executeUpdate();
					//��¼�˺ŵ�����ʱ��
					String datatime = simpleDateFormat.format(date); 
					sql = "INSERT INTO query_statistics(account,datetime)" + "VALUES(?,?)";
					preStmt = conn.prepareStatement(sql);
					preStmt.setString(1, account);
					preStmt.setString(2, datatime);
					preStmt.executeUpdate();
					System.out.println("��ʼ�ϻ�");
				} else {
					System.out.println("�ÿ����Ϊ" + balance + "�����ȳ�ֵ");
				}
			} else {
				System.out.println("�����������");
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
			System.out.println("�����뿨�ţ�");
			account = sc.next();
			// ������ݿ������
			conn = Tools.getConnection();
			// ִ�е�SQL���
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
				System.out.println("���˺Ų����ϻ�״̬�������˺������Ƿ�����");
			else {
				//������Ҫ֧���Ľ��
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
				//�������Ѽ�¼
				sql = "SELECT * FROM query_statistics WHERE account=?";
				preStmt = conn.prepareStatement(sql);
				preStmt.setString(1, account);
				rs = preStmt.executeQuery();
				rs.afterLast();
				rs.previous();
				datetime = rs.getString("datetime");
				sql = "UPDATE query_statistics SET money=? WHERE datetime=?";
				preStmt = conn.prepareStatement(sql);// ���ܳ��� sql��丳ֵ����
				preStmt.setFloat(1, payment);
				preStmt.setString(2, datetime);
				preStmt.executeUpdate();
				
				System.out.println("�˺ţ�" + account + "�»��ɹ�");
				System.out.println("�ÿ�ԭ���Ϊ " + balance);
				System.out.println("�����ϻ�ʱ��Ϊ" + time + "Сʱ");
				System.out.println("��۷�" + payment + "Ԫ������1Ԫ��1Ԫ�۷ѣ�");
				pendingPayment = balance - payment;
				if(pendingPayment >= 0) {
					sql = "UPDATE cards SET balance=? , begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// ���ܳ��� sql��丳ֵ����
					preStmt.setFloat(1, pendingPayment);
					preStmt.setLong(2, 0);
					preStmt.setString(3, account);
					preStmt.executeUpdate();
					System.out.println("�����Ϊ" + pendingPayment + "��������ϣ���ӭ�´ι���");
				}else {
					sql = "UPDATE cards SET balance=? , begin=? WHERE account=?";
					preStmt = conn.prepareStatement(sql);// ���ܳ��� sql��丳ֵ����
					preStmt.setFloat(1, 0);
					preStmt.setLong(2, 0);
					preStmt.setString(3, account);
					preStmt.executeUpdate();
					System.out.println("����������֧�������ϻ����ã��벹��" 
							+ Math.abs(pendingPayment) + "Ԫ");
				}
			}
		} finally {
			Tools.release(preStmt, rs);
		}
	}
}
