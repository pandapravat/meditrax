package com.pravat.meditrax.bi;

import com.pravat.meditrax.bi.domain.AppConfig;
import com.pravat.meditrax.bi.domain.StoreInfo;

public interface ApplicationService {
	
	String ADMIN_PWD = "ADMIN_PWD";
	String USR_PWD = "USR_PWD";
	String PREF_BG = "PREF_BG";
	String CLIENT_CONTACT = "CLIENT_CONTACT";
	String CLIENT_ADD_2 = "CLIENT_ADD_2";
	String CLIENT_ADD_1 = "CLIENT_ADD_1";
	String CLIENT_NM = "CLIENT_NM";
	String VERSION = "APP_VERSION";
	
	public void setUpApplication(AppConfig config);
	public void updateSettings(String newImageKey, String newAdminPass);
	void loadConfig();
	
	String getConfigParam(String key);
	String getSuperAdminPass();
	StoreInfo getStoreInfo();
}
