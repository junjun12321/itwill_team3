package board;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import member.UserDTO;

    //DB연결
    public class BoardDAO {
    	public Connection getConnection() throws Exception{
    		Context init=new InitialContext();
    		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB");
    		Connection con=ds.getConnection();
    		return con;
    	}
   
	    //게시판에 넣을 데이터들의 메서드생성
	    public void insertBoard(BoardDTO dto) {
	    	System.out.println("BoardDAO insertBoard()");
	    	
	    	Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
		try {
			con=getConnection();
			
			int bno=1;
			
			String sql="select max(bno) from board";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				bno=rs.getInt("max(bno)")+1;
			}
			
			sql="insert into board(bno, no, btitle, bcontent, bcount, bdate) values (?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			pstmt.setInt(2, dto.getNo());
			pstmt.setString(3, dto.getBtitle());
			pstmt.setString(4, dto.getBcontent());
			pstmt.setInt(5, dto.getBcount());
			pstmt.setTimestamp(6, dto.getBdate());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		 }
		return;
		}// insertBoard()
		
	    
		//-boardList.jsp 연결메서드
		public ArrayList<BoardDTO> getBoardList(int startRow, int pageSize){
			System.out.println("BoardDAO getBoardList()");
			ArrayList<BoardDTO> boardList=new ArrayList<>();
			Connection con =null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				con=getConnection();
				
				String sql="select * from board order by bno desc limit ?,?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, startRow-1);
				pstmt.setInt(2, pageSize);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					BoardDTO dto=new BoardDTO();

					dto.setBno(rs.getInt("bno"));
					dto.setNo(rs.getInt("no"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setBcontent(rs.getString("bcontent"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBdate(rs.getTimestamp("bdate"));
					
					boardList.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) try { rs.close();} catch (Exception e2) {}
				if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
				if(con!=null) try { con.close();} catch (Exception e2) {}
			}
			return boardList;
		}// getBoardList()
		
		
		//-content.jsp 연결메서드
		public BoardDTO getBoard(int bno) {
			System.out.println("BoardDAO getBoard()");
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			BoardDTO dto=null;
			try {
				con=getConnection();
				
				String sql="select * from board where bno=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					dto=new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setNo(rs.getInt("no"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setBcontent(rs.getString("bcontent"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBdate(rs.getTimestamp("bdate"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) try { rs.close();} catch (Exception e2) {}
				if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
				if(con!=null) try { con.close();} catch (Exception e2) {}
			}
			return dto;
		}//getBoard()
			
		
		//-boardList.jsp 연결메서드(페이징기능 메서드정의)
		public int getBoardCount() {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int count=0;
			try {
				con=getConnection();
				
				String sql="select count(*) from board";
				pstmt=con.prepareStatement(sql);
				
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					count=rs.getInt("count(*)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
					if(rs!=null) try { rs.close();} catch (Exception e2) {}
					if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
					if(con!=null) try { con.close();} catch (Exception e2) {}
			}
			return count;
		}//getBoardCount()
			
		
		//-board_deletePro.jsp 연결메서드
		public void deleteBoard(int bno) {
			System.out.println("BoardDAO deleteBoard()");
			Connection con=null;
			PreparedStatement pstmt=null;
			try {
				
				con=getConnection();
			
				String sql="delete from board where bno=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				
				if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
				if(con!=null) try { con.close();} catch (Exception e2) {}
			}
		}//deleteBoard()
		
		
		public void updateBoard(BoardDTO dto) {
			System.out.println("BoardDAO updateBoard()");
			Connection con=null;
			PreparedStatement pstmt=null;
			try {
				// 1~2 단계
				con=getConnection();
				// 3단계 sql
				String sql="update board set btitle=?, bcontent=? where bno=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, dto.getBtitle());
				pstmt.setString(2, dto.getBcontent());
				pstmt.setInt(3, dto.getBno());
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
				if(con!=null) try { con.close();} catch (Exception e2) {}
			}
		}//updateBoard()
			
//		public void usertype(UserDTO dto) {
//			Connection con=null;
//			PreparedStatement pstmt=null;
//			
//			try {
//				con=getConnection();
//				
//				String sql="select id from users where utype=?";
//				pstmt=con.prepareStatement(sql);
//				pstmt.setInt(1, dto.getUtype());
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally {
//				if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
//				if(con!=null) try { con.close();} catch (Exception e2) {}
//			}
//			
//		}
				
		
			
	
			
			
			
			
    
    }//class
	   

//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//
//public class BoardDAO {
//	public Connection getConnection() throws Exception{
//		//서버에서 미리 1, 2 단계 => 디비연결 => 이름을 불러 연결정보를 가져오기
//		// => 속도 향상, 디비연결 정보 수정 최소화
//		// DataBase Connection Pool (DBCP)=> 디비 연결정보 서버 저장
//		// 1. META-INF context.xml (디비연결정보)
//		// 2. MemberDAO 디비연결정보 불러서 사용
//		Context init=new InitialContext();
//		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB");
//		Connection con=ds.getConnection();
//		return con;
//	}
//	
//	
//	public int getBoardCount() {
//		int count=0;
//		Connection con =null;
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		try {
//			//1,2 디비연결 메서드
//			con=getConnection();
//			
//			//3단계 SQL구문 만들어서 실행할 준비(select 조건)
//			String sql="select count(*) from board";
//			pstmt=con.prepareStatement(sql);
//
//			//4단계 SQL구문을 실행(select) => 결과 저장
//			rs=pstmt.executeQuery();
//			//5단계 결과를 출력, 데이터 담기 (select)
//			// next() 다음행 => 리턴값 데이터 있으면 true/ 데이터 없으면 false
//			//조건이 true 실행문=> 다음행 데이터 있으면 true =>  열접근 출력
//			if(rs.next()){
//				//next() 다음행 => 리턴값 데이터 있으면 true/ 아이디 일치
//				count=rs.getInt("count(*)");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			// 예외 상관없이 마무리작업 => 객체생성한 기억장소 해제
//			if(rs!=null) try { rs.close();} catch (Exception e2) {}
//			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
//			if(con!=null) try { con.close();} catch (Exception e2) {}
//		}
//		return count;
//	}
//
//}

