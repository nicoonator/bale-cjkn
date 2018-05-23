package Logic;

public abstract class Kasse {
	/**
	 * @param kASSE_ID
	 * @param name
	 * @param soll
	 * @param ist
	 */
	public Kasse(int kASSE_ID, String name, double soll, double ist) {
		super();
		KASSE_ID = kASSE_ID;
		this.name = name;
		this.soll = soll;
		this.ist = ist;
	}
	final public int KASSE_ID;
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
