package GUI.Finanzverwaltung;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Finanzverwaltung;
import Logic.Kasse;
import Logic.Person;
import Logic.Rechnung;
import Logic.Topf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GUIRechnungen {
	Tab tab;
	TableView<Rechnung> rechnungTable;
	List<Rechnung> rechnungen = this.getRechnungen();
	
	public GUIRechnungen(Tab tab) {
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
		Button export = new Button("Als PDF exportieren");
		
		modify.setDisable(true);
		delete.setDisable(true);
		export.setDisable(true);
		top.getChildren().addAll(create, modify, delete, export);
		bp.setTop(top);
		
		
		
		//Links
		TableColumn<Rechnung, String> name = new TableColumn<>("Rechnungsname");
		name.setCellValueFactory(new PropertyValueFactory<>("rechnungsname"));

		TableColumn<Rechnung, Date> datum = new TableColumn<>("Datum");
		datum.setCellValueFactory(new PropertyValueFactory<>("RECHNUNGSDATUM"));

		TableColumn<Rechnung, Person> auftraggeber = new TableColumn<>("Auftraggeber");
		auftraggeber.setCellValueFactory(new PropertyValueFactory<>("auftraggeber"));

		TableColumn<Rechnung, Person> verwalter = new TableColumn<>("Ansprechpartner");
		verwalter.setCellValueFactory(new PropertyValueFactory<>("verwalter"));

		TableColumn<Rechnung, Double> betrag = new TableColumn<>("Betrag");
		betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));

		TableColumn<Rechnung, String> art = new TableColumn<>("Bezahlart");
		art.setCellValueFactory(new PropertyValueFactory<>("bezahlart"));

		TableColumn<Rechnung, Topf> topf = new TableColumn<>("Topf");
		topf.setCellValueFactory(new PropertyValueFactory<>("topf"));

		
		rechnungTable = new TableView<>();
		rechnungTable.setItems(getRechnungen());
		rechnungTable.getColumns().addAll(name, datum, auftraggeber, betrag, art, topf);
		rechnungTable.setPrefWidth(751);
		bp.setLeft(rechnungTable);
	
		//Start of CENTER
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(10);
		grid.setHgap(40);
		
		Label reNameLabel = new Label("Rechnungsname: ");
		GridPane.setConstraints(reNameLabel, 0, 0);
		
		TextField reNameInput = new TextField();
		GridPane.setConstraints(reNameInput, 1, 0);
		reNameInput.setDisable(true);
		
		Label auftragLabel = new Label("Auftrag: ");
		GridPane.setConstraints(auftragLabel, 0, 1);
		
		Label auftrag2Label = new Label("- Auftrag -");
		GridPane.setConstraints(auftrag2Label, 1, 1);
		auftrag2Label.setDisable(true);
		
		Label auftraggeberLabel = new Label("Auftraggeber: ");
		GridPane.setConstraints(auftraggeberLabel, 0, 2);
		
		Label auftraggeber2Label = new Label("- Auftraggeber -");
		GridPane.setConstraints(auftraggeber2Label, 1, 2);
		auftraggeber2Label.setDisable(true);
		
		Label auftragVerwalterLabel = new Label("Verwalter: ");
		GridPane.setConstraints(auftragVerwalterLabel, 0, 3);
		
		Label auftragVerwalter2Label = new Label("- Verwalter -");
		GridPane.setConstraints(auftragVerwalter2Label, 1, 3);
		auftragVerwalter2Label.setDisable(true);
		
		Label betragLabel = new Label("Betrag: ");
		GridPane.setConstraints(betragLabel, 0, 4);
		
		TextField betragInput = new TextField();
		GridPane.setConstraints(betragInput, 1, 4);
		betragInput.setDisable(true);
		
		ComboBox<String> comboBoxBezahlart = new ComboBox<>();
		comboBoxBezahlart.setPromptText("Bezahlart auswaehlen");
		comboBoxBezahlart.getItems().addAll(
                "Barkasse",
                "Konto",
                "Kostenstelle"
        );
		GridPane.setConstraints(comboBoxBezahlart, 0, 5);
		comboBoxBezahlart.setDisable(true);
		
		ComboBox<Topf> comboBoxToepfe = new ComboBox<>();
		comboBoxToepfe.setPromptText("Topf auswaehlen");
		comboBoxToepfe.getItems().addAll((GUIToepfe.getToepfe()));
		GridPane.setConstraints(comboBoxToepfe, 0, 6);
		comboBoxToepfe.setDisable(true);
		
		//Checkboxen
		
		CheckBox bearbeitung = new CheckBox("Bearbeitung");
		bearbeitung.setDisable(true);
		GridPane.setConstraints(bearbeitung, 0, 9);
		Label bearbeitungLabel = new Label("Zeitangabe Bearbeitung");
		GridPane.setConstraints(bearbeitungLabel, 1, 9);
		
		CheckBox eingereicht = new CheckBox("Eingereicht");
		eingereicht.setDisable(true);
		GridPane.setConstraints(eingereicht, 0, 10);
		Label eingereichtLabel = new Label("Zeitangabe Eingereicht");
		GridPane.setConstraints(eingereichtLabel, 1, 10);
		
		CheckBox abgewickelt = new CheckBox("Abgewickelt");
		abgewickelt.setDisable(true);
		GridPane.setConstraints(abgewickelt, 0, 11);
		Label abgewickeltLabel = new Label("Zeitangabe Abgewickelt");
		GridPane.setConstraints(abgewickeltLabel, 1, 11);
		
		CheckBox ausstehend = new CheckBox("Ausstehend");
		ausstehend.setDisable(true);
		GridPane.setConstraints(ausstehend, 0, 12);
		Label ausstehendLabel = new Label("Zeitangabe Ausstehend");
		GridPane.setConstraints(ausstehendLabel, 1, 12);
		
		bp.setCenter(grid);
		grid.getChildren().addAll(bearbeitung,eingereicht, abgewickelt, ausstehend, bearbeitungLabel,eingereichtLabel,abgewickeltLabel,ausstehendLabel, 
				reNameInput, reNameLabel, auftrag2Label, auftragLabel, auftraggeberLabel, auftraggeber2Label, auftragVerwalterLabel, auftragVerwalter2Label, 
				betragLabel, betragInput, comboBoxBezahlart, comboBoxToepfe);
	
		//EVENTS
		
		
		create.setOnMouseClicked(e -> {
			GUICreateRechnung.display();
			rechnungTable.setItems(getRechnungen());
			open();
			modify.setDisable(true);
			delete.setDisable(true);
			export.setDisable(true);
		});
		
		modify.setOnMouseClicked(e -> {
			Rechnung tempRechnung = rechnungTable.getSelectionModel().getSelectedItem();
			
			if(Validation.StringInputValidation(reNameInput)&&Validation.DoubleInputValidation(betragInput)&&Validation.ComboBoxValidationTopf(comboBoxToepfe)&&Validation.ComboBoxValidationString(comboBoxBezahlart)) {
				try {
					Finanzverwaltung.getInstance().modifyRechnung(tempRechnung.getRECHNUNG_ID(), "rechnungsname", reNameInput.getText());
					Finanzverwaltung.getInstance().modifyRechnung(tempRechnung.getRECHNUNG_ID(), "TOPF_ID", Integer.toString(comboBoxToepfe.getSelectionModel().getSelectedItem().getTOPF_ID()));
					Finanzverwaltung.getInstance().modifyRechnung(tempRechnung.getRECHNUNG_ID(), "bezahlungsart", comboBoxBezahlart.getSelectionModel().getSelectedItem());
					
					if(abgewickelt.isSelected() != tempRechnung.isAbgewickelt()) {
						if(tempRechnung.isAbgewickelt() == true) {
							tempRechnung.setAbgewickelt(false);
						}else {
							tempRechnung.setAbgewickelt(true);
						}
						Finanzverwaltung.getInstance().changeStatus(tempRechnung.getRECHNUNG_ID(), "abgewickelt");
						abgewickeltLabel.setText(Finanzverwaltung.getInstance().getRechnungByID(tempRechnung.getRECHNUNG_ID()).getDate_abgewickelt().toString());
					}
					
					if(ausstehend.isSelected() != tempRechnung.isAusstehend()) {
						if(tempRechnung.isAusstehend() == true) {
							tempRechnung.setAusstehend(false);
						}else {
							tempRechnung.setAusstehend(true);
						}
						Finanzverwaltung.getInstance().changeStatus(tempRechnung.getRECHNUNG_ID(), "ausstehend");
						ausstehendLabel.setText(Finanzverwaltung.getInstance().getRechnungByID(tempRechnung.getRECHNUNG_ID()).getDate_ausstehend().toString());
					}
					
					if(bearbeitung.isSelected() != tempRechnung.isBearbeitung()) {
						if(tempRechnung.isBearbeitung() == true) {
							tempRechnung.setBearbeitung(false);
						}else {
							tempRechnung.setBearbeitung(true);
						}
						Finanzverwaltung.getInstance().changeStatus(tempRechnung.getRECHNUNG_ID(), "bearbeitung");
						bearbeitungLabel.setText(Finanzverwaltung.getInstance().getRechnungByID(tempRechnung.getRECHNUNG_ID()).getDate_bearbeitung().toString());
					}
					
					if(eingereicht.isSelected() != tempRechnung.isEingereicht()) {
						if(tempRechnung.isEingereicht() == true) {
							tempRechnung.setEingereicht(false);
						}else {
							tempRechnung.setEingereicht(true);
						}
						Finanzverwaltung.getInstance().changeStatus(tempRechnung.getRECHNUNG_ID(), "eingereicht");
						eingereichtLabel.setText(Finanzverwaltung.getInstance().getRechnungByID(tempRechnung.getRECHNUNG_ID()).getDate_eingereicht().toString());
					}
					AlertBox.display("Erfolg!", "Auftrag bearbeitet!");
					rechnungTable.setItems(getRechnungen());
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				rechnungTable.getSelectionModel().select(tempRechnung);
			}
			}
			
		});
		
		
		delete.setOnMouseClicked(e ->{
			Rechnung tempRechnungDel = rechnungTable.getSelectionModel().getSelectedItem();
			try {
				Finanzverwaltung.getInstance().deleteRechnung(tempRechnungDel.getRECHNUNG_ID());
				open();
				
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			
		});
				
		export.setOnMouseClicked(e -> {
			try {
				Finanzverwaltung.getInstance().exportRechnung(rechnungTable.getSelectionModel().getSelectedItem().getRECHNUNG_ID());
				AlertBox.display("Erfolg!", "Rechnungs-PDF erstellt!");
			} catch (SQLException | DatabaseException | IOException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			
		});
		
		
		
		
		rechnungTable.setOnMouseClicked(e -> {
			if(!(rechnungTable.getSelectionModel().isEmpty())) {
				delete.setDisable(false);
				modify.setDisable(false);
				export.setDisable(false);
				Rechnung tempRechnung = rechnungTable.getSelectionModel().getSelectedItem();
				
				//Textboxen
				
				reNameInput.setStyle(null);
				reNameInput.setText(tempRechnung.getRechnungsname());
				reNameInput.setDisable(false);
				betragInput.setStyle(null);
				betragInput.setText(Double.toString(tempRechnung.getBetrag()));
				betragInput.setDisable(false);
				
				comboBoxBezahlart.setDisable(false);
				comboBoxToepfe.setDisable(false);
				
				auftraggeber2Label.setText(tempRechnung.getAuftraggeber().toString());
				auftrag2Label.setText(tempRechnung.getAuftrag().toString());
				auftragVerwalter2Label.setText(tempRechnung.getVerwalter().toString());
				
				comboBoxBezahlart.getSelectionModel().select(tempRechnung.getBezahlart());
				comboBoxToepfe.getSelectionModel().select(indexOf(tempRechnung.getTopf_ID()));
				
				//Logik zum Aktivieren / Deaktivieren der Checkboxen
				if(tempRechnung.isAbgewickelt()==true) {
					abgewickelt.setSelected(true);
				}else {
					abgewickelt.setSelected(false);
				}
				abgewickelt.setDisable(false);
				abgewickeltLabel.setText(tempRechnung.getDate_abgewickelt().toString());
				
				if(tempRechnung.isAusstehend()==true) {
					ausstehend.setSelected(true);
					
				}else {
					ausstehend.setSelected(false);
				}
				ausstehend.setDisable(false);
				ausstehendLabel.setText(tempRechnung.getDate_ausstehend().toString());
				
				if(tempRechnung.isBearbeitung()==true) {
					bearbeitung.setSelected(true);
				}else {
					bearbeitung.setSelected(false);
				}
				bearbeitung.setDisable(false);
				bearbeitungLabel.setText(tempRechnung.getDate_bearbeitung().toString());
				
				if(tempRechnung.isEingereicht()==true) {
					eingereicht.setSelected(true);
				}else {
					eingereicht.setSelected(false);
				}
				eingereicht.setDisable(false);
				eingereichtLabel.setText(tempRechnung.getDate_eingereicht().toString());
			}
			});
		
		comboBoxBezahlart.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
			if(oldValue != null){
				comboBoxToepfe.getItems().clear();
				comboBoxToepfe.getItems().addAll(GUICreateRechnung.getTopfByBezahlart(newValue));
				comboBoxToepfe.setDisable(false);
			}
		});

		tab.setContent(bp);
	}

	/**
	 * @return
	 */
	private ObservableList<Rechnung> getRechnungen() {
		ObservableList<Rechnung> resultAuftrag = FXCollections.observableArrayList();
		try {
			for (Rechnung r :  Finanzverwaltung.getInstance().getAllRechnung()) {
				resultAuftrag.add(r);
			}
		} catch (DatabaseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultAuftrag;
	}
	private int indexOf(int id) {
		int i=0;
		for(Topf t: GUIToepfe.getToepfe()){
			if(t.getTOPF_ID()==id) return i;
			i++;
		}
		return -1;
	}
	
	
}
