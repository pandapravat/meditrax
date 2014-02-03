/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.util;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pandpr02
 */
public class Util {

//	static Rectangle2D rect = Screen.getPrimary().getVisualBounds();
	public static URL getAsResource(String name) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(name);
		return url;
	}

	public static InputStream getAsInputStream(String name) {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		return resourceAsStream;
	}

	
	/*public static double getAvailableWidth( int pct, DimType dimType) {
		double returnVal = 0;
		switch(dimType) {
		case HEIGHT:
			returnVal = (pct * rect.getHeight()) /100;
			break;
		case WIDTH:
			returnVal = (pct * rect.getWidth()) /100;
			break;
		default:
			returnVal = 0D;
		}
		return returnVal;
	}*/
	public static String getDateTimeString(Date date) {
		return sdf.format(date);
	}
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	static SimpleDateFormat sds = new SimpleDateFormat("dd/MM/yyyy");

	public static String getInMoneyFormatString(double retailPrice) {

		DecimalFormat numberInstance = new DecimalFormat("###.00");
		String format = numberInstance.format(retailPrice);
		return format;
	}

	public static double getInMoneyFormat(double amount) {

		return Double.valueOf(getInMoneyFormatString(amount));
	}
	public static String getStrDate(Date date) {
		return sds.format(date);
	}

	public static  String getErrorString(Set<String> errorList) {
		StringBuilder errors = new StringBuilder();
		int count = 1;
		for (String string : errorList) {
			errors.append(count ++).append(". ").append(string).append("\n");
		}
		return errors.toString();
	}

	public static String getBackGroundByKey(String key) {
		String value = (null != backMap.get(key)) ?  backMap.get(key) : DFLT_BG;
		String externalForm = getAsResource(value).toExternalForm();
		System.out.println(key + externalForm);
		return externalForm;
	}

	public static final String DFLT_BG= "images/abstract2.jpg";
	static Map<String, String> backMap;
	static {
		backMap = new HashMap<>();
		backMap.put("STYLE1", "images/abstract1.jpg");
		backMap.put("STYLE2", DFLT_BG);
		backMap.put("STYLE3", "images/abstract3.jpg");
		backMap.put("STYLE4", "images/abstract4.jpg");
		backMap.put("STYLE5", "images/abstract5.png");
	}
	public static Set<String> getAllStyleKeys() {
		return backMap.keySet();
	}
	
	public static void prepareExit() {
		ApplicationContextUtil.destroyContext();
	}
	
	public static String calculatePrice(double rawUnitPrice, int quantity, int discount) {
		double totalPrice = Util.getInMoneyFormat(((double)rawUnitPrice) * quantity);
		totalPrice = totalPrice - (totalPrice*discount)/100;
		
		return getInMoneyFormatString(totalPrice);
	}

}
