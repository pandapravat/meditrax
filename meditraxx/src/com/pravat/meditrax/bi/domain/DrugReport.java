package com.pravat.meditrax.bi.domain;

import java.util.Date;

public class DrugReport {
	long drugId;
	String drugNm;
	int count;
	Date expiryDate;
	public long getDrugId() {
		return drugId;
	}
	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}
	public String getDrugNm() {
		return drugNm;
	}
	public void setDrugNm(String drugNm) {
		this.drugNm = drugNm;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	
}
