/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author pandpr02
 */
public class PropertyLoader {

//	static Map<String, String> properties=null;
//	static ResourceBundle rb;

	/*public static void load() {
		AppConfigDao instance = ApplicationContextUtil.getInstance(AppConfigDao.class);
		properties = instance.readConfigs();
	}*/

	/*public static String getProperty(String name) {
		return getBundle().getString(name);
	}*/

	static ResourceBundle rb1;

	public static ResourceBundle getBundle() {

		if(null == rb1) {
			rb1 = ResourceBundle.getBundle(PROP_NAME, Locale.US);
		}
		return rb1;
	}

	/*static ResourceBundle rb1 = new ResourceBundle() {

		@Override
		protected Object handleGetObject(String key) {
			return properties.get(key);
		}


		@SuppressWarnings("unchecked")
		@Override
		public Enumeration<String> getKeys() {
			if(!CollectionUtils.isEmpty(properties)) {
				return new IteratorEnumeration(properties.keySet().iterator());
			}
			return null;
		}
	};*/
	public static final String PROP_NAME = "config/appConfig";
}
