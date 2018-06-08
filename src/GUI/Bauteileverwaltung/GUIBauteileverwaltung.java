/**
 * 
 */
package GUI.Bauteileverwaltung;

import Logic.Person;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIBauteileverwaltung {
	
	Tab tab;
	boolean admin;
	Person nutzer;
	
	public GUIBauteileverwaltung(Tab tab, boolean admin, Person nutzer) {
		this.tab=tab;
		this.admin=admin;
		this.nutzer=nutzer;
	}
	
	public void open() {	
		TabPane tp =new TabPane();
		SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();
		
		Tab tb1 = new Tab("Bauteilwarenkorb");			
		Tab tb2 = new Tab("Kategorien");			
		Tab tb3 = new Tab("Bauteile");			
		Tab tb4 = new Tab("Nutzerverwaltung");
		
		tp.getTabs().addAll(tb1, tb2, tb3, tb4);
		
		new GUIWarenkorb(tb1, nutzer).open();
		new GUIKategorie(tb2).open();
		new GUIBauteile(tb3).open();
		new GUINutzerverwaltung(tb4, nutzer).open();
		
		tp.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(tb1)) new GUIWarenkorb(tb1, nutzer).open();
			if(newTab.equals(tb2)) new GUIKategorie(tb2).open();
			if(newTab.equals(tb3)) new GUIBauteile(tb3).open();
			if(newTab.equals(tb4)) new GUINutzerverwaltung(tb4, nutzer).open();
	    });
		
		if(!admin) {
			tb4.disableProperty().set(true);
			tb2.disableProperty().set(true);
			tb3.disableProperty().set(true);
			selectionModel.select(0);
		}
		
		VBox v1 =new VBox();
		v1.getChildren().addAll(tp);
		
		
	    tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tab.setContent(tp);
	}
}
