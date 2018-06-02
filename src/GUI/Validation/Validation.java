/**
 * 
 */
package GUI.Validation;

import GUI.AlertBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Nico Rychlik
 *
 */
public class Validation {
	public static boolean mailInputValidation(TextField eMailInput) {
		boolean result=false;
		if (!(eMailInput.getText()==null || eMailInput.getText().trim().isEmpty())) {
			// TODO: 
			if (eMailInput.getText().trim().matches("[0-9].*")) {
				result = true;
			}
			else AlertBox.display("Fehler", "E-Mail Adresse muss in der form 'abd@xyz.pq' sein!");
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");
		return result;
	}
	

	public static boolean HausNrInputValidation(TextField hausnummerInput) {
		boolean result=false;
		if (!(hausnummerInput.getText()==null || hausnummerInput.getText().trim().isEmpty())) {
			if (hausnummerInput.getText().trim().matches("[0-9].*")) {
				result = true;
			}
			else AlertBox.display("Fehler", "Hausnummer muss mit einer Zahl beginnen!");
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");
		return result;
	}
	
	
	public static boolean StringInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			result = true;
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");
		return result;
	}
	
	public static boolean IntegerInputValidation(TextField tf) {
		boolean result=false;
		if (!(tf.getText()==null || tf.getText().trim().isEmpty())) {
			try {
				Integer.parseInt(tf.getText());
				result = true;
			} catch (NumberFormatException e) {
				AlertBox.display("Fehler", "PLZ darf nur Zahlen enthalten!");
			}
		}
		else AlertBox.display("Fehler", "Kein Textfeld darf leer sein!");
		return result;
	}
	
	public static boolean passwordInputValidation(PasswordField passwortInput, PasswordField passwortconfirmInput) {
	    	boolean result=false;
	    	if(passwortInput.getText().equals(passwortconfirmInput.getText())){
				if (!(passwortInput.getText()==null || passwortInput.getText().trim().isEmpty())) {
					result = true;
				}
				else {
					AlertBox.display("Fehler", "Das Passwort muss angegeben werden!");
				}
			}
			else {
				AlertBox.display("Fehler", "Die Passwoerter stimmen nicht ueberein!");
			}
	    	return result;
	    }
	
	public static boolean ComboBoxValidation(ComboBox<String> comboBox) {
		boolean result=false;
		if (!(comboBox.getSelectionModel().isEmpty())) {
			result=true;
		}
		else AlertBox.display("Fehler", "Ein Typ muss ausgewahlt werden!");
		return result;
	}
}

