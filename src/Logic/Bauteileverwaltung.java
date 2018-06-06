package Logic;

import java.sql.SQLException;
import java.util.List;

import DataAccess.SQLManager;
import Exceptions.BauteilNichtImWarenkorbException;
import Exceptions.DatabaseException;
import Exceptions.ZuWenigBauteileImWarenkorbException;

public class Bauteileverwaltung {

	private static Bauteileverwaltung instance;
	
	public static Bauteileverwaltung getInstance(){
		if (instance==null) instance = new Bauteileverwaltung();
		return instance;
	}
	
	
	public void addKategorie(String name) throws DatabaseException, SQLException {
		SQLManager.getInstance().addKategorie(name);
	}
	
	public void deleteKategorie(int ID) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteKategorie(ID);
	}
	
	public void renameKategorie(int ID, String newName) throws DatabaseException, SQLException {
		SQLManager.getInstance().renameKategorie(ID, newName);
	}
	
	public void createBauteil(String name, String link, double preis, int gelagert, int geplant, int bestellt, String ort, int kategorie_id) throws DatabaseException, SQLException {
		SQLManager.getInstance().createBauteil(name, link, preis, gelagert, geplant, bestellt, ort, kategorie_id);
	}
	
	public void modifyBauteil(int ID, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyBauteil(ID, attribut, newData);
	}
	
	public void addBauteil(int ID, int anzahl, int person_id) throws BauteilNichtImWarenkorbException, ZuWenigBauteileImWarenkorbException, SQLException {
		SQLManager.getInstance().addBauteil(ID, anzahl, person_id);
	}
	
	public void removeBauteil(int ID, int anzahl, int person_id) throws DatabaseException, SQLException {
		SQLManager.getInstance().removeBauteil(ID, anzahl, person_id);
	}
	
	public List<Bauteil> getAllBauteile() throws SQLException {
		return SQLManager.getInstance().getAllBauteil();
	}
	
	public Bauteil getBauteilByID(int id) throws SQLException{
		return SQLManager.getInstance().getBauteilByID(id);
	}
	
	public List<Kategorie> getAllKategorie() throws SQLException{
		return SQLManager.getInstance().getAllKategorie();
	}
	
	public Kategorie getKategorieByID(int id) throws SQLException{
		return SQLManager.getInstance().getKategorieByID(id);
	}
	
	public List<Bauteilwarenkorbelement> getBauteilwarenkorbByID(int id) throws SQLException{
		return SQLManager.getInstance().getBauteilwarenkorbByID(id);
	}
	
	public void ClearBauteilwarenkorb(int id) throws SQLException {
		SQLManager.getInstance().clearBauteilwarenkorb(id);
	}
	
	public void deleteBauteilByID(int id) throws SQLException{
		SQLManager.getInstance().deleteBauteilByID(id);
	}
}

