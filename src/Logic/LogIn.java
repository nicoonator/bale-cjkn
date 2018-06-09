package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;

public class LogIn {

	private static LogIn instance;
	
	/**
	 * This method ensures that at most one instance of "LogIn" exists.
	 * 
	 * @author 
	 * @return an instance of "LogIn"
	 */
	public static LogIn getInstance(){
		if (instance==null) instance = new LogIn();
		return instance;
	}
	
	/**
	 * This method tries to login.
	 * 
	 * @author 
	 * @param username
	 * @param password
	 * @return true if login is successful
	 * @throws SQLException
	 */
	public boolean login(String username, String password) throws SQLException {
		return SQLManager.getInstance().login(username, password);
	}
	
}
