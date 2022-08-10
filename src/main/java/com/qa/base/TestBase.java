package com.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	public Properties prop;
	public int Response_Code_200 =200;
	public int Response_Code_500 =500;
	public int Response_Code_400 =400;
	public int Response_Code_401 =401;
	public int Response_Code_201 =201;
	
	//public int Response_Code_200 =200;
	//public int Response_Code_200 =200;
	//public int Response_Code_200 =200;
	
	public TestBase()  {
		prop =new Properties();
		try {
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\config\\config.properties");
			prop.load(ip);
			
			System.out.println("prop.getProperty of URL = " + prop.getProperty("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
			e.getCause();
			e.printStackTrace();
		}	
	}
	
//	public static void main(String[] args) {
//		TestBase tb = new TestBase();
//		
//	}
	
}
