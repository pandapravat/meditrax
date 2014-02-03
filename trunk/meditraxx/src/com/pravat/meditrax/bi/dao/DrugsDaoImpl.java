/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.bi.domain.DrugReport;
import com.pravat.meditrax.bi.domain.DrugSearchCriteria;

/**
 *
 * @author pandpr02
 */
@Component
public class DrugsDaoImpl extends AbstractDaoBase implements DrugsDao{

	Log logger = LogFactory.getLog(getClass()); 

	@Override
	public boolean addDrug(Drug drug) {
		String sql = queryMap.get("meditrax.adddrug");
		int update = template.update(sql, drug.getDrugId(), drug.getName(), drug.getMfr(), drug.getType());

		/*String addDrugDtl = queryMap.get("meditrax.adddrugdtl");
		int dtlUpdate = template.update(addDrugDtl, drug.getDrugId(), drug.getPurchasePrice(), drug.getRetailPrice());*/
		return update == 1;
	}

	@Override
	public Drug getDrug(String drugId) {
		String sql = queryMap.get("meditrax.getdrug");

		Drug drug = template.queryForObject(sql, new DrugsRowMapper(), drugId);

		return drug;
	}

	@Override
	public List<DrugDetails> searchDrugDetails(String searchKey) {

		String sql = queryMap.get("meditrax.drugdtlsearch.byname");
		sql = sql.replace(":searchKey", searchKey);
		logger.info("Search key:" + searchKey + ", Final SQL:" + sql);
		Map<Drug, List<DrugBatch>> drugs = template.query(sql, new DrugsDetailsExtractor());

		List<DrugDetails> allDetails = new ArrayList<DrugDetails>();
		for (Entry<Drug, List<DrugBatch>> aDetails : drugs.entrySet()) {
			DrugDetails drugDetails = new DrugDetails();
			drugDetails.setTargetDrug(aDetails.getKey());
			drugDetails.setAllBatches(aDetails.getValue());
			allDetails.add(drugDetails);
		}

		return allDetails;
	}

	@Override
	public List<Drug> searchDrugs(String searchKey) {

		String sql = queryMap.get("meditrax.drugsearch.byname");
		sql = sql.replace(":searchKey", searchKey);
		logger.info("searchDrugs Search key:" + searchKey + ", Final SQL:" + sql);
		List<Drug> drugs = template.query(sql, new DrugsRowMapper());
		return drugs;
	}

	class DrugsDetailsExtractor implements ResultSetExtractor<Map<Drug, List<DrugBatch>>>{


		@Override
		public Map<Drug, List<DrugBatch>> extractData(ResultSet rs) throws SQLException,
		DataAccessException {

			Map<Drug, List<DrugBatch>> map = new HashMap<Drug, List<DrugBatch>>();
			while (rs.next()) {
				/*D.DRG_ID, D.DRG_NM, D.MFR, D.TYP, DP.BATCH_ID, DP.UNIT_PP, DP.UNIT_RP, DP.AVAIL_UNITS, DP.EXP_DT*/
				Drug drug = new Drug();
				long drgId = rs.getLong("DRG_ID");
				String drgName = rs.getString("DRG_NM");
				String drgMfr = rs.getString("MFR");
				String drgType = rs.getString("TYP");
				drug.setDrugId(drgId);
				drug.setName(drgName);
				drug.setMfr(drgMfr);
				drug.setType(drgType);
				List<DrugBatch> list = map.get(drug);
				if(null == list) {
					list = new ArrayList<DrugBatch>();
					map.put(drug, list);
				}

				// details
				list.add(getBatch(rs));
			}
			return map;
		}

	}

	class DrugsRowMapper implements RowMapper<Drug>{

		@Override
		public Drug mapRow(ResultSet rs, int i) throws SQLException {
			long drgId = rs.getLong(1);
			String drgName = rs.getString(2);
			String drgMfr = rs.getString(3);
			String drgType = rs.getString(4);

			Drug drug = new Drug();
			drug.setDrugId(drgId);
			drug.setName(drgName);
			drug.setMfr(drgMfr);
			drug.setType(drgType);

			return drug;
		}

	}

	@Override
	public boolean deleteDrug(long drugId) {

		String removeDrug = queryMap.get("meditrax.removedrug");
		logger.info("Delete drug. SQL:" + removeDrug + ", Params:" + drugId);
		int update = template.update(removeDrug, drugId);

		String removeDrugDtl = queryMap.get("meditrax.removedrugdtl");
		logger.info("Delete Drug Deatails. SQL:" + removeDrugDtl + ", Params:" + drugId);
		int update2 = template.update(removeDrugDtl, drugId);

		return update > 0 && update2 > 0;
	}

	@Override
	public boolean updateDrug(Drug drug) {

		String updateDrug = queryMap.get("meditrax.updatedrg");
		Object[] params = {drug.getName(), drug.getMfr(), drug.getType(), drug.getDrugId()};
		logger.info("Delete Drug Deatails. SQL:" + updateDrug + ", Params:" + Arrays.toString(params));

		int update = template.update(updateDrug, params);
		return update == 1;
	}

	@Override
	public List<DrugBatch> getDrugBatches(long drugId) {

		String getBatchDtlsSql = queryMap.get("meditrax.getbatchdtls");

		List<DrugBatch> query = template.query(getBatchDtlsSql, new RowMapper<DrugBatch>() {

			@Override
			public DrugBatch mapRow(ResultSet rs, int arg1)
					throws SQLException {

				DrugBatch details = getBatch(rs);

				return details;
			}


		}, drugId);
		return query;
	}

	private DrugBatch getBatch(ResultSet rs) throws SQLException {
		DrugBatch details = new DrugBatch();
		details.setDrugBatchId(rs.getLong("BATCH_ID"));
		details.setPurchasePrice(rs.getDouble("UNIT_PP"));
		details.setRetailPrice(rs.getDouble("UNIT_RP"));
		details.setAvlblUnits(rs.getInt("AVAIL_UNITS"));
		Timestamp timestamp = rs.getTimestamp("EXP_DT");
		details.setExpiryDate(new Date(timestamp.getTime()));
		return details;
	}

	@Override
	public void addDrugBatch(long drugId, DrugBatch batchDetails) {

		String string = queryMap.get("meditrax.adddrugbatch");
		/*INSERT INTO DRUG_BATCH(DRUG_ID, BATCH_ID, UNIT_PP, UNIT_RP, AVAIL_UNITS, EXP_DT)*/
		Timestamp timestamp = new Timestamp(batchDetails.getExpiryDate().getTime());
		template.update(string, drugId, batchDetails.getDrugBatchId(), batchDetails.getPurchasePrice(), 
				batchDetails.getRetailPrice(), batchDetails.getAvlblUnits(), timestamp);
	}

	@Override
	public void updateDrugBatch(long drugId, DrugBatch batchDetails) {
		String string = queryMap.get("meditrax.updatedrugbatch");
		template.update(string, batchDetails.getAvlblUnits(), drugId, batchDetails.getDrugBatchId());
	}

	@Override
	public List<DrugReport> getReport(DrugSearchCriteria criteria) {
		String drugreportquery = queryMap.get("meditrax.drgreport");
		List<Object> paramsList = new ArrayList<Object>();
		
		// substitute the place holders
		drugreportquery = substituteConditions(criteria, drugreportquery, paramsList);
		
		// get the search data
		return template.query(drugreportquery, paramsList.toArray(), new RowMapper<DrugReport>() {

			@Override
			public DrugReport mapRow(ResultSet rs, int arg1)
					throws SQLException {
				DrugReport drugReport = new DrugReport();
				drugReport.setDrugId(rs.getLong("DRUG_ID"));
				drugReport.setDrugNm(rs.getString("DRG_NM"));
				drugReport.setCount(rs.getInt("COUNT"));
				Timestamp timestamp = rs.getTimestamp("EXP_DT");
				drugReport.setExpiryDate(new Date(timestamp.getTime()));
				return drugReport;
			}
			
		});
	}

	private String substituteConditions(DrugSearchCriteria criteria,
			String drugreportquery, List<Object> paramsList) {
		// substitute the expiry date condition
		if(null != criteria.getExpiryDateVal()) {
			drugreportquery = drugreportquery.replace(":drgwherecls:", "AND DB.EXP_DT < ?");
			long time = criteria.getExpiryDateVal().getTime();
			Timestamp expDt = new Timestamp(time);
			paramsList.add(expDt);
		} else {
			drugreportquery = drugreportquery.replace(":drgwherecls:", "");
		}
		// substitute drug id
		if(0L != criteria.getItemId()) {
			drugreportquery = drugreportquery.replace(":drgidcond:", "AND DB.DRUG_ID = ?");
			paramsList.add(criteria.getItemId());
		} else {
			drugreportquery = drugreportquery.replace(":drgidcond:", "");
		}
		// substitute the less than quantity condition
		if(0 != criteria.getLessthanQty()) {
			drugreportquery = drugreportquery.replace(":countcond:", "WHERE ONE.COUNT < ?");
			paramsList.add(criteria.getLessthanQty());
		} else {
			drugreportquery = drugreportquery.replace(":countcond:", "");
		}

		return drugreportquery;
	}
}
