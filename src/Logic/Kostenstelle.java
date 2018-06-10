package Logic;

public class Kostenstelle extends Kasse{
	
	/**
	 * @param kASSE_ID
	 * @param name
	 * @param soll
	 * @param ist
	 */
	
	
	public Kostenstelle(int kASSE_ID, String name, double soll, double ist, long ksnummer) {
		super(kASSE_ID, name, soll, ist, ksnummer);
		this.setTyp("Kostenstelle");
	}



	
}
