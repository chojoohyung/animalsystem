package animal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnector {
	private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/animalsystem?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	private static final String ID = "root";
	private static final String PASSWORD = "1234";

	private static Connection con;

	public static Connection getCon() {
		try {
			Class.forName(DRIVER_PATH);
			//System.out.println("정상적으로 JDBC DRIVER LOAD 하였습니다");
			con = DriverManager.getConnection(URL, ID, PASSWORD);
			System.out.println("");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void resultSetTest() {
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from animal, spec where animal.spec = spec.spec;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getInt("id") + "   ");
				System.out.print(rs.getString("sex") + "   ");
				System.out.print(rs.getString("keeper") + "   ");
				System.out.print(rs.getInt("age") + "   ");
				System.out.println(rs.getString("spec_name"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getCon();
		resultSetTest();
	}

}
