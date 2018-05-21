/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class BauteilAnzahlZuKleinException extends Exception {

	int gelagert;
	
	public BauteilAnzahlZuKleinException(int vorhanden) {
		gelagert=vorhanden;
	}
	
	@Override
	public String getMessage(){
		return "Es gibt nur noch "+Integer.toString(gelagert)+" Teile dieses Bauteils!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
