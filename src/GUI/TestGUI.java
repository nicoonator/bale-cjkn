package GUI;

import GUI.Auftragsverwaltung.GUIAuftragsverwaltung;
import GUI.Bauteileverwaltung.GUIBauteileverwaltung;
import GUI.Finanzverwaltung.GUIFinanzverwaltung;
import GUI.Personenverwaltung.GUIPersonenverwaltung;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestGUI extends Application {
	
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setTitle("Elab-Verwaltungstool");
			
			TabPane tp =new TabPane();
			
			Tab tb1 = new Tab("Personenverwaltung");			
			Tab tb2 = new Tab("Auftragsverwaltung");			
			Tab tb3 = new Tab("Fertigungsverwaltung");			
			Tab tb4 = new Tab("Bauteileverwaltung");
			
			tp.getTabs().addAll(tb1, tb2, tb3, tb4);
			tp.prefWidthProperty().bind(primaryStage.widthProperty());
			
			new GUIPersonenverwaltung(tb1).open();
			new GUIAuftragsverwaltung(tb2).open();
			new GUIFinanzverwaltung(tb3).open();
			new GUIBauteileverwaltung(tb4).open();
			
			VBox v1 =new VBox();
			v1.getChildren().addAll(tp);
			
				
			Scene scene = new Scene(v1,600,500);
			
		    tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	
	
	}
