package Logic;

import java.util.Date;
import java.util.List;

public class Lehrstuhlperson extends Person {

	/**
	 * @param pERSON_ID
	 * @param vorname
	 * @param nachname
	 * @param strasse
	 * @param hausnr
	 * @param pLZ
	 * @param email
	 * @param zuerst_erstellt
	 * @param zuletzt_geaendert
	 * @param nutzername
	 * @param passwort
	 * @param bauteilschulden
	 * @param bauteilwarenkorb
	 * @param admin
	 */
	public Lehrstuhlperson(int pERSON_ID, String vorname, String nachname, String strasse, int hausnr, int pLZ,
			String email, Date zuerst_erstellt, Date zuletzt_geaendert, String nutzername, String passwort,
			double bauteilschulden, List<Bauteil> bauteilwarenkorb, boolean admin) {
		super(pERSON_ID, vorname, nachname, strasse, hausnr, pLZ, email, zuerst_erstellt, zuletzt_geaendert, nutzername,
				passwort, bauteilschulden, bauteilwarenkorb, admin);
		// TODO Auto-generated constructor stub
	}

	private final boolean admin = false;
}
