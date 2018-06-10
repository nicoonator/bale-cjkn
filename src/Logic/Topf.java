package Logic;

import java.sql.SQLException;

import Exceptions.DatabaseException;

public class Topf {

	final private int TOPF_ID;
	private String name;
	private double soll;
	private double ist;
	private int kasse_ID;
	private String kasseName;
	private Kasse kasse;
	
	/**
	 * @param tOPF_ID
	 * @param name
	 * @param soll
	 * @param ist
	 * @param kasse_ID
	 * @param kasseName
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public Topf(int tOPF_ID, String name, double soll, double ist, Kasse kasse){
		super();
		TOPF_ID = tOPF_ID;
		this.name = name;
		this.soll = soll;
		this.ist = ist;
		this.kasse_ID = kasse.getKASSE_ID();
		this.kasseName =  kasse.getName();
		this.kasse=kasse;
	}
	
	@Override
	public String toString() {
		return name +": " +TOPF_ID;
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
	public int getTOPF_ID() {
		return TOPF_ID;
	}
	/**
	 * @return the kasse_ID
	 */
	public int getKasse_ID() {
		return kasse_ID;
	}
	/**
	 * @param kasse_ID the kasse_ID to set
	 */
	public void setKasse_ID(int kasse_ID) {
		this.kasse_ID = kasse_ID;
	}
	/**
	 * @return the kasseName
	 */
	public String getKasseName() {
		return kasseName;
	}
	/**
	 * @param kasseName the kasseName to set
	 */
	public void setKasseName(String kasseName) {
		this.kasseName = kasseName;
	}
	/**
	 * @return the kasse
	 */
	public Kasse getKasse() {
		return kasse;
	}
	/**
	 * @param kasse the kasse to set
	 */
	public void setKasse(Kasse kasse) {
		this.kasse = kasse;
	}
	
}
