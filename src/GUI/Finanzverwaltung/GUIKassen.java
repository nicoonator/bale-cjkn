package GUI.Finanzverwaltung;

import java.sql.SQLException;
import java.util.List;

import GUI.AlertBox;
import GUI.Bauteileverwaltung.GUICreateBauteil;
import GUI.Bauteileverwaltung.GUIWarenkorb;
import GUI.Validation.Validation;
import Logic.Bauteil;
import Logic.Bauteileverwaltung;
import Logic.Finanzverwaltung;
import Logic.Kasse;
import Logic.Kategorie;
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

public class GUIKassen {
	Tab tab;
	TableView<Kasse> kassenTable;
	List<Kasse> kassen = this.getKassen();
	
	public GUIKassen(Tab tab) {
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
		
		TableColumn<Kasse, String> kategorieColumn = new TableColumn<>("Name");
		kategorieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Kasse, Integer> nameColumn = new TableColumn<>("Soll");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("soll"));
		
		TableColumn<Kasse, Integer> linkColumn = new TableColumn<>("Ist");
		linkColumn.setCellValueFactory(new PropertyValueFactory<>("ist"));
		
		TableColumn<Kasse, String> preisColumn = new TableColumn<>("Typ");
		preisColumn.setCellValueFactory(new PropertyValueFactory<>("typ"));
		
		kassenTable = new TableView<>();
		kassenTable.setItems(this.getKassen());
		
		kassenTable.getColumns().addAll(kategorieColumn, nameColumn, linkColumn, preisColumn);
		
		kassenTable.setPrefWidth(350);
		bp.setLeft(kassenTable);
		
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
		btnameInput.setDisable(true);
		
		Label sollLabel = new Label("Soll: ");
		GridPane.setConstraints(sollLabel, 0, 1);
		
		TextField sollInput = new TextField();
		GridPane.setConstraints(sollInput, 1, 1);
		sollInput.setDisable(true);
		
		Label istLabel = new Label("Ist: ");
		GridPane.setConstraints(istLabel, 0, 2);
		
		TextField istInput = new TextField();
		GridPane.setConstraints(istInput, 1, 2);
		istInput.setDisable(true);
		
		Label typLabel = new Label("Typ: ");
		GridPane.setConstraints(typLabel, 0, 3);
		
		TextField typInput = new TextField();
		GridPane.setConstraints(typInput, 1, 3);
		typInput.setDisable(true);
		
		grid.getChildren().addAll(btnameLabel,btnameInput,sollLabel,sollInput,istLabel,istInput ,typLabel,typInput);
		bp.setCenter(grid);
		
		// End of Center
		
		//Events:
		
		create.setOnMouseClicked(e -> {
			GUICreateKasse.display();
			try {
				kassenTable.setItems(GUIWarenkorb.getBauteile());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
		});
		/*
		modify.setOnMouseClicked(e -> {
			Bauteil tempBauteil = kassenTable.getSelectionModel().getSelectedItem();
			try {
				if(Validation.StringInputValidation(btnameInput) && Validation.StringInputValidation(linkInput) &&Validation.IntegerInputValidation(gelagertInput) &&Validation.IntegerInputValidation(geplantInput) &&Validation.IntegerInputValidation(bestelltInput) &&Validation.StringInputValidation(ortInput)&&Validation.ComboBoxValidationKategorie(comboBoxKategorie)) {
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "name", btnameInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "link", linkInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "gelagert", gelagertInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "geplant", geplantInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "bestellt", bestelltInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "ort", ortInput.getText());
					Bauteileverwaltung.getInstance().modifyBauteil(tempBauteil.getID(), "KATEGORIE_ID", String.valueOf(comboBoxKategorie.getSelectionModel().getSelectedItem().getID()));
					kassenTable.setItems(GUIWarenkorb.getBauteile());
				}
			} catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				kassenTable.getSelectionModel().select(tempBauteil);
			}
			
		});
		
		delete.setOnMouseClicked(e -> {
			Bauteil tempBauteil = kassenTable.getSelectionModel().getSelectedItem();
			try {
				Bauteileverwaltung.getInstance().deleteBauteilByID(tempBauteil.getID());
				kassenTable.setItems(GUIWarenkorb.getBauteile());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
			
		});
		*/
		kassenTable.setOnMouseClicked(e -> {
			if(!(kassenTable.getSelectionModel().isEmpty())) {
				modify.setDisable(false);
				delete.setDisable(false);
				Kasse tempKasse = kassenTable.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				btnameInput.setText(tempKasse.getName());
				btnameInput.setStyle(null);
				btnameInput.setDisable(false);
				sollInput.setText(String.valueOf(tempKasse.getSoll()));
				sollInput.setStyle(null);
				sollInput.setDisable(false);
				istInput.setText(String.valueOf(tempKasse.getIst()));
				istInput.setStyle(null);
				istInput.setDisable(false);
				typInput.setText(tempKasse.getTyp());
				typInput.setStyle(null);
			}
		});
		
		tab.setContent(bp);
	}

	private ObservableList<Kasse> getKassen() {
		ObservableList<Kasse> result = FXCollections.observableArrayList();
		try {
			for (Kasse k :  Finanzverwaltung.getInstance().getAllKasse()) {
				result.add(k);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
