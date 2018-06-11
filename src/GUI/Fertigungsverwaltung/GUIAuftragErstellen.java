package GUI.Fertigungsverwaltung;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import Exceptions.DatabaseException;
import GUI.AlertBox;
import GUI.Validation.Validation;
import Logic.Auftrag;
import Logic.Fertigungsverwaltung;
import Logic.Person;
import Logic.Personenverwaltung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIAuftragErstellen {
	 @SuppressWarnings("unchecked")
	public static void display() {
	        Stage window = new Stage();
	        window.setTitle("Auftrag Erstellen");
	        
	        BorderPane bp = new BorderPane();
			bp.setPadding(new Insets(10,10,10,10));
			
			
			//RIGHT
			
			TableView<Person> personTable;
			TableColumn<Person, String> vorname = new TableColumn<>("Vorname");
			vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
			vorname.setMinWidth(110);
	
			TableColumn<Person, String> nachname = new TableColumn<>("Nachname");
			nachname.setCellValueFactory(new PropertyValueFactory<>("nachname"));
			nachname.setMinWidth(110);
			
			personTable = new TableView<>();
			
			personTable.getColumns().addAll(vorname, nachname);
			personTable.setPrefSize(220, 5);
			personTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
			bp.setCenter(personTable);
			
			//END OF RIGHT
			
			
			
					
			
			
			
	        //LEFT
	        GridPane grid = new GridPane();
			grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(8);
			grid.setHgap(10); 
			
			
			//Von Oben nach Unten
			
			//0,0 | 1,0
			Label l1 = new Label("Auftragsersteller");
			GridPane.setConstraints(l1, 0, 0);
			ComboBox<Person> comboBoxPersonen = new ComboBox<>();
			comboBoxPersonen.setPromptText("Person auswaehlen");
			try {
				List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
				try {
						comboBoxPersonen.getItems().addAll(personen);
				} catch (Exception e) {
					comboBoxPersonen.setPromptText("Person auswaehlen");
				}
				
			} catch (SQLException e) {			
			}
			GridPane.setConstraints(comboBoxPersonen, 1, 0);
			
			
			
			//0,1 | 1,1
			Label l2 = new Label("Admins");
			GridPane.setConstraints(l2, 0, 1);
			ComboBox<Person> comboBoxAdmins = new ComboBox<>();
			comboBoxAdmins.setPromptText("Admin auswaehlen");
			comboBoxAdmins.setDisable(true);
			
			
			GridPane.setConstraints(comboBoxAdmins, 1, 1);
					
			
			//0,2|1,2
			Label l3 = new Label("Auftragstitel");
			GridPane.setConstraints(l3 , 0 , 2);
			TextField auftragsTitel = new TextField();
			GridPane.setConstraints(auftragsTitel, 1, 2);
			
			
			//0,3 |1,3
			Label l4 = new Label("Prognostizierte Kosten");
			GridPane.setConstraints(l4 , 0 , 3);
			TextField prognoKosten = new TextField();
			GridPane.setConstraints(prognoKosten, 1, 3);
			
			
			//0,4|1,4
			Label l5 = new Label("Reelle Kosten");
			GridPane.setConstraints(l5 , 0 , 4);
			TextField reelleKosten = new TextField();
			GridPane.setConstraints(reelleKosten, 1, 4);
			
			
			ComboBox<String> comboBoxFertigung = new ComboBox<>();
			comboBoxFertigung.getItems().addAll(
	                "Leiterplatte",
	                "3D-Druck",
	                "Sonstiges"
	        );
			comboBoxFertigung.setPromptText("Fertigungsart");
	        GridPane.setConstraints(comboBoxFertigung, 0, 9);
	        
			Button btn = new Button("Erstellen");
			GridPane.setConstraints(btn, 1, 9);
			
			Button btnClose = new Button ("Schliessen");
			GridPane.setConstraints(btnClose, 1, 9,1,1,HPos.RIGHT,null);
			
			//END OF LEFT
			
			grid.getChildren().addAll(l1, comboBoxPersonen, l2, comboBoxAdmins, l3, auftragsTitel, l4, prognoKosten, l5, reelleKosten , comboBoxFertigung, btn, btnClose  );
			
			
			
			//EVENTS
			
			//Combobox fuer Personen
			
			comboBoxPersonen.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
				if(oldValue != newValue){
					comboBoxAdmins.setDisable(false);
					comboBoxAdmins.getItems().clear();
					try {
						List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
						Iterator<Person> adminsIterator = admins.iterator();
						
						try {
							int counter = 0;
							if((comboBoxPersonen.getSelectionModel().getSelectedItem().isAdmin()==true))
			
								while(adminsIterator.hasNext()){
									if(comboBoxPersonen.getSelectionModel().getSelectedItem().getPERSON_ID()  == (admins.get(counter).getPERSON_ID())){
										adminsIterator.next();										
									}else {
										comboBoxAdmins.getItems().add(admins.get(counter));
									}
									counter++;
								}
							else {
								comboBoxAdmins.getItems().addAll(admins);
							}
						}catch(Exception e) {
							comboBoxAdmins.setPromptText("Admin auswaehlen");
						}
					} catch (SQLException e) {
						//TODO
					} catch (DatabaseException e) {
						//TODO
					}
				}
			});
			
			
			//Combobox fuer Admins abhaengig von der vorher ausgewaehlten Person
			comboBoxAdmins.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) ->{
				if(oldValue != newValue){
					personTable.getItems().clear();
					
					List<Person> verwalter;
					try {
						verwalter = Personenverwaltung.getInstance().getAllAdmins();
						Iterator<Person> verwalterIterator = verwalter.iterator();
						
						try {
							int counter1 = 0;
							while(verwalterIterator.hasNext()) {
								
								if(comboBoxPersonen.getSelectionModel().getSelectedItem().getPERSON_ID() == verwalter.get(counter1).getPERSON_ID() || comboBoxAdmins.getSelectionModel().getSelectedItem().getPERSON_ID() == verwalter.get(counter1).getPERSON_ID() ) {
									verwalterIterator.next();
								}
								else {
									personTable.getItems().add(verwalter.get(counter1));
								}
								counter1++;
							}
						}catch(Exception e) {
							
						}
						
						
					} catch (SQLException | DatabaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			});
			
			
			
			//Fenster schliessen
			btnClose.setOnMouseClicked(e -> {
				window.close();
			});
			
			//Auftrag erstellen
			btn.setOnMouseClicked(e -> {
				if(Validation.StringInputValidation(auftragsTitel)&& Validation.DoubleInputValidation(prognoKosten)&& Validation.DoubleInputValidation(reelleKosten)&& Validation.ComboBoxValidationPerson(comboBoxPersonen)&& Validation.ComboBoxValidationPerson(comboBoxAdmins)&& Validation.ComboBoxValidationString(comboBoxFertigung)) {
					Double progKost = Double.parseDouble(prognoKosten.getText());
					Double reelleKost = Double.parseDouble(reelleKosten.getText());
					try {						
						Fertigungsverwaltung.getInstance().createAuftrag(auftragsTitel.getText(), comboBoxFertigung.getValue(), progKost ,reelleKost, comboBoxPersonen.getValue().getPERSON_ID(), comboBoxAdmins.getValue().getPERSON_ID(), personTable.getSelectionModel().getSelectedItems());
						AlertBox.display("Erfolg!", "Auftrag erzeugt!");
						window.close();
						
					} catch (SQLException e1) {
						AlertBox.display("Fehler", e1.getMessage());
					}
					
				}
				
			});
			
			bp.setLeft(grid);
	        Scene scene = new Scene(bp);
	        window.setScene(scene);
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.showAndWait();
	        
	        
	        
	    }
	 
	 /**
	 * @return
	 * @throws SQLException
	 */
	public static ObservableList<Auftrag> getAuftraege() throws SQLException{
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
	 /**
	  * this method returns all admins 
	 * @return
	 * @throws SQLException
	 */
	public static ObservableList<Person> getAdmins() throws SQLException{
			ObservableList<Person> result = FXCollections.observableArrayList();
			try {
				for (Person p :  Personenverwaltung.getInstance().getAllAdmins()) {
					result.add(p);
				}
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	 
}
