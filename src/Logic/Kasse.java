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
	
	public Kasse(int kASSE_ID, String name, double soll, double ist, long ksnummer) {
		super();
		KASSE_ID = kASSE_ID;
		this.name = name;
		this.soll = soll;
		this.ist = ist;
		this.ksnummer=ksnummer;
	}
	
	final public int KASSE_ID;
	private String name;
	private double soll;
	private double ist;
	private String typ="Konto";
	private long ksnummer;
	
	@Override
	public String toString() {
		return name + ": " +KASSE_ID;
	}
	
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public int getKASSE_ID() {
		return KASSE_ID;
	}
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
	/**
	 * @return the ksnummer
	 */
	public long getKsnummer() {
		return ksnummer;
	}

	/**
	 * @param ksnummer the ksnummer to set
	 */
	public void setKsnummer(long ksnummer) {
		this.ksnummer = ksnummer;
	}
	
	
}
