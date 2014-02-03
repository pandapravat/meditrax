package com.pravat.meditrax.bi;

import java.util.Date;
import java.util.List;

import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.PuchaseTransactionInfo;
import com.pravat.meditrax.bi.domain.PurchaseTransaction;

public interface PurchaseService {
	public void addNewPurchase(long partyId, long drugId, double totalAmount, int totalQty, 
			Date transactionDate, DrugBatch batchDetails, boolean createNewBatch);
	
	public List<PuchaseTransactionInfo> fetchPurchaseDetails(long partyId);

	public void addNewReturn(PurchaseTransaction transaction, int newAvailbl);
}
