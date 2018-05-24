package DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Exceptions.*;
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
	 * @throws PersonHatNochSchuldenException 
	 */
	
	// KEVIN: logiK?
	public void deletePerson (int id) throws SQLException, DatabaseException{
		Statement stmt = c.createStatement();
		String sql;
		if(this.getPersonByID(id).isAdmin()) {
			sql="SELECT count(*) FROM Person WHERE rolle=0;";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.getInt(1)==1) throw new CannotDeleteLastAdminException();
			rs.close();
		}
		if(this.getPersonByID(id).getBauteilschulden()!=0) {
			throw new PersonHatNochSchuldenException();
		}
		sql="SELECT * FROM Verbindung_Person_Auftrag WHERE PERSON_ID = "+id+";";
		ResultSet rs2 = stmt.executeQuery(sql);
		boolean auftraege=false;
		ResultSet rs3;
		while(rs2.next()) {
			sql="SELECT AUFTRAG_ID FROM Verbindung_Person_Auftrag WHERE (rolle ='"+rs2.getString("rolle")+"' AND AUFTRAG_ID = "+rs2.getInt("AUFTRAG_ID")+") AND PERSON_ID != "+rs2.getInt("PERSON_ID")+";";
			rs3 = stmt.executeQuery(sql);
			while(rs3.next()) {
				sql="SELECT * FROM AUFTRAG WHERE AUFTRAG_ID = "+rs3.getInt(1)+" AND abgerechnet = 1 AND abgeholt = 1 ;";
				if(stmt.executeQuery(sql).next()) auftraege = true;
			}
			rs3.close();
		}
		rs2.close();
		if(auftraege) {
			throw new PersonHatAuftraegeException();
		}
		
		
		
		sql= "DELETE FROM Person WHERE PERSON_ID="+id+";";
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
		if (rs.getInt(1)==0) result=true;
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
	
	// TODO: WENN BAUTEILE DRIN SIND? --> Bauteile haben keine Kategorie mehr (Trash Kategorie)
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
		else throw new KategorieBereitsVorhandenException();
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
	 * @throws ZuWenigBauteileImWarenkorbException 
	 */
	// Aus dem Warenkorb IN dem Bestand
	// TODO: debug
	public void addBauteil(int id, int anzahl, int person) throws SQLException, BauteilNichtImWarenkorbException, ZuWenigBauteileImWarenkorbException {
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
				if(rs.getInt("anzahl")>=anzahl) {
					sql="UPDATE Bauteilwarenkorb SET anzahl= anzahl - "+Integer.toString(anzahl)+" WHERE PERSON_ID = '"+person+"' AND BAUTEIL_ID = '"+id+"';";
					stmt.executeUpdate(sql);
					double preis;
					double anz=anzahl;
					sql="SELECT preis FROM Bauteil WHERE BAUTEIL_ID ="+id+";";
					preis = stmt.executeQuery(sql).getDouble(1);
					sql="UPDATE Person SET bauteilschulden = bauteilschulden - "+(preis*anz)+";";
					stmt.executeUpdate(sql);
				}
				else throw new ZuWenigBauteileImWarenkorbException(rs.getInt("anzahl"));
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
	
	// IN den Warenkorb REIN 
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
		String sql ="INSERT INTO Auftrag (titel, art, prog_kosten, reele_kosten,angenommen, gefertigt, kalkuliert, abgeholt, abgerechnet, warten, unterbrochen, defekt, date_angenommen) VALUES ('"+titel+"','"+art+"','"+prog_kosten+"','"+reele_kosten+"',1,0,0,0,0,0,0,0,"+new Date().getTime()/1000L+");";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	public void modifyAuftrag(int AUFTRAG_ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Auftrag SET "+attribut+" = "+newData+" WHERE AUFTRAG_ID="+AUFTRAG_ID+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	//Zeit im Konstruktor
	public Auftrag getAuftragByID(int ID) throws SQLException, DatabaseException {
		Auftrag result = null;
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Auftrag WHERE AUFTRAG_ID = "+ID+";";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) result = new Auftrag(rs.getInt("AUFTRAG_ID"),rs.getString("TITEL"),rs.getString("ART"),rs.getDouble("prognostizierte_kosten"),rs.getDouble("reelle_kosten"),this.convertIntToBoolean(rs.getInt("angenommen")),this.convertIntToBoolean(rs.getInt("gefertigt")),this.convertIntToBoolean(rs.getInt("kalkuliert")),this.convertIntToBoolean(rs.getInt("abgeholt")),this.convertIntToBoolean(rs.getInt("abgerechnet")),this.convertIntToBoolean(rs.getInt("warten")),this.convertIntToBoolean(rs.getInt("unterbrochen")),this.convertIntToBoolean(rs.getInt("defekt")));
		stmt.close();
		rs.close();
		if (result!=null) return result;
		else throw new AuftragNichtVorhandenException();
	}
	//TODO Zeit
	public void changeAuftragStatus (int id, String Status) throws SQLException {
		Statement stmt = c.createStatement();
		String sql ="SELECT "+Status+" FROM AUFTRAG WHERE AUFTRAG_ID = "+id+";";
		if(stmt.executeQuery(sql).getInt(1)==1) {
			sql="UPDATE Auftrag SET "+Status+" = 0 WHERE AUFTRAG_ID = "+id+";";
		}
		else {
			sql="UPDATE Auftrag SET "+Status+" = 1 WHERE AUFTRAG_ID = "+id+";";
		}
		stmt.executeUpdate(sql);
		sql="UPDATE Auftrag SET date_"+Status+" = "+(new Date().getTime()/1000)+" WHERE PERSON_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	//Nur loeschen wenns keine Rechnung gibt
	public void deleteAuftrag (int id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Auftrag WHERE AUFTRAG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	//TODO Zeit
	//TODO: Konstruktor anpassen sobald Dates in der Klasse sind
	public List<Auftrag> getAllAuftrag() throws SQLException {
		List<Auftrag> result= new ArrayList<Auftrag>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Auftrag;");
		Auftrag tempAuftrag=null;
		while (rs.next()) {
			tempAuftrag = new Auftrag(rs.getInt("AUFTRAG_ID"),rs.getString("TITEL"),rs.getString("ART"),rs.getDouble("prognostizierte_kosten"),rs.getDouble("reelle_kosten"),this.convertIntToBoolean(rs.getInt("angenommen")),this.convertIntToBoolean(rs.getInt("gefertigt")),this.convertIntToBoolean(rs.getInt("kalkuliert")),this.convertIntToBoolean(rs.getInt("abgeholt")),this.convertIntToBoolean(rs.getInt("abgerechnet")),this.convertIntToBoolean(rs.getInt("warten")),this.convertIntToBoolean(rs.getInt("unterbrochen")),this.convertIntToBoolean(rs.getInt("defekt")));
			result.add(tempAuftrag);
		}
		rs.close();
		stmt.close();
		return result;
	}
	//TODO: Rechnungsdatum
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id, int topf_id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Rechnung (rechnungsname, bezahlart, betrag, AUFTRAG_ID, AUFTRAGGEBER_ID, ANSPRECHPARTNER_ID, TOPF_ID, bearbeitung, eingereicht, abgewickelt, ausstehend, RECHNUNGSDATUM) VALUES ('"+name+"','"+bezahlart+"','"+betrag+"','"+auftrag_id+"','"+auftraggeber_id+"','"+verwalter_id+"','"+topf_id+"',0,0,0,0, "+new Date().getTime()+");";
		stmt.executeUpdate(sql);
		stmt.close();	
	
	}
	public void deleteRechnung (int id) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="DELETE FROM Rechnung WHERE RECHNUNG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	
	}
	
	//TODO: Konstruktor anpassen sobald Dates in der Klasse sind
	public Rechnung getRechnungByID(int ID) throws SQLException, DatabaseException {
		Rechnung result = null;
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Rechnung WHERE RECHNUNG_ID = "+ID+";";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) result = new Rechnung(rs.getInt("RECHNUNG_ID"),rs.getDate("RECHNUNGSDATUM"),rs.getString("rechnungsname"),rs.getString("bezahlart"),rs.getDouble("betrag"),this.convertIntToBoolean(rs.getInt("bearbeitung")),this.convertIntToBoolean(rs.getInt("eingereicht")),this.convertIntToBoolean(rs.getInt("abgewickelt")),this.convertIntToBoolean(rs.getInt("ausstehend")));
		stmt.close();
		rs.close();
		if (result!=null) return result;
		else throw new RechnungNichtVorhandenException();
	}
	public void modifyRechnung(int RECHNUNG_ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Rechnung SET "+attribut+" = "+newData+" WHERE RECHNUNG_ID="+RECHNUNG_ID+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	

	/**
	 * @author Nico Rychlik
	 * @param i
	 * @return gibt true zur�ck wenn i==1 ist, ansonsten false
	 */

	public void changeRechnungStatus (int id, String Status) throws SQLException {
		Statement stmt = c.createStatement();
		String sql ="SELECT "+Status+" FROM Rechnung WHERE RECHNUNG_ID = "+id+";";
		if(stmt.executeQuery(sql).getInt(1)==1) {
			sql="UPDATE Rechnung SET "+Status+" = 0 WHERE RECHNUNG_ID = "+id+";";
		}
		else {
			sql="UPDATE Rechnung SET "+Status+" = 1 WHERE RECHNUNG_ID = "+id+";";
		}
		stmt.executeUpdate(sql);
		sql="UPDATE Rechnung SET date_"+Status+" = "+(new Date().getTime()/1000)+" WHERE PERSON_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	/**
	 * This method loads the requested "Topf" from database.
	 * 
	 * @author Joel Wolf
	 * @param TOPF_ID
	 * @return the requested "Topf"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Topf getTopfByID(int TOPF_ID) throws SQLException, DatabaseException {
		
		Topf result = null;
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Topf WHERE TOPF_ID = " + TOPF_ID + ";";
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) 
			result = new Topf(rs.getInt("TOPF_ID"), rs.getString("name"), rs.getDouble("soll"), rs.getDouble("ist"));
		
		stmt.close();
		rs.close();
		
		if(result != null)
			return result;
		else 
			throw new TopfNichtVorhandenException();
	}
	
	/**
	 * This method loads all "Toepfe" from database.
	 * 
	 * @author Joel Wolf
	 * @return list of all "Toepfe"
	 * @throws SQLException
	 */
	public List<Topf> getAllToepfe() throws SQLException {
		
		List<Topf> result = new ArrayList<Topf>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Topf;");
		
		while(rs.next()) {
			
			result.add(new Topf(rs.getInt("TOPF_ID"), rs.getString("name"), rs.getDouble("soll"), rs.getDouble("ist")));
		}
		
		stmt.close();		
		rs.close();
		
		return result;
	}
	
	/**
	 * This method saves the modified attribute from a "Topf" to database.
	 * 
	 * @author Joel Wolf
	 * @param TOPF_ID
	 * @param attribut the modified attribute from the "Topf"
	 * @param newData the new value from the attribute
	 * @throws SQLException
	 */
	public void modifyTopf(int TOPF_ID, String attribut, String newData) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "UPDATE Topf SET " + attribut + " = " + newData + " WHERE TOPF_ID = " + TOPF_ID + ";";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
	}
	
	/**
	 * This method inserts a new "Topf" into database.
	 * 
	 * @author Joel Wolf
	 * @param TOPF_ID
	 * @param name the name of the "Topf"
	 * @param soll "soll"-value of the "Topf"
	 * @param ist "ist"-value of the "Topf"
	 * @throws SQLException
	 */
	public void createTopf(int TOPF_ID, String name, double soll, double ist, int KASSE_ID) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Topf (TOPF_ID, name, soll, ist, KASSE_ID) VALUES (" 
				+ TOPF_ID + ", '" + name + "', " + soll + ", " + ist + ", " + KASSE_ID + ");";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
	}
	
	/**
	 * This method deletes a "Topf" from database.
	 * 
	 * @author Joel Wolf
	 * @param TOPF_ID
	 * @throws SQLException
	 */
	public void deleteTopf(int TOPF_ID) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Topf WHERE TOPF_ID = " + TOPF_ID + ";";
		
		stmt.executeUpdate(sql);
		
		stmt.close();	
	}
	
	/**
	 * This method loads the requested "Kasse" from database.
	 * 
	 * @author Joel Wolf
	 * @param KASSE_ID
	 * @return the requested "Kasse"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Kasse getKasseByID(int KASSE_ID) throws SQLException, DatabaseException {
		
		Kasse result = null;
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Kasse WHERE KASSE_ID = " + KASSE_ID + ";";
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			
			switch(rs.getInt("typ")) {
			case 1:
				result = new Barkasse(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"));
				break;
			case 2:
				result = new Konto(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"));
				break;
			case 3:
				result = new Kostenstelle(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"));
				break;
			}
		}
		
		stmt.close();
		rs.close();
		
		if(result != null)
			return result;
		else 
			throw new KasseNichtVorhandenException();
	}
	
	/**
	 * This method loads all "Kassen" from database.
	 * 
	 * @author Joel Wolf
	 * @return list of all "Kassen"
	 * @throws SQLException
	 */
	public List<Kasse> getAllKassen() throws SQLException {
		
		List<Kasse> result = new ArrayList<Kasse>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Kasse;");
		
		while(rs.next()) {
			
			switch(rs.getInt("typ")) {
			case 1:
				result.add(new Barkasse(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist")));
				break;
			case 2:
				result.add(new Konto(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist")));
				break;
			case 3:
				result.add(new Kostenstelle(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist")));
				break;
			}
		}
		
		stmt.close();		
		rs.close();
		
		return result;
	}
	
	/**
	 * This method saves the modified attribute from a "Kasse" to database.
	 * 
	 * @author Joel Wolf
	 * @param KASSE_ID
	 * @param attribut the modified attribute from the "Kasse"
	 * @param newData the new value from the attribute
	 * @throws SQLException
	 */
	public void modifyKasse(int KASSE_ID, String attribut, String newData) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "UPDATE Kasse SET " + attribut + " = " + newData + " WHERE KASSE_ID = " + KASSE_ID + ";";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
	}
	
	/**
	 * This method inserts a new "Kasse" into database.
	 * 
	 * @author Joel Wolf
	 * @param KASSE_ID
	 * @param name the name of the "Kasse"
	 * @param soll "soll"-value of the "Kasse"
	 * @param ist "ist"-value of the "Kasse"
	 * @throws SQLException
	 */
	public void createKasse(int KASSE_ID, String name, double soll, double ist, int typ) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Kasse (KASSE_ID, name, soll, ist) VALUES (" 
				+ KASSE_ID + ", '" + name + "', " + soll + ", " + ist + ", " + typ + ");";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
	}
	
	/**
	 * This method deletes a "Kasse" from database.
	 * 
	 * @author Joel Wolf
	 * @param KASSE_ID
	 * @throws SQLException
	 */
	public void deleteKasse(int KASSE_ID) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Kasse WHERE KASSE_ID = " + KASSE_ID + ";";
		
		stmt.executeUpdate(sql);
		
		stmt.close();	
	}
	
	
	// TODO get ALL Rechung/Auftrag
	// TODO get RechungByID
	// TODO Toepfe / Kassen erstellen (getByID, getAll, modify, create delete? modify)

	
	//TODO: Konstruktor anpassen sobald Dates in der Klasse sind
	public List<Rechnung> getAllRechnung() throws SQLException {
		List<Rechnung> result= new ArrayList<Rechnung>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Rechnung;");
		Rechnung tempRechnung=null;
		while (rs.next()) {
			tempRechnung = new Rechnung(rs.getInt("RECHNUNG_ID"),rs.getDate("RECHNUNGSDATUM"),rs.getString("rechnungsname"),rs.getString("bezahlart"),rs.getDouble("betrag"),this.convertIntToBoolean(rs.getInt("bearbeitung")),this.convertIntToBoolean(rs.getInt("eingereicht")),this.convertIntToBoolean(rs.getInt("abgewickelt")),this.convertIntToBoolean(rs.getInt("ausstehend")));
			result.add(tempRechnung);
		}
		rs.close();
		stmt.close();
		return result;
	}
	
	public boolean convertIntToBoolean (int i) {
		if (i==1) return true;
		else return false;
	}
}

