package com.example.seleniumdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class reusable {
	public static Properties prop;
	FileInputStream fis;

	public String getproperty(String key) throws IOException {
		prop = new Properties();
		fis = new FileInputStream("./src\\main\\java\\resources\\data.properties");
		prop.load(fis);

		String value = prop.getProperty(key);
		return value;
	}

}
