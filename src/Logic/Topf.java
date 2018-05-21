package Logic;

public class Topf extends Finanzverwaltung {
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
