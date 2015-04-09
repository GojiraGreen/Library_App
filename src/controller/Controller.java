package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Account;
import beans.Admin;
import beans.Book;
import beans.User;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds = null;
	private Map<String, String> actions;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		this.actions = new HashMap<>();
		this.actions.put("home", "/home.jsp");
		this.actions.put("login", "/login.jsp");
		this.actions.put("contact", "/contact.jsp");
		this.actions.put("browse", "/browse.jsp");
		this.actions.put("userpage", "/userpage.jsp");
		this.actions.put("request", "/request.jsp");
		this.actions.put("admin", "/admin.jsp");
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		InitialContext initContext;
		try {
			initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			this.ds = (DataSource) env.lookup("jdbc/library");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Create connection to DataSource
	 * 
	 * @return
	 * @throws IOException
	 */
	private Connection dsConnect()
			throws IOException {
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// action parameter for jsp files or user management
		String action = request.getParameter("action");
		
		// invoking proper jsp file
		try {
			reqDispRouting(request, response, action);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		//session attributes
		HttpSession session = request.getSession();
		
		// action parameter for jsp files or user management
		String action = request.getParameter("action");

		Connection conn = dsConnect();

		if (action.equals("dologin")) {
			try {
				doLogin(request, response, session, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(action.equals("dologinadmin")){
			//logout user if logged in
			session.setAttribute("USER", null);
			
			try {
				doLoginAdmin(request, response, session, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (action.equals("createaccount")) {
			try {
				createAccount(request, response, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		}
	}

	/**
	 * Login administrator to the system.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doLoginAdmin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn)
			throws SQLException, ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Admin acc = new Admin(email, conn);
		
		request.setAttribute("email", email);
		request.setAttribute("password", "");

		try {
			if (acc.login(email, password)) {
				session.setAttribute("ADMIN", email);
				conn.close();
				request.getRequestDispatcher("/admin.jsp").forward(request,
						response);
			}
			else {
				request.setAttribute("message", "Email or password incorrect.");
				conn.close();
				request.getRequestDispatcher("/loginadmin.jsp").forward(request,
					response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * Create user account.
	 * 
	 * @param request
	 * @param response
	 * @param conn
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void createAccount(HttpServletRequest request,
			HttpServletResponse response, Connection conn) throws ServletException,
			IOException, SQLException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repeatpassword = request.getParameter("repeatpassword");
		Account acc = new Account(email, conn);
		User user = new User(email, password);
		
		if (!password.equals(repeatpassword)) {
			request.setAttribute("message", "Password's dont match.");
			conn.close();
			request.getRequestDispatcher("/createuser.jsp").forward(
					request, response);
		}
		else if (!user.validate()) {
			request.setAttribute("message", user.getMessage());
			conn.close();
			request.getRequestDispatcher("/createuser.jsp").forward(
					request, response);
		}
		else {
			try {
				acc.create(email, password);
				request.setAttribute("message",
						"Account created, please login.");
				conn.close();
				request.getRequestDispatcher("/login.jsp").forward(request,
						response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Login user into system.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param conn
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void doLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Connection conn)
			throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Account acc = new Account(email, conn);
		
		session.setAttribute("ADMIN", null);
		request.setAttribute("email", email);
		request.setAttribute("password", "");

		try {
			if (acc.login(email, password)) {
				session.setAttribute("USER", email);
				conn.close();
				request.getRequestDispatcher("/home.jsp").forward(request,
						response);
			}
			else {
				request.setAttribute("message", "Username or password incorrect.");
				conn.close();
				request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Invokes jsp file or specific action, according to action parameter.
	 * 
	 * @param request
	 * @param response
	 * @param action
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void reqDispRouting(HttpServletRequest request,
			HttpServletResponse response, String action)
			throws ServletException, IOException, SQLException{

		
		// DataSource connection to database
		Connection conn = dsConnect();

		action = specificActions(request, action, conn);
		
		if (this.actions.containsKey(action)) {
			conn.close();
			request.getRequestDispatcher(this.actions.get(action)).forward(
					request, response);
		}
		else {
			conn.close();
			request.getRequestDispatcher(this.actions.get("home")).forward(
						request, response);
		}
	}

	/**
	 * Specific actions: logout, delete user,
	 *  user book request and request deletion.
	 * 
	 * @param request
	 * @param action
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private String specificActions(HttpServletRequest request, String action,
			Connection conn) throws SQLException {
		// session attributes
		HttpSession session = request.getSession();
		
		// admin for specific actions
		String admin = (String)session.getAttribute("ADMIN");
		
		// user for book request
		String user = (String)session.getAttribute("USER");
		
		// book selected for request.jsp
		String bookId = request.getParameter("bid");
		

		// Specific actions logout/delete user/delete request
		if (action != null && action.equals("logout")) {
			session.setAttribute("ADMIN", null);
			session.setAttribute("USER", null);
			action = "home";
		}
		else if(action != null && action.equals("userpage")){
			String email = (String) session.getAttribute("USER");
			Account acc = new Account(email, conn);
			String bookid = String.valueOf(acc.getBookId());
			request.setAttribute("bookId", bookid);
		}
		/**
		 * Deleting user by admin
		 */
		else if(action != null && action.equals("du")){
			
			// user selected by admin for specific action
			String userId = request.getParameter("uid");
			
			Admin adminAcc = new Admin(admin, conn);
			if(admin != null){
				boolean success = adminAcc.deleteUser(userId);
				if(success){
					request.setAttribute("message", "User deletion succeed.");
				}
				else {
					request.setAttribute("message", "User deletion failed.");
				}
				action = "admin";
			}
			else {
				request.setAttribute("message", "You dont have permission for that action.");
				action = "home";
			}
		}
		/**
		 * Deleting user book request.
		 */
		else if(admin != null && action != null && action.equals("dr")){
			
			// book selected for request.jsp
			String reqBookId = request.getParameter("rbid");
			
			// user selected by admin for specific action
			String userId = request.getParameter("uid");
			
			Admin adminAcc = new Admin(admin, conn);
			Book book = new Book(conn);
			
			book.bookAvailable(reqBookId);
			boolean success = adminAcc.deleteUserRequest(userId);
			if(success){
				request.setAttribute("message", "Request deletion succeed.");
			}
			else {
				request.setAttribute("message", "Request deletion failed.");
			}
			action = "admin";
		}
		/**
		 * If user requested book bookId
		 * updates database.
		 */
		else if (bookId != null && action.equals("request")) {
			
			Book book = new Book(conn);
			Account acc = new Account(user, conn);
			if(user != null){
				//User book request
				boolean success = acc.requestBook(bookId);
				if(success){
					book.bookUnavailable(bookId);
					request.setAttribute("message", "You have successfully requested a book.");
				}
				else {
					request.setAttribute("fail", "fail");
					request.setAttribute("message", "Sorry. You can have only one book request.");
				}
			}
			else {
				request.setAttribute("message", "You dont have permission for that action.");
				action = "home";
			}
		}
		return action;
	}
}
