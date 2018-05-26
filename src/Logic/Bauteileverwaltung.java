package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;
import Exceptions.BauteilAnzahlZuKleinException;
import Exceptions.BauteilBereitsVorhandenException;
import Exceptions.BauteilNichtImWarenkorbException;
import Exceptions.DatabaseException;
import Exceptions.KategorieBereitsVorhandenException;
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
	
	public void deleteKategorie(int ID) throws SQLException {
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
	
}

