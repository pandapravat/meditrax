/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionReport;

import java.util.Date;
import java.util.List;

/**
 *
 * @author pandpr02
 */
public interface SalesDao {
    boolean addNewSale(SaleTransaction transaction);
    SaleTransaction fetchTransaction(long transactionID);
    SaleTransactionReport getSalesReport(Date startDate, Date endDate);
    List<SaleDetails> getSaleDetails(long saleDetailsId);
}
