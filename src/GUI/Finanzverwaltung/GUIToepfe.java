package GUI.Finanzverwaltung;

import java.sql.SQLException;
import java.util.List;
import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Finanzverwaltung;
import Logic.Kasse;
import Logic.Topf;
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

public class GUIToepfe {
	Tab tab;
	TableView<Topf> toepfeTable;
	List<Topf> toepfe = GUIToepfe.getToepfe();
	List<Kasse> kassen = GUIKassen.getKassen();
	
	public GUIToepfe(Tab tab) {
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
		
		TableColumn<Topf, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Topf, Double> sollColumn = new TableColumn<>("Soll");
		sollColumn.setCellValueFactory(new PropertyValueFactory<>("soll"));
		
		TableColumn<Topf, Double> istColumn = new TableColumn<>("Ist");
		istColumn.setCellValueFactory(new PropertyValueFactory<>("ist"));
		
		TableColumn<Topf, Kasse> kasseColumn = new TableColumn<>("Kasse");
		kasseColumn.setCellValueFactory(new PropertyValueFactory<>("kasse"));
		
		toepfeTable = new TableView<>();
		toepfeTable.setItems(GUIToepfe.getToepfe());
		toepfeTable.getColumns().addAll(nameColumn, sollColumn,istColumn, kasseColumn);
		
		toepfeTable.setPrefWidth(350);
		bp.setLeft(toepfeTable);
		
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
		
		ComboBox<Kasse> comboBoxKassen = new ComboBox<>();
		comboBoxKassen.setPromptText("Kasse auswaehlen");
		comboBoxKassen.setItems(GUIKassen.getKassen());
		GridPane.setConstraints(comboBoxKassen, 0, 3);
		comboBoxKassen.setDisable(true);
		
		
		grid.getChildren().addAll(btnameLabel,btnameInput,sollLabel,sollInput,istLabel,istInput ,comboBoxKassen);
		bp.setCenter(grid);
		
		// End of Center
		
		
		
		//Events:
		//Oeffnet CreateTopf Fenster
		create.setOnMouseClicked(e -> {
			GUICreateTopf.display();
			try {
				toepfeTable.setItems(getToepfe());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
		});
		
		
		//Bearbeitet einen Topf
		modify.setOnMouseClicked(e -> {
			Topf tempTopf = toepfeTable.getSelectionModel().getSelectedItem();
			try {
					if(Validation.StringInputValidation(btnameInput)&&Validation.DoubleInputValidation(sollInput)&&Validation.DoubleInputValidation(istInput)) {
						Finanzverwaltung.getInstance().modifyTopf(tempTopf.getTOPF_ID(), "name", btnameInput.getText());
						Finanzverwaltung.getInstance().modifyTopf(tempTopf.getTOPF_ID(), "ist", istInput.getText());
						Finanzverwaltung.getInstance().modifyTopf(tempTopf.getTOPF_ID(), "soll", sollInput.getText());
						Finanzverwaltung.getInstance().modifyTopf(tempTopf.getTOPF_ID(), "KASSE_ID", String.valueOf(comboBoxKassen.getSelectionModel().getSelectedItem().getKASSE_ID()));
					}
					toepfeTable.setItems(getToepfe());
					AlertBox.display("Erfolg!", "Topf bearbeitet!");
				} catch (SQLException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				toepfeTable.getSelectionModel().select(tempTopf);
			}
		});
		
		
		//Loescht einen Topf
		delete.setOnMouseClicked(e -> {
			Topf tempTopf = toepfeTable.getSelectionModel().getSelectedItem();
			try {
				Finanzverwaltung.getInstance().deleteTopf(tempTopf.getTOPF_ID());
				toepfeTable.setItems(GUIToepfe.getToepfe());
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
				comboBoxKassen.getSelectionModel().select(null);
				comboBoxKassen.setPromptText("Kasse auswaehlen");
				comboBoxKassen.setDisable(true);
				AlertBox.display("Erfolg!", "Topf geloescht!");
			}
			
		});
		
		
		//Behandelt den Klick auf einen Tabelleneintrag
		toepfeTable.setOnMouseClicked(e -> {
			if(!(toepfeTable.getSelectionModel().isEmpty())) {
				modify.setDisable(false);
				delete.setDisable(false);
				Topf tempTopf = toepfeTable.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				btnameInput.setText(tempTopf.getName());
				btnameInput.setStyle(null);
				btnameInput.setDisable(false);
				sollInput.setText(String.valueOf(tempTopf.getSoll()));
				sollInput.setStyle(null);
				sollInput.setDisable(false);
				istInput.setText(String.valueOf(tempTopf.getIst()));
				istInput.setStyle(null);
				istInput.setDisable(false);
				comboBoxKassen.getSelectionModel().select(this.indexOf(tempTopf.getKasse_ID()));
				comboBoxKassen.setDisable(false);
			}
		});
		
		tab.setContent(bp);
	}

	
	//Holt alle Toepfe aus der Datenbank
	static ObservableList<Topf> getToepfe() {
		ObservableList<Topf> result = FXCollections.observableArrayList();
		try {
			for (Topf t :  Finanzverwaltung.getInstance().getAllTopf()) {
				result.add(t);
				}
		} catch (SQLException | DatabaseException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		return result;
	}
	
	
	//Gibt den Index einer Kasse zurueck
	private int indexOf(int id) {
		int i=0;
		for(Kasse k: kassen){
			if(k.getKASSE_ID()==id) return i;
			i++;
		}
		return -1;
	}
	
}