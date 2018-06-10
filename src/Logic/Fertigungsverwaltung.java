package Logic;

import java.sql.SQLException;
import java.util.List;
import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Fertigungsverwaltung {
	
	private static Fertigungsverwaltung instance;
	
	/**
	 * This method ensures that at most one instance of "Fertigungsverwaltung" exists.
	 * 
	 * @author 
	 * @return an instance of "Fertigungsverwaltung"
	 */
	public static Fertigungsverwaltung getInstance(){
		if (instance==null) instance = new Fertigungsverwaltung();
		return instance; 
	}
	
	/**
	 * This method tries to create an "Auftrag".
	 * 
	 * @author 
	 * @param titel
	 * @param art
	 * @param prog_kosten
	 * @param reele_kosten
	 * @param auftraggeber_ID
	 * @param verwalter_ID
	 * @param vertreter
	 * @throws SQLException
	 */
	public void createAuftrag(String titel, String art, double prog_kosten, double reele_kosten, int auftraggeber_ID, int verwalter_ID, List<Person> vertreter) throws SQLException {
		SQLManager.getInstance().createAuftrag(titel, art, prog_kosten, reele_kosten,auftraggeber_ID, verwalter_ID, vertreter);
	}
	
	/**
	 * This method tries to delete an "Auftrag".
	 * 
	 * @author 
	 * @param id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void deleteAuftrag(int id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteAuftrag(id);
	}
	
	/**
	 * This method tries to change a status of an "Auftrag".
	 * 
	 * @author 
	 * @param id
	 * @param status
	 * @throws SQLException
	 */
	public void changeStatus (int id, String status) throws SQLException {
		SQLManager.getInstance().changeAuftragStatus(id, status);
	}
	
	/**
	 * This method tries to modify an "Auftrag".
	 * 
	 * @author 
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyAuftrag(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyAuftrag(id, attribut, newData);
	}
	
	/**
	 * This method tries to get an "Auftrag" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return the "Auftrag"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Auftrag getAuftragByID (int id) throws SQLException, DatabaseException{
		return SQLManager.getInstance().getAuftragByID(id);
	}
	
	/**
	 * This method tries to get all "Auftraege".
	 * 
	 * @author 
	 * @return list of all "Auftraege"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public List<Auftrag> getAllAuftrag () throws SQLException, DatabaseException{
		return SQLManager.getInstance().getAllAuftrag();
	}
	
}
