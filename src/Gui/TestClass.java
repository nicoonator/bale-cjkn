package Gui;

import java.sql.SQLException;
import java.util.Date;

import DataAccess.SQLManager;

public class TestClass {

	public static void main(String[] args) {
		try {
			Date testdate =new Date();
			testdate.setTime(1526836286L*1000L);
		//	SQLManager.getInstance().createPerson("Nico", "Rychlik", "Oststrasse", 24, 57234, "", "","", 0);
			System.out.println(testdate.toString());
			SQLManager.getInstance().deletePerson(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
