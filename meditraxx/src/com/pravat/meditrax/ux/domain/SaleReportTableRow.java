package com.pravat.meditrax.ux.domain;

import java.util.Date;

import com.pravat.meditrax.util.Util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SaleReportTableRow {
	SimpleIntegerProperty slNo;
	SimpleObjectProperty<Date> dts;
	SimpleDoubleProperty amount;
	SimpleStringProperty patientName;
	SimpleStringProperty doctorsName;
	SimpleStringProperty transactionId;
	
	
	public String getTransactionId() {
		return transactionId.get();
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = new SimpleStringProperty(transactionId);
	}
	
	public void setPatientName(String patientName) {
		this.patientName = new SimpleStringProperty(patientName);
	}

	public void setDoctorsName(String doctorsName) {
		this.doctorsName = new SimpleStringProperty(doctorsName);
	}

	public String getPatientName() {
		return patientName.get();
	}

	public String getDoctorsName() {
		return doctorsName.get();
	}

	public void setSlNo(int slNo) {
		this.slNo = new SimpleIntegerProperty(slNo);
	}
	
	public void setDts(Date dts) {
		this.dts = new SimpleObjectProperty<Date>(dts);
	}
	
	public void setAmount(double amount) {
		this.amount= new SimpleDoubleProperty(amount);
	}
	public void setSlNo(SimpleIntegerProperty slNo) {
		this.slNo = slNo;
	}
	public void setDts(SimpleObjectProperty<Date> dts) {
		this.dts = dts;
	}
	public void setAmount(SimpleDoubleProperty amount) {
		this.amount = amount;
	}

	public int getSlNo() {
		return slNo.get();
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

	public double getAmount() {
		return amount.get();
	}
	
	
}
