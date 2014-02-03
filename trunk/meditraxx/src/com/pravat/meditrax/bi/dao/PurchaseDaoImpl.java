/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.bi.domain.PuchaseTransactionInfo;
import com.pravat.meditrax.bi.domain.PurchaseTransaction;
import com.pravat.meditrax.bi.domain.PurchaseTransaction.PUR_TX_TYP;
import com.pravat.meditrax.bi.domain.PurchaseTransactionReport;

/**
 *
 * @author pandpr02
 */
@Component
public class PurchaseDaoImpl extends AbstractDaoBase implements PurchaseDao {

    @Override
    public boolean addNewPurchase(PurchaseTransaction transaction) {
        Party party = transaction.getParty();
        
        String sql = queryMap.get("meditrax.purchasetx");
        
        
        int transUpd = template.update(sql, transaction.getTransactionId(), party.getPartyId(), transaction.getTransactionDate(),
        		transaction.getDrugId(), transaction.getBatchId(), transaction.getQuantity(), transaction.getAmount(), transaction.getTxType().getValue());
        
        return transUpd == 1;
    }

    @Override
    public List<PurchaseTransactionReport> getPurchaseReport(Date fromDate, Date toDate) {
        String sql = queryMap.get("meditrax.purchaserpt");
        
        java.sql.Timestamp fromDateSql = new Timestamp(fromDate.getTime());
        java.sql.Timestamp toDateSql = new Timestamp(toDate.getTime());
        Object[] params = {fromDateSql, toDateSql};
        List<PurchaseTransactionReport> query = template.query(sql, params, new PurchaseReportRowMapper());
        
        return query;
    }
    
    class PurchaseReportRowMapper implements RowMapper<PurchaseTransactionReport> {

        @Override
        public PurchaseTransactionReport mapRow(ResultSet rs, int i) throws SQLException {

        	PurchaseTransactionReport report = new PurchaseTransactionReport();
        	report.setTransactionId(rs.getLong("TRNSCT_ID"));
        	report.setPartyName(rs.getString("PRTY_NM"));
        	report.setItemId(rs.getLong("DRUG_ID"));
        	report.setItemName(rs.getString("DRG_NM"));
        	report.setAmount(rs.getDouble("TOT_AMT"));
        	report.setQuantity(rs.getInt("QTY"));
        	short txTypeShort = rs.getShort("TX_TYPE");
        	report.setTxType(PUR_TX_TYP.fromValue(txTypeShort));
        	java.sql.Timestamp dts = rs.getTimestamp("DATE");
            Date date = new Date(dts.getTime());
        	report.setDts(date);
        	 
            return report;
        }
        
    }

	@Override
	public List<PuchaseTransactionInfo> getPurchaseBatches(final long partyId) {
		String string = queryMap.get("meditrax.getpurchasebatches");
		return template.query(string, new RowMapper<PuchaseTransactionInfo>() {

			@Override
			public PuchaseTransactionInfo mapRow(ResultSet rs, int arg1)
					throws SQLException {
				
				PuchaseTransactionInfo info = new PuchaseTransactionInfo();
				info.setDrugName(rs.getString("DRG_NM"));
				info.setDrugId(rs.getLong("DRG_ID"));
				info.setPartyId(partyId);
				
				DrugBatch batchDetails = info.getBatchDetails();
				batchDetails.setDrugBatchId(rs.getLong("BATCH_ID"));
				batchDetails.setPurchasePrice(rs.getDouble("UNIT_PP"));
				batchDetails.setRetailPrice(rs.getDouble("UNIT_RP"));
				batchDetails.setAvlblUnits(rs.getInt("AVAIL_UNITS"));
				batchDetails.setExpiryDate(rs.getTimestamp("EXP_DT"));
				return info;
			}
			
		}, partyId, PUR_TX_TYP.PURCHASE.getValue());
		
	}
}
