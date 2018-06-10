/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class DeleteKasseException extends DatabaseException {

	public DeleteKasseException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die Kasse kann nicht geloescht werden, da ihr noch Toepfe zugewiesen sind.";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}