package com.cm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author prashant kagwad
 */
public class ReadConfig {

	public static void main(String[] args) {

		ReadConfig r = new ReadConfig();
		r.readConfig();
		System.out.println(r.readProperty("path"));
	}

	@SuppressWarnings("rawtypes")
	private void readConfig() {

		try {

			File file = new File("resources\\config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readProperty(String key) {

		String value = "";
		try {

			File file = new File("resources\\config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			if (properties.containsKey(key)) {
				value = properties.getProperty(key);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}
}
