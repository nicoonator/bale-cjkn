package Logic;

public class Bauteil {
	
	private int ID;
	private	String name;
	private	String link;
	private double preis;
	private	int gelagert;
	private	int geplant;
	private	int bestellt;
	private	String ort;
	private int Kategorie_ID;
	
	
	/**
	 * @param iD
	 * @param name
	 * @param link
	 * @param preis
	 * @param gelagert
	 * @param geplant
	 * @param bestellt
	 * @param ort
	 */
	public Bauteil(int iD, String name, String link, double preis, int gelagert, int geplant, int bestellt,
			String ort, int Kategorie_ID) {
		super();
		ID = iD;
		this.name = name;
		this.link = link;
		this.preis = preis;
		this.gelagert = gelagert;
		this.geplant = geplant;
		this.bestellt = bestellt;
		this.ort = ort;
		this.Kategorie_ID=Kategorie_ID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public int getGelagert() {
		return gelagert;
	}
	public void setGelagert(int gelagert) {
		this.gelagert = gelagert;
	}
	public int getGeplant() {
		return geplant;
	}
	public void setGeplant(int geplant) {
		this.geplant = geplant;
	}
	public int getBestellt() {
		return bestellt;
	}
	public void setBestellt(int bestellt) {
		this.bestellt = bestellt;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	/**
	 * @return the kategorie_ID
	 */
	public int getKategorie_ID() {
		return Kategorie_ID;
	}
	/**
	 * @param kategorie_ID the kategorie_ID to set
	 */
	public void setKategorie_ID(int kategorie_ID) {
		Kategorie_ID = kategorie_ID;
	}
	
	
}
