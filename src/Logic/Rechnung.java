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
	public Rechnung(int rECHNUNG_ID, Date rECHNUNGSDATUM, String rechnungsname, String bezahlart, double betrag,
			boolean bearbeitung, boolean eingereicht, boolean abgewickelt, boolean ausstehend) {
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
	}
	final private int RECHNUNG_ID;
	final private Date RECHNUNGSDATUM;
	private String rechnungsname;
	private String bezahlart;
	private double betrag;
	private boolean bearbeitung;
	private boolean eingereicht;
	private boolean abgewickelt;
	private boolean ausstehend;
	
	
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
