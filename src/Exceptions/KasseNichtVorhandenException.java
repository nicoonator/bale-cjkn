package Exceptions;

/**
 * 
 * @author Joel Wolf
 *
 */

public class KasseNichtVorhandenException extends DatabaseException {

	public KasseNichtVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die Kasse ist nicht vorhanden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
