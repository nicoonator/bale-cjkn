/**
 * 
 */
package GUI.Personenverwaltung;

import java.sql.SQLException;
import java.util.Date;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	public GUIPersonenverwaltung(Tab tab) {
		this.tab=tab;
	}
	
	public void open() throws SQLException {
		
		BorderPane bp = new BorderPane();
		
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
		
		create.setOnAction(e -> {
			//code goes here
		});
		
		modify.setOnAction(e -> {
			//code goes here
		});
		
		delete.setOnAction(e -> {
			//code goes here
		});
		
		bp.setTop(top);
		
		// End of TOP
		
		// Start of LEFT
		
		TableColumn<Person, String> vornameColumn = new TableColumn<>("Vorname");
		//vornameColumn.setMinWidth(100);
		vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));
		
		TableColumn<Person, String> nachnameColumn = new TableColumn<>("Nachname");
		nachnameColumn.setMinWidth(100);
		nachnameColumn.setCellValueFactory(new PropertyValueFactory<>("nachname"));
		
		TableColumn<Person, Date> erstelltColumn = new TableColumn<>("Erstellt");
		//erstelltColumn.setMinWidth(150);
		erstelltColumn.setCellValueFactory(new PropertyValueFactory<>("zuerst_erstellt"));
		
		//TODO: DEBUG Zuletzt geaendert
		TableColumn<Person, Date> geaendertColumn = new TableColumn<>("Zuletzt geaendert");
		//erstelltColumn.setMinWidth(150);
		erstelltColumn.setCellValueFactory(new PropertyValueFactory<>("zuletzt_geaendert"));
		
		TableColumn<Person, Date> rolleColumn = new TableColumn<>("Admin?");
		//rolleColumn.setMinWidth(50);
		rolleColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
		
		table = new TableView<>();
		table.setItems(getPersonen());
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
		
		TextField passwortInput = new TextField();
		GridPane.setConstraints(passwortInput, 1, 7);
		
		grid.getChildren().addAll(vornameLabel, vornameInput, nachnameLabel, nachnameInput, strasseLabel, strasseInput, hausnummerLabel, hausnummerInput, 
				PLZLabel, PLZInput, EMailLabel, EMailInput, nutzernameLabel, nutzernameInput, passwortLabel, passwortInput);
		
		bp.setCenter(grid);
		
		// End of Center
		
		//TODO: CENTER: Content, labels checkboxes etc.
		
		//Events:
		
		create.setOnMouseClicked(e -> {
			GUICreatePerson.display();
			try {
				table.setItems(getPersonen());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
		});
		
		table.setOnMouseClicked(e -> {
			delete.setDisable(false);
			modify.setDisable(false);
			Person tempPerson = table.getSelectionModel().getSelectedItem();
			vornameInput.setText(tempPerson.getVorname());
			nachnameInput.setText(tempPerson.getNachname());
			strasseInput.setText(tempPerson.getStrasse());
			hausnummerInput.setText(Integer.toString(tempPerson.getHausnr()));
			PLZInput.setText(Integer.toString(tempPerson.getPLZ()));
			EMailInput.setText(tempPerson.getEmail());
			nutzernameInput.setText(tempPerson.getNutzername());
		});
		
		delete.setOnMouseClicked(e-> {
			Person tempPerson = table.getSelectionModel().getSelectedItem();
			try {
				Personenverwaltung.getInstance().deletePerson(tempPerson.getPERSON_ID());
				table.setItems(getPersonen());
				//TODO: Labels Clearen
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
		});
		
		tab.setContent(bp);
	}
	
	public ObservableList<Person> getPersonen() throws SQLException{
		ObservableList<Person> result = FXCollections.observableArrayList();
		for (Person p :  Personenverwaltung.getInstance().getAllPersons()) {
			result.add(p);
		}
		return result;
	
	}
	
}