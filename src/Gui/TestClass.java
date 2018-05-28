package Gui;

import java.sql.SQLException;
import java.util.Date;

import DataAccess.SQLManager;
import Logic.Finanzverwaltung;
import Logic.Rechnung;

public class TestClass {

	public static void main(String[] args) {
		try {
		//	Date testdate =new Date();
		//	testdate.setTime(1526836286L*1000L);
		//	System.out.println(testdate.toString());
		//	SQLManager.getInstance().createPerson("Nico", "Rychlik", "Oststrasse", 24, 57234, "", "","", 0);
		//	SQLManager.getInstance().deletePerson(1);
		//	SQLManager.getInstance().addKategorie("Test");
		//	SQLManager.getInstance().createBauteil("Testbauteil", "link", 5.33, 10, 0, 0, "Regal", 1);
		//	SQLManager.getInstance().removeBauteil(2, 6, 8);
		//	SQLManager.getInstance().addBauteil(2, 5, 8);
		//	System.out.println(new Date().getTime()/1000);
		//	System.out.println(new Date(1527061626L*1000L).toString());
		//	SQLManager.getInstance().createAuftrag("test", "druck", 5, 10, null);
		//	SQLManager.getInstance().createRechnung("test", "bar", 5, 1, 8, 8, 2);
		//	Finanzverwaltung.getInstance().exportRechnung(1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
