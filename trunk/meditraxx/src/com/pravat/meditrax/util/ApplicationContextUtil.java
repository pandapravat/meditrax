package com.pravat.meditrax.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtil {

	private static ClassPathXmlApplicationContext context;
	public static void initSpring() {
		context = new ClassPathXmlApplicationContext("config/context-app.xml");
		//		context.scan("com.pravat.meditrax.core");

		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
		//		context.getBeanDefinitionNames();
	}

	public static <T> T getInstance(Class<T> classz) {
		if(null == context) initSpring();
		return context.getBean(classz);
	}

	public static String getPropValue(String key) {
		if(null == context) initSpring();
		String message = context.getMessage(key, null, null, Locale.US);
		return message;
	}

	public static void destroyContext() {
		// shut down the database first before shutting down derby when application context is destroyed
		// this is very inportant
		try {
			AppContextPropertyLookUp lookUp = context.getBean(AppContextPropertyLookUp.class);
			DriverManager.getConnection("jdbc:derby:" + lookUp.getDbPath() +";shutdown=true");
		} catch (SQLException e) {
			Log log = LogFactory.getLog(ApplicationContextUtil.class);
			log.info("Database derby stopped properly..");
		}
		if(null != context) {
			context.close();
		}
	}

}
