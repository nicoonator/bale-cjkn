package Logic;

import java.sql.SQLException;
import java.util.List;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;
import Exceptions.deleteAuftragExRechnungException;

public class Fertigungsverwaltung {
	
	private static Fertigungsverwaltung instance;
	
	public static Fertigungsverwaltung getInstance(){
		if (instance==null) instance = new Fertigungsverwaltung();
		return instance;
	}
	
	public void createAuftrag(String titel, String art, double prog_kosten, double reele_kosten, List<Person> personen) throws SQLException {
		SQLManager.getInstance().createAuftrag(titel, art, prog_kosten, reele_kosten, personen);
	}
	
	public void deleteAuftrag(int id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteAuftrag(id);
	}
	
	public void changeStatus (int id, String status) throws SQLException {
		SQLManager.getInstance().changeAuftragStatus(id, status);
	}
	
	public void modifyAuftrag(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyAuftrag(id, attribut, newData);
	}
	
}
