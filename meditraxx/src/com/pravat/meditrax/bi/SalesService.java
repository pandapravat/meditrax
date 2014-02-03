package com.pravat.meditrax.bi;

import java.util.Date;

import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionReport;

public interface SalesService {

	void addNewSale(SaleTransaction saleTransaction);

	SaleTransactionReport getSalesReport(Date fromDateVal, Date toDateVal);

}
