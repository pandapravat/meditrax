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
public class SaleTransactionInfo {
    long transactionId;
    String patientName;
    short patientAge;
    Date date;
    String doctorsName;
    double amount;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public short getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(short patientAge) {
        this.patientAge = patientAge;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctorsName() {
        return doctorsName;
    }

    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

	@Override
	public String toString() {
		return "SaleTransactionInfo [transactionId=" + transactionId
				+ ", patientName=" + patientName + ", patientAge=" + patientAge
				+ ", date=" + date + ", doctorsName=" + doctorsName
				+ ", amount=" + amount + "]";
	}
    
    
    
}
