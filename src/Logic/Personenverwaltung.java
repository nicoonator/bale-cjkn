package Logic;

import java.sql.SQLException;
import java.util.List;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Personenverwaltung {
	
	private static Personenverwaltung instance;
	
	/**
	 * This method ensures that at most one instance of "Personenverwaltung" exists.
	 * 
	 * @author 
	 * @return an instance of "Personenverwaltung"
	 */
	
	public static Personenverwaltung getInstance(){
		if (instance==null) instance = new Personenverwaltung();
		return instance;
	}

	/**
	 * This method tries to create a person.
	 * 
	 * @author 
	 * @param vorname
	 * @param nachname
	 * @param strasse
	 * @param hausnr
	 * @param PLZ
	 * @param email
	 * @param nutzername
	 * @param passwort
	 * @param rolle
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void createPerson(String vorname, String nachname, String strasse, 
			String hausnr, int PLZ, String email, String nutzername, String passwort, int rolle) throws DatabaseException, SQLException {
		
		SQLManager.getInstance().createPerson(vorname, nachname, strasse, hausnr, PLZ, email, nutzername, passwort, rolle);
	}
	
	/**
	 * This method tries to delete a person.
	 * 
	 * @author 
	 * @param id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void deletePerson(int id) throws SQLException, DatabaseException {
		
		SQLManager.getInstance().deletePerson(id);
	}
	
	/**
	 * This method tries to modify a person.
	 * 
	 * @author 
	 * @param id
	 * @param attribute
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyPerson(int id, String attribute, String newData) throws SQLException {
		
		SQLManager.getInstance().modifyPerson(id, attribute, newData);
	}
	
	/**
	 * This method tries to check if person is admin.
	 * 
	 * @author 
	 * @param nutzername
	 * @return
	 * @throws SQLException
	 */
	public boolean checkadmin(String nutzername) throws SQLException {
		
		return SQLManager.getInstance().checkAdmin(nutzername);
		
	}
	
	/**
	 * This method tries to get a person by ID.
	 * 
	 * @author 
	 * @param ID
	 * @return the person
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public Person getPersonByID(int ID) throws DatabaseException, SQLException {
		return SQLManager.getInstance().getPersonByID(ID);
	}
	
	/**
	 * This method tries to get all persons.
	 * 
	 * @author 
	 * @return list of all persons
	 * @throws SQLException
	 */
	public List<Person> getAllPersons() throws SQLException {
		return SQLManager.getInstance().getAllPersons();
	}
	
	/**
	 * This method tries to get all admins.
	 * 
	 * @author 
	 * @return list of all admins
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public List<Person> getAllAdmins() throws SQLException, DatabaseException {
		return SQLManager.getInstance().getAllAdmins();
	}
	
	/**
	 * This method tries to get an ID by the "Nutzername".
	 * 
	 * @author 
	 * @param name
	 * @return the ID
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public int getIDByNutzername(String name) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getIDByNutzername(name);
	}
	
}
