/**
 * 
 */
package GUI.Fertigungsverwaltung;

import GUI.Personenverwaltung.GUIPersonenverwaltung;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIFertigungsverwaltung {
	
	Tab tab;

	public GUIFertigungsverwaltung(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
		
		
		TabPane tp =new TabPane();
		
		Tab tb1 = new Tab("Auftrag Erstellen");			
		Tab tb2 = new Tab("Auftrag Bearbeiten");
		Tab tb3 = new Tab("Auftrag Loeschen");
		
		new GUIAuftragErstellen(tb1).open();
		new GUIAuftragErstellen(tb1).open();
		new GUIAuftragErstellen(tb1).open();
		
		
		tp.getTabs().addAll(tb1, tb2, tb3 );
		tp.setSide(Side.TOP);
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		VBox v1=new VBox();
		v1.setSpacing(10);
		v1.getChildren().addAll(tp);
		tab.setContent(v1);
	}
	
}
