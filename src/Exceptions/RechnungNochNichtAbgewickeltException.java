/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class RechnungNochNichtAbgewickeltException extends DatabaseException {

	public RechnungNochNichtAbgewickeltException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die Rechnung muss erst den Status abgewickelt haben, um geloescht zu werden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
