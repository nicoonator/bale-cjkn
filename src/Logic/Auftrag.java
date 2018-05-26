package Logic;

import java.util.Date;

public class Auftrag {
	/**
	 * @param aUFTRAG_ID
	 * @param tITEL
	 * @param aRT
	 * @param progno_cost
	 * @param reelle_kosten
	 * @param angenommen
	 * @param gefertigt
	 * @param kalkuliert
	 * @param abgeholt
	 * @param abgerechnet
	 * @param warten
	 * @param unterbrochen
	 * @param defekt
	 */
	
	
	final private int AUFTRAG_ID;
	private String titel;
	final private String ART;
	private double progno_cost; 
	private	double reelle_kosten;
	private	boolean angenommen;
	private	boolean gefertigt;
	private	boolean	kalkuliert;
	private	boolean	abgeholt;
	private	boolean abgerechnet;
	private	boolean warten;
	private	boolean unterbrochen;
	private	boolean defekt;
	private Date date_angenommen;
	private Date date_gefertigt;
	private Date date_kalkuliert;
	private Date date_abgeholt;
	private Date date_abgerechnet;
	private Date date_warten;
	private Date date_unterbrochen;
	private Date date_defekt;
	
	/**
	 * @return the date_angenommen
	 */
	public Date getDate_angenommen() {
		return date_angenommen;
	}



	/**
	 * @param date_angenommen the date_angenommen to set
	 */
	public void setDate_angenommen(Date date_angenommen) {
		this.date_angenommen = date_angenommen;
	}



	/**
	 * @return the date_gefertigt
	 */
	public Date getDate_gefertigt() {
		return date_gefertigt;
	}



	/**
	 * @param date_gefertigt the date_gefertigt to set
	 */
	public void setDate_gefertigt(Date date_gefertigt) {
		this.date_gefertigt = date_gefertigt;
	}



	/**
	 * @return the date_kalkuliert
	 */
	public Date getDate_kalkuliert() {
		return date_kalkuliert;
	}



	/**
	 * @param date_kalkuliert the date_kalkuliert to set
	 */
	public void setDate_kalkuliert(Date date_kalkuliert) {
		this.date_kalkuliert = date_kalkuliert;
	}



	/**
	 * @return the date_abgeholt
	 */
	public Date getDate_abgeholt() {
		return date_abgeholt;
	}



	/**
	 * @param date_abgeholt the date_abgeholt to set
	 */
	public void setDate_abgeholt(Date date_abgeholt) {
		this.date_abgeholt = date_abgeholt;
	}



	/**
	 * @return the date_abgerechnet
	 */
	public Date getDate_abgerechnet() {
		return date_abgerechnet;
	}



	/**
	 * @param date_abgerechnet the date_abgerechnet to set
	 */
	public void setDate_abgerechnet(Date date_abgerechnet) {
		this.date_abgerechnet = date_abgerechnet;
	}



	/**
	 * @return the date_warten
	 */
	public Date getDate_warten() {
		return date_warten;
	}



	/**
	 * @param date_warten the date_warten to set
	 */
	public void setDate_warten(Date date_warten) {
		this.date_warten = date_warten;
	}



	/**
	 * @return the date_unterbrochen
	 */
	public Date getDate_unterbrochen() {
		return date_unterbrochen;
	}



	/**
	 * @param date_unterbrochen the date_unterbrochen to set
	 */
	public void setDate_unterbrochen(Date date_unterbrochen) {
		this.date_unterbrochen = date_unterbrochen;
	}



	/**
	 * @return the date_defekt
	 */
	public Date getDate_defekt() {
		return date_defekt;
	}



	/**
	 * @param date_defekt the date_defekt to set
	 */
	public void setDate_defekt(Date date_defekt) {
		this.date_defekt = date_defekt;
	}



	public Auftrag(int aUFTRAG_ID, String tITEL, String aRT, double prognostizierte_kosten, double reelle_kosten,
			boolean angenommen, boolean gefertigt, boolean kalkuliert, boolean abgeholt, boolean abgerechnet,
			boolean warten, boolean unterbrochen, boolean defekt) {
		super();
		AUFTRAG_ID = aUFTRAG_ID;
		titel = tITEL;
		ART = aRT;
		this.progno_cost = prognostizierte_kosten;
		this.reelle_kosten = reelle_kosten;
		this.angenommen = angenommen;
		this.gefertigt = gefertigt;
		this.kalkuliert = kalkuliert;
		this.abgeholt = abgeholt;
		this.abgerechnet = abgerechnet;
		this.warten = warten;
		this.unterbrochen = unterbrochen;
		this.defekt = defekt;
	}
	
	
	
	public String getTitel() {
		return titel;
	}



	public void setTitel(String titel) {
		this.titel = titel;
	}



	public double getProgno_cost() {
		return progno_cost;
	}


	public void setProgno_cost(double progno_cost) {
		this.progno_cost = progno_cost;
	}


	public double getReelle_kosten() {
		return reelle_kosten;
	}
	public void setReelle_kosten(double reelle_kosten) {
		this.reelle_kosten = reelle_kosten;
	}
	public boolean isAngenommen() {
		return angenommen;
	}
	public void setAngenommen(boolean angenommen) {
		this.angenommen = angenommen;
	}
	public boolean isGefertigt() {
		return gefertigt;
	}
	public void setGefertigt(boolean gefertigt) {
		this.gefertigt = gefertigt;
	}
	public boolean isKalkuliert() {
		return kalkuliert;
	}
	public void setKalkuliert(boolean kalkuliert) {
		this.kalkuliert = kalkuliert;
	}
	public boolean isAbgeholt() {
		return abgeholt;
	}
	public void setAbgeholt(boolean abgeholt) {
		this.abgeholt = abgeholt;
	}
	public boolean isAbgerechnet() {
		return abgerechnet;
	}
	public void setAbgerechnet(boolean abgerechnet) {
		this.abgerechnet = abgerechnet;
	}
	public boolean isWarten() {
		return warten;
	}
	public void setWarten(boolean warten) {
		this.warten = warten;
	}
	public boolean isUnterbrochen() {
		return unterbrochen;
	}
	public void setUnterbrochen(boolean unterbrochen) {
		this.unterbrochen = unterbrochen;
	}
	public boolean isDefekt() {
		return defekt;
	}
	public void setDefekt(boolean defekt) {
		this.defekt = defekt;
	}
	public int getAUFTRAG_ID() {
		return AUFTRAG_ID;
	}
	public String getTITEL() {
		return titel;
	}
	public String getART() {
		return ART;
	}
}
