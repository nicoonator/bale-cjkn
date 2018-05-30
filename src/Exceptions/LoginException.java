package Exceptions;

public class LoginException extends Exception {

	public LoginException() {
		
	}
	
	@Override
	public String getMessage(){
		return "Nutzername oder Passwort falsch!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}