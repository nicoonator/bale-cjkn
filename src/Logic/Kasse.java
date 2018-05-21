package Logic;

public abstract class Kasse extends Finanzverwaltung {
	final public KASSE_ID;
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
	
}
