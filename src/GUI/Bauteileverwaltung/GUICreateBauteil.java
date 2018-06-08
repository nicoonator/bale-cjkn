/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Bauteileverwaltung;
import Logic.Kategorie;
import Logic.Person;
import Logic.Personenverwaltung;
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

/**
 * @author Nico Rychlik
 *
 */
public class GUICreateBauteil {
	
	public static void display() {
        Stage window = new Stage();
        window.setTitle("Bauteil Erstellen");
        window.setMinWidth(300);
        window.setMinHeight(100);
        
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Button btn = new Button("Erstellen");
		GridPane.setConstraints(btn, 1, 8);
		
		Button btnClose = new Button ("Schliessen");
		GridPane.setConstraints(btnClose, 1, 8,1,1,HPos.RIGHT,null);
		
		Label btnameLabel = new Label("Name: ");
		GridPane.setConstraints(btnameLabel, 0, 0);
		
		TextField btnameInput = new TextField();
		GridPane.setConstraints(btnameInput, 1, 0);
		
		Label linkLabel = new Label("Link: ");
		GridPane.setConstraints(linkLabel, 0, 1);
		
		TextField linkInput = new TextField();
		GridPane.setConstraints(linkInput, 1, 1);
		
		Label preisLabel = new Label("Preis: ");
		GridPane.setConstraints(preisLabel, 0, 2);
		
		TextField preisInput = new TextField();
		GridPane.setConstraints(preisInput, 1, 2);
		
		Label gelagertLabel = new Label("gelagert: ");
		GridPane.setConstraints(gelagertLabel, 0, 3);
		
		TextField gelagertInput = new TextField();
		GridPane.setConstraints(gelagertInput, 1, 3);
		
		Label geplantLabel = new Label("geplant: ");
		GridPane.setConstraints(geplantLabel, 0, 4);
		
		TextField geplantInput = new TextField();
		GridPane.setConstraints(geplantInput, 1, 4);
		
		Label bestelltLabel = new Label("bestellt: ");
		GridPane.setConstraints(bestelltLabel, 0, 5);
		
		TextField bestelltInput = new TextField();
		GridPane.setConstraints(bestelltInput, 1, 5);
		
		Label ortLabel = new Label("Ort: ");
		GridPane.setConstraints(ortLabel, 0, 6);
		
		TextField ortInput = new TextField();
		GridPane.setConstraints(ortInput, 1, 6);
		
		Label kategorieLabel = new Label("Kategorie");
		GridPane.setConstraints(kategorieLabel, 0, 7);
		
		ComboBox<Kategorie> comboBoxKategorie = new ComboBox<>();
		comboBoxKategorie.setPromptText("Kategorie");
		
		try {
			comboBoxKategorie.getItems().addAll(Bauteileverwaltung.getInstance().getAllKategorie());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		GridPane.setConstraints(comboBoxKategorie, 1, 7);
		
		grid.getChildren().addAll(btnameLabel,btnameInput,linkLabel,linkInput,preisLabel,preisInput ,gelagertLabel,gelagertInput,geplantLabel,geplantInput,bestelltLabel,bestelltInput, ortLabel,ortInput,kategorieLabel,comboBoxKategorie, btn, btnClose);
		
		btn.setOnMouseClicked(e -> {
			
			try {
				if(Validation.StringInputValidation(btnameInput) && Validation.StringInputValidation(linkInput) &&Validation.IntegerInputValidation(gelagertInput) &&Validation.IntegerInputValidation(geplantInput) &&Validation.IntegerInputValidation(bestelltInput) &&Validation.StringInputValidation(ortInput)) {
					Bauteileverwaltung.getInstance().createBauteil(btnameInput.getText(), linkInput.getText(), Double.parseDouble(preisInput.getText()), Integer.parseInt(gelagertInput.getText()), Integer.parseInt(geplantInput.getText()), Integer.parseInt(bestelltInput.getText()), ortInput.getText(), comboBoxKategorie.getSelectionModel().getSelectedItem().getID() );
					AlertBox.display("Erfolg!", "Bauteil erzeugt!");
					window.close();
				}
			} catch (SQLException | NumberFormatException | DatabaseException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
			}});
		
		btnClose.setOnMouseClicked(e -> {
			window.close();
		});
		
		
		
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
       
        
        
        
    }	
}