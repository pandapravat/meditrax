package com.pravat.meditrax.bi.dao;

import com.pravat.meditrax.bi.dao.SuperAdminDaoImpl.QueryResult;

public interface SuperAdminDao {
	
	int runUpdateStmt(String sql);
	QueryResult runQuery(String sql);

}
