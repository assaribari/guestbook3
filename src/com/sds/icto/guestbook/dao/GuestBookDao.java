package com.sds.icto.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sds.icto.guestbook.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		// 1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. connection 생성
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		conn = DriverManager.getConnection(url, "webdb", "webdb");

		return conn;
	}

	public void insert(GuestBookVo vo) {
		try {
			Connection conn = getConnection();

			// 3. Statement 준비

			String sql = "insert into guestbook values(guestbook_seq.nextval , ?, ?, ?, sysdate)";

			PreparedStatement pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			// 5. 쿼리 실행
			pstmt.executeUpdate();

			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	} // insert

	// id 받아서 지울 때
	public void delete(Long no, String password) {
		try {
			// 1. Connection 생성
			Connection conn = getConnection();

			// 2. statement 생성
			Statement stmt = conn.createStatement();

			// 3. 쿼리 실행
			String sql = "delete from guestbook where no = ? and password = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setLong(1, no);
			pstmt.setString(2, password);

			// 5. 쿼리 실행
			pstmt.executeUpdate();

			// 6. 자원 정리
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}

	}

	// 전부 다 지울 때
	public void delete() throws ClassNotFoundException, SQLException {
		// 1. Connection 생성
		Connection conn = getConnection();

		// 2. statement 생성
		Statement stmt = conn.createStatement();

		// 3. 쿼리 실행
		String sql = "delete from guestbook";
		stmt.executeUpdate(sql);

		// 4. 자원 정리
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public List<GuestBookVo> fetchList() {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		try {
			// 1. Connection 생성
			Connection conn = getConnection();

			// 2. statement 생성
			Statement stmt = conn.createStatement();

			// 3. sql문 실행
			String sql = "select * from guestbook";
			ResultSet rs = stmt.executeQuery(sql);

			// 4. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String sysdate = rs.getString(5);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setSysdate(sysdate);

				list.add(vo);
			}

			// 5. 자원정리
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return list;
	}

}
