package GUI;

import java.sql.SQLException;

import Exceptions.DatabaseException;
import GUI.Bauteileverwaltung.GUIBauteileverwaltung;
import GUI.Fertigungsverwaltung.GUIFertigungsverwaltung;
import GUI.Finanzverwaltung.GUIFinanzverwaltung;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import Logic.Personenverwaltung;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	boolean admin;
	int nutzerID;
	
	public MainGUI (boolean admin, int nutzerID) {
		super();
		this.admin=admin;
		this.nutzerID=nutzerID;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Elab-Verwaltungstool");
			
			TabPane tp =new TabPane();
			SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();
			
			Tab tb1 = new Tab("Personenverwaltung");			
			Tab tb2 = new Tab("Fertigungsverwaltung");			
			Tab tb3 = new Tab("Finanzverwaltung");			
			Tab tb4 = new Tab("Bauteileverwaltung");
			
			tp.getTabs().addAll(tb1, tb2, tb3, tb4);
			tp.prefWidthProperty().bind(primaryStage.widthProperty());
			
			new GUIPersonenverwaltung(tb1).open();
			new GUIFertigungsverwaltung(tb2).open();
			new GUIFinanzverwaltung(tb3).open();
			new GUIBauteileverwaltung(tb4, admin, Personenverwaltung.getInstance().getPersonByID(nutzerID)).open();
			
			tp.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
				if(newTab.equals(tb1)) new GUIPersonenverwaltung(tb1).open();
				if(newTab.equals(tb2)) new GUIFertigungsverwaltung(tb2).open();
				if(newTab.equals(tb3)) new GUIFinanzverwaltung(tb3).open();
				if(newTab.equals(tb4))
					try {
						new GUIBauteileverwaltung(tb4, admin, Personenverwaltung.getInstance().getPersonByID(nutzerID)).open();
					} catch (DatabaseException e) {
						AlertBox.display("Fehler", e.getMessage());
					} catch (SQLException e) {
						AlertBox.display("Fehler", e.getMessage());
					}
		    });
			
			if(!admin) {
				tb1.disableProperty().set(true);
				tb2.disableProperty().set(true);
				tb3.disableProperty().set(true);
				selectionModel.select(3);
			}
			
			VBox v1 =new VBox();
			v1.getChildren().addAll(tp);
			
				
			Scene scene = new Scene(v1,1300,600);
			
		    tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			
			primaryStage.setScene(scene);
			//primaryStage.setMaximized(true);
			//primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	
	
	}
