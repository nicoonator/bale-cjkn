package GUI.Finanzverwaltung;

import java.sql.SQLException;
import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Fertigungsverwaltung.GUIFertigungsverwaltung;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Finanzverwaltung;
import Logic.Kasse;
import Logic.Topf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUICreateRechnung {
	
	
	@SuppressWarnings("unused")
	public static void display(Auftrag a) {
		Auftrag auftrag=a;

        Stage window = new Stage();
        window.setTitle("Rechnung Erstellen");
        
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label reNameLabel = new Label("Rechnungsname: ");
		GridPane.setConstraints(reNameLabel, 0, 0);
		
		TextField reNameInput = new TextField();
		GridPane.setConstraints(reNameInput, 1, 0);
		
		ComboBox<Auftrag> comboBoxAuftrag = new ComboBox<>();
		comboBoxAuftrag.setPromptText("Auftrag auswaehlen");
		try {
			comboBoxAuftrag.setItems(GUIFertigungsverwaltung.getAuftraege());
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		comboBoxAuftrag.getSelectionModel().select(GUICreateRechnung.indexOf(auftrag.getAUFTRAG_ID()));
		GridPane.setConstraints(comboBoxAuftrag, 1, 1);
		
		Label betragLabel = new Label("Betrag: ");
		GridPane.setConstraints(betragLabel, 0, 2);
		
		TextField betragInput = new TextField();
		GridPane.setConstraints(betragInput, 1, 2);
		betragInput.setText(String.valueOf(auftrag.getReelle_kosten()));
		betragInput.setDisable(true);
		
		ComboBox<String> comboBoxBezahlart = new ComboBox<>();
		comboBoxBezahlart.setPromptText("Bezahlart auswaehlen");
		comboBoxBezahlart.getItems().addAll(
                "Barkasse",
                "Konto",
                "Kostenstelle"
        );
		GridPane.setConstraints(comboBoxBezahlart, 1, 3);
		
		ComboBox<Topf> comboBoxToepfe = new ComboBox<>();
		comboBoxToepfe.setPromptText("Topf auswaehlen");
		comboBoxToepfe.setItems(GUIToepfe.getToepfe());
		GridPane.setConstraints(comboBoxToepfe, 0, 4);
		comboBoxToepfe.setDisable(true);
		
		Button btn = new Button("Erstellen");
		GridPane.setConstraints(btn, 1, 4);
		
		
		
		Button btnClose = new Button ("Schliessen");
		GridPane.setConstraints(btnClose, 1, 4,1,1,HPos.RIGHT,null);
		
		grid.getChildren().addAll(reNameInput, reNameLabel, comboBoxAuftrag, 
				betragLabel, betragInput, comboBoxBezahlart, comboBoxToepfe, btn ,btnClose);
		
		
		//Rechnung erstellen
		btn.setOnMouseClicked(e -> {
			
			if(Validation.StringInputValidation(reNameInput)&&Validation.ComboBoxValidationString(comboBoxBezahlart)&&Validation.ComboBoxValidationAuftrag(comboBoxAuftrag)&&Validation.ComboBoxValidationTopf(comboBoxToepfe)) {
				try {
					Finanzverwaltung.getInstance().createRechnung(reNameInput.getText(), comboBoxBezahlart.getSelectionModel().getSelectedItem(), Double.parseDouble(betragInput.getText()), comboBoxAuftrag.getSelectionModel().getSelectedItem().getAUFTRAG_ID(), comboBoxAuftrag.getSelectionModel().getSelectedItem().getAuftraggeber().getPERSON_ID(), comboBoxAuftrag.getSelectionModel().getSelectedItem().getVerwalter().getPERSON_ID(), comboBoxToepfe.getSelectionModel().getSelectedItem().getTOPF_ID());
					AlertBox.display("Erfolg!", "Rechnung erzeugt!");
					window.close();
				} catch (NumberFormatException | SQLException | DatabaseException e1) {
					AlertBox.display("Fehler", e1.getMessage());
				}
			}});
		
		
		//Fenster schliessen
		btnClose.setOnMouseClicked(e -> {
			window.close();
		});
		
		comboBoxAuftrag.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
			if(oldValue != newValue){
				betragInput.setText(String.valueOf(newValue.getReelle_kosten()));
			}
		});
		
		comboBoxBezahlart.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
			if(oldValue != newValue){
				comboBoxToepfe.getItems().clear();
				comboBoxToepfe.getItems().addAll(getTopfByBezahlart(newValue));
				comboBoxToepfe.setDisable(false);
			}
		});
		
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        
    }	

    public static void display() {
    	@SuppressWarnings("unused")
		Auftrag auftrag;
    	
        Stage window = new Stage();
        window.setTitle("Rechnung Erstellen");
        
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label reNameLabel = new Label("Rechnungsname: ");
		GridPane.setConstraints(reNameLabel, 0, 0);
		
		TextField reNameInput = new TextField();
		GridPane.setConstraints(reNameInput, 1, 0);
		
		ComboBox<Auftrag> comboBoxAuftrag = new ComboBox<>();
		comboBoxAuftrag.setPromptText("Auftrag auswaehlen");
		try {
			comboBoxAuftrag.setItems(GUIFertigungsverwaltung.getAuftraege());
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		GridPane.setConstraints(comboBoxAuftrag, 1, 1);
		
		Label betragLabel = new Label("Betrag: ");
		GridPane.setConstraints(betragLabel, 0, 2);
		
		TextField betragInput = new TextField();
		GridPane.setConstraints(betragInput, 1, 2);
		betragInput.setDisable(true);
		
		ComboBox<String> comboBoxBezahlart = new ComboBox<>();
		comboBoxBezahlart.setPromptText("Bezahlart auswaehlen");
		comboBoxBezahlart.getItems().addAll(
                "Barkasse",
                "Konto",
                "Kostenstelle"
        );
		GridPane.setConstraints(comboBoxBezahlart, 1, 3);
		
		ComboBox<Topf> comboBoxToepfe = new ComboBox<>();
		comboBoxToepfe.setPromptText("Topf auswaehlen");
		comboBoxToepfe.setItems(GUIToepfe.getToepfe());
		GridPane.setConstraints(comboBoxToepfe, 0, 4);
		comboBoxToepfe.setDisable(true);
		
		Button btn = new Button("Erstellen");
		GridPane.setConstraints(btn, 1, 4);
		
		
		
		Button btnClose = new Button ("Schliessen");
		GridPane.setConstraints(btnClose, 1, 4,1,1,HPos.RIGHT,null);
		
		grid.getChildren().addAll(reNameInput, reNameLabel, comboBoxAuftrag, 
				betragLabel, betragInput, comboBoxBezahlart, comboBoxToepfe, btn ,btnClose);
		
		btn.setOnMouseClicked(e -> {
			
			if(Validation.StringInputValidation(reNameInput)&&Validation.ComboBoxValidationString(comboBoxBezahlart)&&Validation.ComboBoxValidationAuftrag(comboBoxAuftrag)&&Validation.ComboBoxValidationTopf(comboBoxToepfe)) {
				try {
					Finanzverwaltung.getInstance().createRechnung(reNameInput.getText(), comboBoxBezahlart.getSelectionModel().getSelectedItem(), Double.parseDouble(betragInput.getText()), comboBoxAuftrag.getSelectionModel().getSelectedItem().getAUFTRAG_ID(), comboBoxAuftrag.getSelectionModel().getSelectedItem().getAuftraggeber().getPERSON_ID(), comboBoxAuftrag.getSelectionModel().getSelectedItem().getVerwalter().getPERSON_ID(), comboBoxToepfe.getSelectionModel().getSelectedItem().getTOPF_ID());
					AlertBox.display("Erfolg!", "Rechnung erzeugt!");
					window.close();
				} catch (NumberFormatException | SQLException | DatabaseException e1) {
					AlertBox.display("Fehler", e1.getMessage());
				}
			}});
		
		btnClose.setOnMouseClicked(e -> {
			window.close();
		});
		
		comboBoxAuftrag.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
			if(oldValue != newValue){
				betragInput.setText(String.valueOf(newValue.getReelle_kosten()));
			}
		});
		
		comboBoxBezahlart.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
			if(oldValue != newValue){
				comboBoxToepfe.getItems().clear();
				comboBoxToepfe.getItems().addAll(getTopfByBezahlart(newValue));
				comboBoxToepfe.setDisable(false);
			}
		});
		
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        
    }	
    
    //Holt alle Toepfe entsprechend der Bezahlart
    static ObservableList<Topf> getTopfByBezahlart(String bezahlart){
    	ObservableList<Topf> result = FXCollections.observableArrayList();
    	
		try {
			for (Topf t :  Finanzverwaltung.getInstance().getAllTopf()) {
				if(t.getKasse().getTyp()==bezahlart)result.add(t);
			}
		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
    
    
    //Holt den Index eines Auftrags
    private static int indexOf(int id) {
		int i=0;
		try {
			for(Auftrag a: GUIFertigungsverwaltung.getAuftraege()){
				if(a.getAUFTRAG_ID()==id) return i;
				i++;
			}
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		return -1;
	}
}