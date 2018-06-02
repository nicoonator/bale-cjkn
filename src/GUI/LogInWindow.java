package GUI;
import java.sql.SQLException;

import Exceptions.DatabaseException;
import Exceptions.LoginException;
import Logic.LogIn;
import Logic.Personenverwaltung;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogInWindow extends Application {
	public static void main(String[] args) {
		launch(args);
		}
	
	@Override
    public void start(Stage primaryStage) throws Exception {
	
		//Fensterueberschrift
		primaryStage.setTitle("Willkommen!");
        
		//Einteilung des Fensters
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setPadding(new Insets (25, 25, 25, 25));
       
        
        //Ueberschrift
        Text scenetitle = new Text("Einloggen");
        scenetitle.setFont(Font.font("Comic Sans", FontWeight.EXTRA_BOLD, 25));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //Erstellung der Benutzernamen Beschriftung und Textfeld
        Label nutzerName = new Label("Benutzername:");
        nutzerName.setFont(Font.font("Comic Sans", FontWeight.EXTRA_BOLD, 15));
        grid.add(nutzerName, 0, 1);
        TextField nutzerTextFeld = new TextField();
        grid.add(nutzerTextFeld, 1, 1);    
        
        //Erstellung der Passwort Beschriftung und Textfeld
        Label passwort = new Label("Passwort:");
        passwort.setFont(Font.font("Comic Sans", FontWeight.EXTRA_BOLD, 15));
        grid.add(passwort, 0, 2);
        PasswordField passTextFeld = new PasswordField();
        grid.add(passTextFeld, 1, 2);
        
        
        Button btn = new Button("Bestaetigen");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        //Einlog Button
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
     
        //Eventhandler des Einlog-Knopfs
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent e) {
        		
        		
        		String nutzer=nutzerTextFeld.getText();
        		String pw=passTextFeld.getText();
        		//Test
        		//System.out.println(nutzerTextFeld.getText() + "  " + passTextFeld.getText());
        		
        		
        		//TODO: Exceptions -> NotaValidString fuer Benutzername
        		
        		
        		//Moeglichkeit dem Nutzer anzuzeigen, dass entweder Nutzername oder 
        		//Passwort falsch ist.
       
        		
        		//Bei Betaetigen des Knopfes oeffnet sich die Tab Instanz
        		
        			try {
						if(LogIn.getInstance().login(nutzer, pw)) new MainGUI(Personenverwaltung.getInstance().checkadmin(nutzer)).start(primaryStage);
						else actiontarget.setText(new LoginException().getMessage());;//TODO LoginException.getMessage nutzen
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						actiontarget.setText(e1.getMessage()); // <-- Diesen String nutzen
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						actiontarget.setText(e2.getMessage());
					} 
				
        		
            }
        });
        
        
        //Zusammenfuehren zum Fenster
        Scene scene = new Scene (grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Bypass:
        new MainGUI(true).start(primaryStage);
    }
    
}