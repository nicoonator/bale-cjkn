/**
 * 
 */
package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Fertigungsverwaltung;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
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
		
		TableColumn<Auftrag, String> art = new TableColumn<>("Auftragsart");
		art.setCellValueFactory(new PropertyValueFactory<>("ART"));
		
		TableColumn<Auftrag, String> auftrag_id = new TableColumn<>("Auftragsid");
		auftrag_id.setCellValueFactory(new PropertyValueFactory<>("AUFTRAG_ID"));
		
		TableColumn<Auftrag, String> auftraggeber = new TableColumn<>("Auftragsgeber");
		auftraggeber.setCellValueFactory(new PropertyValueFactory<>("auftraggeber"));
		
		TableColumn<Auftrag, String> verwalter = new TableColumn<>("Verwalter");
		verwalter.setCellValueFactory(new PropertyValueFactory<>("verwalter"));
		
		TableColumn<Auftrag, Double> prognostKosten = new TableColumn<>("Prognost. Kosten");
		prognostKosten.setCellValueFactory(new PropertyValueFactory<>("progno_cost"));
		
		TableColumn<Auftrag, Double> reellKosten = new TableColumn<>("Reelle Kosten");
		reellKosten.setCellValueFactory(new PropertyValueFactory<>("reelle_kosten"));
		
		auftragTable = new TableView<>();
		try {
			auftragTable.setItems(getAuftraege());
		} catch (SQLException e2) {
			e2.printStackTrace();
			AlertBox.display("Fehler", e2.getMessage() );
		}
		auftragTable.getColumns().addAll(titel, art, auftrag_id, auftraggeber, verwalter, prognostKosten, reellKosten);
		
		auftragTable.setPrefWidth(650);
		bp.setLeft(auftragTable);
	
		
		
		
		//Start of CENTER
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(10);
		grid.setHgap(40);
		
		
		//Dropdown Personen		
		Label l1 = new Label("Auftragsersteller");
		GridPane.setConstraints(l1, 0, 0);
		int counter1 = 0;
		ComboBox<String> comboBoxPersonen = new ComboBox<>();
		comboBoxPersonen.setPromptText("Ersteller");
		try {
			List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
			try {
				while(personen.listIterator().hasNext()) {
					comboBoxPersonen.getItems().addAll(personen.listIterator(counter1).next().getVorname() +" "+ personen.listIterator(counter1).next().getNachname()+ personen.listIterator(counter1).next().getPERSON_ID() );
					counter1++;
				}
			} catch (Exception e) {
			}
			
		} catch (SQLException e) {			
		}
		comboBoxPersonen.setDisable(true);
		GridPane.setConstraints(comboBoxPersonen, 0, 1);
		
		//Dropdown Admins
		Label l2 = new Label("Admins");
		GridPane.setConstraints(l2, 2, 0);
		int counter2 = 0;
		ComboBox<String> comboBoxAdmins = new ComboBox<>();
		comboBoxAdmins.setPromptText("Verwalter");
		try {
			List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
			try {
				
					
					while(admins.listIterator().hasNext()) {
					comboBoxAdmins.getItems().addAll(admins.listIterator(counter2).next().getVorname()+ " " + admins.listIterator(counter2).next().getNachname() + admins.listIterator(counter2).next().getPERSON_ID());
					counter2++;
					}
					
			}catch(Exception e) {
			}
		} catch (SQLException e) {
		} catch (DatabaseException e) {
		}
		comboBoxAdmins.setDisable(true);
		GridPane.setConstraints(comboBoxAdmins, 2, 1);
		
		//Auftragstitel und Feld
		Label l3 = new Label("Auftragstitel");
		GridPane.setConstraints(l3 , 0 , 3);
		TextField auftragsTitel = new TextField();
		auftragsTitel.setDisable(true);
		GridPane.setConstraints(auftragsTitel, 0, 4);
		
		//Art der Fertigung
		Label l4 = new Label("Fertigungsart");
		GridPane.setConstraints(l4 , 2 , 3);
		ComboBox<String> comboBoxArt = new ComboBox<>();
		comboBoxArt.setPromptText("Fertigungsart");
		comboBoxArt.getItems().addAll("Leiterplatte", "3D-Druck" , "Sonstiges");
		comboBoxArt.setDisable(true);
		GridPane.setConstraints(comboBoxArt, 2, 4);
		
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
		
		Button btn = new Button("Auftrag Exportieren");
		GridPane.setConstraints(btn, 2, 15);
		
		
		
		
		
		
		//EVENTS
		
		
		create.setOnMouseClicked(e -> {
			GUIAuftragErstellen.display();
			try {
				auftragTable.setItems(getAuftraege());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			
			modify.setDisable(true);
			delete.setDisable(true);
		});
		
		modify.setOnMouseClicked(e -> {
			
			
			
			Auftrag tempAuftragMod = auftragTable.getSelectionModel().getSelectedItem();
			
			if(Validation.StringInputValidation(auftragsTitel) && Validation.DoubleInputValidation(prognoKosten) && Validation.DoubleInputValidation(reelleKosten)) {
				try {
					
					Fertigungsverwaltung.getInstance().modifyAuftrag(tempAuftragMod.getAUFTRAG_ID(),"PERSON_ID" , comboBoxPersonen.getValue());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		delete.setOnMouseClicked(e ->{
			Auftrag tempAuftragDel = auftragTable.getSelectionModel().getSelectedItem();
			try {
				Fertigungsverwaltung.getInstance().deleteAuftrag(tempAuftragDel.getAUFTRAG_ID());
				auftragTable.setItems(getAuftraege());
				comboBoxPersonen.setValue("Ersteller");
				comboBoxAdmins.setValue("Verwalter");
				comboBoxArt.setValue("Fertigungsart");
				auftragsTitel.setDisable(true);
				auftragsTitel.clear();
				prognoKosten.clear();
				prognoKosten.setDisable(true);
				reelleKosten.clear();
				reelleKosten.setDisable(true);
				angenommen.setSelected(false);
				angenommenLabel.setText("Zeitangabe Angenommen");
				gefertigt.setSelected(false);
				gefertigt.setDisable(true);
				gefertigtLabel.setText("Zeitangabe Gefertigt");
				kalkuliert.setSelected(false);
				kalkuliert.setDisable(true);
				kalkuliertLabel.setText("Zeitangabe Kalkuliert");
				abgeholt.setSelected(false);
				abgeholt.setDisable(true);
				abgeholtLabel.setText("Zeitangabe Abgeholt");
				abgerechnet.setSelected(false);
				abgerechnet.setDisable(true);
				abgerechnetLabel.setText("Zeitangabe Abgerechnet");
				warten.setSelected(false);
				warten.setDisable(true);
				wartenLabel.setText("Zeitangabe Warten");
				unterbrochen.setSelected(false);
				unterbrochen.setDisable(true);
				unterbrochenLabel.setText("Zeitangabe Unterbrochen");
				defekt.setSelected(false);
				defekt.setDisable(true);
				defektLabel.setText("Zeitangabe Defekt");
				
				
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			
		});
		
		
		
		
		
		
		auftragTable.setOnMouseClicked(e -> {
			delete.setDisable(false);
			modify.setDisable(false);
			
			Auftrag tempAuftragTable = auftragTable.getSelectionModel().getSelectedItem();
			
			auftragsTitel.setStyle(null);
			auftragsTitel.setText(tempAuftragTable.getTitel());
			auftragsTitel.setDisable(false);
			comboBoxArt.setStyle(null);
			comboBoxArt.setValue(tempAuftragTable.getART());
			comboBoxPersonen.setStyle(null);
			comboBoxPersonen.setValue(tempAuftragTable.getAuftraggeber().getVorname() + " "+tempAuftragTable.getAuftraggeber().getNachname() + tempAuftragTable.getAuftraggeber().getPERSON_ID());
			comboBoxAdmins.setStyle(null);
			comboBoxAdmins.setValue(tempAuftragTable.getVerwalter().getVorname()+ " " + tempAuftragTable.getVerwalter().getNachname()+tempAuftragTable.getVerwalter().getPERSON_ID());
			prognoKosten.setStyle(null);
			prognoKosten.setText(Double.toString(tempAuftragTable.getProgno_cost()));
			prognoKosten.setDisable(false);
			reelleKosten.setStyle(null);
			reelleKosten.setText(Double.toString(tempAuftragTable.getReelle_kosten()));
			reelleKosten.setDisable(false);
			
			
			
			
			
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
			
			});
		
		
		
		
		
		
		
		
		
		btn.setOnMouseClicked(e -> {
		});
		
		bp.setCenter(grid);
		grid.getChildren().addAll(l1,comboBoxPersonen, l2, comboBoxAdmins,l3, auftragsTitel, l4, comboBoxArt, l5, prognoKosten , l6, reelleKosten, btn, angenommen,gefertigt, kalkuliert, abgeholt, abgerechnet, warten, unterbrochen, defekt
				,angenommenLabel,gefertigtLabel,kalkuliertLabel,abgeholtLabel,abgerechnetLabel,wartenLabel,unterbrochenLabel,defektLabel);
		
		
		tab.setContent(bp);
	}
	
	public ObservableList<Auftrag> getAuftraege() throws SQLException{
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
