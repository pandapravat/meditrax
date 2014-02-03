package com.pravat.meditrax.bi.domain;

import java.util.Date;

public class DrugSearchCriteria {
	Date expiryDateVal;
	int lessthanQty;
	long itemId;
	public Date getExpiryDateVal() {
		return expiryDateVal;
	}
	public void setExpiryDateVal(Date expiryDateVal) {
		this.expiryDateVal = expiryDateVal;
	}
	public int getLessthanQty() {
		return lessthanQty;
	}
	public void setLessthanQty(int lessthanQty) {
		this.lessthanQty = lessthanQty;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public DrugSearchCriteria(Date expiryDateVal, int lessthanQty, long itemId) {
		super();
		this.expiryDateVal = expiryDateVal;
		this.lessthanQty = lessthanQty;
		this.itemId = itemId;

	}


}
