package com.pravat.meditrax.bi.dao;

import java.util.Map;


public interface AppConfigDao {
	Map<String, String> readConfigs();
	void pingDb();
	void createObjects();
	void insertConfig(Map<String, String> configMap);
	void updateConfig(String key, String value);
	long getNextSeqVal(String seqName);
}
