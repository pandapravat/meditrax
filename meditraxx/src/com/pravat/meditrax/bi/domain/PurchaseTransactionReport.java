package com.pravat.meditrax.bi.domain;

import java.util.Date;

import com.pravat.meditrax.bi.domain.PurchaseTransaction.PUR_TX_TYP;

public class PurchaseTransactionReport {
	
	long transactionId;
	Date dts;
	String partyName;
	String itemName;
	long itemId;
	double amount;
	int quantity;
	PUR_TX_TYP txType;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Date getDts() {
		return dts;
	}
	public void setDts(Date dts) {
		this.dts = dts;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public PUR_TX_TYP getTxType() {
		return txType;
	}
	public void setTxType(PUR_TX_TYP txType) {
		this.txType = txType;
	}
	
	

}
