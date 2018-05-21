package DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Exceptions.NutzernameVorhandenException;
import Exceptions.PersonNichtInDBException;

import java.util.Date;
import Logic.*;

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
			String sql ="INSERT INTO Person (Vorname, Nachname, strasse, hausnr, PLZ, email, zuerst_erstellt, zuletzt_geaendert, nutzername, passwort, rolle) VALUES ('"+vname+"','"+nname+"','"+strasse+"','"+hausnummer+"','"+PLZ+"','"+email+"', "+(new Date().getTime()/1000)+", "+(new Date().getTime()/1000)+",'"+nutzername+"','"+passwort+"','"+rolle+"');";
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
				tempPerson=new Mitglied();
				break;
			
			case 1:
				tempPerson=new Kunde();
				break;
			
			case 2:
				tempPerson=new Lehrstuhlperson();
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
<<<<<<< HEAD
	
	
		
		
=======
	
	/**
	 * @author Nico Rychlik
	 * @param ID
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyPerson(int ID, String attribut, String newData) throws SQLException{
		Statement stmt = c.createStatement();
		String sql = "UPDATE Person SET "+attribut+" = "+newData+" WHERE PERSON_ID="+ID+";";
		stmt.executeUpdate(sql);
		String sql2 = "UPDATE Person SET zuletzt_geaendert = "+(new Date().getTime()/1000)+" WHERE PERSON_ID="+ID+";";
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
				result=new Mitglied();
				break;
			
			case 1:
				result=new Kunde();
				break;
			
			case 2:
				result=new Lehrstuhlperson();
				break;
			}
		}
		rs.close();
		stmt.close();
		if (!(result==null)) return result;
		else throw new PersonNichtInDBException();
	}
	
	
	
	public void createAuftrag(String titel, String art, double prog_kosten, double reele_kosten, List <Person> persons) throws SQLException{
		Statement stmt = c.createStatement();
		String sql ="INSERT INTO Auftrag (titel, art, prog_kosten, reele_kosten,personen) VALUES ('"+titel+"','"+art+"','"+prog_kosten+"','"+reele_kosten+"','"+ List <Person> persons+"');";
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
>>>>>>> branch 'master' of https://github.com/nicoonator/elab-cjkn.git
