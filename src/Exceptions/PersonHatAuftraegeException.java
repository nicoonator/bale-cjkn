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
		return "Die zu loeschende Person hat noch nicht abgewickelte Auftraege, fuer die Sie verantwortlich ist!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}