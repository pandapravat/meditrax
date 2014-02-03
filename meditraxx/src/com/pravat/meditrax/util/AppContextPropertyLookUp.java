package com.pravat.meditrax.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppContextPropertyLookUp {
	
	@Value("${com.pravat.meditrax.bi.db.localpath}")
	String dbPath;

	public String getDbPath() {
		return dbPath;
	}

}
