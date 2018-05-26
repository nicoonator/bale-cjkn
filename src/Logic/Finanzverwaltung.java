package Logic;


public class Finanzverwaltung {
	
	private static Finanzverwaltung instance;
	
	public static Finanzverwaltung getInstance(){
		if (instance==null) instance = new Finanzverwaltung();
		return instance;
	}
	
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id) {
		//TODO
	}
	
	public void deleteRechnung(int rechnung_id) {
		//TODO
	}
	
	public void modifyRechnung (int rechnung_id, String attribut, String newData) {
		//TODO
	}
	
	public void exportRechnung(int rechnung_id) {
		//TODO PDF!
	}
	
	public void changeStatus (int id, String status) {
		//TODO
	}
}
