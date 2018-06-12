/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Bauteil;
import Logic.Bauteileverwaltung;
import Logic.Bauteilwarenkorbelement;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
public class GUIWarenkorb {
	Tab tab;
	Person nutzer;
	TableView<Bauteil> bauteillager;
	TableView<Bauteilwarenkorbelement> warenkorb;
	
	public GUIWarenkorb(Tab tab, Person nutzer) {
		this.tab=tab;
		this.nutzer=nutzer;
	}
	
	@SuppressWarnings("unchecked")
	public void open() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		
		// Start of TOP
		/*
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
		*/
		// End of TOP
		
		// Start of LEFT
		
		TableColumn<Bauteil, String> kategorieColumn = new TableColumn<>("Kategorie");
		kategorieColumn.setCellValueFactory(new PropertyValueFactory<>("kategorie_name"));
		
		TableColumn<Bauteil, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Bauteil, String> linkColumn = new TableColumn<>("link");
		linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
		
		TableColumn<Bauteil, Double> preisColumn = new TableColumn<>("Preis");
		preisColumn.setCellValueFactory(new PropertyValueFactory<>("preis"));
		
		TableColumn<Bauteil, Integer> gelagertColumn = new TableColumn<>("Gelagert");
		gelagertColumn.setCellValueFactory(new PropertyValueFactory<>("gelagert"));
		
		TableColumn<Bauteil, String> ortColumn = new TableColumn<>("Ort");
		ortColumn.setCellValueFactory(new PropertyValueFactory<>("ort"));
		
		bauteillager = new TableView<>();
		try {
			bauteillager.setItems(getBauteile());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		bauteillager.getColumns().addAll(kategorieColumn, nameColumn, linkColumn, preisColumn, gelagertColumn, ortColumn);
		
		bauteillager.setPrefWidth(350);
		bp.setLeft(bauteillager);
		
		// End of LEFT
		
		// Start of Center
		
		// Center Lager:
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		
		Label bauteilLabel = new Label("Bauteil: ");
		GridPane.setConstraints(bauteilLabel, 0, 0);
		
		Label bauteilnameLabel = new Label();
		GridPane.setConstraints(bauteilnameLabel, 0, 1);
		
		Label anzahlLabel = new Label("Anzahl: ");
		GridPane.setConstraints(anzahlLabel, 1, 0);
		
		TextField anzahlInput = new TextField();
		GridPane.setConstraints(anzahlInput, 1, 1);
		anzahlInput.setDisable(true);
		
		Button takeOut = new Button("Herausnehmen");
		GridPane.setConstraints(takeOut, 1, 2);
		takeOut.setDisable(true);
		
		grid.getChildren().addAll(bauteilLabel, bauteilnameLabel, anzahlLabel, anzahlInput, takeOut);

		bp.setCenter(grid);
		
		//Center Warenkorb:
		
		GridPane grid2 = new GridPane();
		grid2.setPadding(new Insets(10,10,10,10));
		grid2.setVgap(8);
		grid2.setHgap(10);
		grid2.setAlignment(Pos.CENTER);
		
		Label bauteil2Label = new Label("Bauteil: ");
		GridPane.setConstraints(bauteil2Label, 0, 0);
		
		Label bauteil2nameLabel = new Label();
		GridPane.setConstraints(bauteil2nameLabel, 0, 1);
		
		Label anzahl2Label = new Label("Anzahl: ");
		GridPane.setConstraints(anzahl2Label, 1, 0);
		
		TextField anzahl2Input = new TextField();
		GridPane.setConstraints(anzahl2Input, 1, 1);
		anzahl2Input.setDisable(true);
		
		Button takeBack = new Button("Zuruecklegen");
		GridPane.setConstraints(takeBack, 1, 2);
		takeBack.setDisable(true);

		grid2.getChildren().addAll(bauteil2Label, bauteil2nameLabel, anzahl2Label, anzahl2Input, takeBack);
		
		// End of Center
		
		// Start of RIGHT
		
		TableColumn<Bauteilwarenkorbelement, String> kategoriewColumn = new TableColumn<>("Kategorie");
		kategoriewColumn.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
		
		TableColumn<Bauteilwarenkorbelement, String> namewColumn = new TableColumn<>("Name");
		namewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Bauteilwarenkorbelement, String> linkwColumn = new TableColumn<>("link");
		linkwColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
		
		TableColumn<Bauteilwarenkorbelement, Double> preiswColumn = new TableColumn<>("Preis");
		preiswColumn.setCellValueFactory(new PropertyValueFactory<>("preis"));
		
		TableColumn<Bauteilwarenkorbelement, Integer> anzahlColumn = new TableColumn<>("Anzahl");
		anzahlColumn.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
		
		
		warenkorb = new TableView<>();
		try {
			warenkorb.setItems(getBauteilewarenkorb());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		warenkorb.getColumns().addAll(kategoriewColumn, namewColumn, linkwColumn, preiswColumn, anzahlColumn);
		
		warenkorb.setPrefWidth(400);
		bp.setRight(warenkorb);
		
		// END of RIGHT
		
		// Bottom
		
		HBox bot = new HBox();
		bot.setAlignment(Pos.BOTTOM_CENTER);
		bot.setSpacing(10);
		bot.setPadding(new Insets(10,10,10,20));
		Label schulden =new Label();
		try {
			schulden.setText("Aktuelle Schulden in Euro: "+String.valueOf(Personenverwaltung.getInstance().getPersonByID(nutzer.getPERSON_ID()).getBauteilschulden()));
		} catch (DatabaseException | SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		bot.getChildren().addAll(schulden);
		
		bp.setBottom(bot);
		
		// End Bottom
		
		
		
		
		
		//Events:
		
		
		//Handling fuer Klicks auf Tabelleneintrag
		bauteillager.setOnMouseClicked(e -> {
			if(!(bauteillager.getSelectionModel().isEmpty())) {
				takeBack.setDisable(false);
				takeOut.setDisable(false);
				anzahlInput.setDisable(false);
				anzahl2Input.setDisable(false);
				Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				bauteilnameLabel.setText(tempBauteil.getName());
			}
		});
		
		
		//Handling fuer Klicks auf Tabelleneintrag
		warenkorb.setOnMouseClicked(e -> {
			if(!(warenkorb.getSelectionModel().isEmpty())) {
				takeBack.setDisable(false);
				takeOut.setDisable(false);
				anzahlInput.setDisable(false);
				anzahl2Input.setDisable(false);
				Bauteilwarenkorbelement tempBauteilelement = warenkorb.getSelectionModel().getSelectedItem();
				bp.setCenter(grid2);
				bauteil2nameLabel.setText(tempBauteilelement.getName());
			}
		});
		
		
		//Hinzufuegen von Items in den Personen Warenkorb
		takeOut.setOnMouseClicked(e -> {
			if(Validation.IntegerInputValidation(anzahlInput)) {
				if((bauteillager.getSelectionModel().getSelectedItem() == null)){
					AlertBox.display("Fehler", "Kein Bauteil einer Tabelle ausgewaehlt!");
				}
				else{
					Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
					try {
						Bauteileverwaltung.getInstance().removeBauteil(bauteillager.getSelectionModel().getSelectedItem().getID(), Integer.parseInt(anzahlInput.getText()), nutzer.getPERSON_ID());
						bauteillager.setItems(getBauteile());
						warenkorb.setItems(getBauteilewarenkorb());
						schulden.setText("Aktuelle Schulden in Euro: "+Double.toString(Personenverwaltung.getInstance().getPersonByID(nutzer.getPERSON_ID()).getBauteilschulden()) +" Euro");
					} catch (NumberFormatException | DatabaseException | SQLException e1) {
						AlertBox.display("Fehler", e1.getMessage());
					} finally {	
						bauteillager.getSelectionModel().select(tempBauteil);
						
					}
				}
				
			}
		});
		
		
		//Hinzufuegen von Items aus dem Personen Warenkorb ins Lager
		takeBack.setOnMouseClicked(e -> {
			if(Validation.IntegerInputValidation(anzahl2Input)) {
				if((warenkorb.getSelectionModel().getSelectedItem() == null)){
					AlertBox.display("Fehler", "Kein Bauteil einer Tabelle ausgewaehlt!");
				}else{
					Bauteilwarenkorbelement tempWarenkorbelement = warenkorb.getSelectionModel().getSelectedItem();
					try {
						Bauteileverwaltung.getInstance().addBauteil(warenkorb.getSelectionModel().getSelectedItem().getBauteil().getID(), Integer.parseInt(anzahl2Input.getText()), nutzer.getPERSON_ID());
						warenkorb.setItems(getBauteilewarenkorb());
						bauteillager.setItems(getBauteile());
						schulden.setText("Aktuelle Schulden in Euro: "+Double.toString(Personenverwaltung.getInstance().getPersonByID(nutzer.getPERSON_ID()).getBauteilschulden()) +" Euro");
					} catch (NumberFormatException | DatabaseException | SQLException e1) {
						AlertBox.display("Fehler", e1.getMessage());
					} finally {	
						warenkorb.getSelectionModel().select(tempWarenkorbelement);
					}
				}
				
			}
		});
		
		// finally
		
		tab.setContent(bp);
	}
	
	// methoden
	
	
	//Holt alle Bauteile aus der Datenbank
	public static ObservableList<Bauteil> getBauteile() throws SQLException{
		ObservableList<Bauteil> result = FXCollections.observableArrayList();
		for (Bauteil b :  Bauteileverwaltung.getInstance().getAllBauteile()) {
			result.add(b);
		}
		return result;
	}
	
	
	//Holt alle Bauteile aus der Datenbank fuer die Person
	public ObservableList<Bauteilwarenkorbelement> getBauteilewarenkorb() throws SQLException{
		ObservableList<Bauteilwarenkorbelement> result = FXCollections.observableArrayList();
		for (Bauteilwarenkorbelement b :  Bauteileverwaltung.getInstance().getBauteilwarenkorbByID(nutzer.getPERSON_ID())) {
			if (b.getAnzahl()>0) {
				result.add(b);
			}
		}
		return result;
	}
	
}