package products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AppointmentDAO {
	public Connection getConnection() throws Exception{
		Context init=new InitialContext();
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB");
		Connection con=ds.getConnection();
		return con;
	}
	
	// 예약 등록
	public void insertAppointment(AppointmentDTO dto) {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=getConnection();
			String sql="insert into Appointment(ano,pno,no,astatus,adate) values(?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAno());  
			pstmt.setInt(2, dto.getPno()); 
			pstmt.setInt(3, dto.getNo());
			pstmt.setInt(4, dto.getAstatus());
			pstmt.setTimestamp(5, dto.getAdate());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return;
	}//insertAppointment() 메서드
	
	// 회원별 예약상황 조회
	public ArrayList<AppointmentDTO> getUserAppointmentList(int no){
		ArrayList<AppointmentDTO> AppointmentList=new ArrayList<AppointmentDTO>();
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select * from appointment where no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();	
			while(rs.next()) {
				AppointmentDTO dto=new AppointmentDTO();
				dto.setNo(rs.getInt("no"));
				dto.setAno(rs.getInt("ano"));
				dto.setPno(rs.getInt("pno"));
				dto.setAstatus(rs.getInt("astatus"));
				dto.setAdate(rs.getTimestamp("adate"));
				AppointmentList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return AppointmentList;
	}//getAppointmentList()
	
	// 예약 테이블 조회
	public ArrayList<AppointmentDTO> getAdminAppointmentList(){
		ArrayList<AppointmentDTO> AppointmentList=new ArrayList<AppointmentDTO>();
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select * from appointment";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			while(rs.next()) {
				AppointmentDTO dto=new AppointmentDTO();
				dto.setNo(rs.getInt("no"));
				dto.setAno(rs.getInt("ano"));
				dto.setPno(rs.getInt("pno"));
				dto.setAstatus(rs.getInt("astatus"));
				dto.setAdate(rs.getTimestamp("adate"));
				AppointmentList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return AppointmentList;
	}//getAppointmentList()
	
	// ano로 예약 테이블 조회
	public AppointmentDTO AppointmentCheck(int ano) {
		AppointmentDTO dto=null;
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = getConnection();
			String sql="select * from Appointment where ano=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, ano);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dto=new AppointmentDTO();
				dto.setAno(rs.getInt("ano"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return dto;
	}//appointmentCheck()
	
	// 예약취소
	public void deleteAppointment(int ano) {
		Connection con =null;
		PreparedStatement pstmt2=null;
		try {
			con=getConnection();
			String sql2="delete from Appointment where ano=?";
			pstmt2=con.prepareStatement(sql2);
			pstmt2.setInt(1, ano);  //set 문자열 (1번째 물음표, 값 id)
			pstmt2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt2!=null) try { pstmt2.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
	}//deleteAppointment()
	
	// 회원별 예약 리스트 조회
	public ArrayList<AppointmentDTO> getAppointmentList(int no, int startRow,int pageSize){
		ArrayList<AppointmentDTO> AppointmentList=new ArrayList<AppointmentDTO>();
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select * from Appointment where no=? order by ano desc limit ?, ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);
			rs=pstmt.executeQuery();	
			while(rs.next()) {
				AppointmentDTO dto=new AppointmentDTO();
				dto.setNo(rs.getInt("no"));
				dto.setAno(rs.getInt("ano"));
				dto.setPno(rs.getInt("pno"));
				dto.setAstatus(rs.getInt("astatus"));
				dto.setAdate(rs.getTimestamp("adate"));
				AppointmentList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return AppointmentList;
	}//getAppointmentList()
			
	// 예약리스트 조회
	public ArrayList<AppointmentDTO> getAppointmentList2(int startRow,int pageSize){
		ArrayList<AppointmentDTO> AppointmentList=new ArrayList<AppointmentDTO>();
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=getConnection();
			String sql="select * from Appointment order by ano desc limit ?, ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			pstmt.setInt(2, pageSize);
			rs=pstmt.executeQuery();	
			while(rs.next()) {
				AppointmentDTO dto=new AppointmentDTO();
				dto.setNo(rs.getInt("no"));
				dto.setAno(rs.getInt("ano"));
				dto.setPno(rs.getInt("pno"));
				dto.setAstatus(rs.getInt("astatus"));
				dto.setAdate(rs.getTimestamp("adate"));
				AppointmentList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
		return AppointmentList;
	}//getAppointmentList()
	
	// 회원별 예약 갯수 계산
	public int getAppointmentCount(int no) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=getConnection();
			String sql="select count(*) from Appointment where no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt("count(*)");	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}				
		}
		return count;
	}
	
	// 전체 예약 갯수 계산
	public int getAdminAppointmentCount() {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=getConnection();
			String sql="select count(*) from Appointment";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt("count(*)");	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try { rs.close();} catch (Exception e2) {}
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}				
		}
		return count;
	}
	
	// 예약 상태 변경(관리자 송금확인)
	public void updateAppointment(AppointmentDTO dto) {
		Connection con =null;
		PreparedStatement pstmt=null;
		try {
			con=getConnection();
			String sql="update Appointment set astatus=? where ano=?";
			pstmt=con.prepareStatement(sql);
			//? 채워넣기
			pstmt.setInt(1, dto.getAstatus()); //set 문자열(1번째 물음표, 값 name)
			pstmt.setInt(2, dto.getAno());  
			System.out.println(dto.getAstatus());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try { pstmt.close();} catch (Exception e2) {}
			if(con!=null) try { con.close();} catch (Exception e2) {}
		}
	}//updateAppointment()
	
}
