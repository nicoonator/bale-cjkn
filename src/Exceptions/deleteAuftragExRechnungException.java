/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class deleteAuftragExRechnungException extends DatabaseException {

	public deleteAuftragExRechnungException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Dieser Auftrag kann nicht geloescht werden, da zu ihm bereits eine Rechnung erstellt wurde. Loeschen Sie die Rechnung zuerst!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
