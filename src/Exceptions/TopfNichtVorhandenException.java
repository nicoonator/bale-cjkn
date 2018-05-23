package Exceptions;

/**
 * 
 * @author Joel Wolf
 *
 */

public class TopfNichtVorhandenException extends DatabaseException {

	public TopfNichtVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Der Topf ist nicht vorhanden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
