/**
 * 
 */
package Logic;

/**
 * @author Nico Rychlik
 *
 */
public class Bauteilwarenkorbelement {
	
	private int bauteil_ID;
	private int anzahl;
	
	
	
	/**
	 * @param bauteil_ID
	 * @param anzahl
	 */
	public Bauteilwarenkorbelement(int bauteil_ID, int anzahl) {
		super();
		this.bauteil_ID = bauteil_ID;
		this.anzahl = anzahl;
	}
	/**
	 * @return the bauteil_ID
	 */
	public int getBauteil_ID() {
		return bauteil_ID;
	}
	/**
	 * @param bauteil_ID the bauteil_ID to set
	 */
	public void setBauteil_ID(int bauteil_ID) {
		this.bauteil_ID = bauteil_ID;
	}
	/**
	 * @return the anzahl
	 */
	public int getAnzahl() {
		return anzahl;
	}
	/**
	 * @param anzahl the anzahl to set
	 */
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
}