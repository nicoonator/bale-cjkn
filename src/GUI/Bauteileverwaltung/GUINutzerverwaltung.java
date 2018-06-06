/**
 * 
 */
package GUI.Bauteileverwaltung;

import Logic.Person;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
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
	
	public GUINutzerverwaltung(Tab tab, Person nutzer) {
		this.tab=tab;
		this.nutzer=nutzer;
	}
	
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
		
		// Nutzer
	
		// End of LEFT
		
		// Start of Center
		
		GridPane grid = new GridPane();

		bp.setCenter(grid);
		
		// End of Center
		
		// Right
		
		// Warenkorb
		
		// End Right
		
		//Events:
		

		tab.setContent(bp);
	}
	
}