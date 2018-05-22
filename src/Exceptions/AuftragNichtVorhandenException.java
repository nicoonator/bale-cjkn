/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class AuftragNichtVorhandenException extends DatabaseException {

	public AuftragNichtVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Der Auftrag ist nicht vorhanden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}