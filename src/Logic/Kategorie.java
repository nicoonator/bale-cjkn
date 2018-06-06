/**
 * 
 */
package Logic;


public class Kategorie {


	
	private final int ID;
	private String name;
	
	
	
	public Kategorie(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}

	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param iD
	 * @param name
	 */
	
	@Override
	public String toString() {
		return name;
	}

}
