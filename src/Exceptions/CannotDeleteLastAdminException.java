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
		return "Die zu l�schende Person hat noch Bauteilschulden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}