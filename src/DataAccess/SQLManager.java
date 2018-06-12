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
	
	/**
	 * This method ensures that at most one instance of "SQLManager" exists.
	 * 
	 * @author 
	 * @return an instance of "SQLManager"
	 */
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
	 * @param username
	 * @param password
	 * @return returns true if username & password are correct
	 * @throws SQLException
	 */
	public boolean login (String username, String password) throws SQLException {
		boolean result=false;
		Statement stmt =c.createStatement();
		String sql="SELECT * FROM PERSON WHERE nutzername= '"+username+"' AND passwort= '"+password+"';";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) result=true;
		rs.close();
		stmt.close();
		
		return result;
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
	public void createPerson (String vname, String nname, String strasse, String hausnummer, int PLZ, String email, String nutzername, String passwort, int rolle) throws SQLException, NutzernameVorhandenException{
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Person WHERE nutzername = '"+nutzername+"';");
		if(!rs.next()) {
			String sql ="INSERT INTO Person (Vorname, Nachname, strasse, hausnr, PLZ, email, zuerst_erstellt, "
					+ "zuletzt_geaendert, nutzername, passwort, rolle, bauteilschulden) VALUES "
					+ "('"+vname+"','"+nname+"','"+strasse+"','"+hausnummer+"','"+PLZ+"','"+email+"', "
					+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+",'"+nutzername+"','"+passwort+"','"+rolle+"', 0);";
			stmt.executeUpdate(sql);
		}
		else throw new NutzernameVorhandenException();
		stmt.close();		
		rs.close();
	}

	/**
	 * 
	 * @author Nico Rychlik
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyPerson(int id, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Person SET "+attribut+" = '"+newData+"' WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql);
		String sql2 = "UPDATE Person SET zuletzt_geaendert = "+(new Date().getTime()/1000)+" WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql2);
		stmt.close();	
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
		
		if(rs2.next()) {
			throw new PersonHatAuftraegeException();
		}
		rs2.close();
		
		
		sql= "DELETE FROM Person WHERE PERSON_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
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
				tempPerson=new Mitglied(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), true);
				break;
			
			case 1:
				tempPerson=new Kunde(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			
			case 2:
				tempPerson=new Lehrstuhlperson(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), false);
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
	 * @return returns true if id is of type admin
	 * @throws SQLException
	 */
	public boolean checkAdmin (String nutzername) throws SQLException {
		boolean result= false;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT rolle FROM Person WHERE nutzername = '"+nutzername+"';");
		if (rs.getInt(1)==0) result=true;
		rs.close();
		stmt.close();
		return result;
	}

	public int getIDByNutzername(String name) throws SQLException, DatabaseException {
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT PERSON_ID FROM PERSON WHERE nutzername = '"+name+"';");
		if(rs.next()) return rs.getInt(1);
		else throw new NutzernameNichtVorhandenException();
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
				result=new Mitglied(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), true);
				break;
			
			case 1:
				result=new Kunde(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			
			case 2:
				result=new Lehrstuhlperson(rs.getInt("PERSON_ID"), rs.getString("vorname"), rs.getString("nachname"),  rs.getString("strasse"),  rs.getString("hausnr"),  rs.getInt("PLZ"), rs.getString("email"), new Date((rs.getLong("zuerst_erstellt"))*1000L), new Date((rs.getLong("zuletzt_geaendert"))*1000L), rs.getString("nutzername"), rs.getString("passwort"), rs.getDouble("bauteilschulden"), new ArrayList<Bauteil>(), false);
				break;
			}
		}
		rs.close();
		stmt.close();
		if (!(result==null)) return result;
		else throw new PersonNichtInDBException();
	}
	
	/**
	 * This method takes a list of all admins from database.
	 * 
	 * @author 
	 * @return list of all admins
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public List<Person> getAllAdmins() throws SQLException, DatabaseException{
		List<Person> result= new ArrayList<Person>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Person_ID FROM Person WHERE rolle = 0;");
		while (rs.next()) {
				result.add(this.getPersonByID(rs.getInt(1)));
		}
		stmt.close();
		rs.close();
		return result;
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
	 * returns all Kategorien
	 * @author Nico Rychlik
	 * @return returns List of all Kategories
	 * @throws SQLException
	 */
	public List<Kategorie> getAllKategorie() throws SQLException {
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
	 * loescht die Kategorie und weist Bauteilen die dieser Kategorie angehoeren die Trash Kategorie zu und erstellt sie ggf.
	 * @author Nico Rychlik
	 * @param id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void deleteKategorie(int id) throws SQLException, DatabaseException {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Bauteil WHERE KATEGORIE_ID = "+id+";";
		if (stmt.executeQuery(sql).next()) {
			/** Kategorie 3 --> Trash Kategorie */
			if (!(stmt.executeQuery("SELECT KATEGORIE_ID FROM Kategorie WHERE name = 'trash';").next())) {
				this.addKategorie("trash");
			}
			sql = "SELECT KATEGORIE_ID FROM Kategorie WHERE name = 'trash'";
			ResultSet rs = stmt.executeQuery(sql);
			int trash = rs.getInt(1);
			if (trash == id) throw new DeleteTrashException();
			sql = "UPDATE Bauteil SET KATEGORIE_ID = "+trash+" WHERE KATEGORIE_ID = "+id+";";
			stmt.executeUpdate(sql);
			rs.close();
		}
		sql ="DELETE FROM Kategorie WHERE KATEGORIE_ID = "+id+";";
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
			String sql = "UPDATE Kategorie SET name = '"+name+"' WHERE KATEGORIE_ID="+id+";";
			stmt.executeUpdate(sql);
		}
		else throw new KategorieBereitsVorhandenException();
		stmt.close();	
	}
	
	/**
	 * This method takes a "Kategorie" by ID from database.
	 * 
	 * @author 
	 * @param id
	 * @return the "Kategorie"
	 * @throws SQLException
	 */
	public Kategorie getKategorieByID (int id) throws SQLException {
		Kategorie result = null;
		Statement stmt = c.createStatement();
		String sql="SELECT * FROM Kategorie WHERE KATEGORIE_ID = "+id+";";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			result=(new Kategorie (rs.getInt("KATEGORIE_ID"),rs.getString("name")));
		}
		stmt.close();		
		rs.close();
		return result;
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
		String sql = "UPDATE Bauteil SET "+attribut+" = '"+newData+"' WHERE BAUTEIL_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	public void deleteBauteilByID(int ID) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Bauteilwarenkorb WHERE BAUTEIL_ID = "+ID+";";
		stmt.executeUpdate(sql);
		sql="DELETE FROM Bauteil WHERE BAUTEIL_ID = "+ID+";";
		stmt.executeUpdate(sql);
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
			} else {
				sql="UPDATE Bauteilwarenkorb SET anzahl = anzahl + "+anzahl+" WHERE BAUTEIL_ID="+id+" AND PERSON_ID = "+person+";";
				stmt.executeUpdate(sql);
			}
			rs2.close();
			double preis;
			double anz=anzahl;
			sql="SELECT preis FROM Bauteil WHERE BAUTEIL_ID ="+id+";";
			preis = stmt.executeQuery(sql).getDouble(1);
			sql="UPDATE Person SET bauteilschulden = bauteilschulden + "+(preis*anz)+" WHERE PERSON_ID ="+person+";";
			stmt.executeUpdate(sql);
		}
		else throw new BauteilAnzahlZuKleinException(rs.getInt(1));
		rs.close();
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
					sql="UPDATE Person SET bauteilschulden = bauteilschulden - "+(preis*anz)+" WHERE PERSON_ID ="+person+";";
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
	 * This method takes a "Bauteil" by ID from database.
	 * 
	 * @author 
	 * @param id
	 * @return the "Bauteil"
	 * @throws SQLException
	 */
	public Bauteil getBauteilByID (int id) throws SQLException {
		Bauteil result = null;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Bauteil WHERE BAUTEIL_ID = "+id+";");
		ResultSet rs2 = stmt.executeQuery("SELECT * FROM Bauteil WHERE BAUTEIL_ID = "+id+";");
		if (rs.next()) {
				result = new Bauteil(rs.getInt("BAUTEIL_ID"), rs.getString("name"), rs.getString("link"),
						rs.getDouble("preis"), rs.getInt("gelagert"), rs.getInt("geplant"), rs.getInt("bestellt"),
						rs.getString("ort"), rs.getInt("KATEGORIE_ID"),
						Bauteileverwaltung.getInstance().getKategorieByID(rs.getInt("Kategorie_ID")).getName());
		}
		stmt.close();
		rs.close();
		rs2.close();
		return result;
	}
	
	/**
	 * This method takes a list of all "Bauteile" from database.
	 * 
	 * @author 
	 * @return list of all "Bauteile"
	 * @throws SQLException
	 */
	public List<Bauteil> getAllBauteil () throws SQLException {
		List<Bauteil> result = new ArrayList<Bauteil>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Bauteil;");
		while(rs.next()) {
			result.add(this.getBauteilByID(rs.getInt(1)));
			}
		stmt.close();		
		rs.close();
		return result;
	}

	/**
	 * This method creates an "Auftrag" and inserts it into database.
	 * 
	 * @author 
	 * @param titel
	 * @param art
	 * @param prog_kosten
	 * @param reelle_kosten
	 * @param person_ID
	 * @param verwalter_ID
	 * @param vertreter
	 * @throws SQLException
	 */
	public void createAuftrag(String titel, String art, double prog_kosten, double reelle_kosten, int person_ID, int verwalter_ID, List<Person> vertreter) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Auftrag (titel, art, prognostizierte_kosten, reelle_kosten,angenommen, gefertigt, kalkuliert, abgeholt, abgerechnet, warten, unterbrochen, defekt, date_angenommen, date_gefertigt, date_kalkuliert, date_abgeholt, date_abgerechnet, date_warten, date_unterbrochen, date_defekt) VALUES ('"+titel+"','"+art+"','"+prog_kosten+"','"+reelle_kosten+"',1,0,0,0,0,0,0,0,"+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+","+new Date().getTime()/1000L+");";
		stmt.executeUpdate(sql);
		int auftrag_id=stmt.executeQuery("SELECT * FROM Auftrag WHERE AUFTRAG_ID = (SELECT MAX(AUFTRAG_ID)  FROM Auftrag);").getInt(1);
		sql ="INSERT INTO Verbindung_Person_Auftrag (PERSON_ID, AUFTRAG_ID, rolle) values ("+person_ID+","+auftrag_id+",0);";
		stmt.executeUpdate(sql);
		sql ="INSERT INTO Verbindung_Person_Auftrag (PERSON_ID, AUFTRAG_ID, rolle) values ("+verwalter_ID+","+auftrag_id+",1);";
		stmt.executeUpdate(sql);
		for (Person p : vertreter) {
			sql ="INSERT INTO Verbindung_Person_Auftrag (PERSON_ID, AUFTRAG_ID, rolle) values ("+p.getPERSON_ID()+","+auftrag_id+",2);";
			stmt.executeUpdate(sql);
		}
		stmt.close();	
	}
	
	/**
	 * This method modifies an "Auftrag" and puts it into database.
	 * 
	 * @author 
	 * @param AUFTRAG_ID
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyAuftrag(int AUFTRAG_ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Auftrag SET "+attribut+" = '"+newData+"' WHERE AUFTRAG_ID="+AUFTRAG_ID+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}

	//Nur loeschen wenns keine Rechnung gibt
	/**
	 * @author Christin Meyer, Nico Rychlik
	 * @param id
	 * @throws SQLException
	 * @throws deleteAuftragExRechnungException 
	 */
	public void deleteAuftrag (int id) throws SQLException, deleteAuftragExRechnungException{
		Statement stmt = c.createStatement();
		if (stmt.executeQuery("SELECT * FROM Rechnung WHERE AUFTRAG_ID = "+id+";").next()) throw new deleteAuftragExRechnungException();
		String sql ="DELETE FROM Auftrag WHERE AUFTRAG_ID="+id+";";
		stmt.executeUpdate(sql);
		sql ="DELETE FROM Verbindung_Person_Auftrag WHERE AUFTRAG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * This method takes an "Auftrag" by ID from database.
	 * 
	 * @author 
	 * @param ID
	 * @return the "Auftrag"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Auftrag getAuftragByID(int ID) throws SQLException, DatabaseException {
		Auftrag result = null;
		Statement stmt = c.createStatement();
		String sql2 ="SELECT PERSON_ID FROM Verbindung_Person_Auftrag WHERE AUFTRAG_ID = "+ID+" AND rolle = 0;";
		ResultSet rs3 = stmt.executeQuery(sql2);
		Person auftraggeber = this.getPersonByID(rs3.getInt(1));
		sql2 ="SELECT PERSON_ID FROM Verbindung_Person_Auftrag WHERE AUFTRAG_ID = "+ID+" AND rolle = 1;";
		rs3 = stmt.executeQuery(sql2);
		Person verwalter = this.getPersonByID(rs3.getInt(1));
		sql2 ="SELECT PERSON_ID FROM Verbindung_Person_Auftrag WHERE AUFTRAG_ID = "+ID+" AND rolle = 2;";
		List<Person> vertreter= new ArrayList<Person>();
		ResultSet rs2 = stmt.executeQuery(sql2);
		while(rs2.next()) {
			vertreter.add(this.getPersonByID(rs2.getInt(1)));
		}
		String sql = "SELECT * FROM Auftrag WHERE AUFTRAG_ID = '"+ID+"';";
		ResultSet apfel = stmt.executeQuery(sql);
		if (apfel.next()) {
			result = new Auftrag(apfel.getInt("AUFTRAG_ID"),apfel.getString("TITEL"),apfel.getString("ART"),apfel.getDouble("prognostizierte_kosten"),apfel.getDouble("reelle_kosten"),this.convertIntToBoolean(apfel.getInt("angenommen")),this.convertIntToBoolean(apfel.getInt("gefertigt")),this.convertIntToBoolean(apfel.getInt("kalkuliert")),this.convertIntToBoolean(apfel.getInt("abgeholt")),this.convertIntToBoolean(apfel.getInt("abgerechnet")),this.convertIntToBoolean(apfel.getInt("warten")),this.convertIntToBoolean(apfel.getInt("unterbrochen")),this.convertIntToBoolean(apfel.getInt("defekt")),new Date(apfel.getLong("date_angenommen")*1000L),new Date(apfel.getLong("date_gefertigt")*1000L),new Date(apfel.getLong("date_kalkuliert")*1000L),new Date(apfel.getLong("date_abgeholt")*1000L),new Date(apfel.getLong("date_abgerechnet")*1000L),new Date(apfel.getLong("date_warten")*1000L),new Date(apfel.getLong("date_unterbrochen")*1000L),new Date(apfel.getLong("date_defekt")*1000L),auftraggeber,verwalter,vertreter);
			stmt.close();
			apfel.close();
			rs2.close();
			rs3.close();
			return result;
		}
		else {
			stmt.close();
			apfel.close();
			rs2.close();
			rs3.close();
			throw new AuftragNichtVorhandenException();
			
		}
	}

	/**
	 * This method changes a status of an "Auftrag" and puts it into database.
	 * 
	 * @author 
	 * @param id
	 * @param Status
	 * @throws SQLException
	 */
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
		sql="UPDATE Auftrag SET date_"+Status+" = "+(new Date().getTime()/1000)+" WHERE AUFTRAG_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	/**
	 * This method takes a list of all "Auftraege" from database.
	 * 
	 * @author 
	 * @return list of all "Auftraege"
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public List<Auftrag> getAllAuftrag() throws SQLException, DatabaseException {
		List<Auftrag> result= new ArrayList<Auftrag>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT AUFTRAG_ID FROM Auftrag;");
		while (rs.next()) {
			result.add(this.getAuftragByID(rs.getInt(1)));
		}
		rs.close();
		stmt.close();
		return result;
	}

	/**
	 * This method creates an invoice and inserts it into database.
	 * 
	 * @author 
	 * @param name
	 * @param bezahlart
	 * @param betrag
	 * @param auftrag_id
	 * @param verwalter_id
	 * @param topf_id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int verwalter_id, int topf_id) throws SQLException, DatabaseException{
		Statement stmt = c.createStatement();
		String sql="SELECT * FROM RECHNUNG WHERE AUFTRAG_ID = "+auftrag_id+";";
		if(!stmt.executeQuery(sql).next()){
			sql ="INSERT INTO Rechnung (rechnungsname, bezahlungsart, betrag, AUFTRAG_ID, AUFTRAGGEBER_ID, "
					+ "ANSPRECHPARTNER_ID, TOPF_ID, bearbeitung, eingereicht, abgewickelt, ausstehend, RECHNUNGSDATUM, "
					+ "date_bearbeitung, date_eingereicht, date_abgewickelt, date_ausstehend) VALUES "
					+ "('"+name+"','"+bezahlart+"','"+betrag+"','"+auftrag_id+"','"+this.getAuftragByID(auftrag_id).getAuftraggeber().getPERSON_ID()+"'"
					+ ",'"+verwalter_id+"','"+topf_id+"',0,0,0,0, "+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+", "
					+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+");";
			stmt.executeUpdate(sql);	
		} else throw new RechnungVorhandenException();
		
		stmt.close();	
	
	}
	
	/**
	 * nur loeschen, wenn die Rechnung auch abgewickelt ist
	 * @author Christin Meyer, Nico Rychlik
	 * @param id
	 * @throws SQLException
	 * @throws DatabaseException 
	 */
	public void deleteRechnung (int id) throws SQLException, DatabaseException{
		Statement stmt = c.createStatement();
		if (!this.getRechnungByID(id).isAbgewickelt()) throw new RechnungNochNichtAbgewickeltException();
		String sql ="DELETE FROM Rechnung WHERE RECHNUNG_ID="+id+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	
	}
	
	/**
	 * This method takes an invoice by ID from database.
	 * 
	 * @author 
	 * @param ID
	 * @return the invoice
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Rechnung getRechnungByID(int ID) throws SQLException, DatabaseException {
		Rechnung result = null;
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM Rechnung WHERE RECHNUNG_ID = "+ID+";";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) result = new Rechnung(rs.getInt("RECHNUNG_ID"),new Date((rs.getLong("Rechnungsdatum"))*1000L), 
				rs.getString("rechnungsname"),rs.getString("bezahlungsart"),rs.getDouble("betrag"),
				this.convertIntToBoolean(rs.getInt("bearbeitung")),this.convertIntToBoolean(rs.getInt("eingereicht")),
				this.convertIntToBoolean(rs.getInt("abgewickelt")),this.convertIntToBoolean(rs.getInt("ausstehend")),
				new Date((rs.getLong("date_bearbeitung"))*1000L),new Date(rs.getLong(("date_eingereicht"))*1000L),
				new Date((rs.getLong("date_abgewickelt"))*1000L),new Date((rs.getLong("date_ausstehend"))*1000L), 
				rs.getInt("AUFTRAG_ID"), rs.getInt("ANSPRECHPARTNER_ID"), rs.getInt("TOPF_ID"));
		stmt.close();
		rs.close();
		if (result!=null) return result;
		else throw new RechnungNichtVorhandenException();
	}
	
	/**
	 * This method takes a list of all invoices from database.
	 * 
	 * @author 
	 * @return list of all invoices
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public List<Rechnung> getAllRechnung() throws SQLException, DatabaseException {
		List<Rechnung> result= new ArrayList<Rechnung>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM RECHNUNG;");
		while (rs.next()) {
			result.add(this.getRechnungByID(rs.getInt(1)));
		}
		rs.close();
		stmt.close();
		return result;
	}

	/**
	 * This method modifies an invoice and puts it into database.
	 * 
	 * @author 
	 * @param RECHNUNG_ID
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyRechnung(int RECHNUNG_ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE RECHNUNG SET "+attribut+" = '"+newData+"' WHERE RECHNUNG_ID="+RECHNUNG_ID+";";
		stmt.executeUpdate(sql);
		stmt.close();	
	}
	
	/**
	 * This method changes a status of an invoice and puts it into database.
	 * 
	 * @author 
	 * @param id
	 * @param Status
	 * @throws SQLException
	 */
	public void changeRechnungStatus (int id, String Status) throws SQLException {
		Statement stmt = c.createStatement();
		String sql ="SELECT "+Status+" FROM RECHNUNG WHERE RECHNUNG_ID = "+id+";";
		if(stmt.executeQuery(sql).getInt(1)==1) {
			sql="UPDATE RECHNUNG SET "+Status+" = 0 WHERE RECHNUNG_ID = "+id+";";
		}
		else {
			sql="UPDATE RECHNUNG SET "+Status+" = 1 WHERE RECHNUNG_ID = "+id+";";
		}
		stmt.executeUpdate(sql);
		sql="UPDATE RECHNUNG SET date_"+Status+" = "+(new Date().getTime()/1000)+" WHERE RECHNUNG_ID = "+id+";";
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	/**
	 * This method inserts a new "Topf" into database.
	 * 
	 * @author Joel Wolf
	 * @param name the name of the "Topf"
	 * @param soll "soll"-value of the "Topf"
	 * @param ist "ist"-value of the "Topf"
	 * @throws SQLException
	 */
	public void createTopf(String name, double soll, double ist, int KASSE_ID) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Topf (name, soll, ist, KASSE_ID) VALUES ('" + name + "', " + soll + ", " + ist + ", " + KASSE_ID + ");";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
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
		String sql = "UPDATE Topf SET " + attribut + " = '" + newData + "' WHERE TOPF_ID = " + TOPF_ID + ";";
		
		stmt.executeUpdate(sql);
		
		stmt.close();
	}

	/**
	 * This method deletes a "Topf" from database.
	 * 
	 * @author Joel Wolf
	 * @param TOPF_ID
	 * @throws SQLException
	 * @throws DatabaseException 
	 */
	public void deleteTopf(int TOPF_ID) throws SQLException, DatabaseException {
		Statement stmt = c.createStatement();
		String sql="SELECT * FROM Rechnung WHERE TOPF_ID = "+TOPF_ID+";";
		if(!stmt.executeQuery(sql).next()) {
			sql = "DELETE FROM Topf WHERE TOPF_ID = " + TOPF_ID + ";";
			stmt.executeUpdate(sql);
		} else throw new DeleteTopfException();
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
			result = new Topf(rs.getInt("TOPF_ID"), rs.getString("name"), rs.getDouble("soll"), rs.getDouble("ist"), this.getKasseByID(rs.getInt("KASSE_ID")));
		
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
	 * @throws DatabaseException 
	 */
	public List<Topf> getAllToepfe() throws SQLException, DatabaseException {
		
		List<Topf> result = new ArrayList<Topf>();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Topf;");
		
		while(rs.next()) {
			
			result.add(new Topf(rs.getInt("TOPF_ID"), rs.getString("name"), rs.getDouble("soll"), rs.getDouble("ist"), this.getKasseByID(rs.getInt("KASSE_ID"))));
		}
		
		stmt.close();		
		rs.close();
		
		return result;
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
	public void createKasse( String name, double soll, double ist, int typ, long ksnummer) throws SQLException {
		
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Kasse ( name, soll, ist, typ) VALUES ("
				+ "'" + name + "', " + soll + ",  " + ist + " , "+typ+");";		
		stmt.executeUpdate(sql);
		if (typ==2) {
			sql="SELECT KASSE_ID FROM KASSE WHERE KASSE_ID = (SELECT MAX(KASSE_ID) FROM KASSE);";
			int id =stmt.executeQuery(sql).getInt(1);
			sql="INSERT INTO VERBINDUNG_KASSE_KOSTENSTELLENNUMMER (KASSE_ID, kostenstellennummer) VALUES ('"+id+"' , '"+ksnummer+"') ;";
			stmt.executeUpdate(sql);
		}
		stmt.close();
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
		String sql="";
		if(!attribut.equals("kostenstellennummer")) {
			sql = "UPDATE Kasse SET " + attribut + " = '" + newData + "' WHERE KASSE_ID = " + KASSE_ID + ";";
		} else {
			sql = "UPDATE VERBINDUNG_Kasse_KOSTENSTELLENNUMMER SET " + attribut + " = '" + newData + "' WHERE KASSE_ID = " + KASSE_ID + ";";
		}
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
	public void deleteKasse(int KASSE_ID) throws SQLException, DatabaseException {
		Statement stmt = c.createStatement();
		String sql = "SELECT* FROM TOPF WHERE KASSE_ID = "+KASSE_ID+";";
		if(!stmt.executeQuery(sql).next()) {
			sql = "DELETE FROM Kasse WHERE KASSE_ID = " + KASSE_ID + ";";
			stmt.executeUpdate(sql);
			if(stmt.executeQuery("SELECT * FROM VERBINDUNG_KASSE_KOSTENSTELLENNUMMER WHERE KASSE_ID = "+KASSE_ID+";").next()) {
				stmt.executeUpdate("DELETE FROM VERBINDUNG_KASSE_KOSTENSTELLENNUMMER WHERE KASSE_ID = "+KASSE_ID+";");
			}
		} else throw new DeleteKasseException();
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
		String sql = "SELECT * FROM KASSE left JOIN Verbindung_Kasse_Kostenstellennummer ON KASSE.KASSE_ID = Verbindung_Kasse_Kostenstellennummer.KASSE_ID WHERE KASSE.KASSE_ID = " + KASSE_ID + ";";
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			
			switch(rs.getInt("typ")) {
			case 0:
				result = new Barkasse(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"));
				break;
			case 1:
				result = new Konto(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"));
				break;
			case 2:				
				result = new Kostenstelle(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist"),rs.getLong("kostenstellennummer"));
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
		ResultSet rs = stmt.executeQuery("SELECT * FROM KASSE left JOIN Verbindung_Kasse_Kostenstellennummer ON KASSE.KASSE_ID = Verbindung_Kasse_Kostenstellennummer.KASSE_ID;");
		
		while(rs.next()) {
			
			switch(rs.getInt("typ")) {
			case 0:
				result.add(new Barkasse(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist")));
				break;
			case 1:
				result.add(new Konto(rs.getInt("KASSE_ID"), rs.getString("name"), 
						rs.getDouble("soll"), rs.getDouble("ist")));
				break;
			case 2:
				result.add(new Kostenstelle(rs.getInt("KASSE_ID"), rs.getString("name"), rs.getDouble("soll"), rs.getDouble("ist"),rs.getLong("kostenstellennummer")));
				break;
			}
		}
		
		stmt.close();		
		rs.close();
		
		return result;
	}
	
	/**
	 * This method clears a "Bauteilwarenkorb" and puts it into database.
	 * 
	 * @author 
	 * @param id
	 * @throws SQLException
	 */
	public void clearBauteilwarenkorb (int id) throws SQLException {
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Bauteilwarenkorb WHERE PERSON_ID = " + id + ";";
		stmt.executeUpdate(sql);
		this.modifyPerson(id, "bauteilschulden", "0.0");
		stmt.close();	
	}

	/**
	 * This method takes a "Bauteilwarenkorb" by ID from database.
	 * 
	 * @author 
	 * @param person_ID
	 * @return the "Bauteilwarenkorb"
	 * @throws SQLException
	 */
	public List<Bauteilwarenkorbelement> getBauteilwarenkorbByID(int person_ID) throws SQLException {
		List<Bauteilwarenkorbelement> result = new ArrayList<Bauteilwarenkorbelement>();
		Statement stmt = c.createStatement();
		String sql= "SELECT * FROM Bauteilwarenkorb WHERE PERSON_ID = "+person_ID+";";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			result.add(new Bauteilwarenkorbelement(this.getBauteilByID(rs.getInt("BAUTEIL_ID")), rs.getInt("anzahl")));
		}
		stmt.close();
		rs.close();
		return result;
	}
	
	/**
	 * 
	 * @author Nico Rychlik
	 * @param i
	 * @return true if i is 1 else false
	 */
	public boolean convertIntToBoolean (int i) {
		if (i==1) return true;
		else return false;
	}
	
}

