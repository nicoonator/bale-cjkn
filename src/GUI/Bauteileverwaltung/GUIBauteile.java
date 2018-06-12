/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.List;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Bauteil;
import Logic.Bauteileverwaltung;
import Logic.Kategorie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class GUIBauteile {
	Tab tab;
	TableView<Bauteil> bauteillager;
	List<Kategorie> kategorien = this.getKategorien();
	
	public GUIBauteile(Tab tab) {
		this.tab=tab;
	}
	
	private List<Kategorie> getKategorien() {
		try {
			return Bauteileverwaltung.getInstance().getAllKategorie();
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void open() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		// Start of TOP
		
		HBox top = new HBox();
		top.setSpacing(10);
		top.setPadding(new Insets(10,10,10,10));
		
		Button create = new Button("Neu");
		Button modify = new Button("Bearbeiten");
		Button delete = new Button("Loeschen");
		
		modify.setDisable(true);
		delete.setDisable(true);
		
		top.getChildren().addAll(create, modify,delete);
		
		bp.setTop(top);
		
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
			bauteillager.setItems(GUIWarenkorb.getBauteile());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		bauteillager.getColumns().addAll(kategorieColumn, nameColumn, linkColumn, preisColumn, gelagertColumn, ortColumn);
		
		bauteillager.setPrefWidth(350);
		bp.setLeft(bauteillager);
		
		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label btnameLabel = new Label("Name: ");
		GridPane.setConstraints(btnameLabel, 0, 0);
		
		TextField btnameInput = new TextField();
		btnameInput.setDisable(true);
		GridPane.setConstraints(btnameInput, 1, 0);
		
		Label linkLabel = new Label("Link: ");
		GridPane.setConstraints(linkLabel, 0, 1);
		
		TextField linkInput = new TextField();
		linkInput.setDisable(true);
		GridPane.setConstraints(linkInput, 1, 1);
		
		Label preisLabel = new Label("Preis: ");
		GridPane.setConstraints(preisLabel, 0, 2);
		
		TextField preisInput = new TextField();
		preisInput.setDisable(true);
		GridPane.setConstraints(preisInput, 1, 2);
		preisInput.setDisable(true);
		
		Label gelagertLabel = new Label("gelagert: ");
		GridPane.setConstraints(gelagertLabel, 0, 3);
		
		TextField gelagertInput = new TextField();
		gelagertInput.setDisable(true);
		GridPane.setConstraints(gelagertInput, 1, 3);
		
		Label geplantLabel = new Label("geplant: ");
		GridPane.setConstraints(geplantLabel, 0, 4);
		
		TextField geplantInput = new TextField();
		geplantInput.setDisable(true);
		GridPane.setConstraints(geplantInput, 1, 4);
		
		Label bestelltLabel = new Label("bestellt: ");
		GridPane.setConstraints(bestelltLabel, 0, 5);
		
		TextField bestelltInput = new TextField();
		bestelltInput.setDisable(true);
		GridPane.setConstraints(bestelltInput, 1, 5);
		
		Label ortLabel = new Label("Ort: ");
		GridPane.setConstraints(ortLabel, 0, 6);
		
		TextField ortInput = new TextField();
		ortInput.setDisable(true);
		GridPane.setConstraints(ortInput, 1, 6);
		
		Label kategorieLabel = new Label("Kategorie");
		GridPane.setConstraints(kategorieLabel, 0, 7);
		
		ComboBox<Kategorie> comboBoxKategorie = new ComboBox<>();
		comboBoxKategorie.setDisable(true);
		comboBoxKategorie.setPromptText("Kategorie");
		comboBoxKategorie.getItems().addAll(kategorien);
		
		
		GridPane.setConstraints(comboBoxKategorie, 1, 7);
		
		grid.getChildren().addAll(btnameLabel,btnameInput,linkLabel,linkInput,preisLabel,preisInput ,gelagertLabel,gelagertInput,geplantLabel,geplantInput,bestelltLabel,bestelltInput, ortLabel,ortInput,kategorieLabel,comboBoxKategorie);
		bp.setCenter(grid);
		
		// End of Center
		
		//Events:
		
		
		//CreateBauteil Fenster oeffnen
		create.setOnMouseClicked(e -> {
			GUICreateBauteil.display();
			try {
				bauteillager.setItems(GUIWarenkorb.getBauteile());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
		});
		
		
		//Bauteil bearbeiten
		modify.setOnMouseClicked(e -> {
			Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
			try {
				if(Validation.StringInputValidation(btnameInput) && Validation.StringInputValidation(linkInput) &&Validation.IntegerInputValidation(gelagertInput) &&Validation.IntegerInputValidation(geplantInput) &&Validation.IntegerInputValidation(bestelltInput) &&Validation.StringInputValidation(ortInput)&&Validation.ComboBoxValidationKategorie(comboBoxKategorie)) {
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "name", btnameInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "link", linkInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "gelagert", gelagertInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "geplant", geplantInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "bestellt", bestelltInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "ort", ortInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "KATEGORIE_ID", String.valueOf(comboBoxKategorie.getSelectionModel().getSelectedItem().getID()));
					bauteillager.setItems(GUIWarenkorb.getBauteile());
					AlertBox.display("Erfolg!", "Bauteil bearbeitet!");
				}
			} catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				bauteillager.getSelectionModel().select(tempBauteil);
				
			}
			
		});
		
		
		//Bauteil loeschen
		delete.setOnMouseClicked(e -> {
			Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
			try {
				Bauteileverwaltung.getInstance().deleteBauteilByID(tempBauteil.getID());
				bauteillager.setItems(GUIWarenkorb.getBauteile());
				AlertBox.display("Erfolg!", "Bauteil geloescht!");
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			} finally {
				modify.setDisable(true);
				delete.setDisable(true);
				open();
			}
			
		});
		
		
		//Handling fuer Klick auf Tabelleneintrag
		bauteillager.setOnMouseClicked(e -> {
			if(!(bauteillager.getSelectionModel().isEmpty())) {
				modify.setDisable(false);
				delete.setDisable(false);
				Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				btnameInput.setText(tempBauteil.getName());
				btnameInput.setStyle(null);
				btnameInput.setDisable(false);
				linkInput.setText(tempBauteil.getLink());
				linkInput.setStyle(null);
				linkInput.setDisable(false);
				preisInput.setText(String.valueOf(tempBauteil.getPreis()));
				preisInput.setStyle(null);
				preisInput.setDisable(false);
				gelagertInput.setText(Integer.toString(tempBauteil.getGelagert()));
				gelagertInput.setStyle(null);
				gelagertInput.setDisable(false);
				geplantInput.setText(Integer.toString(tempBauteil.getGeplant()));
				geplantInput.setStyle(null);
				geplantInput.setDisable(false);
				bestelltInput.setText(Integer.toString(tempBauteil.getBestellt()));
				bestelltInput.setStyle(null);
				bestelltInput.setDisable(false);
				ortInput.setText(tempBauteil.getOrt());
				ortInput.setStyle(null);
				ortInput.setDisable(false);
				comboBoxKategorie.getSelectionModel().select(this.indexOf(tempBauteil.getKategorie_ID()));
				comboBoxKategorie.setDisable(false);
			}
			
		});

		tab.setContent(bp);
	}

	//Return index der Kategorie
	private int indexOf(int kategorie_ID) {
		int i=0;
		for(Kategorie k: kategorien){
			if(k.getID()==kategorie_ID) return i;
			i++;
		}
		return -1;
	}

	
}