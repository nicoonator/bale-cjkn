package Logic;

public class Barkasse extends Kasse {

	/**
	 * @param kASSE_ID
	 * @param name
	 * @param soll
	 * @param ist
	 */
	public Barkasse(int kASSE_ID, String name, double soll, double ist) {
		super(kASSE_ID, name, soll, ist);
		this.setTyp("Barkasse");
	}

}
