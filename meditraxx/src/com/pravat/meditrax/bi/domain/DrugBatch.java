package com.pravat.meditrax.bi.domain;

import java.util.Date;

import com.pravat.meditrax.util.Util;

public class DrugBatch {
	
	public static final long DFLT = -999999999;
	long drugBatchId;
	int avlblUnits;
	double retailPrice;
	double purchasePrice;
	Date expiryDate;
	
	public static final DrugBatch DFLT_BATCH = new DrugBatch(DFLT);
	
	public DrugBatch() {
	}

	private DrugBatch(long dflt2) {
		this.drugBatchId = dflt2;
	}
	public long getDrugBatchId() {
		return drugBatchId;
	}
	public void setDrugBatchId(long drugBatchId) {
		this.drugBatchId = drugBatchId;
	}
	public int getAvlblUnits() {
		return avlblUnits;
	}
	public void setAvlblUnits(int avlblUnits) {
		this.avlblUnits = avlblUnits;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		if(drugBatchId == DFLT) return "Add New Batch";
		return  "AVL=" +avlblUnits + " | U.MRP=" + retailPrice +" | U.PP=" + purchasePrice + " | EXP=" + Util.getStrDate(expiryDate) ;
	}
	
	
}
