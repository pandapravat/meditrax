package com.pravat.meditrax.bi;

import java.util.Date;
import java.util.List;

import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.bi.domain.DrugReport;

public interface DrugService {
	
	public boolean addDrug(Drug drug);

	public void deleteDrug(Long valueOf);

	public void updateDrug(Drug drug);

	public List<DrugDetails> searchDrugDetails(String lowerCase);

	public List<DrugReport> getReport(Date expiryDateVal, int lessthanQty, long itemId);

}
