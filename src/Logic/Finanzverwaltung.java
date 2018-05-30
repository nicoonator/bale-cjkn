package Logic;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;

import DataAccess.SQLManager;
import Exceptions.DatabaseException;

public class Finanzverwaltung {
	
	private static Finanzverwaltung instance;
	
	public static Finanzverwaltung getInstance(){
		if (instance==null) instance = new Finanzverwaltung();
		return instance;
	}
	
	public void createRechnung(String name, String bezahlart, double betrag, int auftrag_id, int auftraggeber_id, int verwalter_id, int topf_id) throws SQLException, DatabaseException {
		SQLManager.getInstance().createRechnung(name, bezahlart, betrag, auftrag_id, verwalter_id, topf_id);
	}
	
	public void deleteRechnung(int rechnung_id) throws SQLException, DatabaseException {
		SQLManager.getInstance().deleteRechnung(rechnung_id);
	}
	
	public void modifyRechnung (int rechnung_id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyRechnung(rechnung_id, attribut, newData);
	}
	
	/**
	 * This method exports an invoice to PDF.
	 * 
	 * @param rechnung_id the ID of the selected invoice
	 * @throws SQLException
	 * @throws DatabaseException
	 * @throws IOException
	 */
	public void exportRechnung(int rechnung_id) throws SQLException, DatabaseException, IOException {
		Rechnung tempRechnung = SQLManager.getInstance().getRechnungByID(rechnung_id);
		
		//TODO Druckdatum, Name/Anschrift von Auftraggeber, ausdruckender Admin
		
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
		contentStream.newLine();
		contentStream.newLine();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
		contentStream.showText("Rechnungsname: " + tempRechnung.getRechnungsname());
		contentStream.newLine();
		contentStream.showText("ID: " + rechnung_id);
		contentStream.newLine();
		contentStream.showText("Datum: " + tempRechnung.getRECHNUNGSDATUM());
		contentStream.newLine();
		contentStream.showText("Betrag: " + tempRechnung.getBetrag() + " €");
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
	
	public void changeStatus (int id, String status) throws SQLException {
		SQLManager.getInstance().changeRechnungStatus(id, status);
	}
	
	public Topf getTopfByID(int id) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getTopfByID(id);
	}
	
	public List<Topf> getAllTopf() throws SQLException {
		return SQLManager.getInstance().getAllToepfe();
	}
	
	public void modifyTopf(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyTopf(id, attribut, newData);
	}
	
	public Kasse getKasseByID(int id) throws SQLException, DatabaseException {
		return SQLManager.getInstance().getKasseByID(id);
	}
	
	public List<Kasse> getAllKasse() throws SQLException {
		return SQLManager.getInstance().getAllKassen();
	}
	
	public void modifyKasse(int id, String attribut, String newData) throws SQLException {
		SQLManager.getInstance().modifyKasse(id, attribut, newData);
	}
}
