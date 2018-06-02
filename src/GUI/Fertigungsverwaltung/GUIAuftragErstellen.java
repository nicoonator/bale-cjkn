package GUI.Fertigungsverwaltung;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import DataAccess.SQLManager;
import Exceptions.DatabaseException;
import Logic.Person;
import Logic.Personenverwaltung;


public class GUIAuftragErstellen {

	Tab tab;

	public GUIAuftragErstellen(Tab tab) {
		this.tab=tab;
	}
	
	public void open() {
				
		Label l1 = new Label("Admins");
		
		
		//Dropdown Menue erstellen und per Iterator befuellen.
		int counter1 = 0;
		ChoiceBox<String> choiceBoxPersonen = new ChoiceBox<>();
		try {
			List<Person> personen = Personenverwaltung.getInstance().getAllPersons();
			try {
				while(personen.listIterator().hasNext()) {
					choiceBoxPersonen.getItems().addAll(personen.listIterator(counter1).next().getVorname() +" "+ personen.listIterator(counter1).next().getNachname() );
					counter1++;
				}
			} catch (Exception e) {
				choiceBoxPersonen.setValue(personen.get(0).getVorname()+ " " +personen.get(0).getNachname() );
			}
			
		} catch (SQLException e) {			
		}
		Label l2 = new Label("Personen");
		int counter2 = 0;
		ChoiceBox<String> choiceBoxAdmins = new ChoiceBox<>();
		try {
			List<Person> admins = Personenverwaltung.getInstance().getAllAdmins();
			try {
				while(admins.listIterator().hasNext()) {
					choiceBoxAdmins.getItems().addAll(admins.listIterator(counter2).next().getVorname()+ " " + admins.listIterator(counter2).next().getNachname());
					counter2++;
				}
			}catch(Exception e) {
				choiceBoxAdmins.setValue(admins.get(0).getVorname()+ " " +admins.get(0).getNachname() );
			}
		} catch (SQLException e) {
		} catch (DatabaseException e) {
		}
		
		
		
		VBox v1=new VBox();
		v1.setSpacing(10);
		
		v1.getChildren().addAll(l1,choiceBoxAdmins,l2, choiceBoxPersonen);
		
		tab.setContent(v1);
	}

	private void getChoice(ChoiceBox<String> choiceBox) {
		 System.out.println(choiceBox.getValue());
	}
}
