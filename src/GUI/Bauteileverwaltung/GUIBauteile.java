/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Bauteil;
import Logic.Bauteileverwaltung;
import Logic.Kategorie;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

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
		GridPane.setConstraints(btnameInput, 1, 0);
		
		Label linkLabel = new Label("Link: ");
		GridPane.setConstraints(linkLabel, 0, 1);
		
		TextField linkInput = new TextField();
		GridPane.setConstraints(linkInput, 1, 1);
		
		Label preisLabel = new Label("Preis: ");
		GridPane.setConstraints(preisLabel, 0, 2);
		
		TextField preisInput = new TextField();
		GridPane.setConstraints(preisInput, 1, 2);
		preisInput.setDisable(true);
		
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
		comboBoxKategorie.getItems().addAll(kategorien);
		
		
		GridPane.setConstraints(comboBoxKategorie, 1, 7);
		
		grid.getChildren().addAll(btnameLabel,btnameInput,linkLabel,linkInput,preisLabel,preisInput ,gelagertLabel,gelagertInput,geplantLabel,geplantInput,bestelltLabel,bestelltInput, ortLabel,ortInput,kategorieLabel,comboBoxKategorie);
		bp.setCenter(grid);
		
		// End of Center
		
		//Events:
		
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
				}
			} catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				bauteillager.getSelectionModel().select(tempBauteil);
			}
			
		});
		
		delete.setOnMouseClicked(e -> {
			Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
			try {
				Bauteileverwaltung.getInstance().deleteBauteilByID(tempBauteil.getID());
				bauteillager.setItems(GUIWarenkorb.getBauteile());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
			
		});
		
		bauteillager.setOnMouseClicked(e -> {
			if(!(bauteillager.getSelectionModel().isEmpty())) {
				modify.setDisable(false);
				delete.setDisable(false);
				Bauteil tempBauteil = bauteillager.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				btnameInput.setText(tempBauteil.getName());
				btnameInput.setStyle(null);
				linkInput.setText(tempBauteil.getLink());
				linkInput.setStyle(null);
				preisInput.setText(String.valueOf(tempBauteil.getPreis()));
				preisInput.setStyle(null);
				gelagertInput.setText(Integer.toString(tempBauteil.getGelagert()));
				gelagertInput.setStyle(null);
				geplantInput.setText(Integer.toString(tempBauteil.getGeplant()));
				geplantInput.setStyle(null);
				bestelltInput.setText(Integer.toString(tempBauteil.getBestellt()));
				bestelltInput.setStyle(null);
				ortInput.setText(tempBauteil.getOrt());
				ortInput.setStyle(null);
				comboBoxKategorie.getSelectionModel().select(this.indexOf(tempBauteil.getKategorie_ID()));
			}
		});

		tab.setContent(bp);
	}

	private int indexOf(int kategorie_ID) {
		int i=0;
		for(Kategorie k: kategorien){
			if(k.getID()==kategorie_ID) return i;
			i++;
		}
		return -1;
	}

	
}