package Logic;

import java.sql.SQLException;
import java.util.Date;

import Exceptions.DatabaseException;

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
	private int auftrag_ID;
	private int verwalter_ID;
	private int topf_ID;
	private Person auftraggeber;
	private Person verwalter;
	private Topf topf;
	private Auftrag auftrag;
	
	//AUFTRAG_ID
	
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
	 * @param auftrag_ID
	 * @param verwalter_ID
	 * @param topf_ID
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public Rechnung(int rECHNUNG_ID, Date rECHNUNGSDATUM, String rechnungsname, String bezahlart, double betrag,
			boolean bearbeitung, boolean eingereicht, boolean abgewickelt, boolean ausstehend, Date date_bearbeitung,
			Date date_eingereicht, Date date_abgewickelt, Date date_ausstehend, int auftrag_ID, int verwalter_ID,
			int topf_ID) throws SQLException, DatabaseException {
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
		this.auftrag_ID = auftrag_ID;
		this.verwalter_ID = verwalter_ID;
		this.topf_ID = topf_ID;
		this.auftraggeber=Fertigungsverwaltung.getInstance().getAuftragByID(auftrag_ID).getAuftraggeber();
		this.verwalter=Fertigungsverwaltung.getInstance().getAuftragByID(auftrag_ID).getVerwalter();
		this.topf=Finanzverwaltung.getInstance().getTopfByID(topf_ID);
		this.auftrag=Fertigungsverwaltung.getInstance().getAuftragByID(auftrag_ID);
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
	/**
	 * @return the auftrag_ID
	 */
	public int getAuftrag_ID() {
		return auftrag_ID;
	}
	/**
	 * @param auftrag_ID the auftrag_ID to set
	 */
	public void setAuftrag_ID(int auftrag_ID) {
		this.auftrag_ID = auftrag_ID;
	}
	/**
	 * @return the verwalter_ID
	 */
	public int getVerwalter_ID() {
		return verwalter_ID;
	}
	/**
	 * @param verwalter_ID the verwalter_ID to set
	 */
	public void setVerwalter_ID(int verwalter_ID) {
		this.verwalter_ID = verwalter_ID;
	}
	/**
	 * @return the topf_ID
	 */
	public int getTopf_ID() {
		return topf_ID;
	}
	/**
	 * @param topf_ID the topf_ID to set
	 */
	public void setTopf_ID(int topf_ID) {
		this.topf_ID = topf_ID;
	}

	/**
	 * @return the auftraggeber
	 */
	public Person getAuftraggeber() {
		return auftraggeber;
	}

	/**
	 * @param auftraggeber the auftraggeber to set
	 */
	public void setAuftraggeber(Person auftraggeber) {
		this.auftraggeber = auftraggeber;
	}

	/**
	 * @return the verwalter
	 */
	public Person getVerwalter() {
		return verwalter;
	}

	/**
	 * @param verwalter the verwalter to set
	 */
	public void setVerwalter(Person verwalter) {
		this.verwalter = verwalter;
	}

	/**
	 * @return the topf
	 */
	public Topf getTopf() {
		return topf;
	}

	/**
	 * @param topf the topf to set
	 */
	public void setTopf(Topf topf) {
		this.topf = topf;
	}

	/**
	 * @return the auftrag
	 */
	public Auftrag getAuftrag() {
		return auftrag;
	}

	/**
	 * @param auftrag the auftrag to set
	 */
	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}
	
}
