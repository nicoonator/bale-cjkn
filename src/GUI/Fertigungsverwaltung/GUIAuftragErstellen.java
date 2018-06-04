package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Fertigungsverwaltung;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIAuftragErstellen {
	 public static void display() {
	        Stage window = new Stage();
	        window.setTitle("Auftrag Erstellen");
	        
	        GridPane grid = new GridPane();
			grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(8);
			grid.setHgap(10); 
			
			
			//Von Oben nach Unten
			
			//0,0 | 1,0
			Label l1 = new Label("Auftragsersteller");
			GridPane.setConstraints(l1, 0, 0);
			ComboBox<Person> comboBoxPersonen = new ComboBox<>();
			comboBoxPersonen.setPromptText("Person auswaehlen");
			try {
				List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
				try {
						comboBoxPersonen.getItems().addAll(personen);
				} catch (Exception e) {
					comboBoxPersonen.setPromptText("Person auswaehlen");
					System.out.println(comboBoxPersonen.getValue());
				}
				
			} catch (SQLException e) {			
			}
			GridPane.setConstraints(comboBoxPersonen, 1, 0);
			
			
			
			//0,1 | 1,1
			Label l2 = new Label("Admins");
			GridPane.setConstraints(l2, 0, 1);
			int counter2 = 0;
			ComboBox<Person> comboBoxAdmins = new ComboBox<>();
			comboBoxAdmins.setPromptText("Admin auswaehlen");
			try {
				List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
				try {
						comboBoxAdmins.getItems().addAll(admins);
				}catch(Exception e) {
					comboBoxAdmins.setPromptText("Admin auswaehlen");
				}
			} catch (SQLException e) {
			} catch (DatabaseException e) {
			}
			GridPane.setConstraints(comboBoxAdmins, 1, 1);
			
			
			
			//0,2|1,2
			Label l3 = new Label("Auftragstitel");
			GridPane.setConstraints(l3 , 0 , 2);
			TextField auftragsTitel = new TextField();
			GridPane.setConstraints(auftragsTitel, 1, 2);
			
			
			//0,3 |1,3
			Label l4 = new Label("Prognostizierte Kosten");
			GridPane.setConstraints(l4 , 0 , 3);
			TextField prognoKosten = new TextField();
			GridPane.setConstraints(prognoKosten, 1, 3);
			
			
			//0,4|1,4
			Label l5 = new Label("Reelle Kosten");
			GridPane.setConstraints(l5 , 0 , 4);
			TextField reelleKosten = new TextField();
			GridPane.setConstraints(reelleKosten, 1, 4);
			
			
			ComboBox<String> comboBoxFertigung = new ComboBox<>();
			comboBoxFertigung.getItems().addAll(
	                "Leiterplatte",
	                "3D-Druck",
	                "Sonstiges"
	        );
			comboBoxFertigung.setPromptText("Fertigungsart");
	        GridPane.setConstraints(comboBoxFertigung, 0, 9);
	        
			Button btn = new Button("Erstellen");
			GridPane.setConstraints(btn, 1, 9);
			
			Button btnClose = new Button ("Schließen");
			GridPane.setConstraints(btnClose, 1, 9,1,1,HPos.RIGHT,null);
			
			
			
			
			grid.getChildren().addAll(btnClose, l1, comboBoxPersonen, l2, comboBoxAdmins, btn, comboBoxFertigung, l3, l4 , l5, reelleKosten, prognoKosten, auftragsTitel);
			
			
			
			
			//EVENTS
			//Fenster schließen
			btnClose.setOnMouseClicked(e -> {
				window.close();
			});
			
			//Auftrag erstellen
			btn.setOnMouseClicked(e -> {
				if(Validation.StringInputValidation(auftragsTitel)&& Validation.DoubleInputValidation(prognoKosten)&& Validation.DoubleInputValidation(reelleKosten)&& Validation.ComboBoxValidationPerson(comboBoxPersonen)&& Validation.ComboBoxValidationPerson(comboBoxAdmins)&& Validation.ComboBoxValidationString(comboBoxFertigung)) {
					Double progKost = Double.parseDouble(prognoKosten.getText());
					Double reelleKost = Double.parseDouble(reelleKosten.getText());
					try {
						
						
						Fertigungsverwaltung.getInstance().createAuftrag(auftragsTitel.getText(), comboBoxFertigung.getValue(), progKost ,reelleKost, comboBoxPersonen.getValue().getPERSON_ID(), comboBoxAdmins.getValue().getPERSON_ID(), Personenverwaltung.getInstance().getAllPersons());
						AlertBox.display("Erfolg!", "Auftrag erzeugt!");
						window.close();
						
					} catch (SQLException e1) {
						AlertBox.display("Fehler", e1.getMessage());
					}
					
				}
				
			});
			
			
	        Scene scene = new Scene(grid);
	        window.setScene(scene);
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.showAndWait();
	        
	        
	        
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
	 
}
