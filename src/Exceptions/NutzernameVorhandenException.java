package Exceptions;

public class NutzernameVorhandenException extends Exception {

	public NutzernameVorhandenException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Dieser Nutzername ist bereits vergeben!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
