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
	
	
}
