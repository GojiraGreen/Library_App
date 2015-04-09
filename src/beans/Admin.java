/**
 * 
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Website administrator, delete users
 * delete user book requests
 * 
 * @author gojira
 *
 */
public class Admin {
	private Connection conn = null;
	private String email = "";
	
	/**
	 * Constructor create connection do DataSource
	 * email for login 
	 */
	public Admin(String email, Connection conn) {
		this.email = email;
		this.conn = conn;
	}
	
	/**
	 * Login administrator to system.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean login(String email, String password) throws SQLException{
		String sql = "SELECT COUNT(*) AS count FROM admins WHERE email=? and password=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, email);
		statement.setString(2, password);
		
		int count = 0;
		ResultSet rset = statement.executeQuery();
		
		if(rset.next()){
			count = rset.getInt("count");
		}
		
		rset.close();
		
		if (count == 0){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Remove user from database
	 * @return
	 * @throws SQLException 
	 */
	public boolean deleteUser(String userId) throws SQLException{
		boolean safe = safeCheck(userId);
		
		if(safe){
			String sql = "DELETE FROM users WHERE id=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, userId);
			int success = statement.executeUpdate();
			if(success == 0){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checking if user have book in his possession.
	 * @param userEmail
	 * @return
	 * @throws SQLException 
	 */
	private boolean safeCheck(String userId) throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM users WHERE id=? AND book_id IS NOT NULL";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, userId);
		
		int count = 0;
		ResultSet rset = statement.executeQuery();
		
		if(rset.next()){
			count = rset.getInt("count");
		}
		
		rset.close();
		
		if (count != 0){
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Check if admin exists
	 * @throws SQLException 
	 */
	public boolean validateAdmin() throws SQLException{
		String sql = "SELECT COUNT(*) AS count FROM admins WHERE email=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, this.email);
		
		int count = 0;
		ResultSet rset = statement.executeQuery();
		
		if(rset.next()){
			count = rset.getInt("count");
		}
		
		rset.close();
		
		if (count == 0){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Remove user book request.
	 * @return
	 * @throws SQLException 
	 */
	public boolean deleteUserRequest(String userId) throws SQLException{
		String sql = "UPDATE users SET book_id=NULL WHERE id=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, userId);
		int success = statement.executeUpdate();
		
		if(success == 0){
			return true;
		}
		return false;
	}
}














