package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;

public class LogIn {

	private static LogIn instance;
	
	public static LogIn getInstance(){
		if (instance==null) instance = new LogIn();
		return instance;
	}
	
	public boolean login(String username, String password) throws SQLException {
		return SQLManager.getInstance().login(username, password);
	}
	
}
