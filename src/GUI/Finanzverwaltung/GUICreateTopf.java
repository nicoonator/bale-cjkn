package GUI.Finanzverwaltung;

import java.sql.SQLException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Finanzverwaltung;
import Logic.Kasse;
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

public class GUICreateTopf {
	
	 public static void display() {
	        Stage window = new Stage();
	        window.setTitle("Topf Erstellen");
	        
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
			
			ComboBox<Kasse> comboBoxKassen = new ComboBox<>();
			comboBoxKassen.setPromptText("Kasse auswaehlen");
			comboBoxKassen.setItems(GUIKassen.getKassen());
			GridPane.setConstraints(comboBoxKassen, 0, 3);
			
			Button btn = new Button("Erstellen");
			GridPane.setConstraints(btn, 1, 3);
			
			Button btnClose = new Button ("Schliessen");
			GridPane.setConstraints(btnClose, 1, 3,1,1,HPos.RIGHT,null);
			
			grid.getChildren().addAll(btnameLabel,btnameInput,sollLabel,sollInput,istLabel,istInput,comboBoxKassen, btn ,btnClose);
			
			btn.setOnMouseClicked(e -> {
				try {
					if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)&&Validation.ComboBoxValidationKassenauswahl(comboBoxKassen)) {
						Finanzverwaltung.getInstance().createTopf(btnameInput.getText(), Double.parseDouble(sollInput.getText()), Double.parseDouble(istInput.getText()), comboBoxKassen.getSelectionModel().getSelectedItem().getKASSE_ID());
						AlertBox.display("Topf erzeugt", "Erfolg! Topf erzeugt!");
						window.close();
					} 
				} catch (SQLException e1) {
					AlertBox.display("Fehler", e1.getMessage());
				}
				});
			
			btnClose.setOnMouseClicked(e -> {
				window.close();
			});
			
			
			
	        Scene scene = new Scene(grid);
	        window.setScene(scene);
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.showAndWait();
	    }	
	}