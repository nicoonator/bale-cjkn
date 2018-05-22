package Logic;

import java.util.Date;
import java.util.List;

public abstract class Person {

	/**
	 * @param pERSON_ID
	 * @param vorname
	 * @param nachname
	 * @param strasse
	 * @param hausnr
	 * @param pLZ
	 * @param email
	 * @param zuerst_erstellt
	 * @param zuletzt_geaendert
	 * @param nutzername
	 * @param passwort
	 * @param bauteilschulden
	 * @param bauteilwarenkorb
	 * @param admin
	 */
	public Person(int pERSON_ID, String vorname, String nachname, String strasse, int hausnr, int pLZ, String email,
			Date zuerst_erstellt, Date zuletzt_geaendert, String nutzername, String passwort, double bauteilschulden,
			List<Bauteil> bauteilwarenkorb, boolean admin) {
		super();
		PERSON_ID = pERSON_ID;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.hausnr = hausnr;
		PLZ = pLZ;
		this.email = email;
		this.zuerst_erstellt = zuerst_erstellt;
		this.zuletzt_geaendert = zuletzt_geaendert;
		this.nutzername = nutzername;
		this.passwort = passwort;
		this.bauteilschulden = bauteilschulden;
		this.bauteilwarenkorb = bauteilwarenkorb;
		this.admin = admin;
	}
	private final int PERSON_ID;
	private String vorname;
	private String nachname;
	private String strasse;
	private int hausnr;
	private int PLZ;
	private String email;
	private final Date zuerst_erstellt;
	private Date zuletzt_geaendert;
	private String nutzername;
	private String passwort;
	private double bauteilschulden = 0;
	private List<Bauteil> bauteilwarenkorb;
	private final boolean admin;
	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}
	/**
	 * @param strasse the strasse to set
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	/**
	 * @return the hausnr
	 */
	public int getHausnr() {
		return hausnr;
	}
	/**
	 * @param hausnr the hausnr to set
	 */
	public void setHausnr(int hausnr) {
		this.hausnr = hausnr;
	}
	/**
	 * @return the pLZ
	 */
	public int getPLZ() {
		return PLZ;
	}
	/**
	 * @param pLZ the pLZ to set
	 */
	public void setPLZ(int pLZ) {
		PLZ = pLZ;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the zuletzt_geaendert
	 */
	public Date getZuletzt_geaendert() {
		return zuletzt_geaendert;
	}
	/**
	 * @param zuletzt_geaendert the zuletzt_geaendert to set
	 */
	public void setZuletzt_geaendert(Date zuletzt_geaendert) {
		this.zuletzt_geaendert = zuletzt_geaendert;
	}
	/**
	 * @return the nutzername
	 */
	public String getNutzername() {
		return nutzername;
	}
	/**
	 * @param nutzername the nutzername to set
	 */
	public void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}
	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}
	/**
	 * @param passwort the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	/**
	 * @return the bauteilschulden
	 */
	public double getBauteilschulden() {
		return bauteilschulden;
	}
	/**
	 * @param bauteilschulden the bauteilschulden to set
	 */
	public void setBauteilschulden(double bauteilschulden) {
		this.bauteilschulden = bauteilschulden;
	}
	/**
	 * @return the bauteilwarenkorb
	 */
	public List<Bauteil> getBauteilwarenkorb() {
		return bauteilwarenkorb;
	}
	/**
	 * @param bauteilwarenkorb the bauteilwarenkorb to set
	 */
	public void setBauteilwarenkorb(List<Bauteil> bauteilwarenkorb) {
		this.bauteilwarenkorb = bauteilwarenkorb;
	}
	/**
	 * @return the pERSON_ID
	 */
	public int getPERSON_ID() {
		return PERSON_ID;
	}
	/**
	 * @return the zuerst_erstellt
	 */
	public Date getZuerst_erstellt() {
		return zuerst_erstellt;
	}
	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
}
