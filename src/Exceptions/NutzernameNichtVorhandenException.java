/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class NutzernameNichtVorhandenException extends DatabaseException {
	
	public NutzernameNichtVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Ein Nutzer mit diesem Namen existiert nicht!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}