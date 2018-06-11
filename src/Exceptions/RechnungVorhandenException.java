package Exceptions;

public class RechnungVorhandenException extends DatabaseException {

	public RechnungVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Eine Rechnung zu diesem Auftrag wurde bereits erstellt.";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}