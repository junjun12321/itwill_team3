package qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDAO {
private Connection getConnection() throws Exception {
		
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/MysqlDB");
		Connection con = ds.getConnection();
		return con;
	} // 디비연결

public void insertQna(QnaDTO dto) {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		
		con = getConnection();

		int qno=1;

		String sql = "select max(qno) from qna";
		pstmt = con.prepareStatement(sql);
		// 4
		rs=pstmt.executeQuery();
		// 5
		if(rs.next()) {
			qno=rs.getInt("max(qno)")+1;
		}
		sql="insert into qna(qno, no, qtitle, qcontent, qcount, qpw, qtype, qdate) values(?,?,?,?,?,?,?,?)";
		pstmt=con.prepareStatement(sql);

		pstmt.setInt(1, qno);  
		pstmt.setInt(2, dto.getNo()); 
		pstmt.setString(3, dto.getQtitle());
		pstmt.setString(4, dto.getQcontent());
		pstmt.setInt(5, dto.getQcount());
		pstmt.setInt(6, dto.getQpw());
		pstmt.setString(7, dto.getQtype());
		pstmt.setTimestamp(8, dto.getQdate());

		pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if(rs!=null) try { rs.close();} catch (Exception e2) {}
		if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
		if(con!=null) try { con.close();} catch (Exception e2) {}
	}
	return;
}//insertQna() 

public ArrayList<QnaDTO> getQnaList(int startRow,int pageSize){
	System.out.println("QnaDAO getQnaList()");
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	ArrayList<QnaDTO> qnaList=new ArrayList<>();
	try {
		con=getConnection();

		String sql="select * from qna order by num desc limit ?, ?";
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, startRow-1);
		pstmt.setInt(2, pageSize);
		
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			
			QnaDTO dto=new QnaDTO();
			dto.setNo(rs.getInt("no"));
			dto.setQno(rs.getInt("qno"));
			dto.setQtitle(rs.getString("qtitle"));
			dto.setQcontent(rs.getString("qcontent"));
			dto.setQcount(rs.getInt("qcount"));
			dto.setQpw(rs.getInt("qpw"));
			dto.setQtype(rs.getString("qtype"));
			dto.setQdate(rs.getTimestamp("qdate"));
			
			qnaList.add(dto);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if(rs!=null) try { rs.close();} catch (Exception e2) {}
		if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
		if(con!=null) try { con.close();} catch (Exception e2) {}
	}
	return qnaList;
}// getQnaList()
}


