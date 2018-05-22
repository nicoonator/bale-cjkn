/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class DatabaseException extends Exception {

	DatabaseException(){
		
	}
	
	@Override
	public String getMessage(){
		return "";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}