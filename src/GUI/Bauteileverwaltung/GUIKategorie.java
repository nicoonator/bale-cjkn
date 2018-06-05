/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.Date;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Personenverwaltung.GUICreatePerson;
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
public class GUIKategorie {
	Tab tab;
	TableView<Kategorie> table;
	
	public GUIKategorie(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		// Start of TOP
		
		HBox top = new HBox();
		top.setSpacing(10);
		top.setPadding(new Insets(10,10,10,10));
		
		Button create = new Button("Neu");
		Button modify = new Button("Umbenennen");
		Button delete = new Button("Loeschen");
		
		modify.setDisable(true);
		delete.setDisable(true);
		
		top.getChildren().addAll(create, modify, delete);
		
		bp.setTop(top);
		
		// End of TOP
		
		// Start of LEFT
		
		TableColumn<Kategorie, String> katNameColumn = new TableColumn<>("Kategorie");
		//vornameColumn.setMinWidth(100);
		katNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Kategorie, Integer> katIDColumn = new TableColumn<>("ID");
		//nachnameColumn.setMinWidth(100);
		katIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

		table = new TableView<>();
		try {
			table.setItems(getKategorien());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		table.getColumns().addAll(katNameColumn, katIDColumn);

		table.setPrefWidth(150);
		bp.setLeft(table);
		
		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		TextField tfKatName = new TextField();
		GridPane.setConstraints(tfKatName, 1, 0);
		
		Label katName = new Label("Name: ");
		GridPane.setConstraints(katName, 0, 0);
		
		grid.getChildren().addAll(tfKatName,katName);
		bp.setCenter(grid);
		
		// End of Center
		
		//Events:
		
		create.setOnMouseClicked(e -> {
			GUICreateKategorie.display();
			try {
				table.setItems(getKategorien());
			} catch (SQLException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
			finally {
				modify.setDisable(true);
				delete.setDisable(true);
			}
		});
		
		modify.setOnMouseClicked(e -> {
			Kategorie tempKategorie = table.getSelectionModel().getSelectedItem();
			try {
				if(Validation.StringInputValidation(tfKatName)) {
					Bauteileverwaltung.getInstance().renameKategorie(tempKategorie.getID(), tfKatName.getText());
					table.setItems(getKategorien());
				}
			} catch (SQLException | DatabaseException e1) {
			AlertBox.display("Fehler", e1.getMessage());
			} finally {
				table.getSelectionModel().select(tempKategorie);
			}
			
		});
		
		delete.setOnMouseClicked(e-> {
			Kategorie tempKategorie = table.getSelectionModel().getSelectedItem();
			try {
				Bauteileverwaltung.getInstance().deleteKategorie(tempKategorie.getID());
				table.setItems(getKategorien());
				tfKatName.clear();
				modify.setDisable(true);
				delete.setDisable(true);
			} catch (SQLException | DatabaseException e1) {
				AlertBox.display("Fehler", e1.getMessage());
			}
		});
		
		table.setOnMouseClicked(e -> {
			delete.setDisable(false);
			modify.setDisable(false);
			if(!(table.getSelectionModel().isEmpty())) {
				Kategorie tempKategorie = table.getSelectionModel().getSelectedItem();
				bp.setCenter(grid);
				tfKatName.setText(tempKategorie.getName());
			}
		});

		tab.setContent(bp);
	}

	/**
	 * @return
	 * @throws SQLException 
	 */
	private ObservableList<Kategorie> getKategorien() throws SQLException {
		ObservableList<Kategorie> result = FXCollections.observableArrayList();
		for (Kategorie k :  Bauteileverwaltung.getInstance().getAllKategorie()) {
			result.add(k);
		}
		return result;
	}
	
}