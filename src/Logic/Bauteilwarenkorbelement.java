/**
 * 
 */
package Logic;

/**
 * @author Nico Rychlik
 *
 */
public class Bauteilwarenkorbelement {
	
	private Bauteil bauteil;
	private int anzahl;
	private String name;
	private String link; 
	private String kategorie;
	private Double preis;
	
	
	
	/**
	 * @param bauteil_ID
	 * @param anzahl
	 */
	public Bauteilwarenkorbelement(Bauteil bauteil, int anzahl) {
		super();
		this.bauteil = bauteil;
		this.anzahl = anzahl;
		this.name=bauteil.getName();
		this.link=bauteil.getLink();
		this.kategorie=bauteil.getKategorie_name();
		this.preis=bauteil.getPreis();
	}
	/**
	 * @return the bauteil_ID
	 */

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

	/**
	 * @return the bauteil
	 */
	public Bauteil getBauteil() {
		return bauteil;
	}

	/**
	 * @param bauteil the bauteil to set
	 */
	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the kategorie
	 */
	public String getKategorie() {
		return kategorie;
	}

	/**
	 * @param kategorie the kategorie to set
	 */
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * @return the preis
	 */
	public Double getPreis() {
		return preis;
	}

	/**
	 * @param preis the preis to set
	 */
	public void setPreis(Double preis) {
		this.preis = preis;
	}
	
}