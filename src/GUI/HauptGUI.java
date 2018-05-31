package GUI;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HauptGUI extends Application {
	
	
	boolean admin;
	
	public HauptGUI (boolean admin) {
		super();
		this.admin=admin;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Elab-Verwaltungstool");
		primaryStage.setMaximized(true);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		TabPane tabPane = new TabPane();
	    String arr[] = {"Personenverwaltung", "Auftragsverwaltung", "Finanzverwaltung", "Bauteileverwaltung"};
	    
	    for (int i = 0; i < arr.length; i++) {
	    	//if(admin)
	    	Tab tab = new Tab(arr[i]);
	    	HBox hbox = new HBox();
	    	HBox hboxbtn = new HBox();
	    	tab.setClosable(false);
	    	hbox.getChildren().add(new Label("Tab" + i));
	      
	    	Button btn = new Button("Bestaetigen");

	    	btn.setOnAction(e -> {
	    		System.out.println("test");
	    	});
	    		
	    	hboxbtn.getChildren().add(btn);
	    	hboxbtn.setAlignment(Pos.BOTTOM_CENTER);
	    	hbox.setAlignment(Pos.CENTER);
	    	
	    	tab.setContent(hboxbtn);
	    	tab.setContent(hbox);
	    	
	    	tabPane.getTabs().add(tab);  
	    }
   	    
	    //tabPane.setTabClosingPolicy oder tab.setClosable(false);   
	    //tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	
		Scene scene = new Scene (grid, 1000, 500);
	    
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(tabPane);
		borderPane.setCenter(grid);
		
		//grid.setTop(tabPane);
		//grid.add

	    
	    primaryStage.setScene(scene);
	    primaryStage.show();
	  }

	
	
	}
