package Logic;


public class Personenverwaltung {
	
	private static Personenverwaltung instance;
	
	public static Personenverwaltung getInstance(){
		if (instance==null) instance = new Personenverwaltung();
		return instance;
	}

	public void createPerson(String vorname, String nachname, String strasse, 
			int hausnr, int PLZ, String email) {
		
		//TODO
	}
	
	public void deletePerson(int id) {
		
		//TODO
	}
	
	public void modifyPerson(int id, String attribute, String newData) {
		
		//TODO
	}
	
	public boolean checkadmin(int id) {
		
		//TODO
		
		return false;
	}
}
