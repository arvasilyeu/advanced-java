package com.avas.db.mysql;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		var db = Database.getInstance(); 
		
		try {
			db.connect();
		} catch (SQLException e) {
			System.out.println("Can not connect to database!");
		}
		
		
		
		System.out.println("Connected!");
		
		
		
		try {
			db.disconnect();
		} catch (SQLException e) {
			System.out.println("Can not close database connection!");
		}
	}
}
