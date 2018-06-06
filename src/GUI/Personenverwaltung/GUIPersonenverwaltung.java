/**
 * 
 */
package GUI.Personenverwaltung;

import java.sql.SQLException;
import java.util.Date;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIPersonenverwaltung {
	
	Tab tab;
	TableView<Person> table;
	Person nutzer;
	public GUIPersonenverwaltung(Tab tab, Person person) {
		this.tab=tab;
		this.nutzer = person;
	}
	
	public void open() {
		
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		
		// Start of TOP
		
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
		
		// End of TOP
		
		// Left
		
		TableColumn<Person, String> vornameColumn = new TableColumn<>("Vorname");
		//vornameColumn.setMinWidth(100);
		vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));

		TableColumn<Person, String> nachnameColumn = new TableColumn<>("Nachname");
		nachnameColumn.setMinWidth(100);
		nachnameColumn.setCellValueFactory(new PropertyValueFactory<>("nachname"));

		TableColumn<Person, Date> erstelltColumn = new TableColumn<>("Erstellt");
		//erstelltColumn.setMinWidth(150);
		erstelltColumn.setCellValueFactory(new PropertyValueFactory<>("zuerst_erstellt"));

		TableColumn<Person, Date> geaendertColumn = new TableColumn<>("Zuletzt geaendert");
		//erstelltColumn.setMinWidth(150);
		geaendertColumn.setCellValueFactory(new PropertyValueFactory<>("zuletzt_geaendert"));

		TableColumn<Person, Boolean> rolleColumn = new TableColumn<>("Admin?");
		//rolleColumn.setMinWidth(50);
		rolleColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

		table = new TableView<>();
		try {
			table.setItems(getPersonen());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		table.getColumns().addAll(vornameColumn, nachnameColumn, erstelltColumn, geaendertColumn, rolleColumn);

		table.setPrefWidth(620);
		bp.setLeft(table);

		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label vornameLabel = new Label("Vorname: ");
		GridPane.setConstraints(vornameLabel, 0, 0);
		
		TextField vornameInput = new TextField();
		vornameInput.setEditable(false);
		vornameInput.setDisable(true);
		GridPane.setConstraints(vornameInput, 1, 0);
		
		Label nachnameLabel = new Label("Nachname: ");
		GridPane.setConstraints(nachnameLabel, 0, 1);
		
		TextField nachnameInput = new TextField();
		nachnameInput.setEditable(false);
		nachnameInput.setDisable(true);
		GridPane.setConstraints(nachnameInput, 1, 1);
		
		Label strasseLabel = new Label("Strasse: ");
		GridPane.setConstraints(strasseLabel, 0, 2);
		
		TextField strasseInput = new TextField();
		strasseInput.setEditable(false);
		strasseInput.setDisable(true);
		GridPane.setConstraints(strasseInput, 1, 2);
		
		Label hausnummerLabel = new Label("Hausnummer: ");
		GridPane.setConstraints(hausnummerLabel, 0, 3);
		
		TextField hausnummerInput = new TextField();
		hausnummerInput.setEditable(false);
		hausnummerInput.setDisable(true);
		GridPane.setConstraints(hausnummerInput, 1, 3);
		
		Label PLZLabel = new Label("PLZ: ");
		GridPane.setConstraints(PLZLabel, 0, 4);
		
		TextField PLZInput = new TextField();
		PLZInput.setEditable(false);
		PLZInput.setDisable(true);
		GridPane.setConstraints(PLZInput, 1, 4);
		
		Label EMailLabel = new Label("E-Mail: ");
		GridPane.setConstraints(EMailLabel, 0, 5);
		
		TextField EMailInput = new TextField();
		EMailInput.setEditable(false);
		EMailInput.setDisable(true);
		GridPane.setConstraints(EMailInput, 1, 5);
		
		Label nutzernameLabel = new Label("Nutzername: ");
		GridPane.setConstraints(nutzernameLabel, 0, 6);
		
		TextField nutzernameInput = new TextField();
		nutzernameInput.setEditable(false);
		nutzernameInput.setDisable(true);
		GridPane.setConstraints(nutzernameInput, 1, 6);
		
		// passwort ohne whitespace vorne oder hinten!
		
		Label oldpasswortLabel = new Label("Altes Passwort: ");
		GridPane.setConstraints(oldpasswortLabel, 0, 12);
		
		PasswordField oldpasswortconfirmInput = new PasswordField();
		oldpasswortconfirmInput.setEditable(false);
		oldpasswortconfirmInput.setDisable(true);
		GridPane.setConstraints(oldpasswortconfirmInput, 1, 12);
		
		Label passwortLabel = new Label("Neues Passwort: ");
		GridPane.setConstraints(passwortLabel, 0, 13);
		
		PasswordField passwortInput = new PasswordField();
		passwortInput.setEditable(false);
		passwortInput.setDisable(true);
		GridPane.setConstraints(passwortInput, 1, 13);
		
		Label passwortconfirmLabel = new Label("Neues Passwort bestaetigen: ");
		GridPane.setConstraints(passwortconfirmLabel, 0, 14);
		
		PasswordField passwortconfirmInput = new PasswordField();
		passwortconfirmInput.setEditable(false);
		passwortconfirmInput.setDisable(true);
		GridPane.setConstraints(passwortconfirmInput, 1, 14);
		
		grid.getChildren().addAll(vornameLabel, vornameInput, nachnameLabel, nachnameInput, strasseLabel, strasseInput, hausnummerLabel, hausnummerInput, 
				PLZLabel, PLZInput, EMailLabel, EMailInput, nutzernameLabel, nutzernameInput,oldpasswortLabel,oldpasswortconfirmInput ,passwortLabel, passwortInput, passwortconfirmLabel, passwortconfirmInput);
		
		bp.setCenter(grid);
		
		// End of Center
		
		//Events:
		
		create.setOnMouseClicked(e -> {
			GUICreatePerson.display();
			try {
				table.setItems(getPersonen());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
				open();
			}
		});
		
		modify.setOnMouseClicked(e -> {
			Person tempPerson = table.getSelectionModel().getSelectedItem();
			try {
				if((oldpasswortconfirmInput.getText()==null || oldpasswortconfirmInput.getText().trim().isEmpty()) &&(passwortInput.getText()==null || passwortInput.getText().trim().isEmpty())&&(passwortconfirmInput.getText()==null || passwortconfirmInput.getText().trim().isEmpty())) {
					//Wenn kein passwort geandert werden soll
					if(Validation.IntegerInputValidation(PLZInput) && Validation.nutzernameInputValidation(nutzernameInput) && Validation.StringInputValidation(vornameInput) && Validation.StringInputValidation(nachnameInput) && Validation.HausNrInputValidation(hausnummerInput) && Validation.StringInputValidation(strasseInput) && Validation.mailInputValidation(EMailInput)){
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "vorname", vornameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "nachname", nachnameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "strasse", strasseInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "email", EMailInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "nutzername", nutzernameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "hausnr", hausnummerInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "PLZ", PLZInput.getText().trim());
						AlertBox.display("Erfolg!", "Person bearbeitet!");
					}		
				}
				else {
					//Wenn passwort geandert werden soll
					if(Validation.changePasswordInputValidation(oldpasswortconfirmInput, passwortInput, passwortconfirmInput, tempPerson) && Validation.IntegerInputValidation(PLZInput) && Validation.nutzernameInputValidation(nutzernameInput) && Validation.StringInputValidation(vornameInput) && Validation.StringInputValidation(nachnameInput) && Validation.HausNrInputValidation(hausnummerInput) && Validation.mailInputValidation(EMailInput) && Validation.StringInputValidation(strasseInput)){
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "passwort", passwortInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "vorname", vornameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "nachname", nachnameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "strasse", strasseInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "email", EMailInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "nutzername", nutzernameInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "hausnr", hausnummerInput.getText().trim());
						Personenverwaltung.getInstance().modifyPerson(tempPerson.getPERSON_ID(), "PLZ", PLZInput.getText().trim());
						oldpasswortconfirmInput.clear();
						passwortconfirmInput.clear();
						passwortInput.clear();
						AlertBox.display("Erfolg!", "Person bearbeitet!");
					}
				}
				table.setItems(getPersonen());
				
			}
			
			catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			}
			
			finally {
				table.getSelectionModel().select(tempPerson);
			}
			
		});
		
		table.setOnMouseClicked(e -> {
			if(!(table.getSelectionModel().isEmpty())) {
				
				delete.setDisable(false);
				modify.setDisable(false);
				Person tempPerson = table.getSelectionModel().getSelectedItem();
				vornameInput.setStyle(null);
				vornameInput.setText(tempPerson.getVorname());
				vornameInput.setDisable(false);
				vornameInput.setEditable(true);
				nachnameInput.setStyle(null);
				nachnameInput.setText(tempPerson.getNachname());
				nachnameInput.setDisable(false);
				nachnameInput.setEditable(true);
				strasseInput.setStyle(null);
				strasseInput.setText(tempPerson.getStrasse());
				strasseInput.setDisable(false);
				strasseInput.setEditable(true);
				hausnummerInput.setStyle(null);
				hausnummerInput.setText(tempPerson.getHausnr());
				hausnummerInput.setDisable(false);
				hausnummerInput.setEditable(true);
				PLZInput.setStyle(null);
				PLZInput.setText(Integer.toString(tempPerson.getPLZ()));
				PLZInput.setDisable(false);
				PLZInput.setEditable(true);
				EMailInput.setStyle(null);
				EMailInput.setText(tempPerson.getEmail());
				EMailInput.setDisable(false);
				EMailInput.setEditable(true);
				nutzernameInput.setStyle(null);
				nutzernameInput.setText(tempPerson.getNutzername());
				nutzernameInput.setDisable(false);
				nutzernameInput.setEditable(true);
				passwortInput.setDisable(false);
				passwortInput.setEditable(true);
				passwortconfirmInput.setDisable(false);
				passwortconfirmInput.setEditable(true);
				oldpasswortconfirmInput.setDisable(false);
				oldpasswortconfirmInput.setEditable(true);
			}
		});
		
		delete.setOnMouseClicked(e-> {
			Person tempPerson = table.getSelectionModel().getSelectedItem();
			try {
				Personenverwaltung.getInstance().deletePerson(tempPerson.getPERSON_ID());
				table.setItems(getPersonen());
				vornameInput.clear();
				nachnameInput.clear();
				strasseInput.clear();
				PLZInput.clear();
				EMailInput.clear();
				nutzernameInput.clear();
				passwortInput.clear();
				hausnummerInput.clear();
				modify.setDisable(true);
				delete.setDisable(true);
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
		});
		
		tab.setContent(bp);
	}
	

	public static ObservableList<Person> getPersonen() throws SQLException{
		ObservableList<Person> result = FXCollections.observableArrayList();
		for (Person p :  Personenverwaltung.getInstance().getAllPersons()) {
			result.add(p);
		}
		return result;
	}
}
	
