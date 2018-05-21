/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class BauteilBereitsVorhandenException extends Exception {

	public BauteilBereitsVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Ein Bauteil mit diesem Namen existiert bereits!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
