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
		return "Die zu löschende Person hat noch nicht abgewickelte Aufträge, für die Sie verantwortlich ist!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}