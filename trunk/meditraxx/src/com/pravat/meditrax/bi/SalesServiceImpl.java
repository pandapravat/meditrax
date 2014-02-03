package com.pravat.meditrax.bi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.bi.dao.SalesDao;
import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionReport;

@Controller
@Transactional
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	SalesDao salesDao;
	@Autowired
	AppConfigDao configDao;
	

	@Override
	public void addNewSale(SaleTransaction saleTransaction) {
		
		long transactionId = configDao.getNextSeqVal("SALE_TX_ID_SQ");
		saleTransaction.getInfo().setTransactionId(transactionId);
		
		List<SaleDetails> allDetails = saleTransaction.getDetails().getSaleDetails();
		
		// create details id for each sale item
		for (SaleDetails saleDetails : allDetails) {
			long saleDetailsIdentifier = configDao.getNextSeqVal("SALE_DTLS_ID_SQ");
			saleDetails.setId(saleDetailsIdentifier);
		}
		salesDao.addNewSale(saleTransaction);
	}


	@SuppressWarnings("deprecation")
	@Override
	public SaleTransactionReport getSalesReport(Date fromDateVal, Date toDateVal) {
		// set the from date as start of day and end date as end of the day
		fromDateVal.setHours(0);
		fromDateVal.setMinutes(0);
		fromDateVal.setSeconds(0);
		toDateVal.setHours(23);
		fromDateVal.setMinutes(59);
		fromDateVal.setSeconds(59);
		return salesDao.getSalesReport(fromDateVal, toDateVal);
	}

}
