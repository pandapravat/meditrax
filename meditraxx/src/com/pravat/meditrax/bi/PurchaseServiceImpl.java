package com.pravat.meditrax.bi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.dao.PurchaseDao;
import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.bi.domain.PuchaseTransactionInfo;
import com.pravat.meditrax.bi.domain.PurchaseTransaction;
import com.pravat.meditrax.bi.domain.PurchaseTransaction.PUR_TX_TYP;

@Component
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	PurchaseDao purchaseDao;
	
	@Autowired
	DrugsDao drugsDao;
	
	@Autowired
	AppConfigDao configDao;

	@Override
	@Transactional
	public void addNewPurchase(long partyId, long drugId, double totalAmount, int totalQty,
			Date transactionDate, DrugBatch batchDetails, boolean createNewBatch) {
		
		if(createNewBatch) {
			// create a new batch
			long nextBatchId = configDao.getNextSeqVal("DRUG_BATCH_ID_SQ");
			batchDetails.setDrugBatchId(nextBatchId);
			drugsDao.addDrugBatch(drugId, batchDetails);
		} else {
			// update existing Batch
			drugsDao.updateDrugBatch(drugId, batchDetails);
		}
		// create transaction details
		long nextPurTxId = configDao.getNextSeqVal("PURCHASE_TX_ID_SQ");
		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setTransactionId(nextPurTxId); 
		transaction.setDrugId(drugId);
		transaction.setQuantity(totalQty);
		transaction.setTransactionDate(transactionDate);
		transaction.setAmount(totalAmount);
		Party party = new Party();
		party.setPartyId(partyId);
		transaction.setParty(party);
		transaction.setBatchId(batchDetails.getDrugBatchId());
		transaction.setTxType(PUR_TX_TYP.PURCHASE);
		
		purchaseDao.addNewPurchase(transaction);
		
	}

	@Override
	public List<PuchaseTransactionInfo> fetchPurchaseDetails(long partyId) {
		return purchaseDao.getPurchaseBatches(partyId);
		
	}

	@Override
	public void addNewReturn(PurchaseTransaction transaction, int newAvlbl) {
		
		DrugBatch drugBatch = new DrugBatch();
		drugBatch.setDrugBatchId(transaction.getBatchId());
		drugBatch.setAvlblUnits(newAvlbl);
		
		
		long nextPurTxId = configDao.getNextSeqVal("PURCHASE_TX_ID_SQ");
		
		drugsDao.updateDrugBatch(transaction.getDrugId(), drugBatch);
		transaction.setTxType(PUR_TX_TYP.PUR_RETURN);
		transaction.setTransactionId(nextPurTxId);
		purchaseDao.addNewPurchase(transaction);
	}

}
