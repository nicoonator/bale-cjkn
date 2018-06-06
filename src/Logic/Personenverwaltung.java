package Logic;

import java.sql.SQLException;
import java.util.List;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Personenverwaltung {
	
	private static Personenverwaltung instance;
	
	public static Personenverwaltung getInstance(){
		if (instance==null) instance = new Personenverwaltung();
		return instance;
	}

	public void createPerson(String vorname, String nachname, String strasse, 
			String hausnr, int PLZ, String email, String nutzername, String passwort, int rolle) throws DatabaseException, SQLException {
		
		SQLManager.getInstance().createPerson(vorname, nachname, strasse, hausnr, PLZ, email, nutzername, passwort, rolle);
	}
	
	public void deletePerson(int id) throws SQLException, DatabaseException {
		
		SQLManager.getInstance().deletePerson(id);
	}
	
	public void modifyPerson(int id, String attribute, String newData) throws SQLException {
		
		SQLManager.getInstance().modifyPerson(id, attribute, newData);
	}
	
	public boolean checkadmin(String nutzername) throws SQLException {
		
		return SQLManager.getInstance().checkAdmin(nutzername);
		
	}
	public Person getPersonByID(int ID) throws DatabaseException, SQLException {
		return SQLManager.getInstance().getPersonByID(ID);
	}
	
	public List<Person> getAllPersons() throws SQLException {
		return SQLManager.getInstance().getAllPersons();
	}
	
	public List<Person> getAllAdmins() throws SQLException, DatabaseException {
		return SQLManager.getInstance().getAllAdmins();
	}
	
	public int getIDByNutzername(String name) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getIDByNutzername(name);
	}
	
}
