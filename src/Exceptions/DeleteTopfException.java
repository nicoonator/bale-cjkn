/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class DeleteTopfException extends DatabaseException {

	public DeleteTopfException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Der Topf kann nicht geloescht werden, da ihr noch Rechnungen zugewiesen sind.";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}