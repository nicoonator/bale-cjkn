/**
 * 
 */
package GUI.Bauteileverwaltung;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIBauteileverwaltung {
	
	Tab tab;

	public GUIBauteileverwaltung(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
		
		//content here
		
		Label l1 = new Label("Hier koennte ihre Werbung stehen");
		
		VBox v1=new VBox();
		v1.setSpacing(10);
		
		v1.getChildren().addAll(l1);
		
		tab.setContent(v1);
	}
	
}
