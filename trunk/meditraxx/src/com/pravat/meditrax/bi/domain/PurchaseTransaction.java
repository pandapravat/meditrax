/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.domain;

import java.util.Date;

/**
 *
 * @author pandpr02
 */
public class PurchaseTransaction {
    long transactionId;
    long drugId;
    int quantity;
    Date transactionDate;
    double amount;
    Party party;
    long batchId;
    PUR_TX_TYP txType = PUR_TX_TYP.PURCHASE;
    
    

	public enum PUR_TX_TYP {
    	PURCHASE(1), PUR_RETURN(2);
    	
    	private int value;
    	PUR_TX_TYP(int type) {
    		this.value = type;
    	}
		public int getValue() {
			return value;
		}
		
		public static PUR_TX_TYP fromValue(int value){
			PUR_TX_TYP[] values = PUR_TX_TYP.values();
			for (PUR_TX_TYP pur_TX_TYP : values) {
				if(pur_TX_TYP.getValue() == value) {
					return pur_TX_TYP;
				}
			}
			return null;
		}
    	
    }

    
    public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public long getDrugId() {
		return drugId;
	}

	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}

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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Party getParty() {
    	if(null == party) party = new Party();
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    public PUR_TX_TYP getTxType() {
		return txType;
	}

	public void setTxType(PUR_TX_TYP txType) {
		this.txType = txType;
	}
    
    
}
