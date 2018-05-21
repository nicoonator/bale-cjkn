package Logic;

import java.util.Date;
import java.util.List;

public abstract class Person {

	private final int PERSON_ID;
	private String vorname;
	private String nachname;
	private String strasse;
	private int hausnr;
	private int PLZ;
	private String email;
	private final Date zuerst_erstellt;
	private Date zuletzt_geaendert;
	private String nutzername;
	private String passwort;
	private double bauteilschulden = 0;
	private List<Bauteil> bauteilwarenkorb;
	private final boolean admin;
}
