package com.bit2016.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit2016.mysite.vo.BoardVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		System.out.println( "BoardDao");
		
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 :" + e);
		}
		return conn;
	}
	
	public void insert( BoardVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			if( vo.getGroupNo() == null ) {
				/* 새글 등록 */
				String sql = 
					" insert" +
					"   into board" +
					" values( board_seq.nextval, ?, ?, sysdate, 0, nvl((select max(group_no) from board),0) + 1, 1, 0, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString( 1, vo.getTitle() );
				pstmt.setString( 2, vo.getContent() );
				pstmt.setLong( 3, vo.getUserNo() );
			} else {
				/* 답글 등록 */
				String sql = 
					" insert" +
					"   into board" +
					" values( board_seq.nextval, ?, ?, sysdate, 0, ?, ?, ?, ? )"; 
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString( 1, vo.getTitle() );
				pstmt.setString( 2, vo.getContent() );
				pstmt.setInt( 3, vo.getGroupNo() );
				pstmt.setInt( 4, vo.getOrderNo() );
				pstmt.setInt( 5, vo.getDepth() );
				pstmt.setLong( 6, vo.getUserNo() );
			}

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
	}
	public int getTotalCount() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		int totalCount = 0;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
		String sql = "select count(*) from board";
		rs = stmt.executeQuery( sql );
		if( rs.next() ) {
			totalCount = rs.getInt(1);
		}
			
		} catch (SQLException e) {
			System.out.println( "error: " + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( stmt != null ) {
					stmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error: " + e );
			}
		}
		
		return totalCount;
	}
	
	public List<BoardVo> getList(Integer page, Integer size) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = " select * " +
					"   from ( select no, title, hit, reg_date, depth, name, users_no, rownum as rn" +
					"            from(  select a.no, a.title, a.hit, to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date, a.depth, b.name, a.users_no" +
					"                     from board a, users b" +
					"                    where a.users_no = b.no" +
	                "                 order by group_no desc, order_no asc ))" +
	                "  where (?-1)*?+1 <= rn and rn <= ?*?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt( 1, page);
			pstmt.setInt( 2, size );
			pstmt.setInt( 3, page);
			pstmt.setInt( 4, size);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				int hit = rs.getInt( 3 );
				String regDate = rs.getString( 4 );
				int depth = rs.getInt( 5 );
				String userName = rs.getString( 6 );
				long userNo = rs.getLong( 7 );
				
				BoardVo vo = new BoardVo();
				vo.setNo( no );
				vo.setTitle( title );
				vo.setHit( hit );
				vo.setRegDate( regDate );
				vo.setDepth( depth );
				vo.setUserName( userName );
				vo.setUserNo( userNo );
				
				list.add( vo );
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		return list;
	}
	public void increaseGroupOrder(int groupNo, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set order_no = order_no + 1 where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, groupNo );
			pstmt.setInt(2, orderNo );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
	}

	public BoardVo get(long boardNo) {
		BoardVo vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				" select no, title, content, group_no, order_no, depth, users_no" +
				"   from board" +
				"  where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong( 1, boardNo );
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				String content = rs.getString( 3 );
				int groupNo = rs.getInt( 4 );
				int orderNo = rs.getInt( 5 );
				int depth = rs.getInt( 6 );
				long userNo = rs.getLong( 7 );
				
				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
			}
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return vo;
	}
}
