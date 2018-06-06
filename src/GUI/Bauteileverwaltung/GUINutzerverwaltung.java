/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import GUI.AlertBox;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import Logic.Bauteileverwaltung;
import Logic.Person;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class GUINutzerverwaltung {
	Tab tab;
	Person nutzer;
	TableView<Person> table;
	Person tempPerson;
	
	public GUINutzerverwaltung(Tab tab, Person nutzer) {
		this.tab=tab;
		this.nutzer=nutzer;
	}
	
	public void open() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,10,10,10));
		
		// Start of TOP
		
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

		TableColumn<Person, Date> geaendertColumn = new TableColumn<>("Zuletzt geaendert");
		//erstelltColumn.setMinWidth(150);
		geaendertColumn.setCellValueFactory(new PropertyValueFactory<>("zuletzt_geaendert"));

		TableColumn<Person, Boolean> rolleColumn = new TableColumn<>("Admin?");
		//rolleColumn.setMinWidth(50);
		rolleColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

		table = new TableView<>();

		table.setItems(this.getPersonenOhneNutzer());

		table.getColumns().addAll(vornameColumn, nachnameColumn, erstelltColumn, geaendertColumn, rolleColumn);

		table.setPrefWidth(620);
		bp.setLeft(table);
	
		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();
		
		Label schulden = new Label();
		GridPane.setConstraints(schulden, 0, 0);
		grid.getChildren().add(schulden);
		
		Button btn = new Button("Zuruecksetzen");
		btn.setDisable(true);
		GridPane.setConstraints(btn, 0, 1);
		grid.getChildren().add(btn);
		
		grid.setAlignment(Pos.BASELINE_CENTER);
		bp.setCenter(grid);
		
		// End of Center
		
		// Right
		
		// Warenkorb
		
		// End Right
		
		//Events:
		
		btn.setOnMouseClicked(e -> {
			try {
				Bauteileverwaltung.getInstance().ClearBauteilwarenkorb(tempPerson.getPERSON_ID());
				table.setItems(getPersonenOhneNutzer());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				table.getSelectionModel().select(tempPerson);
			}
		});
		
		table.setOnMouseClicked(e -> {
			if(!(table.getSelectionModel().isEmpty())) {
				tempPerson = table.getSelectionModel().getSelectedItem();
				schulden.setText("Aktuelle Schulden in Euro: "+String.valueOf(tempPerson.getBauteilschulden()));
				btn.setDisable(false);
			}
		});
		tab.setContent(bp);
	}

	
	 	private ObservableList<Person> getPersonenOhneNutzer() {
		ObservableList<Person> result=null;
		try {
			result = GUIPersonenverwaltung.getPersonen();
			ObservableList<Person> temp=GUIPersonenverwaltung.getPersonen();
			for(Person p: result){
				if(p.getPERSON_ID()==nutzer.getPERSON_ID())temp.add(p);
			}
			for(Person p: temp){
				result.remove(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("HUHU");
			e.printStackTrace();
		}
		return result;
	}
	
}