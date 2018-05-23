/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class CannotDeleteLastAdminException extends DatabaseException {

	public CannotDeleteLastAdminException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Der letzte Admin kann nicht geloescht werden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}