/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.Date;

import GUI.AlertBox;
import Logic.Bauteil;
import Logic.Bauteileverwaltung;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	public GUIWarenkorb(Tab tab, Person nutzer) {
		this.tab=tab;
		this.nutzer=nutzer;
	}
	
	@SuppressWarnings("unchecked")
	public void open() {
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
			bauteillager.setItems(getBauteile());
		} catch (SQLException e2) {
			AlertBox.display("Fehler", e2.getMessage());
		}
		
		bauteillager.getColumns().addAll(kategorieColumn, nameColumn, linkColumn, preisColumn, gelagertColumn, ortColumn);
		
		bauteillager.setPrefWidth(400);
		bp.setLeft(bauteillager);
		
		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();

		bp.setCenter(grid);
		
		// End of Center
		
		//TODO: CENTER: Content, labels checkboxes etc.
		
		//Events:
		

		tab.setContent(bp);
	}
	
	public ObservableList<Bauteil> getBauteile() throws SQLException{
		ObservableList<Bauteil> result = FXCollections.observableArrayList();
		for (Bauteil b :  Bauteileverwaltung.getInstance().getAllBauteile()) {
			result.add(b);
		}
		return result;
	}
	
}