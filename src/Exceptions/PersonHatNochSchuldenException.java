/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class PersonHatNochSchuldenException extends DatabaseException {

	public PersonHatNochSchuldenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die zu loeschende Person hat noch Bauteilschulden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}