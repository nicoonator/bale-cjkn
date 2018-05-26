package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Finanzverwaltung {
	
	private static Finanzverwaltung instance;
	
	public static Finanzverwaltung getInstance(){
		if (instance==null) instance = new Finanzverwaltung();
		return instance;
	}
	
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id, int topf_id) throws SQLException {
		SQLManager.getInstance().createRechnung(name, bezahlart, betrag, auftrag_id, auftraggeber_id, verwalter_id, topf_id);
	}
	
	public void deleteRechnung(int rechnung_id) throws SQLException {
		SQLManager.getInstance().deleteRechnung(rechnung_id);
	}
	
	public void modifyRechnung (int rechnung_id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyRechnung(rechnung_id, attribut, newData);
	}
	
	public void exportRechnung(int rechnung_id) throws SQLException, DatabaseException {
		Rechnung tempRechnung = SQLManager.getInstance().getRechnungByID(rechnung_id);
	}
	
	public void changeStatus (int id, String status) throws SQLException {
		SQLManager.getInstance().changeRechnungStatus(id, status);
	}
	
	//TODO Create Töpfe/Kassen
	
}
