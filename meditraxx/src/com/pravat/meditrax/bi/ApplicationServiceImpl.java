package com.pravat.meditrax.bi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.bi.domain.AppConfig;
import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.util.Constants;

@Component
public class ApplicationServiceImpl implements ApplicationService{

	
	Map<String, String> configs;
	
	@Autowired
	AppConfigDao dao;
	
	@Override
	public void setUpApplication(AppConfig config) {
		dao.createObjects();

		Map<String, String> configMap = new HashMap<>();

		configMap.put(CLIENT_NM, config.getInfo().getStoreName());
		configMap.put(CLIENT_ADD_1, config.getInfo().getAddress1());
		configMap.put(CLIENT_ADD_2, config.getInfo().getAddress2());
		configMap.put(CLIENT_CONTACT, config.getInfo().getPhone());
		configMap.put(PREF_BG, "STYLE1");
		configMap.put(USR_PWD, config.getPassword());
		configMap.put(ADMIN_PWD, config.getAdminPassWord());
		configMap.put(VERSION, config.getVersion());
		dao.insertConfig(configMap);
	}

	StoreInfo storeInfo;
	@Override
	public StoreInfo getStoreInfo() {
		loadConfig();
		if(null == storeInfo) {
			storeInfo = new StoreInfo();
			storeInfo.setStoreName(configs.get(CLIENT_NM));
			storeInfo.setAddress1(configs.get(CLIENT_ADD_1));
			storeInfo.setAddress2(configs.get(CLIENT_ADD_2));
			storeInfo.setPhone(configs.get(CLIENT_CONTACT));
		}

		return storeInfo;
	}
	
	@Override
	public void loadConfig() {
		if(null == configs)
			configs = dao.readConfigs();
	}

	@Override
	public void updateSettings(String newImageKey, String newPass) {
		if(StringUtils.isNotBlank(newImageKey)) {
			dao.updateConfig(PREF_BG, newImageKey);
		}
		if(StringUtils.isNotBlank(newPass)) {
			dao.updateConfig(ADMIN_PWD, newPass);
		}
	}

	private String getProperty(String prop) {
		if(null == configs) {
			loadConfig();
		}
		return configs.get(prop);
	}

	public String getUserPass() {
		return getProperty(USR_PWD);
	}
	public String getStoreName() {
		return getProperty(CLIENT_NM);
	}

	public String getStoreAddress1() {
		return getProperty(CLIENT_ADD_1);
	}
	public String getStoreAddress2() {
		return getProperty(CLIENT_ADD_2);
	}
	public String getStorePnone() {
		return getProperty(CLIENT_CONTACT);
	}

	@Override
	public String getSuperAdminPass() {
		return Constants.MASTER_PASS;
	}

	public String getAdminPass() {
		return getProperty(ADMIN_PWD);
	}

	@Override
	public String getConfigParam(String key) {
		return getProperty(key);
	}
}
