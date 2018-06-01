/**
 * 
 */
package GUI.Personenverwaltung;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.SQLException;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import Logic.Personenverwaltung;
import javafx.geometry.*;

public class GUICreatePerson {

    public static void display() {
        Stage window = new Stage();

        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label vornameLabel = new Label("Vorname: ");
		GridPane.setConstraints(vornameLabel, 0, 0);
		
		TextField vornameInput = new TextField();
		GridPane.setConstraints(vornameInput, 1, 0);
		
		Label nachnameLabel = new Label("Nachname: ");
		GridPane.setConstraints(nachnameLabel, 0, 1);
		
		TextField nachnameInput = new TextField();
		GridPane.setConstraints(nachnameInput, 1, 1);
		
		Label strasseLabel = new Label("Strasse: ");
		GridPane.setConstraints(strasseLabel, 0, 2);
		
		TextField strasseInput = new TextField();
		GridPane.setConstraints(strasseInput, 1, 2);
		
		Label hausnummerLabel = new Label("Hausnummer: ");
		GridPane.setConstraints(hausnummerLabel, 0, 3);
		
		TextField hausnummerInput = new TextField();
		GridPane.setConstraints(hausnummerInput, 1, 3);
		
		Label PLZLabel = new Label("PLZ: ");
		GridPane.setConstraints(PLZLabel, 0, 4);
		
		TextField PLZInput = new TextField();
		GridPane.setConstraints(PLZInput, 1, 4);
		
		Label EMailLabel = new Label("E-Mail: ");
		GridPane.setConstraints(EMailLabel, 0, 5);
		
		TextField EMailInput = new TextField();
		GridPane.setConstraints(EMailInput, 1, 5);
		
		Label nutzernameLabel = new Label("Nutzername: ");
		GridPane.setConstraints(nutzernameLabel, 0, 6);
		
		TextField nutzernameInput = new TextField();
		GridPane.setConstraints(nutzernameInput, 1, 6);
		
		Label passwortLabel = new Label("Passwort: ");
		GridPane.setConstraints(passwortLabel, 0, 7);
		
		PasswordField passwortInput = new PasswordField();
		GridPane.setConstraints(passwortInput, 1, 7);
		
		Button btn = new Button("Erstellen");
		GridPane.setConstraints(btn, 1, 8);
		
		
		ComboBox<String> comboBox;
		comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Admin",
                "Nutzer",
                "Lehrstuhlperson"
        );
        comboBox.setPromptText("Rolle der Person");
        GridPane.setConstraints(comboBox, 0, 8);
		
		grid.getChildren().addAll(vornameLabel, vornameInput, nachnameLabel, nachnameInput, strasseLabel, strasseInput, hausnummerLabel, hausnummerInput, 
				PLZLabel, PLZInput, EMailLabel, EMailInput, nutzernameLabel, nutzernameInput, passwortLabel, passwortInput, btn, comboBox);
		
		btn.setOnMouseClicked(e -> {
			try{
				//TODO InputValidation
				Personenverwaltung.getInstance().createPerson(vornameInput.getText(), nachnameInput.getText(), strasseInput.getText(), Integer.parseInt(hausnummerInput.getText()), Integer.parseInt(PLZInput.getText()), EMailInput.getText(), nutzernameInput.getText(), passwortInput.getText(), comboBox.getSelectionModel().getSelectedIndex());
				AlertBox.display("Erfolg!", "Person erzeugt!");
				window.close();
			}
			
			catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
		});
		
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }

}