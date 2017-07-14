package com.cm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author prashant kagwad
 */
public class WriteConfig {

	public static void main(String[] args) {

		WriteConfig w = new WriteConfig();
		w.creatConfig();
	}

	public void creatConfig() {
		try {

			String dirPath = System.getProperty("user.dir") + "\\resources";
			File dir = new File(dirPath);
			dir.mkdir();

			Properties properties = new Properties();

			String path = System.getProperty("user.dir")
			+ "\\Record\\userrecords.txt";
			properties.setProperty("path", path );

			File file = new File(dirPath + "\\config.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Config File");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
