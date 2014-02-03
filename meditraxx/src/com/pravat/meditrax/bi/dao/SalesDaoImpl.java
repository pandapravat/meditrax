/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.bi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleDetailsList;
import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionInfo;
import com.pravat.meditrax.bi.domain.SaleTransactionReport;

/**
 *
 * @author pandpr02
 */
@Component
public class SalesDaoImpl extends AbstractDaoBase implements SalesDao{
	
    @Override
    public boolean addNewSale(SaleTransaction transact) {
        String sql = queryMap.get("meditrax.insertsaletx");
        SaleTransactionInfo info = transact.getInfo();
        SaleDetailsList details = transact.getDetails();
        
        // update sale transaction
        int update = template.update(sql, info.getTransactionId(), info.getPatientName(),
                info.getPatientAge(), info.getDate(),
                info.getDoctorsName(), info.getAmount());
        
        String salesDtlSql = queryMap.get("meditrax.insertsalesdtl");
        
        // update all the sale items
        final List<SaleDetails> saleDetails = details.getSaleDetails();
        final long transactionId = info.getTransactionId();
        final int batchSize = details.getSaleDetails().size();
        // insert entries in the sale details table
        template.batchUpdate(salesDtlSql, new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SaleDetails details = saleDetails.get(i);
                ps.setLong(1, details.getId());
                ps.setLong(2, transactionId);
                ps.setLong(3, details.getDrugId());
                ps.setInt(4, details.getQuantity());
                ps.setDouble(5, details.getUnitPrice());
                ps.setShort(6, (short)details.getDiscountPct());
                ps.setLong(7, details.getBatchId());
            }
            
            @Override
            public int getBatchSize() {
                return batchSize;
            }
            
        });
        
        String updateBatchSQL = queryMap.get("meditrax.updatebatchcount");
        // Reduce the amount from batch
        template.batchUpdate(updateBatchSQL, new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SaleDetails details = saleDetails.get(i);
                ps.setInt(1, details.getQuantity());
                ps.setLong(2, details.getBatchId());
            }
            
            @Override
            public int getBatchSize() {
                return batchSize;
            }
            
        });
        
        
        return update == 1;
    }
    
    @Override
    public SaleTransaction fetchTransaction(long transactionID) {
        String fetchSaleTx = queryMap.get("meditrax.fetchsaletx");
        Object[] params = {transactionID};
        
        log.info("Fetching sale transaction. SQL :" + fetchSaleTx + " Params:" +Arrays.toString(params));
        return template.query(fetchSaleTx, params, new ResultSetExtractor<SaleTransaction>() {
            
            @Override
            public SaleTransaction extractData(ResultSet rs) throws SQLException, DataAccessException {
                SaleTransaction stx = new SaleTransaction();
                SaleTransactionInfo sti = stx.addNewInfo();
                SaleDetailsList details = stx.getDetails();
                int count = 0;
                // construct the sales details
                while(rs.next()) {
                    
                    // create the transaction info for the first record
                    if(count == 0) {
                        sti.setTransactionId(rs.getLong(1));
                        sti.setPatientName(rs.getString(2));
                        sti.setPatientAge(rs.getShort(3));
                        sti.setDate(new Date(rs.getTimestamp(4).getTime()));
                        sti.setDoctorsName(rs.getString(5));
                        sti.setAmount(rs.getDouble(6));
                    }
                    SaleDetails saleDetail = details.addNewSaleDetails();
                    saleDetail.setId(rs.getLong(8));
                    saleDetail.setDrugId(rs.getLong(9));
                    saleDetail.setQuantity(rs.getInt(10));
                    saleDetail.setUnitPrice(rs.getDouble(11));
                    count++;
                }
                return stx;
            }
            
        });
    }
    
  
    @Override
    public SaleTransactionReport getSalesReport(Date startDate, Date endDate) {
        
        String sql = queryMap.get("meditrax.fetchsalesrpt");
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime())};
        
        log.info("Fetching sales report. SQL :" + sql + " Params:" +Arrays.toString(params));
        List<SaleTransactionInfo> allSale = template.query(sql, params, new RowMapper<SaleTransactionInfo>() {
            
            @Override
            public SaleTransactionInfo mapRow(ResultSet rs, int i) throws SQLException {
            	SaleTransactionInfo transactionInfo = new SaleTransactionInfo();
            	
            	transactionInfo.setTransactionId(rs.getLong("TRNSCT_ID"));
            	transactionInfo.setPatientName(rs.getString("PATIENT_NM"));
            	transactionInfo.setPatientAge(rs.getShort("PATIENT_AGE"));
            	Date transactDate = new Date(rs.getTimestamp("DATE").getTime());
            	transactionInfo.setDate(transactDate);
            	transactionInfo.setDoctorsName(rs.getString("DOCTOR_NM"));
            	transactionInfo.setAmount(rs.getDouble("TOT_AMT"));
            	
            	return transactionInfo;
            }
            
        });
        SaleTransactionReport saleTransactionReport = new SaleTransactionReport();
        saleTransactionReport.setSalesTxList(allSale);
        return saleTransactionReport;
    }
    
    @Override
    public List<SaleDetails> getSaleDetails(long transactionId) {
        String sql = queryMap.get("meditrax.fetchsaledtls");
        Object[] params = {transactionId};
        List<SaleDetails> saleDtls = template.query(sql, new RowMapper<SaleDetails>() {
            
            @Override
            public SaleDetails mapRow(ResultSet rs, int i) throws SQLException {
            	
            	/*S.ID, S.SALE_DTLS_ID, S.DRUG_ID, D.DRG_NM, S.QTY, S.UNIT_PRC*/
                long id = rs.getLong("ID");
//                long saleDtlsId = rs.getLong("SALE_DTLS_ID");
                long drugId = rs.getLong("DRUG_ID");
                int quantity = rs.getInt("QTY");
                double unitPrice = rs.getDouble("UNIT_PRC");
                String name = rs.getString("DRG_NM");
                
                SaleDetails saleDetails = new SaleDetails(id, drugId, quantity, unitPrice);
                saleDetails.setName(name);
                return saleDetails;
            }
        }, params);
        
        return saleDtls;
    }
}
