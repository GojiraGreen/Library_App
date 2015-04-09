package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author gojira
 * Manage books in library.
 */
public class Book {
	private String title = "";
	private String author = "";
	private boolean available;
	private Connection conn = null;
	
	public Book(Connection conn) {
		this.conn = conn;
	}

	public void bookAvailable(String id) throws SQLException{
		String sql = "update books set available=\"1\" where id=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, id);
		
		statement.executeUpdate();
	}
	
	public void bookUnavailable(String id) throws SQLException{
		String sql = "update books set available=\"0\" where id=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, id);
		
		statement.executeUpdate();
	}
	
	public boolean exists(String title, String author) throws SQLException {
		String sql = "select count(*) as count from books where title=? and author=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, title);
		statement.setString(2, author);
		
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return available;
	}	
}
