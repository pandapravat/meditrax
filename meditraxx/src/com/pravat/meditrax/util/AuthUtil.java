package com.pravat.meditrax.util;

import org.apache.commons.lang.StringUtils;

import com.pravat.meditrax.bi.ApplicationService;

public class AuthUtil {
	
	static boolean isAdmin;
	static boolean isUser;
	static boolean isSuperAdmin;
	
	static ApplicationService service = ApplicationContextUtil.getInstance(ApplicationService.class);
	
	
	public static boolean authenticateAndSet(String password) {
		boolean auth = false;
		isAdmin = false;
		isUser = false;
		isSuperAdmin = false;
		System.out.println("Input password" + password);
		if(StringUtils.equals(password, service.getConfigParam(ApplicationService.ADMIN_PWD))) {
			isAdmin = true;
			isUser = true;
			auth = true;
		} else if(StringUtils.equals(password, service.getConfigParam(ApplicationService.USR_PWD))){
			isUser = true;
			auth = true;
		}
		else if(StringUtils.equals(password, service.getSuperAdminPass())){
			isSuperAdmin = true;
			isUser = true;
			isAdmin = true;
			auth = true;
		}
		return auth;
	}
	
	public static boolean isAdmin() {
		return isAdmin;
	}


	public static boolean isUser() {
		return isUser;
	}


	public static boolean isSuperAdmin() {
		return isSuperAdmin;
	}

}
