/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.util.Date;
import java.util.List;

import com.pravat.meditrax.bi.domain.PuchaseTransactionInfo;
import com.pravat.meditrax.bi.domain.PurchaseTransaction;
import com.pravat.meditrax.bi.domain.PurchaseTransactionReport;

/**
 *
 * @author pandpr02
 */
public interface PurchaseDao {
    boolean addNewPurchase(PurchaseTransaction transaction);
    List<PurchaseTransactionReport> getPurchaseReport(Date fromDate, Date toDate);
	List<PuchaseTransactionInfo> getPurchaseBatches(long partyId);
}
