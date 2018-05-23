package Logic;

import java.sql.SQLException;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;
import Exceptions.KategorieBereitsVorhandenException;

public class Bauteileverwaltung {

	public void addKategorie(String name) throws DatabaseException, SQLException {
		SQLManager.getInstance().addKategorie(name);
	}
	
	public void deleteKategorie(int ID) throws SQLException {
		SQLManager.getInstance().deleteKategorie(ID);
	}
	
	public void renameKategorie(int ID, String newName) throws DatabaseException, SQLException {
		SQLManager.getInstance().renameKategorie(ID, newName);
	}
	
	public void createBauteil(String name, String link, int gelagert, int geplant, int bestellt, String ort) {
		//TODO
	}
	
	public void modifyBauteil(int ID, String attribut, String newData) {
		//TODO
	}
	
	public void addBauteil(int ID, int anzahl) {
		//TODO
	}
	
	public void removeBauteil(int ID, int anzahl) {
		//TODO
	}
	
}

