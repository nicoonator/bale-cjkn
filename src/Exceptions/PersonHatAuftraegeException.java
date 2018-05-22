/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class PersonHatAuftraegeException extends DatabaseException {

	public PersonHatAuftraegeException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die zu l�schende Person hat noch nicht abgewickelte Auftr�ge, f�r die Sie verantwortlich ist!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}