package com.avas.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
	
	private static Database db = new Database();
	private static String dbUrl = "jdbc:mysql://localhost:3306/people";
	private Connection conn;
	
	
//	private static String user = "root";
//	private static String pass = "1234567890";
	
	
//	the constructor is private to don't be used outside the class
	private Database() {
		
	}
	
	public static Database getInstance() {
		return db;
	}
	
	public void connect() throws SQLException {
//		conn = DriverManager.getConnection(dbUrl, user, pass);
		conn = DriverManager.getConnection(dbUrl, "root", "1234567890");
	}

	public void disconnect() throws SQLException {
		conn.close();
	}
}
