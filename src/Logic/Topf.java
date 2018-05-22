package Logic;

public class Topf extends Finanzverwaltung {
	/**
	 * @param tOPF_ID
	 * @param name
	 * @param soll
	 * @param ist
	 */
	public Topf(int tOPF_ID, String name, double soll, double ist) {
		super();
		TOPF_ID = tOPF_ID;
		this.name = name;
		this.soll = soll;
		this.ist = ist;
	}
	final public int TOPF_ID;
	private String name;
	private double soll;
	private double ist;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSoll() {
		return soll;
	}
	public void setSoll(double soll) {
		this.soll = soll;
	}
	public double getIst() {
		return ist;
	}
	public void setIst(double ist) {
		this.ist = ist;
	}
	public int getTOPF_ID() {
		return TOPF_ID;
	}
	
}
