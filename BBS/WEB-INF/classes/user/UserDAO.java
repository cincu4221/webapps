package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost/bbs?useSSL=false&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "1234";
//			String dbURL = "jdbc:mysql://localhost:3306/BBS";
//			String dbID = "root";
//			String dbPassword = "root";
			Class.forName("com.mysql.cj.jdbc.Driver"); // Class.forname()메소드는 드라이브 로드만을 담당함 연결과는 관련 없음
			System.out.println("test1");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("test2");
		} catch (Exception e) {
			System.out.println("test3");
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			System.out.println("test4");
			pstmt.setString(1, userID);
			System.out.println("test5");
			rs = pstmt.executeQuery();
			System.out.println("test6");
			if (rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					System.out.println("test7");
					return 1; // 로그인 성공
				}
				else {
					System.out.println("test8");
					return 0; // 비밀번호 불일치
				}
			}
			System.out.println("test9");
			return -1; // 아이디가 없음
		} catch (Exception e) {
			System.out.println("test10");
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserID());
			pstmt.setString(2,  user.getUserPassword());
			pstmt.setString(3,  user.getUserName());
			pstmt.setString(4,  user.getUserGender());
			pstmt.setString(5,  user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
