package DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Exceptions.BauteilAnzahlZuKleinException;
import Exceptions.BauteilBereitsVorhandenException;
import Exceptions.BauteilNichtImWarenkorbException;
import Exceptions.KategorieBereitsVorhandenException;
import Exceptions.NutzernameVorhandenException;
import Exceptions.PersonNichtInDBException;

import java.util.Date;
import Logic.*;

/**
 * @author Nico Rychlik
 *
 */
public class SQLManager {
	
	private static SQLManager instance;
	private Connection c;
	
	public static SQLManager getInstance(){
		if (instance==null) instance = new SQLManager();
		return instance;
	}
	
	private SQLManager (){
		
		try{
			Class.forName("org.sqlite.JDBC");
			c= DriverManager.getConnection("jdbc:sqlite:Database");
		}
		
		catch (Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * @author Nico Rychlik
	 * @param vname
	 * @param nname
	 * @param strasse
	 * @param hausnummer
	 * @param PLZ
	 * @param email
	 * @param nutzername
	 * @param passwort
	 * @param rolle 0: admin, 1: Kunde, 2: Lehrstuhlperson
	 * @throws SQLException
	 * @throws NutzernameVorhandenException 
	 */
	public void createPerson (String vname, String nname, String strasse, int hausnummer, int PLZ, String email, String nutzername, String passwort, int rolle) throws SQLException, NutzernameVorhandenException{
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Person WHERE nutzername = '"+nutzername+"';");
		if(!rs.next()) {
			String sql ="INSERT INTO Person (Vorname, Nachname, strasse, hausnr, PLZ, email, zuerst_erstellt, zuletzt_geaendert, nutzername, passwort, rolle, bauteilschulden) VALUES ('"+vname+"','"+nname+"','"+strasse+"','"+hausnummer+"','"+PLZ+"','"+email+"', "+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+",'"+nutzername+"','"+passwort+"','"+rolle+"', 0);";
			stmt.executeUpdate(sql);
		}
		else throw new NutzernameVorhandenException();
		stmt.close();		
		rs.close();
	}
	
	/**
	 * @author Nico Rychlik
	 * @return returns all Persons currently in Database as a List<Person>
	 * @throws SQLException
	 */
	public List<Person> getAllPersons() throws SQLException {
		List<Person> result= new ArrayList<Person>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Person ORDER BY rolle ASC;");
		Person tempPerson=null;
		while (rs.next()) {
			switch (rs.getInt("rolle")) {
			case 0:
				tempPerson=new Mitglied(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), true);
				break;
			
			case 1:
				tempPerson=new Kunde(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			
			case 2:
				tempPerson=new Lehrstuhlperson(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			}
			result.add(tempPerson);
		}
		rs.close();
		stmt.close();
		return result;
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @throws SQLException
	 */
	public void deletePerson (int id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Person WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}

	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyPerson(int id, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Person SET "+attribut+" = "+newData+" WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql);
		String sql2 = "UPDATE Person SET zuletzt_geaendert = "+(new Date().getTime()/1000)+" WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql2);
		stmt.close();	
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @return returns true if id is of type admin
	 * @throws SQLException
	 */
	public boolean checkAdmin (int id) throws SQLException {
		boolean result= false;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT rolle FROM Person WHERE PERSON_ID = "+id+";");
		if (rs.getInt(0)==0) result=true;
		rs.close();
		stmt.close();
		return result;
	}
	
	/**
	 * @author Nico Rychlik
	 * @param username
	 * @param password
	 * @return returns true if username & password are correct
	 * @throws SQLException
	 */
	public boolean login (String username, String password) throws SQLException {
		boolean result=false;
		Statement stmt =c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON WHERE nutzername='"+username+"AND WHERE passwort='"+password+"';");
		if (rs.next()) result=true;
		rs.close();
		stmt.close();
		
		return result;
	}
	
	/**
	 * @author Nico Rychlik
	 * @param ID
	 * @return Person with the specific ID
	 * @throws SQLException
	 * @throws PersonNichtInDBException 
	 */
	public Person getPersonByID (int ID) throws SQLException, PersonNichtInDBException {
		Person result = null;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Person WHERE PERSON_ID = '"+ID+"';");
		if(rs.next()) {
			switch (rs.getInt("rolle")) {
			case 0:
				result=new Mitglied(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), true);
				break;
			
			case 1:
				result=new Kunde(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			
			case 2:
				result=new Lehrstuhlperson(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getInt("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getInt("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			}
		}
		rs.close();
		stmt.close();
		if (!(result==null)) return result;
		else throw new PersonNichtInDBException();
	}
	
	/**
	 * @author Nico Rychlik
	 * @param name
	 * @throws SQLException
	 * @throws KategorieBereitsVorhandenException
	 */
	public void addKategorie (String name) throws SQLException, KategorieBereitsVorhandenException {
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE name = '"+name+"';");
		if(!rs.next()) {
			String sql ="INSERT INTO Kategorie (name) VALUES ('"+name+"');";
			stmt.executeUpdate(sql);
		}
		else throw new KategorieBereitsVorhandenException();
		stmt.close();		
		rs.close();
	}
	
	/**
	 * @author Nico Rychlik
	 * @return returns List of all Kategories
	 * @throws SQLException
	 */
	public List<Kategorie> getAllKategorien() throws SQLException {
		List<Kategorie> result = new ArrayList<Kategorie>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie;");
		while(rs.next()) {
			result.add(new Kategorie (rs.getInt("KATEGORIE_ID"),rs.getString("name")));
		}
		stmt.close();		
		rs.close();
		return result;
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @throws SQLException
	 */
	public void deleteKategorie(int id) throws SQLException {
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Kategorie WHERE KATEGORIE_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @param name
	 * @throws SQLException
	 * @throws KategorieBereitsVorhandenException
	 */
	public void renameKategorie (int id, String name) throws SQLException, KategorieBereitsVorhandenException {
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE name = '"+name+"';");
		if(!rs.next()) {
			String sql = "UPDATE Kategorie SET name = "+name+" WHERE KATEGORIE_ID="+id+";";
			stmt.executeUpdate(sql);
		}
		stmt.close();	
	}
	
	/**
	 * @author Nico Rychlik
	 * @param name
	 * @param link
	 * @param preis
	 * @param gelagert
	 * @param geplant
	 * @param bestellt
	 * @param ort
	 * @param id
	 * @throws SQLException
	 * @throws BauteilBereitsVorhandenException
	 */
	public void createBauteil (String name, String link, double preis, int gelagert, int geplant, int bestellt, String ort, int kategorie) throws SQLException, BauteilBereitsVorhandenException {
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE name = '"+name+"';");
		if(!rs.next()) {
			String sql ="INSERT INTO Bauteil (name, link, preis, gelagert, geplant, bestellt, ort, KATEGORIE_ID) VALUES ('"+name+"', '"+link+"', '"+preis+"', '"+gelagert+"', '"+geplant+"', '"+bestellt+"', '"+ort+"', "+kategorie+");";
			stmt.executeUpdate(sql);
		}
		else throw new BauteilBereitsVorhandenException();
		stmt.close();		
		rs.close();
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyBauteil(int id, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Bauteil SET "+attribut+" = "+newData+" WHERE BAUTEIL_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @param anzahl
	 * @param person
	 * @throws SQLException
	 * @throws BauteilNichtImWarenkorbException 
	 */
	
	// TODO: Noch können 10 Bauteile zurückgegeben werden obwohl der nutzer nur 4 hat.
	public void addBauteil(int id, int anzahl, int person) throws SQLException, BauteilNichtImWarenkorbException {
		Statement stmt = c.createStatement();
		String sql = "UPDATE Bauteil SET gelagert = gelagert + "+anzahl+" WHERE BAUTEIL_ID = "+id+";";
		stmt.executeUpdate(sql);
		sql ="SELECT * FROM Bauteilwarenkorb WHERE PERSON_ID = "+person+" AND BAUTEIL_ID = "+id+";";
		ResultSet rs = stmt.executeQuery(sql);
		if(!rs.next()) {
			throw new BauteilNichtImWarenkorbException();
		}
		else {
			if(rs.getInt("anzahl")!=0) {
				sql="UPDATE Bauteilwarenkorb SET anzahl= anzahl - "+Integer.toString(anzahl)+" WHERE PERSON_ID = '"+person+"' AND BAUTEIL_ID = '"+id+"';";
				stmt.executeUpdate(sql);
				double preis;
				double anz=anzahl;
				sql="SELECT preis FROM Bauteil WHERE BAUTEIL_ID ="+id+";";
				preis = stmt.executeQuery(sql).getDouble(1);
				sql="UPDATE Person SET bauteilschulden = bauteilschulden - "+(preis*anz)+";";
				stmt.executeUpdate(sql);
			}
			else throw new BauteilNichtImWarenkorbException();
		}
		rs.close();
		stmt.close();	
	}
	
	/**
	 * @author Nico Rychlik
	 * @param id
	 * @param anzahl
	 * @param person
	 * @throws SQLException
	 * @throws BauteilAnzahlZuKleinException
	 */
	public void removeBauteil(int id, int anzahl, int person) throws SQLException, BauteilAnzahlZuKleinException {
		Statement stmt = c.createStatement();
		String sql = "SELECT gelagert FROM Bauteil WHERE BAUTEIL_ID = "+id+";";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.getInt(1)>=anzahl) {
			sql = "UPDATE Bauteil SET gelagert = gelagert - "+anzahl+" WHERE BAUTEIL_ID = "+id+";";
			stmt.executeUpdate(sql);
			sql="SELECT * FROM Bauteilwarenkorb WHERE BAUTEIL_ID="+id+" AND PERSON_ID = "+person+";";
			ResultSet rs2 = stmt.executeQuery(sql);
			if(!rs2.next()) {
				sql="INSERT INTO Bauteilwarenkorb (PERSON_ID, BAUTEIL_ID, anzahl) VALUES ("+person+", "+id+", "+anzahl+");";
				stmt.executeUpdate(sql);
			}
			else {
				sql="UPDATE Bauteilwarenkorb SET anzahl = anzahl + "+anzahl+" WHERE BAUTEIL_ID="+id+" AND PERSON_ID = "+person+";";
			}
			rs2.close();
			double preis;
			double anz=anzahl;
			sql="SELECT preis FROM Bauteil WHERE BAUTEIL_ID ="+id+";";
			preis = stmt.executeQuery(sql).getDouble(1);
			sql="UPDATE Person SET bauteilschulden = bauteilschulden + "+(preis*anz)+";";
			stmt.executeUpdate(sql);
		}
		else throw new BauteilAnzahlZuKleinException(rs.getInt(1));
		rs.close();
		stmt.close();	
	}
	
	public void createAuftrag(String titel, String art, double prog_kosten, double reele_kosten, List <Person> persons) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Auftrag (titel, art, prog_kosten, reele_kosten,personen) VALUES ('"+titel+"','"+art+"','"+prog_kosten+"','"+reele_kosten+"','"+ List<Person> persons+"');";
		stmt.executeUpdate(sql);
		stmt.close();	
	}

	public void deleteAuftrag (int id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Auftrag WHERE AUFTRAG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Rechnung (name, bezahlart, betrag, auftrag_id, auftraggeber_id, verwalter_id) VALUES ('"+name+"','"+bezahlart+"','"+betrag+"','"+auftrag_id+"','"+auftraggeber_id+"','"+verwalter_id+"');";
		stmt.executeUpdate(sql);
		stmt.close();	
	
	}
	public void deleteRechnung (int id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Rechnung WHERE RECHNUNG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	public void modifyRechnung(int RECHNUNG_ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Rechnung SET "+attribut+" = "+newData+" WHERE RECHNUNG_ID="+RECHNUNG_ID+";";
		stmt.executeUpdate(sql);
		String sql2 = "UPDATE Rechnung SET zuletzt_geaendert = "+(new Date().getTime()/1000)+" WHERE RECHNUNG_ID="+RECHNUNG_ID+";";
		stmt.executeUpdate(sql2);
		stmt.close();	
	}
}

