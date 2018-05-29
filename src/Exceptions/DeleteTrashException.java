/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class DeleteTrashException extends DatabaseException {

	public DeleteTrashException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Diese Kategorie kann erst gelöscht werden, wenn die verbliebenen Bauteile, die dieser Kategorie zugewiesen ist, anderen Kategorien zugewiesen werden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}