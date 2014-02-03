package com.pravat.meditrax.bi.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminDaoImpl extends AbstractDaoBase implements SuperAdminDao {

	
	@Override
	public int runUpdateStmt(String sql) {
		return template.update(sql);
	}

	@Override
	public QueryResult runQuery(String sql) {

		return template.query(sql, new ResultSetExtractor<QueryResult>() {

			@Override
			public QueryResult extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				QueryResult qr = new QueryResult();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				List<String> columnNames = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					columnNames.add(columnName);
				}
				qr.setColumnNames(columnNames);
				while(rs.next()) {
					QueryRow addNewQueryRow = qr.addNewQueryRow();
					for (int i = 1; i <= columnCount; i++) {
						Object object = rs.getObject(i);
						addNewQueryRow.addColumn(object);
					}
				}
				return qr;
			}

		});
	}

	public class QueryResult {


		List<String> columnNames;
		List<QueryRow> values;
		public List<String> getColumnNames() {
			return columnNames;
		}
		public void setColumnNames(List<String> columnNames) {
			this.columnNames = columnNames;
		}
		public List<QueryRow> getValues() {
			return values;
		}
		public void setValues(List<QueryRow> values) {
			this.values = values;
		}

		public QueryRow addNewQueryRow() {
			if(null == values) values = new ArrayList<>();
			QueryRow queryRow = new QueryRow();
			values.add(queryRow);
			return queryRow;
		}

	}

	public class QueryRow {

		List<Object> columnValues;

		public List<Object> getColumnValues() {
			return columnValues;
		}

		public void setColumnValues(List<Object> columnValues) {
			this.columnValues = columnValues;
		}
		public void addColumn(Object column) {
			if(null == columnValues) columnValues = new ArrayList<>();
			columnValues.add(column);
		}

	}

}
