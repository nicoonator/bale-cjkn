/**
 * 
 */
package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.Iterator;
import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Finanzverwaltung.GUICreateRechnung;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Fertigungsverwaltung;
import Logic.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIFertigungsverwaltung {
	
	Tab tab;
	TableView<Auftrag> auftragTable;
	
	public GUIFertigungsverwaltung(Tab tab) {
		this.tab=tab; 
	}
	
	@SuppressWarnings("unchecked")
	public void open() {
		
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		
		//Oben
		HBox top = new HBox();
		top.setSpacing(10);
		top.setPadding(new Insets(10,10,10,20));
		
		Button create = new Button("Neu");
		Button modify = new Button("Bearbeiten");
		Button delete = new Button("Loeschen");
		
		modify.setDisable(true);
		delete.setDisable(true);
		top.getChildren().addAll(create, modify, delete);
		bp.setTop(top);
		
		
		
		//Links
		TableColumn<Auftrag, Integer> titel = new TableColumn<>("Auftragstitel");
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titel.setMinWidth(107);
		TableColumn<Auftrag, String> art = new TableColumn<>("Auftragsart");
		art.setCellValueFactory(new PropertyValueFactory<>("ART"));
		art.setMinWidth(107);
		TableColumn<Auftrag, String> auftrag_id = new TableColumn<>("Auftragsid");
		auftrag_id.setCellValueFactory(new PropertyValueFactory<>("AUFTRAG_ID"));
		auftrag_id.setMinWidth(107);
		TableColumn<Auftrag, String> auftraggeber = new TableColumn<>("Auftragsgeber");
		auftraggeber.setCellValueFactory(new PropertyValueFactory<>("auftraggeber"));
		auftraggeber.setMinWidth(107);
		TableColumn<Auftrag, String> verwalter = new TableColumn<>("Verwalter");
		verwalter.setCellValueFactory(new PropertyValueFactory<>("verwalter"));
		verwalter.setMinWidth(107);
		TableColumn<Auftrag, Double> prognostKosten = new TableColumn<>("Prognost. Kosten");
		prognostKosten.setCellValueFactory(new PropertyValueFactory<>("progno_cost"));
		prognostKosten.setMinWidth(107);
		TableColumn<Auftrag, Double> reellKosten = new TableColumn<>("Reelle Kosten");
		reellKosten.setCellValueFactory(new PropertyValueFactory<>("reelle_kosten"));
		reellKosten.setMinWidth(107);
		
		auftragTable = new TableView<>();
		try {
			auftragTable.setItems(getAuftraege());
		} catch (SQLException e2) {
			e2.printStackTrace();
			AlertBox.display("Fehler", e2.getMessage() );
		}
		auftragTable.getColumns().addAll(titel, art, auftrag_id, auftraggeber, verwalter, prognostKosten, reellKosten);
		
		auftragTable.setPrefWidth(751);
		bp.setLeft(auftragTable);
	
		//RECHTS
		
		VBox rechts = new VBox();
		rechts.setSpacing(10);
		rechts.setPadding(new Insets(10,10,10,20));
		Label tabellenLabel = new Label("Liste der Auftragsvertreter");
		
		TableColumn<Person, String> vorname = new TableColumn<>("Vorname");
		vorname.setMinWidth(125);
		vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
		
		TableColumn<Person, String> nachname = new TableColumn<>("Nachname");
		nachname.setMinWidth(125);
		nachname.setCellValueFactory(new PropertyValueFactory<>("nachname"));
		
		
		TableView<Person> vertretungTable = new TableView<>();
		vertretungTable.getColumns().addAll(vorname,nachname);
		vertretungTable.setPrefWidth(252);
		
		
		rechts.getChildren().addAll(tabellenLabel, vertretungTable);
		bp.setRight(rechts);
		
		//Start of CENTER
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(10);
		grid.setHgap(40);
		
		
		//Personen		
		Label l1 = new Label("Auftragsersteller:");
		GridPane.setConstraints(l1, 0, 0);
		Label erstellerLabel = new Label("- Auftragsersteller -");
		GridPane.setConstraints(erstellerLabel, 0, 1);
		
		Label auftragsnrName = new Label("AuftragsID");
		GridPane.setConstraints(auftragsnrName, 1, 0);
		Label auftragsnrInt = new Label(" - Auftrags ID -");
		GridPane.setConstraints(auftragsnrInt, 1, 1);
		
		//Admins
		Label l2 = new Label("Auftragsverwalter");
		GridPane.setConstraints(l2, 2, 0);
		Label verwalterLabel = new Label("- Auftragverwalter -");
		GridPane.setConstraints(verwalterLabel, 2, 1);
		
		//Auftragstitel und Feld
		Label l3 = new Label("Auftragstitel");
		GridPane.setConstraints(l3 , 0 , 3);
		TextField auftragsTitel = new TextField();
		auftragsTitel.setDisable(true);
		GridPane.setConstraints(auftragsTitel, 0, 4);
		
		//Art der Fertigung
		Label l4 = new Label("Fertigungsart");
		GridPane.setConstraints(l4 , 2 , 3);
		Label fertigungsArt = new Label ("- Fertigungsart - ");
		GridPane.setConstraints(fertigungsArt, 2, 4);
		
		//Prognostizierte Kosten
		Label l5 = new Label("Prognostizierte Kosten");
		GridPane.setConstraints(l5 , 0 , 6);
		TextField prognoKosten = new TextField();
		prognoKosten.setDisable(true);
		GridPane.setConstraints(prognoKosten, 0, 7);
		
		//Reelle Kosten
		Label l6 = new Label("Reelle Kosten");
		GridPane.setConstraints(l6 , 2 , 6);
		TextField reelleKosten = new TextField();
		reelleKosten.setDisable(true);
		GridPane.setConstraints(reelleKosten, 2, 7);
		
		
		//Checkboxen
		
		CheckBox angenommen = new CheckBox("Angenommen");
		angenommen.setDisable(true);
		GridPane.setConstraints(angenommen, 0, 9);
		Label angenommenLabel = new Label("Zeitangabe Angenommen");
		GridPane.setConstraints(angenommenLabel, 1, 9);
		
		CheckBox gefertigt = new CheckBox("Gefertigt");
		gefertigt.setDisable(true);
		GridPane.setConstraints(gefertigt, 0, 10);
		Label gefertigtLabel = new Label("Zeitangabe Gefertigt");
		GridPane.setConstraints(gefertigtLabel, 1, 10);
		
		CheckBox kalkuliert = new CheckBox("Kalkuliert");
		kalkuliert.setDisable(true);
		GridPane.setConstraints(kalkuliert, 0, 11);
		Label kalkuliertLabel = new Label("Zeitangabe Kalkuliert");
		GridPane.setConstraints(kalkuliertLabel, 1, 11);
		
		CheckBox abgeholt = new CheckBox("Abgeholt");
		abgeholt.setDisable(true);
		GridPane.setConstraints(abgeholt, 0, 12);
		Label abgeholtLabel = new Label("Zeitangabe Abgeholt");
		GridPane.setConstraints(abgeholtLabel, 1, 12);
		
		CheckBox abgerechnet = new CheckBox("Abgerechnet");
		abgerechnet.setDisable(true);
		GridPane.setConstraints(abgerechnet, 0, 13);
		Label abgerechnetLabel = new Label("Zeitangabe Abgerechnet");
		GridPane.setConstraints(abgerechnetLabel, 1, 13);
		
		CheckBox warten = new CheckBox("Warten");
		warten.setDisable(true);
		GridPane.setConstraints(warten, 0, 14);
		Label wartenLabel = new Label("Zeitangabe Warten");
		GridPane.setConstraints(wartenLabel, 1, 14);
		
		CheckBox unterbrochen = new CheckBox("Unterbrochen");
		unterbrochen.setDisable(true);
		GridPane.setConstraints(unterbrochen, 0, 15);
		Label unterbrochenLabel = new Label("Zeitangabe Unterbrochen");
		GridPane.setConstraints(unterbrochenLabel, 1, 15);
		
		CheckBox defekt = new CheckBox("Defekt");
		defekt.setDisable(true);
		GridPane.setConstraints(defekt, 0, 16);
		Label defektLabel = new Label("Zeitangabe Defekt");
		GridPane.setConstraints(defektLabel, 1, 16);
		
		Button export = new Button("Auftrag Exportieren");
		GridPane.setConstraints(export, 2, 15);
		export.setDisable(true);
		
		
		
		
		
		
		//EVENTS
		
		
		create.setOnMouseClicked(e -> {
			GUIAuftragErstellen.display();
			try {
				auftragTable.setItems(getAuftraege());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			open();
			modify.setDisable(true);
			delete.setDisable(true);
			export.setDisable(true);
		});
		
		modify.setOnMouseClicked(e -> {
			Auftrag tempAuftragMod = auftragTable.getSelectionModel().getSelectedItem();
			
			if(Validation.StringInputValidation(auftragsTitel)&&Validation.DoubleInputValidation(prognoKosten)&&Validation.DoubleInputValidation(reelleKosten)) {
			try {
				Fertigungsverwaltung.getInstance().modifyAuftrag(tempAuftragMod.getAUFTRAG_ID(), "TITEL" , auftragsTitel.getText());
				Fertigungsverwaltung.getInstance().modifyAuftrag(tempAuftragMod.getAUFTRAG_ID(), "prognostizierte_kosten" , prognoKosten.getText());
				Fertigungsverwaltung.getInstance().modifyAuftrag(tempAuftragMod.getAUFTRAG_ID(), "reelle_kosten" , reelleKosten.getText());
				
				//TODO Timestamps nach Veraenderung des Status sofort aendern
				
				if(gefertigt.isSelected() != tempAuftragMod.isGefertigt()) {
					if(tempAuftragMod.isGefertigt() == true) {
						tempAuftragMod.setGefertigt(false);
					}else {
						tempAuftragMod.setGefertigt(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "gefertigt");
					
					gefertigtLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_gefertigt().toString());
				}
				
				if(kalkuliert.isSelected() != tempAuftragMod.isKalkuliert()) {
					if(tempAuftragMod.isKalkuliert() == true) {
						tempAuftragMod.setKalkuliert(false);
					}else {
						tempAuftragMod.setKalkuliert(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "kalkuliert");
					kalkuliertLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_kalkuliert().toString());
				}
				
				if(abgeholt.isSelected() != tempAuftragMod.isAbgeholt()) {
					if(tempAuftragMod.isAbgeholt() == true) {
						tempAuftragMod.setAbgeholt(false);
					}else {
						tempAuftragMod.setAbgeholt(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "abgeholt");
					abgeholtLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_abgeholt().toString());
				}
				
				if(abgerechnet.isSelected() != tempAuftragMod.isAbgerechnet()) {
					if(tempAuftragMod.isAbgerechnet() == true) {
						tempAuftragMod.setAbgerechnet(false);
					}else {
						tempAuftragMod.setAbgerechnet(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "abgerechnet");
					abgerechnetLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_abgerechnet().toString());
				}
				
				if(warten.isSelected() != tempAuftragMod.isWarten()) {
					if(tempAuftragMod.isWarten() == true) {
						tempAuftragMod.setWarten(false);
					}else {
						tempAuftragMod.setWarten(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "warten");
					wartenLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_warten().toString());
				}
				
				if(unterbrochen.isSelected() != tempAuftragMod.isUnterbrochen()) {
					if(tempAuftragMod.isUnterbrochen() == true) {
						tempAuftragMod.setUnterbrochen(false);
					}else {
						tempAuftragMod.setUnterbrochen(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "unterbrochen");
					unterbrochenLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_unterbrochen().toString());
				}
				
				if(defekt.isSelected() != tempAuftragMod.isDefekt()) {
					if(tempAuftragMod.isDefekt() == true) {
						tempAuftragMod.setDefekt(false);
					}else {
						tempAuftragMod.setDefekt(true);
					}
					Fertigungsverwaltung.getInstance().changeStatus(tempAuftragMod.getAUFTRAG_ID(), "defekt");
					defektLabel.setText(Fertigungsverwaltung.getInstance().getAuftragByID(tempAuftragMod.getAUFTRAG_ID()).getDate_defekt().toString());
				}

				
			
				
				auftragTable.setItems(getAuftraege());
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				AlertBox.display("Erfolg!", "Auftrag bearbeitet!");
				auftragTable.getSelectionModel().select(tempAuftragMod);
			}
			}
			
		});
		
		delete.setOnMouseClicked(e ->{
			Auftrag tempAuftragDel = auftragTable.getSelectionModel().getSelectedItem();
			try {
				Fertigungsverwaltung.getInstance().deleteAuftrag(tempAuftragDel.getAUFTRAG_ID());
				AlertBox.display("Erfolg!", "Auftrag geloescht!");
				open();
				
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			
		});
				
		export.setOnMouseClicked(e -> {
			Auftrag tempAuftrag = auftragTable.getSelectionModel().getSelectedItem();
			GUICreateRechnung.display(tempAuftrag);
		});
		
		
		
		
		auftragTable.setOnMouseClicked(e -> {
			if(!(auftragTable.getSelectionModel().isEmpty())) {
				delete.setDisable(false);
				modify.setDisable(false);
				export.setDisable(false);
				Auftrag tempAuftragTable = auftragTable.getSelectionModel().getSelectedItem();
				
				//Tabelle
				vertretungTable.setItems(getVertretung(tempAuftragTable));
				
				auftragsnrInt.setText(Integer.toString(tempAuftragTable.getAUFTRAG_ID()));
				
				
				//Textboxen
				
				auftragsTitel.setStyle(null);
				auftragsTitel.setText(tempAuftragTable.getTitel());
				auftragsTitel.setDisable(false);
				fertigungsArt.setStyle(null);
				fertigungsArt.setText(tempAuftragTable.getART());
				erstellerLabel.setStyle(null);
				erstellerLabel.setText(tempAuftragTable.getAuftraggeber().getVorname() + " "+tempAuftragTable.getAuftraggeber().getNachname());
				verwalterLabel.setStyle(null);
				verwalterLabel.setText(tempAuftragTable.getVerwalter().getVorname()+ " " + tempAuftragTable.getVerwalter().getNachname());
				prognoKosten.setStyle(null);
				prognoKosten.setText(Double.toString(tempAuftragTable.getProgno_cost()));
				prognoKosten.setDisable(false);
				reelleKosten.setStyle(null);
				reelleKosten.setText(Double.toString(tempAuftragTable.getReelle_kosten()));
				reelleKosten.setDisable(false);
				
				
				//Logik zum Aktivieren / Deaktivieren der Checkboxen
				if(tempAuftragTable.isAngenommen()==true) {
					angenommen.setSelected(true);
				}else {
					angenommen.setSelected(false);
				}
				angenommenLabel.setText(tempAuftragTable.getDate_angenommen().toString());
				
				if(tempAuftragTable.isGefertigt()==true) {
					gefertigt.setSelected(true);
					
				}else {
					gefertigt.setSelected(false);
				}
				gefertigt.setDisable(false);
				gefertigtLabel.setText(tempAuftragTable.getDate_gefertigt().toString());
				
				if(tempAuftragTable.isKalkuliert()==true) {
					kalkuliert.setSelected(true);
				}else {
					kalkuliert.setSelected(false);
				}
				kalkuliert.setDisable(false);
				kalkuliertLabel.setText(tempAuftragTable.getDate_kalkuliert().toString());
				
				if(tempAuftragTable.isAbgeholt()==true) {
					abgeholt.setSelected(true);
				}else {
					abgeholt.setSelected(false);
				}
				abgeholt.setDisable(false);
				abgeholtLabel.setText(tempAuftragTable.getDate_abgeholt().toString());
							
				if(tempAuftragTable.isAbgerechnet()==true) {
					abgerechnet.setSelected(true);
				}else {
					abgerechnet.setSelected(false);
				}
				abgerechnet.setDisable(false);
				abgerechnetLabel.setText(tempAuftragTable.getDate_abgerechnet().toString());
				
				if(tempAuftragTable.isWarten()==true) {
					warten.setSelected(true);
				}else {
					warten.setSelected(false);
				}
				warten.setDisable(false);
				wartenLabel.setText(tempAuftragTable.getDate_warten().toString());
				
				if(tempAuftragTable.isUnterbrochen()==true) {
					unterbrochen.setSelected(true);
				}else {
					unterbrochen.setSelected(false);
				}
				unterbrochen.setDisable(false);
				unterbrochenLabel.setText(tempAuftragTable.getDate_unterbrochen().toString());
				
				if(tempAuftragTable.isDefekt()==true) {
					defekt.setSelected(true);
				}else {
					defekt.setSelected(false);
				}
				defekt.setDisable(false);
				defektLabel.setText(tempAuftragTable.getDate_defekt().toString());
			}
			});
		
		
		bp.setCenter(grid);
		grid.getChildren().addAll(auftragsnrInt, auftragsnrName, l1,erstellerLabel, l2, verwalterLabel,l3, auftragsTitel, l4, fertigungsArt, l5, prognoKosten , l6, reelleKosten, export, angenommen,gefertigt, kalkuliert, abgeholt, abgerechnet, warten, unterbrochen, defekt
				,angenommenLabel,gefertigtLabel,kalkuliertLabel,abgeholtLabel,abgerechnetLabel,wartenLabel,unterbrochenLabel,defektLabel);
		
		
		tab.setContent(bp);
	}
	
	public static ObservableList<Auftrag> getAuftraege() throws SQLException{
		ObservableList<Auftrag> resultAuftrag = FXCollections.observableArrayList();
		try {
			for (Auftrag a :  Fertigungsverwaltung.getInstance().getAllAuftrag()) {
				resultAuftrag.add(a);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultAuftrag;
	}
	
	public ObservableList<Person> getVertretung (Auftrag tempAuftragTable){
		ObservableList<Person> resultVertretung = FXCollections.observableArrayList();
		Iterator<Person> vertretungIterator = tempAuftragTable.getVertreter().iterator();
		int count = 0;
		while(vertretungIterator.hasNext()){
			resultVertretung.add(tempAuftragTable.getVertreter().get(count));
			count++;
			vertretungIterator.next();
		}
		return resultVertretung;
	}
	
	
	
	
	
	public ObservableList<Auftrag> getDruckAuftraege() throws SQLException{
		ObservableList<Auftrag> resultAuftrag = FXCollections.observableArrayList();
		try {
			for (Auftrag a :  Fertigungsverwaltung.getInstance().getAllAuftrag()) {
				if(a.getART().equals("3D-Druck"))resultAuftrag.add(a);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultAuftrag;
	}
	
	
}
