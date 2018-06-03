/**
 * 
 */
package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Fertigungsverwaltung;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

public class GUIFertigungsverwaltung {
	
	Tab tab;
	TableView<Auftrag> auftragTable;
	public GUIFertigungsverwaltung(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
		
		BorderPane bp = new BorderPane();
		
		
		//Oben
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
		
		
		//Links
		TableColumn<Auftrag, Integer> titel = new TableColumn<>("Auftragstitel");
		//vornameColumn.setMinWidth(100);
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		
		TableColumn<Auftrag, String> art = new TableColumn<>("Auftragsart");
		//vornameColumn.setMinWidth(100);
		art.setCellValueFactory(new PropertyValueFactory<>("ART"));
		
		TableColumn<Auftrag, String> auftrag_id = new TableColumn<>("Auftragsid");
		//vornameColumn.setMinWidth(100);
		auftrag_id.setCellValueFactory(new PropertyValueFactory<>("AUFTRAG_ID"));
		
		TableColumn<Auftrag, String> auftraggeber = new TableColumn<>("Auftragsgeber");
		//vornameColumn.setMinWidth(100);
		auftraggeber.setCellValueFactory(new PropertyValueFactory<>("auftraggeber"));
		
		TableColumn<Auftrag, String> verwalter = new TableColumn<>("Verwalter");
		//vornameColumn.setMinWidth(100);
		verwalter.setCellValueFactory(new PropertyValueFactory<>("verwalter"));
		
		auftragTable = new TableView<>();
		try {
			auftragTable.setItems(getAuftraege());
		} catch (SQLException e2) {
			
		
			
			
			//Resultset Fehlerfenster
			
			e2.printStackTrace();
			AlertBox.display("Fehler", e2.getMessage() );
			
		
		
		
		
		
		
		
		}
		auftragTable.getColumns().addAll(titel, art, auftrag_id, auftraggeber, verwalter);
		
		auftragTable.setPrefWidth(620);
		bp.setLeft(auftragTable);
	
		
				
				// End of LEFT
		
		
		
		//Start of CENTER
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(10);
		grid.setHgap(40);
		
		
		//Dropdown Personen		
		Label l1 = new Label("Auftragsersteller");
		GridPane.setConstraints(l1, 0, 0);
		int counter1 = 0;
		ChoiceBox<String> choiceBoxPersonen = new ChoiceBox<>();
		try {
			List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
			try {
				while(personen.listIterator().hasNext()) {
					choiceBoxPersonen.getItems().addAll(personen.listIterator(counter1).next().getVorname() +" "+ personen.listIterator(counter1).next().getNachname()+ personen.listIterator(counter1).next().getPERSON_ID() );
					counter1++;
				}
			} catch (Exception e) {
				choiceBoxPersonen.setValue(personen.get(0).getVorname()+ " " +personen.get(0).getNachname() );
			}
			
		} catch (SQLException e) {			
		}
		GridPane.setConstraints(choiceBoxPersonen, 0, 1);
		
		//Dropdown Admins
		Label l2 = new Label("Admins");
		GridPane.setConstraints(l2, 2, 0);
		int counter2 = 0;
		ChoiceBox<String> choiceBoxAdmins = new ChoiceBox<>();
		try {
			List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
			try {
				while(admins.listIterator().hasNext()) {
					choiceBoxAdmins.getItems().addAll(admins.listIterator(counter2).next().getVorname()+ " " + admins.listIterator(counter2).next().getNachname() + admins.listIterator(counter2).next().getPERSON_ID());
				
					counter2++;
				}
			}catch(Exception e) {
				choiceBoxAdmins.setValue(admins.get(0).getVorname()+ " " +admins.get(0).getNachname() );
			}
		} catch (SQLException e) {
		} catch (DatabaseException e) {
		}
		choiceBoxAdmins.getItems().indexOf(choiceBoxAdmins.getValue());
		GridPane.setConstraints(choiceBoxAdmins, 2, 1);
		
		//Auftragstitel und Feld
		Label l3 = new Label("Auftragstitel");
		GridPane.setConstraints(l3 , 0 , 3);
		TextField auftragsTitel = new TextField();
		GridPane.setConstraints(auftragsTitel, 0, 4);
		
		//Art der Fertigung
		Label l4 = new Label("Fertigungsart");
		GridPane.setConstraints(l4 , 2 , 3);
		ChoiceBox<String> choiceBoxArt = new ChoiceBox<>();
		choiceBoxArt.getItems().addAll("Leiterplatte", "3D-Druck" , "Sonstiges");
		GridPane.setConstraints(choiceBoxArt, 2, 4);
		
		//Prognostizierte Kosten
		Label l5 = new Label("Prognostizierte Kosten");
		GridPane.setConstraints(l5 , 0 , 6);
		TextField prognoKosten = new TextField();
		GridPane.setConstraints(prognoKosten, 0, 7);
		
		//Reele Kosten
		Label l6 = new Label("Prognostizierte Kosten");
		GridPane.setConstraints(l6 , 2 , 6);
		TextField reeleKosten = new TextField();
		GridPane.setConstraints(reeleKosten, 2, 7);
		
		
		CheckBox angenommen = new CheckBox("Angenommen");
		// In den Neu Knopf einbauen, dass wenn der gedrueckt wird, das die Untere Zeile ausgefuehrt wird
		//angenommen.setSelected(true);
		//
		angenommen.setDisable(true);
		GridPane.setConstraints(angenommen, 0, 9);
		
		CheckBox gefertigt = new CheckBox("Gefertigt");
		gefertigt.setDisable(true);
		GridPane.setConstraints(gefertigt, 1, 9);
		
		CheckBox kalkuliert = new CheckBox("Kalkuliert");
		kalkuliert.setDisable(true);
		GridPane.setConstraints(kalkuliert, 0, 10);
		
		CheckBox abgeholt = new CheckBox("Abgeholt");
		abgeholt.setDisable(true);
		GridPane.setConstraints(abgeholt, 1, 10);
		
		CheckBox abgerechnet = new CheckBox("Abgerechnet");
		abgerechnet.setDisable(true);
		GridPane.setConstraints(abgerechnet, 0, 11);
		
		CheckBox warten = new CheckBox("Warten");
		warten.setDisable(true);
		GridPane.setConstraints(warten, 1, 11);
		
		CheckBox unterbrochen = new CheckBox("Unterbrochen");
		unterbrochen.setDisable(true);
		GridPane.setConstraints(unterbrochen, 0, 12);
		
		CheckBox defekt = new CheckBox("Defekt");
		defekt.setDisable(true);
		GridPane.setConstraints(defekt, 1, 12);
		
		//TODO Eventhandler
		Button btn = new Button("Auftrag Exportieren");
		GridPane.setConstraints(btn, 2, 15);
		
		create.setOnMouseClicked(e -> {
			GUIAuftragErstellen.display();
			
		});
		
		btn.setOnMouseClicked(e -> {
		});
		
		bp.setCenter(grid);
		grid.getChildren().addAll(l1,choiceBoxPersonen, l2, choiceBoxAdmins,l3, auftragsTitel, l4, choiceBoxArt, l5, prognoKosten , l6, reeleKosten, btn, angenommen,gefertigt, kalkuliert, abgeholt, abgerechnet, warten, unterbrochen, defekt);
		
		
		tab.setContent(bp);
	}
	
	public ObservableList<Auftrag> getAuftraege() throws SQLException{
		ObservableList<Auftrag> resultAuftrag = FXCollections.observableArrayList();
		try {
			for (Auftrag a :  Fertigungsverwaltung.getInstance().getAllAuftrag()) {
				resultAuftrag.add(a);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultAuftrag;
	}
}
