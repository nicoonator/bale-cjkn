package DataAccess;

import java.sql.*;

public class SQLManager {
	
	private static SQLManager instance;
	private Connection c;
	
	public static SQLManager getInstance(){
		if (instance==null) instance = new SQLManager();
		return instance;
	}
	
	private SQLManager (){
		
		try{
			Class.forName("org.sqlite.JDBC");
			c= DriverManager.getConnection("jdbc:sqlite:Database");
		}
		
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param vname
	 * @param nname
	 * @param strasse
	 * @param hausnummer
	 * @param PLZ
	 * @param email
	 * @param nutzername
	 * @param passwort
	 * @param Rolle 0: admin, 1: Kunde, 2: Lehrstuhlperson
	 * @throws SQLException
	 */
	public void insertPersonIntoDB (String vname, String nname, String strasse, int hausnummer, int PLZ, String email, String nutzername, String passwort, int rolle) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Person (Vorname, Nachname, strasse, hausnummer, PLZ, email, nutzername, passwort, rolle) VALUES ('"+vname+"','"+nname+"','"+strasse+"','"+hausnummer+"','"+PLZ+"','"+email+"','"+nutzername+"','"+passwort+"','"+rolle+"');";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
}