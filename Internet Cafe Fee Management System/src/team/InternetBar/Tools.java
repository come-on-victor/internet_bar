package team.InternetBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Tools {
	static Connection conn;
	static {
		// 加载驱动,并建立数据库连接
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/internet_bar?useSSL=false";
		String username = "root";
		String password = "123456";
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	//释放数据库连接
	public static void releaseConn() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	//释放资源
	public static void release(PreparedStatement preStmt, ResultSet rs) {
		if(preStmt != null) {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			preStmt = null;
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	} 
	
	public static void release(PreparedStatement preStmt) {
		if(preStmt != null) {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			preStmt = null;
		}
	} 
	
	// 加载驱动,并建立数据库连接
//	public static Connection getConnection() throws SQLException, ClassNotFoundException {
//		Class.forName("com.mysql.jdbc.Driver");
//		String url = "jdbc:mysql://localhost:3306/internet_bar?useSSL=false";
//		String username = "root";
//		String password = "123456";
//		Connection conn = DriverManager.getConnection(url, username, password);
//		return conn;
//	}
	
	//关闭数据库连接,释放资源
//	public static void release(Statement stmt, Connection conn) {
//		if(stmt != null) {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			stmt = null;
//		}
//		if(conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			conn = null;
//		}
//	}
//	public static void release(PreparedStatement preStmt, Connection conn) {
//		if(preStmt != null) {
//			try {
//				preStmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			preStmt = null;
//		}
//		if(conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			conn = null;
//		}
//	}
//	public static void release(PreparedStatement preStmt, Connection conn, ResultSet rs) {
//		if(preStmt != null) {
//			try {
//				preStmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			preStmt = null;
//		}
//		if(conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			conn = null;
//		}
//		if(rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			rs = null;
//		}
//	}
//	public static void release(ResultSet rs, Statement stmt, Connection conn) {
//		if(rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			rs = null;
//		}
//		release(stmt, conn);
//	}
}
