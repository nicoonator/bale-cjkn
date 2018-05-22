/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class KategorieBereitsVorhandenException extends DatabaseException {

	public KategorieBereitsVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Eine Kategorie mit diesem Namen existiert bereits!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}


