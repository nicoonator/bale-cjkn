package GUI.Finanzverwaltung;

import java.sql.SQLException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Finanzverwaltung;
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

public class GUICreateKasse {
	 public static void display() {
	        Stage window = new Stage();
	        window.setTitle("Kasse Erstellen");
	        
	        GridPane grid = new GridPane();
			grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(8);
			grid.setHgap(10);
			
			Label btnameLabel = new Label("Name: ");
			GridPane.setConstraints(btnameLabel, 0, 0);
			
			TextField btnameInput = new TextField();
			GridPane.setConstraints(btnameInput, 1, 0);
			
			Label sollLabel = new Label("Soll: ");
			GridPane.setConstraints(sollLabel, 0, 1);
			
			TextField sollInput = new TextField();
			GridPane.setConstraints(sollInput, 1, 1);
			
			Label istLabel = new Label("Ist: ");
			GridPane.setConstraints(istLabel, 0, 2);
			
			TextField istInput = new TextField();
			GridPane.setConstraints(istInput, 1, 2);
			
			Label kostenstellennummerLabel = new Label("Kostenstellennummer: ");
			GridPane.setConstraints(kostenstellennummerLabel, 0, 3);
			kostenstellennummerLabel.setDisable(true);
			
			TextField kostenstellennummerInput = new TextField();
			GridPane.setConstraints(kostenstellennummerInput, 1, 3);
			kostenstellennummerInput.setDisable(true);
			
			Button btn = new Button("Erstellen");
			GridPane.setConstraints(btn, 1, 4);
			
			Button btnClose = new Button ("Schliessen");
			GridPane.setConstraints(btnClose, 1, 4,1,1,HPos.RIGHT,null);
			
			
			ComboBox<String> comboBox;
			comboBox = new ComboBox<>();
	        comboBox.getItems().addAll(
	                "Barkasse",
	                "Konto",
	                "Kostenstelle"
	        );
	        comboBox.setPromptText("Kassentyp");
	        GridPane.setConstraints(comboBox, 0, 4);
			
			grid.getChildren().addAll(btnameLabel,btnameInput,sollLabel,sollInput,istLabel,istInput,kostenstellennummerLabel,kostenstellennummerInput, comboBox, btn ,btnClose);
			
			
			//Kasse erstellen
			btn.setOnMouseClicked(e -> {
				try {
					if(comboBox.getSelectionModel().getSelectedIndex()==2) {
						//Wenn eine Kostenstelle erstellt wird
						if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)&&Validation.KostenstellenInputValidation(kostenstellennummerInput)&&Validation.ComboBoxValidationKasse(comboBox)) {
							Finanzverwaltung.getInstance().createKasse(btnameInput.getText(), Double.parseDouble(sollInput.getText()), Double.parseDouble(istInput.getText()), comboBox.getSelectionModel().getSelectedIndex(), Long.parseLong(kostenstellennummerInput.getText()));
							AlertBox.display("Kasse erzeugt", "Erfolg! Kasse erzeugt!");
							window.close();
						} //Wenn keine Kostenstelle erstellt wird
					} else if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)&&Validation.ComboBoxValidationKasse(comboBox)) {
						Finanzverwaltung.getInstance().createKasse(btnameInput.getText(), Double.parseDouble(sollInput.getText()), Double.parseDouble(istInput.getText()), comboBox.getSelectionModel().getSelectedIndex(), Long.parseLong("0"));
						AlertBox.display("Kasse erzeugt", "Erfolg! Kasse erzeugt!");
						window.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					AlertBox.display("Fehler", e1.getMessage());
				}
				});
			
			
			//Fenster schliessen
			btnClose.setOnMouseClicked(e -> {
				window.close();
			});
			
			comboBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) ->{
				if(newValue == "Kostenstelle"){
					kostenstellennummerLabel.setDisable(false);
					kostenstellennummerInput.setDisable(false);
				}
				else {
					kostenstellennummerLabel.setDisable(true);
					kostenstellennummerInput.setDisable(true);
					kostenstellennummerInput.clear();
				}
			});
			
			
			
	        Scene scene = new Scene(grid);
	        window.setScene(scene);
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.showAndWait();
	       
	        
	        
	        
	    }	
	}