package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * @author gojira
 *
 * This bean is managing user's accounts.
 * Login, create, existence, book request, only admin
 * can remove user book request.
 */
public class Account {

	/**
	 * Connection to database trough DataSource
	 */
	private Connection conn;
	
	/**
	 * Email address of user acc
	 */
	private String email = "";
	
	/**
	 * Id of the book requested by user
	 */
	private int bookId = 0;
	
	public Account(String email, Connection conn) throws SQLException {
		this.conn = conn;
		this.email = email;
		this.bookId = checkBookId(email, conn);
	}

	/**
	 * Checking if user have a book in his possession.
	 * 
	 * @param email
	 * @param conn
	 * @throws SQLException
	 */
	private int checkBookId(String email, Connection conn)
			throws SQLException {
		String sql = "SELECT * FROM users WHERE email=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, email);
		
		ResultSet rset = statement.executeQuery();
		
		if(rset.next()){
			return rset.getInt("book_id");
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Login user to system.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean login(String email, String password) throws SQLException{
		String sql = "SELECT COUNT(*) AS count FROM users WHERE email=? and password=?";
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
	 * Create user in database table users
	 * @param email
	 * @param password
	 * @throws SQLException
	 */
	public void create(String email, String password) throws SQLException{
		String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, email);
		statement.setString(2, password);
		
		statement.executeUpdate();
		
		statement.close();
	}
	
	/**
	 * Checking if user exists in database table users
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean exists(String email) throws SQLException{
		String sql = "select count(*) as count from users where email=?";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, email);

		ResultSet rset = statement.executeQuery();

		int count = 0;

		if (rset.next()) {
			count = rset.getInt("count");
		}
		
		rset.close();

		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * User book request
	 * @param bookId
	 * @return
	 * @throws SQLException
	 */
	public boolean requestBook(String bookId) throws SQLException {
		
		if (this.bookId > 0) {
			return false;
		}
		else {
			String sql = "update users set book_id=? where email=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, bookId);
			statement.setString(2, this.email);
			statement.executeUpdate();
			
			return true;
		}
	}
	
	/**
	 * Return bookId which holds id of the book requested by user
	 * @return
	 */
	public int getBookId(){
		return this.bookId;
	}
}








