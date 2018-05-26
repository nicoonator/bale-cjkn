package Logic;

import java.util.Date;

public class Rechnung {

	/**
	 * @param rECHNUNG_ID
	 * @param rECHNUNGSDATUM
	 * @param rechnungsname
	 * @param bezahlart
	 * @param betrag
	 * @param bearbeitung
	 * @param eingereicht
	 * @param abgewickelt
	 * @param ausstehend
	 */

	final private int RECHNUNG_ID;
	final private Date RECHNUNGSDATUM;
	private String rechnungsname;
	private String bezahlart;
	private double betrag;
	private boolean bearbeitung;
	private boolean eingereicht;
	private boolean abgewickelt;
	private boolean ausstehend;
	private Date date_bearbeitung;
	private Date date_eingereicht;
	private Date date_abgewickelt;
	private Date date_ausstehend;
	
	
	
	/**
	 * @param rECHNUNG_ID
	 * @param rECHNUNGSDATUM
	 * @param rechnungsname
	 * @param bezahlart
	 * @param betrag
	 * @param bearbeitung
	 * @param eingereicht
	 * @param abgewickelt
	 * @param ausstehend
	 * @param date_bearbeitung
	 * @param date_eingereicht
	 * @param date_abgewickelt
	 * @param date_ausstehend
	 */
	public Rechnung(int rECHNUNG_ID, Date rECHNUNGSDATUM, String rechnungsname, String bezahlart, double betrag,
			boolean bearbeitung, boolean eingereicht, boolean abgewickelt, boolean ausstehend, Date date_bearbeitung,
			Date date_eingereicht, Date date_abgewickelt, Date date_ausstehend) {
		super();
		RECHNUNG_ID = rECHNUNG_ID;
		RECHNUNGSDATUM = rECHNUNGSDATUM;
		this.rechnungsname = rechnungsname;
		this.bezahlart = bezahlart;
		this.betrag = betrag;
		this.bearbeitung = bearbeitung;
		this.eingereicht = eingereicht;
		this.abgewickelt = abgewickelt;
		this.ausstehend = ausstehend;
		this.date_bearbeitung = date_bearbeitung;
		this.date_eingereicht = date_eingereicht;
		this.date_abgewickelt = date_abgewickelt;
		this.date_ausstehend = date_ausstehend;
	}
	/**
	 * @return the date_bearbeitung
	 */
	public Date getDate_bearbeitung() {
		return date_bearbeitung;
	}
	/**
	 * @param date_bearbeitung the date_bearbeitung to set
	 */
	public void setDate_bearbeitung(Date date_bearbeitung) {
		this.date_bearbeitung = date_bearbeitung;
	}
	/**
	 * @return the date_eingereicht
	 */
	public Date getDate_eingereicht() {
		return date_eingereicht;
	}
	/**
	 * @param date_eingereicht the date_eingereicht to set
	 */
	public void setDate_eingereicht(Date date_eingereicht) {
		this.date_eingereicht = date_eingereicht;
	}
	/**
	 * @return the date_abgewickelt
	 */
	public Date getDate_abgewickelt() {
		return date_abgewickelt;
	}
	/**
	 * @param date_abgewickelt the date_abgewickelt to set
	 */
	public void setDate_abgewickelt(Date date_abgewickelt) {
		this.date_abgewickelt = date_abgewickelt;
	}
	/**
	 * @return the date_ausstehend
	 */
	public Date getDate_ausstehend() {
		return date_ausstehend;
	}
	/**
	 * @param date_ausstehend the date_ausstehend to set
	 */
	public void setDate_ausstehend(Date date_ausstehend) {
		this.date_ausstehend = date_ausstehend;
	}
	public String getRechnungsname() {
		return rechnungsname;
	}
	public void setRechnungsname(String rechnungsname) {
		this.rechnungsname = rechnungsname;
	}
	public String getBezahlart() {
		return bezahlart;
	}
	public void setBezahlart(String bezahlart) {
		this.bezahlart = bezahlart;
	}
	public double getBetrag() {
		return betrag;
	}
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	public boolean isBearbeitung() {
		return bearbeitung;
	}
	public void setBearbeitung(boolean bearbeitung) {
		this.bearbeitung = bearbeitung;
	}
	public boolean isEingereicht() {
		return eingereicht;
	}
	public void setEingereicht(boolean eingereicht) {
		this.eingereicht = eingereicht;
	}
	public boolean isAbgewickelt() {
		return abgewickelt;
	}
	public void setAbgewickelt(boolean abgewickelt) {
		this.abgewickelt = abgewickelt;
	}
	public boolean isAusstehend() {
		return ausstehend;
	}
	public void setAusstehend(boolean ausstehend) {
		this.ausstehend = ausstehend;
	}
	public int getRECHNUNG_ID() {
		return RECHNUNG_ID;
	}
	public Date getRECHNUNGSDATUM() {
		return RECHNUNGSDATUM;
	}
	
}
