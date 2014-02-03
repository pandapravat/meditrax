package com.pravat.meditrax.bi.domain;

public class AppConfig {
	
	StoreInfo info;
	String password;
	String adminPassWord;
	boolean createDBMode;
	String version;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public StoreInfo getInfo() {
		return info;
	}
	public void setInfo(StoreInfo info) {
		this.info = info;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isCreateDBMode() {
		return createDBMode;
	}
	public void setCreateDBMode(boolean createDBMode) {
		this.createDBMode = createDBMode;
	}
	
	public StoreInfo addNewStoreInfo() {
		info = new StoreInfo();
		return info;
	}
	public String getAdminPassWord() {
		return adminPassWord;
	}
	public void setAdminPassWord(String adminPassWord) {
		this.adminPassWord = adminPassWord;
	}

}
