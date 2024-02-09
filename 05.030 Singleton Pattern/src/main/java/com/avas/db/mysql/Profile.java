package com.avas.db.mysql;

import java.io.IOException;
import java.util.Properties;

public class Profile {

	public static Properties getProperties(String name) {
		String env = System.getProperty("env");
		if (env == null) {
			env = "dev";
		}

		Properties props = new Properties();
		String PropertiesFile = String.format("/config/%s.%s.properties", name, env);

		try {
			props.load(App.class.getResourceAsStream(PropertiesFile));
		} catch (IOException e) {
			throw new RuntimeException("Can not load propert ies file: " + PropertiesFile);
		}
		return props;
	}

}
