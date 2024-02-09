package com.avas.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Database {
	
	private static Database db = new Database();
	private Connection conn;	
	
//	the constructor is private to don't be used outside the class
	private Database() {
		
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public static Database getInstance() {
		return db;
	}
	
	public void connect(Properties props) throws SQLException {		
		String server = props.getProperty("server");
		String port = props.getProperty("port");
		String database = props.getProperty("database");
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		
		String dbUrl = String.format("jdbc:mysql://%s:%s/%s", server, port, database);
		
		conn = DriverManager.getConnection(dbUrl, user, password);
	}

	public void disconnect() throws SQLException {
		conn.close();
	}
}
