package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Personenverwaltung {
	
	private static Personenverwaltung instance;
	
	public static Personenverwaltung getInstance(){
		if (instance==null) instance = new Personenverwaltung();
		return instance;
	}

	public void createPerson(String vorname, String nachname, String strasse, 
			int hausnr, int PLZ, String email, String nutzername, String passwort, int rolle) throws DatabaseException, SQLException {
		
		SQLManager.getInstance().createPerson(vorname, nachname, strasse, hausnr, PLZ, email, nutzername, passwort, rolle);
	}
	
	public void deletePerson(int id) throws SQLException, DatabaseException {
		
		SQLManager.getInstance().deletePerson(id);
	}
	
	public void modifyPerson(int id, String attribute, String newData) throws SQLException {
		
		SQLManager.getInstance().modifyPerson(id, attribute, newData);
	}
	
	public boolean checkadmin(String nutzername) throws SQLException {
		
		SQLManager.getInstance().checkAdmin(nutzername);
		
		return false;
	}
}
