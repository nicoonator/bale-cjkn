/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class BauteilNichtImWarenkorbException extends Exception {

	public BauteilNichtImWarenkorbException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Das Bauteil ist nicht im Warenkorb des Benutzers!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}

