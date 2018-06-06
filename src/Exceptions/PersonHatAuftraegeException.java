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
		return "Die zu loeschende Person hat noch Auftraege, fuer die Sie verantwortlich ist!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}