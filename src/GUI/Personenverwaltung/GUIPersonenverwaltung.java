/**
 * 
 */
package GUI.Personenverwaltung;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIPersonenverwaltung {
	
	Tab tab;

	public GUIPersonenverwaltung(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
		
		BorderPane bp = new BorderPane();
		
		
		
		//TODO: LEFT: Tableview (oder so)
		//TODO:	TOP: Buttons (Create, Modify, Delete)
		//TODO: CENTER: Conten, labels checkboxes etc.
		
		tab.setContent(bp);
	}
	
}
