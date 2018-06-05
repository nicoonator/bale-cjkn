/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Bauteileverwaltung;
import Logic.Personenverwaltung;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Nico Rychlik
 *
 */
public class GUICreateKategorie {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Kategorie Erstellen");
        window.setMinWidth(300);
        window.setMinHeight(100);
        
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		TextField tfKatName = new TextField();
		GridPane.setConstraints(tfKatName, 1, 0);
		
		Label katName = new Label("Name: ");
		GridPane.setConstraints(katName, 0, 0);
		
		Button btn = new Button("Erstellen");
		GridPane.setConstraints(btn, 1, 1);
		
		Button btnClose = new Button ("Schließen");
		GridPane.setConstraints(btnClose, 1, 1,1,1,HPos.RIGHT,null);
		
		
		grid.getChildren().addAll(btn, btnClose, katName, tfKatName);
		
		btn.setOnMouseClicked(e -> {
			
			if(Validation.StringInputValidation(tfKatName)) {
				try{
					Bauteileverwaltung.getInstance().addKategorie(tfKatName.getText());
					AlertBox.display("Erfolg!", "Kategorie erzeugt!");
					window.close();
				}
			
			catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
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