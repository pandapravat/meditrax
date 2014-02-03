package com.pravat.meditrax.ux.domain;

import java.util.Date;

import com.pravat.meditrax.util.Util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PurchaseReportTableRow {
	SimpleIntegerProperty slNo;
	SimpleObjectProperty<Date> dts;
	SimpleDoubleProperty amount;
	SimpleStringProperty partyName;
	SimpleStringProperty itemName;
	SimpleStringProperty transactionId;
	SimpleIntegerProperty totalQty;
	SimpleStringProperty isReturn;
	
	
	

	public String getIsReturn() {
		return isReturn.get();
	}




	public void setIsReturn(SimpleStringProperty isReturn) {
		this.isReturn = isReturn;
	}




	public int getSlNo() {
		return slNo.get();
	}




	public void setSlNo(SimpleIntegerProperty slNo) {
		this.slNo = slNo;
	}




	public double getAmount() {
		return amount.get();
	}




	public void setAmount(SimpleDoubleProperty amount) {
		this.amount = amount;
	}




	public String getPartyName() {
		return partyName.get();
	}




	public void setPartyName(SimpleStringProperty partyName) {
		this.partyName = partyName;
	}




	public String getItemName() {
		return itemName.get();
	}




	public void setItemName(SimpleStringProperty itemName) {
		this.itemName = itemName;
	}




	public String getTransactionId() {
		return transactionId.get();
	}




	public void setTransactionId(SimpleStringProperty transactionId) {
		this.transactionId = transactionId;
	}




	public int getTotalQty() {
		return totalQty.get();
	}




	public void setTotalQty(SimpleIntegerProperty totalQty) {
		this.totalQty = totalQty;
	}




	public void setDts(SimpleObjectProperty<Date> dts) {
		this.dts = dts;
	}




	public Date getDts() {
		return new Date(dts.get().getTime()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String toString() {
				return Util.getDateTimeString(this);
			}
		};
		
	}
	
}
