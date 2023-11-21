package animal.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import animal.vo.AnimalVO;

public class AnimalDAO {
	ArrayList<AnimalVO> animalVOList;
	String[] searchColName = { "id", "sex", "keeper", "age", "spec_name", "name" };
	
	public ArrayList<AnimalVO> select(String searchmod, int comboSearchIndex) {
		Connection con = JDBCConnector.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		animalVOList = new ArrayList<AnimalVO>();
		try {
			String sql = "select id, sex, keeper, age, name, spec_name from animal, spec where animal.spec = spec.spec and "
					+ searchColName[comboSearchIndex] + " like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchmod + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AnimalVO vo = new AnimalVO();
				vo.setId(rs.getInt("id"));
				vo.setSex(rs.getString("sex"));
				vo.setKeeper(rs.getString("keeper"));
				vo.setAge(rs.getInt("age"));
				vo.setspecName(rs.getString("spec_name"));
				vo.setName(rs.getString("name"));
				animalVOList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return animalVOList;
	}

	public void insert(AnimalVO vo) {
		Connection con = JDBCConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO animal VALUES(?, ?, ?, ?, ?, ?);";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getId());
			pstmt.setString(2, vo.getSex());
			pstmt.setString(3, vo.getKeeper());
			pstmt.setInt(4, vo.getAge());
			int specId = 0;
			switch (vo.getspecName()) {
			case "사자":
				specId = 10;
				break;
			case "호랑이":
				specId = 20;
				break;

			case "팬더":
				specId = 30;
				break;

			case "곰":
				specId = 40;
				break;

			case "기린":
				specId = 50;
				break;
			}
			pstmt.setInt(5, specId);
			pstmt.setString(6, vo.getName());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void update(AnimalVO vo) {
		Connection con = JDBCConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "UPDATE animal SET sex=?, keeper=?, age=?, spec=?, name=? WHERE id=?;";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getSex());
			pstmt.setString(2, vo.getKeeper());
			pstmt.setInt(3, vo.getAge());
			int specId = 0;
			switch (vo.getspecName()) {
			case "사자":
				specId = 10;
				break;
			case "호랑이":
				specId = 20;
				break;

			case "팬더":
				specId = 30;
				break;

			case "곰":
				specId = 40;
				break;

			case "기린":
				specId = 50;
				break;
			}
			pstmt.setInt(4, specId);
			pstmt.setString(5, vo.getName());
			pstmt.setInt(6, vo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(AnimalVO vo) {
		Connection con = JDBCConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM animal WHERE id=?;";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void backup () throws FileNotFoundException {
		Connection con = JDBCConnector.getCon();
		try {
			File file = new File("C:\\backup.txt");
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			Statement stmt = con.createStatement();
			String sql = "select * from animal, spec where animal.spec = spec.spec;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getInt("id") + "   ");
				System.out.print(rs.getString("sex") + "   ");
				System.out.print(rs.getString("keeper") + "   ");
				System.out.print(rs.getInt("age") + "   ");
				System.out.print(rs.getString("spec_name") + "   ");
				System.out.println(rs.getString("name"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
