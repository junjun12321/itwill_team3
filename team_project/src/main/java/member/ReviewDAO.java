package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReviewDAO {
	
	private Connection getConnection() throws Exception {
		
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/MysqlDB");
		Connection con = ds.getConnection();
		return con;
	}
	
	public void insertReview(ReviewDTO dto) {
		System.out.println("ReviewDAO insertReview()");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con != null) try {con.close();} catch (Exception e2) {}
			if(pstmt != null) try {pstmt.close();} catch (SQLException e) {}
		}
		
	} // insertReview()
	

}
