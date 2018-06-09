package Logic;

import java.sql.SQLException;
import java.util.List;

import DataAccess.SQLManager;
import Exceptions.BauteilNichtImWarenkorbException;
import Exceptions.DatabaseException;
import Exceptions.ZuWenigBauteileImWarenkorbException;

public class Bauteileverwaltung {

	private static Bauteileverwaltung instance;
	
	/**
	 * This method ensures that at most one instance of "Bauteileverwaltung" exists.
	 * 
	 * @author 
	 * @return an instance of "Bauteileverwaltung"
	 */
	
	public static Bauteileverwaltung getInstance(){
		if (instance==null) instance = new Bauteileverwaltung();
		return instance;
	}
	
	/**
	 * This method tries to add a "Kategorie".
	 * 
	 * @author 
	 * @param name
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void addKategorie(String name) throws DatabaseException, SQLException {
		SQLManager.getInstance().addKategorie(name);
	}
	
	/**
	 * This method tries to delete a "Kategorie".
	 * 
	 * @author 
	 * @param ID
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void deleteKategorie(int ID) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteKategorie(ID);
	}
	
	/**
	 * This method tries to rename a "Kategorie".
	 * 
	 * @author 
	 * @param ID
	 * @param newName
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void renameKategorie(int ID, String newName) throws DatabaseException, SQLException {
		SQLManager.getInstance().renameKategorie(ID, newName);
	}
	
	/**
	 * This method tries to create a "Bauteil".
	 * 
	 * @author 
	 * @param name
	 * @param link
	 * @param preis
	 * @param gelagert
	 * @param geplant
	 * @param bestellt
	 * @param ort
	 * @param kategorie_id
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void createBauteil(String name, String link, double preis, int gelagert, int geplant, int bestellt, String ort, int kategorie_id) throws DatabaseException, SQLException {
		SQLManager.getInstance().createBauteil(name, link, preis, gelagert, geplant, bestellt, ort, kategorie_id);
	}
	
	/**
	 * This method tries to modify a "Bauteil".
	 * 
	 * @author 
	 * @param ID
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyBauteil(int ID, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyBauteil(ID, attribut, newData);
	}
	
	/**
	 * This method tries to add a "Bauteil".
	 * 
	 * @author 
	 * @param ID
	 * @param anzahl
	 * @param person_id
	 * @throws BauteilNichtImWarenkorbException
	 * @throws ZuWenigBauteileImWarenkorbException
	 * @throws SQLException
	 */
	public void addBauteil(int ID, int anzahl, int person_id) throws BauteilNichtImWarenkorbException, ZuWenigBauteileImWarenkorbException, SQLException {
		SQLManager.getInstance().addBauteil(ID, anzahl, person_id);
	}
	
	/**
	 * This method tries to remove a "Bauteil".
	 * 
	 * @author 
	 * @param ID	
	 * @param anzahl
	 * @param person_id
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void removeBauteil(int ID, int anzahl, int person_id) throws DatabaseException, SQLException {
		SQLManager.getInstance().removeBauteil(ID, anzahl, person_id);
	}
	
	/**
	 * This method tries to get all "Bauteile".
	 * 
	 * @author 
	 * @return list of all "Bauteile"
	 * @throws SQLException
	 */
	public List<Bauteil> getAllBauteile() throws SQLException {
		return SQLManager.getInstance().getAllBauteil();
	}
	
	/**
	 * This method tries to get a "Bauteil" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return the "Bauteil"
	 * @throws SQLException
	 */
	public Bauteil getBauteilByID(int id) throws SQLException{
		return SQLManager.getInstance().getBauteilByID(id);
	}
	
	/**
	 * This method tries to get all "Kategorien".
	 * 
	 * @author 
	 * @return list of all "Kategorien"
	 * @throws SQLException
	 */
	public List<Kategorie> getAllKategorie() throws SQLException{
		return SQLManager.getInstance().getAllKategorie();
	}
	
	/**
	 * This method tries to get a "Kategorie" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return the "Kategorie"
	 * @throws SQLException
	 */
	public Kategorie getKategorieByID(int id) throws SQLException{
		return SQLManager.getInstance().getKategorieByID(id);
	}
	
	/**
	 * This method tries to get a "Bauteilewarenkorb" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return the "Bauteilewarenkorb"
	 * @throws SQLException
	 */
	public List<Bauteilwarenkorbelement> getBauteilwarenkorbByID(int id) throws SQLException{
		return SQLManager.getInstance().getBauteilwarenkorbByID(id);
	}
	
	/**
	 * This method tries to clear a "Bauteilewarenkorb".
	 * 
	 * @author 
	 * @param id
	 * @throws SQLException
	 */
	public void ClearBauteilwarenkorb(int id) throws SQLException {
		SQLManager.getInstance().clearBauteilwarenkorb(id);
	}
	
	/**
	 * This method tries to delete a "Bauteil" by ID.
	 * 
	 * @author 
	 * @param id
	 * @throws SQLException
	 */
	public void deleteBauteilByID(int id) throws SQLException{
		SQLManager.getInstance().deleteBauteilByID(id);
	}
}

