/**
 * 
 */
package GUI.Bauteileverwaltung;

import java.sql.SQLException;
import java.util.Date;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Fertigungsverwaltung.GUIFertigungsverwaltung;
import GUI.Finanzverwaltung.GUIFinanzverwaltung;
import GUI.Personenverwaltung.GUICreatePerson;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import GUI.Validation.Validation;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Nico Rychlik
 *
 */

public class GUIBauteileverwaltung {
	
	Tab tab;
	boolean admin;
	Person nutzer;
	TableView<Person> table;
	
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
		
		if(!admin) {
			tb4.disableProperty().set(true);
			tb2.disableProperty().set(true);
			tb3.disableProperty().set(true);
			selectionModel.select(1);
		}
		
		VBox v1 =new VBox();
		v1.getChildren().addAll(tp);
		
		
	    tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tab.setContent(tp);
	}
}
