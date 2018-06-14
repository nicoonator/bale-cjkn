package GUI.Finanzverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Finanzverwaltung;
import Logic.Kasse;
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

public class GUIKassen {
	Tab tab;
	TableView<Kasse> kassenTable;
	List<Kasse> kassen = GUIKassen.getKassen();
	
	public GUIKassen(Tab tab) {
		this.tab=tab;
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
		
		TableColumn<Kasse, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Kasse, Double> sollColumn = new TableColumn<>("Soll");
		sollColumn.setCellValueFactory(new PropertyValueFactory<>("soll"));
		
		TableColumn<Kasse, Double> istColumn = new TableColumn<>("Ist");
		istColumn.setCellValueFactory(new PropertyValueFactory<>("ist"));
		
		TableColumn<Kasse, String> typColumn = new TableColumn<>("Typ");
		typColumn.setCellValueFactory(new PropertyValueFactory<>("typ"));
		
		kassenTable = new TableView<>();
		kassenTable.setItems(GUIKassen.getKassen());
		kassenTable.getColumns().addAll(nameColumn, sollColumn, istColumn, typColumn);
		
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
		
		Label ksLabel = new Label("Kostenstellennummer: ");
		GridPane.setConstraints(ksLabel, 0, 4);
		
		TextField ksInput = new TextField();
		GridPane.setConstraints(ksInput, 1, 4);
		ksInput.setDisable(true);
		
		grid.getChildren().addAll(btnameLabel,btnameInput,sollLabel,sollInput,istLabel,istInput ,typLabel,typInput, ksLabel, ksInput);
		bp.setCenter(grid);
		
		// End of Center
		
		
		
		//Events:
		//Oeffnet das CreateKasse Fenster
		create.setOnMouseClicked(e -> {
			GUICreateKasse.display();
			try {
				kassenTable.setItems(getKassen());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
		});
		
		
		//Bearbeitet eine Kasse
		modify.setOnMouseClicked(e -> {
			Kasse tempKasse = kassenTable.getSelectionModel().getSelectedItem();
			try {
				if(!ksInput.isDisable()) {
					if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)&&Validation.KostenstellenInputValidation(ksInput)) {
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "name", btnameInput.getText());
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "soll", sollInput.getText());
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "ist", istInput.getText());
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "kostenstellennummer", ksInput.getText());
						AlertBox.display("Erfolg!", "Kasse bearbeitet!");
					}
				} else {
					if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)) {
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "name", btnameInput.getText());
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "soll", sollInput.getText());
						Finanzverwaltung.getInstance().modifyKasse(tempKasse.getKASSE_ID(), "ist", istInput.getText());
						AlertBox.display("Erfolg!", "Kasse bearbeitet!");
					}
				}
					kassenTable.setItems(getKassen());
				} catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				kassenTable.getSelectionModel().select(tempKasse);
				
			}
		});
		
		
		//Loescht eine Kasse
		delete.setOnMouseClicked(e -> {
			Kasse tempKasse = kassenTable.getSelectionModel().getSelectedItem();
			try {
				Finanzverwaltung.getInstance().deleteKasse(tempKasse.getKASSE_ID());
				kassenTable.setItems(GUIKassen.getKassen());
				AlertBox.display("Erfolg!", "Kasse geloescht!");
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			} finally {
				modify.setDisable(true);
				delete.setDisable(true);
				btnameInput.setDisable(true);
				btnameInput.clear();
				sollInput.setDisable(true);
				sollInput.clear();
				istInput.setDisable(true);
				istInput.clear();
				typInput.setDisable(true);
				typInput.clear();
				ksInput.setDisable(true);
				ksInput.clear();
			}
			
		});
		
		
		//Behandelt den Klick auf einen Tabelleneintrag
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
				if (tempKasse.getTyp()=="Kostenstelle") {
					ksInput.setText(String.valueOf(tempKasse.getKsnummer()));
					ksInput.setStyle(null);
					ksInput.setDisable(false);
				} else {
					ksInput.clear();
					ksInput.setDisable(true);
				}
			}
		});
		
		tab.setContent(bp);
	}

	
	//Gibt alle Kassen aus der Datenbank zurueck
	static ObservableList<Kasse> getKassen() {
		ObservableList<Kasse> result = FXCollections.observableArrayList();
		try {
			for (Kasse k :  Finanzverwaltung.getInstance().getAllKasse()) {
				result.add(k);
				}
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		return result;
	}
}
