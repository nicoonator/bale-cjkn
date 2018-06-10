package Logic;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Finanzverwaltung {
	
	private static Finanzverwaltung instance;
	
	/**
	 * This method ensures that at most one instance of "Finanzverwaltung" exists.
	 * 
	 * @author 
	 * @return an instance of "Finanzverwaltung"
	 */
	public static Finanzverwaltung getInstance(){
		if (instance==null) instance = new Finanzverwaltung();
		return instance;
	}
	
	/**
	 * This method tries to create an invoice.
	 * 
	 * @author 
	 * @param name
	 * @param bezahlart
	 * @param betrag
	 * @param auftrag_id
	 * @param auftraggeber_id
	 * @param verwalter_id
	 * @param topf_id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id, int topf_id) throws SQLException, DatabaseException {
		SQLManager.getInstance().createRechnung(name, bezahlart, betrag, auftrag_id, verwalter_id, topf_id);
	}
	
	/**
	 * This method tries to delete an invoice.
	 * 
	 * @author 
	 * @param rechnung_id
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void deleteRechnung(int rechnung_id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteRechnung(rechnung_id);
	}
	
	/**
	 * This method tries to modify an invoice.
	 * 
	 * @author 
	 * @param rechnung_id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyRechnung (int rechnung_id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyRechnung(rechnung_id, attribut, newData);
	}
	
	/**
	 * This method exports an invoice to PDF.
	 * 
	 * @author Joel Wolf
	 * @param rechnung_id the ID of the selected invoice
	 * @throws SQLException
	 * @throws DatabaseException
	 * @throws IOException
	 */
	public void exportRechnung(int rechnung_id) throws SQLException, DatabaseException, IOException {
		Rechnung tempRechnung = SQLManager.getInstance().getRechnungByID(rechnung_id);
		Auftrag tempAuftrag = SQLManager.getInstance().getAuftragByID(tempRechnung.getAuftrag_ID());
		
		String path = "Rechnungen/Rechnung_" + rechnung_id + ".pdf";
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		document.addPage(page);
		
		contentStream.beginText();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
		contentStream.newLineAtOffset(80, 700);
		contentStream.setLeading(29.0f);
		
		contentStream.showText("Rechnung");
		
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
		
		contentStream.showText("                                                    ");
		contentStream.showText(new SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()));
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText(tempAuftrag.getAuftraggeber().getVorname() + " " 
				+ tempAuftrag.getAuftraggeber().getNachname());
		contentStream.newLine();
		contentStream.showText(tempAuftrag.getAuftraggeber().getStrasse() + " " 
				+ tempAuftrag.getAuftraggeber().getHausnr());
		contentStream.newLine();
		contentStream.showText(String.valueOf(tempAuftrag.getAuftraggeber().getPLZ()));
		contentStream.newLine();
		contentStream.showText("E-Mail: " + tempAuftrag.getAuftraggeber().getEmail());
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText("Rechnungsname: " + tempRechnung.getRechnungsname());
		contentStream.newLine();
		contentStream.showText("Rechnungs-ID: " + rechnung_id + " / Auftrags-ID: " 
				+ tempAuftrag.getAUFTRAG_ID());
		contentStream.newLine();
		contentStream.showText("Rechnungsdatum: " 
				+ (tempRechnung.getRECHNUNGSDATUM()));
		contentStream.newLine();
		contentStream.showText("Betreuender Admin: " + tempAuftrag.getVerwalter());
		contentStream.newLine();
		contentStream.showText("Betrag: " 
				+ NumberFormat.getCurrencyInstance(new Locale("de", "DE")).format(tempRechnung.getBetrag()));
		contentStream.newLine();
		contentStream.showText("Bezahlart: " + tempRechnung.getBezahlart());
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText("Bezahlt: ________________________________________");
		contentStream.newLine();
		contentStream.showText("                           (Datum, Unterschrift eLab)");
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText("Anmerkungen:");
		
		contentStream.endText();
		contentStream.close();
		
		File file = new File("Rechnungen");
		if(!file.isDirectory())
			file.mkdir();
		
		document.save(path);
		
		document.close();
	}
	
	/**
	 * This method tries to change a status.
	 * 
	 * @author 
	 * @param id
	 * @param status
	 * @throws SQLException
	 */
	public void changeStatus (int id, String status) throws SQLException {
		SQLManager.getInstance().changeRechnungStatus(id, status);
	}
	
	/**
	 * This method tries to get a "Topf" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Topf getTopfByID(int id) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getTopfByID(id);
	}
	
	/**
	 * This method tries to get all "Toepfe".
	 * 
	 * @author 
	 * @return
	 * @throws SQLException
	 * @throws DatabaseException 
	 */
	public List<Topf> getAllTopf() throws SQLException, DatabaseException {
		return SQLManager.getInstance().getAllToepfe();
	}
	
	/**
	 * This method tries to modify a "Topf".
	 * 
	 * @author 
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyTopf(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyTopf(id, attribut, newData);
	}
	
	/**
	 * This method tries to get a "Kasse" by ID.
	 * 
	 * @author 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public Kasse getKasseByID(int id) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getKasseByID(id);
	}
	
	/**
	 * This method tries to get all "Kassen".
	 * 
	 * @author 
	 * @return
	 * @throws SQLException
	 */
	public List<Kasse> getAllKasse() throws SQLException {
		return SQLManager.getInstance().getAllKassen();
	}
	
	/**
	 * This method tries to modify a "Kasse".
	 * 
	 * @author 
	 * @param id
	 * @param attribut
	 * @param newData
	 * @throws SQLException
	 */
	public void modifyKasse(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyKasse(id, attribut, newData);
	}
	
	public void createKasse(String name, double soll, double ist, int typ, long ksnummer) throws SQLException {
		if (typ!=2){
			SQLManager.getInstance().createKasse(name, soll, ist, typ, 0);
		} else {
			SQLManager.getInstance().createKasse(name, soll, ist, typ, ksnummer);
		}
	}
	
	public void deleteKasse(int id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteKasse(id);
	}
	
	public void createTopf(String name, double soll, double ist, int KASSE_ID) throws SQLException {
		SQLManager.getInstance().createTopf(name, soll, ist, KASSE_ID);
	}
	
	public void deleteTopf(int id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteTopf(id);
	}
	
	public Rechnung getRechnungByID(int id) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getRechnungByID(id);
	}
	
	public List<Rechnung> getAllRechnung() throws SQLException, DatabaseException{
		return SQLManager.getInstance().getAllRechnung();
	}
}
