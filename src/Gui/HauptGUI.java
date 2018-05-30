package Gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
//import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HauptGUI extends Application{
	
	boolean admin;
	
	public HauptGUI (boolean admin) {
		super();
		this.admin=admin;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Tabs");
		Group root = new Group();
		Scene scene = new Scene (root, 1000, 500);
		TabPane tabPane = new TabPane();
	    BorderPane borderPane = new BorderPane();
	    String arr[] = {"Personenverwaltung", "Auftragsverwaltung", "Finanzverwaltung", "Bauteileverwaltung"};
	    
	    for (int i = 0; i < 4; i++) {
	    	//if(admin)
	    	Tab tab = new Tab(arr[i]);
	    	HBox hbox = new HBox();
	    	HBox hboxbtn = new HBox();
	    	tab.setClosable(false);
	    	hbox.getChildren().add(new Label("Tab" + i));
	      
	    	Button btn = new Button("Bestaetigen");
	    	hboxbtn.getChildren().add(btn);
	    	hboxbtn.setAlignment(Pos.BOTTOM_CENTER);
	    	hbox.setAlignment(Pos.CENTER);
	    	
	    	//tab.setContent laesst nur immer eine Sache zu, ueberschreibt die andere?
	    	tab.setContent(hboxbtn);
	    	tab.setContent(hbox);
	    	
	    	
	     
	      
	    	tabPane.getTabs().add(tab);
	      
	    }
	    
	   	    
	    //tabPane.setTabClosingPolicy oder tab.setClosable(false); 
	    
	    //tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	    
	    
	    borderPane.prefHeightProperty().bind(scene.heightProperty());
	    borderPane.prefWidthProperty().bind(scene.widthProperty());

	    borderPane.setCenter(tabPane);
	    root.getChildren().add(borderPane);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	  }
	}