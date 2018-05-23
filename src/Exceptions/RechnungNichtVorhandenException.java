package Exceptions;



public class RechnungNichtVorhandenException extends DatabaseException {
	
	public RechnungNichtVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Die Rechnung ist nicht vorhanden!";
	}
	
	@Override
	public String toString(){
		return getMessage();
}
}
