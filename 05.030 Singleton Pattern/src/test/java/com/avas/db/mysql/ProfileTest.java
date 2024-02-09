package com.avas.db.mysql;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ProfileTest {
	
	@Test
	public void testLoadDbConfig() {
		
		System.setProperty("env", "test");
		
		var props = Profile.getProperties("db");
		assertNotNull("Canno not load properties.", props);
		
		var dbName = props.getProperty("database");
		assertEquals("The database name is wrong", "peopletest", dbName);
	}

}
