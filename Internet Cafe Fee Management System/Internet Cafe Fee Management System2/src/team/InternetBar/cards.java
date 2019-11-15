package team.InternetBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class cards {
	private String account, randNumber;
	private final int MAX = 999999999, MIN = 100000000;
	Scanner sc = Input.getInput();
	Connection conn = null;
	PreparedStatement preStmt = null;
	Random rand = new Random();
	//构造方法
	public cards(String password, String balance) throws SQLException {
		randNumber = Integer.toString(rand.nextInt(MAX - MIN + 1) + MIN);
		try {
			// 获得数据库的连接
			conn = Tools.getConnection();
			// 执行的SQL语句
			String sql = "INSERT INTO cards(account,password,balance)" + "VALUES(?,?,?)";
			account = randNumber;
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, account);
			preStmt.setString(2, password);
			preStmt.setString(3, balance);
			preStmt.executeUpdate();
		} finally {
			Tools.release(preStmt);
		}
	}
	public String getAccount() {
		return account;
	}
}
