package com.pravat.meditrax.bi.domain;

import java.util.Date;

import com.pravat.meditrax.util.Util;

public class PuchaseTransactionInfo {
	
	long transactionId;
	long drugId;
	String drugName;
	int quantity;
	Date transactionDate;
	long partyId;
	DrugBatch batchDetails;
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public DrugBatch getBatchDetails() {
		if(null == batchDetails) {
			batchDetails = new DrugBatch();
		}
		return batchDetails;
	}
	public void setBatchDetails(DrugBatch batchDetails) {
		this.batchDetails = batchDetails;
	}
	
	public long getDrugId() {
		return drugId;
	}
	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}
	public long getPartyId() {
		return partyId;
	}
	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}
	public String toString() {
		return  "NM=" + drugName +" | AVL=" +batchDetails.getAvlblUnits() + " | U.MRP=" + batchDetails.getRetailPrice() +
				" | U.PP=" + batchDetails.getPurchasePrice() + " | EXP DT=" + Util.getStrDate(batchDetails.getExpiryDate()) ;
	}

}
