/**
 * 
 */
package Exceptions;

/**
 * @author Nico Rychlik
 *
 */
public class ZuWenigBauteileImWarenkorbException extends DatabaseException {

int gelagert;
	
	public ZuWenigBauteileImWarenkorbException(int vorhanden) {
		gelagert=vorhanden;
	}
	
	@Override
	public String getMessage(){
		return "Der Nutzer hat nur noch "+Integer.toString(gelagert)+" Teile dieses Bauteils, will aber mehr Teile zurückgeben!";
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
