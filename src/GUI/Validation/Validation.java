/**
 * 
 */
package GUI.Validation;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import GUI.AlertBox;
import Logic.Auftrag;
import Logic.Kasse;
import Logic.Kategorie;
import Logic.LogIn;
import Logic.Person;
import Logic.Topf;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Nico Rychlik
 *
 */
public class Validation {
	public static boolean mailInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	           Pattern p = Pattern.compile(ePattern);
	           Matcher m = p.matcher(tf.getText());
	           boolean match = m.matches();
			
	           if(match) result=true;
	           else AlertBox.display("Fehler", "E-Mail Adresse muss in der form 'abc@xyz.pq' sein!");
			}
		
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");
		
		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	

	public static boolean HausNrInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			if (tf.getText().trim().matches("[0-9].*")) {
				result = true;
			}
			else AlertBox.display("Fehler", "Hausnummer muss mit einer Zahl beginnen!");
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	
	
	public static boolean StringInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			/*if (!(Pattern.compile( "[0-9]" ).matcher( tf.getText() ).find()))*/ result = true;
			//else AlertBox.display("Fehler", "Regulaere Textfelder duerfen keine Zahlen enthalten!");	
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	
	
	public static boolean nutzernameInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			result = true;
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	
	public static boolean IntegerInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			try {
				Integer.parseInt(tf.getText());
				result = true;
			} catch (NumberFormatException e) {
				AlertBox.display("Fehler", "Es wurde eine Zahl erwartet!");
			}
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	
	
	
	public static boolean DoubleInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			try {
				Double.parseDouble(tf.getText());
				result = true;
			} catch (NumberFormatException e) {
				AlertBox.display("Fehler", "Es wurde eine Integer-Zahl erwartet!");
			}
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	

		
	
	
	public static boolean passwordInputValidation(PasswordField passwortInput, PasswordField passwortconfirmInput) {
	    	boolean result=false;
	    	if(passwortInput.getText().equals(passwortconfirmInput.getText())){
				if (!(passwortInput.getText()==null || passwortInput.getText().trim().isEmpty())) result = true;
				else AlertBox.display("Fehler", "Das Passwort muss angegeben werden!");
			}
			else AlertBox.display("Fehler", "Die Passwoerter stimmen nicht ueberein!");
			
	    	return result;
	    }
	
	public static boolean changePasswordInputValidation(PasswordField oldPassword, PasswordField passwortInput, PasswordField passwortconfirmInput, Person nutzer) {
    	boolean result=false;
    	try {
				if(LogIn.getInstance().login(nutzer.getNutzername(), oldPassword.getText().trim())){
					if(passwordInputValidation(passwortInput, passwortconfirmInput)) result=true;
				}else {
					AlertBox.display("Fehler", "Das alte Passwort und die Eingabe stimmen nicht ueberein!");
				}
				
		} catch (SQLException e) {
			AlertBox.display("Fehler", e.getMessage());
		}
		
    	return result;
    }
	
	
	
	public static boolean ComboBoxValidationString(ComboBox<String> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Ein Fertigungstyp muss ausgewahlt werden!");
		
		return result;
	}
	
	public static boolean ComboBoxValidationPerson(ComboBox<Person> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Eine Person muss ausgewahlt werden!");
		
		return result;
	}
	
	public static boolean ComboBoxValidationKategorie(ComboBox<Kategorie> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Eine Kategorie muss ausgewahlt werden!");
		
		return result;
	}
	
	public static boolean ComboBoxValidationKasse(ComboBox<String> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Ein Kassentyp muss ausgewahlt werden!");
		
		return result;
	}


	/**
	 * @param kostenstellennummerInput
	 * @return
	 */
	public static boolean KostenstellenInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			try {
				Long ksnummer=Long.parseLong(tf.getText());
				if ((ksnummer>=100000L && ksnummer<1000000L)||(ksnummer>=1000000000000000L&&ksnummer<10000000000000000L)) {
					result=true;
				} else AlertBox.display("Fehler", "Die Kostenstellennummer ist 6- oder 16- Stellig!");
			} catch (NumberFormatException e) {
				AlertBox.display("Fehler", "Es wurde eine Zahl erwartet!");
			}
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}


	/**
	 * @param comboBoxKassen
	 * @return
	 */
	public static boolean ComboBoxValidationKassenauswahl(ComboBox<Kasse> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Eine Kasse muss ausgewahlt werden!");
		
		return result;
	}


	/**
	 * @param comboBoxToepfe
	 * @return
	 */
	public static boolean ComboBoxValidationTopf(ComboBox<Topf> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Ein Topf muss ausgewahlt werden!");
		
		return result;
	}


	/**
	 * @param comboBoxAuftrag
	 * @return
	 */
	public static boolean ComboBoxValidationAuftrag(ComboBox<Auftrag> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Ein Auftrag muss ausgewahlt werden!");
		
		return result;
	}


	/**
	 * @param telefonInput
	 * @return
	 */
	public static boolean LongInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			try {
				Long.parseLong(tf.getText());
				result = true;
			} catch (NumberFormatException e) {
				AlertBox.display("Fehler", "Es wurde eine Zahl erwartet!");
			}
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");

		if (!result) tf.setStyle("-fx-text-fill: red;");
		else tf.setStyle(null);
		
		return result;
	}
	
	
}

