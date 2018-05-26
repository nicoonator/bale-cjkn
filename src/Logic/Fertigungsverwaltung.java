package Logic;

import java.util.List;

public class Fertigungsverwaltung {
	
	private static Fertigungsverwaltung instance;
	
	public static Fertigungsverwaltung getInstance(){
		if (instance==null) instance = new Fertigungsverwaltung();
		return instance;
	}
	
	public void createAuftrag(String titel, String art, double prog_kosten, double reele_kosten, List<Person> personen) {
		//TODO
	}
	
	public void deleteAuftrag(int id) {
		//TODO
	}
	
	public void changeStatus (int id, String status) {
		//TODO
	}
	
}
