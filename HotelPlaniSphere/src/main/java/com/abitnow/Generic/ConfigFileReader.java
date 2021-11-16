package com.abitnow.Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	public static String readConfigData(String data) {
		String value=null;
		Properties prop = null;
		try {
			File file = new File("./Configs/Configuation.properties");
			FileInputStream fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
			} 
			catch (FileNotFoundException e) {
			e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			value = prop.getProperty(data);
			return value;
	}
}

//To read Data -
//String RestaurantName = ConfigFileReader.readConfigData("RestaurantName");