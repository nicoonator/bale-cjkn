package Logic;

// Ist vererbung von Gui noetig?
public class Finanzverwaltung {
	
	
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id) {
		//TODO
	}
	
	//Frage fuer Besprechung: Im UML war in der Methodenuebergabe rechnung_id vom Typ id eingetragen
	//Denke damit war aber Int gemeint
	public void deleteRechnung(int rechnung_id) {
		//TODO
	}
	
	public void modifyRechnung (int rechnung_id, String attribut, String newData) {
		//TODO
	}
	
	public void exportRechnung(int rechnung_id) {
		//TODO
	}
	
	public void changeStatus (int id, String status) {
		//TODO
	}
}
