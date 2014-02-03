package com.pravat.meditrax.bi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AppConfigDaoImpl extends AbstractDaoBase implements AppConfigDao {
	
	@PostConstruct
	public void initialize() {
		// change the default sequence pre allocator to 10. So that a lot of 
		// allocation does not happen when database is not shutdown properly
		// ps: The database shutdown code is present in ApplicationContextUtil class
		template.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(" +
			    "'derby.language.sequence.preallocator', '10')");
		
	}

	
	@Override
	public Map<String, String> readConfigs() {
		String string = queryMap.get("meditrax.getprops");

		Map<String, String> query = template.query(string, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				Map<String, String> hashMap = new HashMap<>();
				while(rs.next()) {
					String key = rs.getString("KEY_ITM");
					String val = rs.getString("VALUE_ITM");
					hashMap.put(key, val);
				}
				return hashMap;
			}

		});
		return query;
	}

	@Override
	public void pingDb() {
		template.queryForInt("select count(*) from APP_CONFIG");

	}

	@Override
	public void createObjects() {

		Collection<String> keySet = initQueryMap.values();
		for (String tableDDL : keySet) {
			log.info("Executing query table :" + tableDDL);
			template.execute(tableDDL);
		}

	}

	@Override
	public void insertConfig(final Map<String, String> configuration) {

		Set<String> keySet = configuration.keySet();
		final List<String> arrayList = new ArrayList<>(keySet);
		
		String string = queryMap.get("meditrax.insertinitprops");
		// remove all records and recreate
		template.update("delete from app_config where 1=1");
		template.batchUpdate(string, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement arg0, int idx) throws SQLException {
				String key = arrayList.get(idx);
				arg0.setString(1, key);
				String value = configuration.get(key);
				// replace place holders before inserting
				/*value = value.replaceAll(":storeName", config.getInfo().getStoreName());
				value = value.replaceAll(":storeAddress1", config.getInfo().getAddress1());
				value = value.replaceAll(":storeAddress2", config.getInfo().getAddress2());
				value = value.replaceAll(":phoneNumber", config.getInfo().getPhone());
				value = value.replaceAll(":adminPass", config.getPassword());*/
				arg0.setString(2, value);
			}

			@Override
			public int getBatchSize() {
				return arrayList.size();
			}
		});


	}

	@Override
	public void updateConfig(String key, String value) {
		String string = queryMap.get("meditrax.updateconfigprop");
		template.update(string, value, key);

	}

	@Override
	public long getNextSeqVal(String seqName) {
		String sql = queryMap.get("meditrax.getnxtsqval");
		String resolvedSql = sql.replace(":seqNm", seqName);
		return template.queryForLong(resolvedSql);
	}

}
