package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
			
			//0,0 | 1,0
			Label l1 = new Label("Auftragsersteller");
			GridPane.setConstraints(l1, 0, 0);
			int counter1 = 0;
			ChoiceBox<String> choiceBoxPersonen = new ChoiceBox<>();
			try {
				List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
				try {
					while(personen.listIterator().hasNext()) {
						choiceBoxPersonen.getItems().addAll(personen.listIterator(counter1).next().getVorname() +" "+ personen.listIterator(counter1).next().getNachname()+ personen.listIterator(counter1).next().getPERSON_ID() );
						counter1++;
					}
				} catch (Exception e) {
					choiceBoxPersonen.setValue(personen.get(0).getVorname()+ " " +personen.get(0).getNachname() );
				}
				
			} catch (SQLException e) {			
			}
			GridPane.setConstraints(choiceBoxPersonen, 1, 0);
			
			//0,1 | 1,1
			Label l2 = new Label("Admins");
			GridPane.setConstraints(l2, 0, 1);
			int counter2 = 0;
			ChoiceBox<String> choiceBoxAdmins = new ChoiceBox<>();
			try {
				List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
				try {
					while(admins.listIterator().hasNext()) {
						choiceBoxAdmins.getItems().addAll(admins.listIterator(counter2).next().getVorname()+ " " + admins.listIterator(counter2).next().getNachname() + admins.listIterator(counter2).next().getPERSON_ID());
					
						counter2++;
					}
				}catch(Exception e) {
					choiceBoxAdmins.setValue(admins.get(0).getVorname()+ " " +admins.get(0).getNachname() );
				}
			} catch (SQLException e) {
			} catch (DatabaseException e) {
			}
			choiceBoxAdmins.getItems().indexOf(choiceBoxAdmins.getValue());
			GridPane.setConstraints(choiceBoxAdmins, 1, 1);
			
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
			Label l5 = new Label("Prognostizierte Kosten");
			GridPane.setConstraints(l5 , 0 , 4);
			TextField reeleKosten = new TextField();
			GridPane.setConstraints(reeleKosten, 1, 4);
			
			ComboBox<String> comboBox;
			comboBox = new ComboBox<>();
	        comboBox.getItems().addAll(
	                "Leiterplatte",
	                "3D-Druck",
	                "Sonstiges"
	        );
	        comboBox.setPromptText("Fertigungsart");
	        GridPane.setConstraints(comboBox, 0, 9);
	        
			Button btn = new Button("Erstellen");
			GridPane.setConstraints(btn, 1, 9);
			
			
			
			
			grid.getChildren().addAll(l1, choiceBoxPersonen, l2, choiceBoxAdmins, btn, comboBox, l3, l4 , l5, reeleKosten, prognoKosten, auftragsTitel);
		
			/*btn.setOnMouseClicked(e -> {
				
				if(Validation.passwordInputValidation(passwortInput, passwortconfirmInput) && Validation.IntegerInputValidation(PLZInput) && Validation.nutzernameInputValidation(nutzernameInput) && Validation.StringInputValidation(vornameInput) && Validation.StringInputValidation(nachnameInput) && Validation.HausNrInputValidation(hausnummerInput) && Validation.StringInputValidation(strasseInput) && Validation.ComboBoxValidation(comboBox)){
						
				try{
					
					Personenverwaltung.getInstance().createPerson(vornameInput.getText(), nachnameInput.getText(), strasseInput.getText(), Integer.parseInt(hausnummerInput.getText()), Integer.parseInt(PLZInput.getText()), EMailInput.getText(), nutzernameInput.getText(), passwortInput.getText(), comboBox.getSelectionModel().getSelectedIndex());
					AlertBox.display("Erfolg!", "Person erzeugt!");
					window.close();
				}
				
				catch (SQLException | DatabaseException e1) {
					AlertBox.display("Fehler", e1.getMessage());
				}
				}});
			
			*/
	        Scene scene = new Scene(grid);
	        window.setScene(scene);
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.showAndWait();
	        
	        
	        
	    }
}
