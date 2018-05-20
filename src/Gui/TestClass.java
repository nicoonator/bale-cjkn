package Gui;

import java.sql.SQLException;

import DataAccess.SQLManager;

public class TestClass {

	public static void main(String[] args) {
		try {
			SQLManager.getInstance().insertPersonIntoDB("Nico", "Rychlik", "Oststrasse", 24, 57234, "", "","", 0);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
