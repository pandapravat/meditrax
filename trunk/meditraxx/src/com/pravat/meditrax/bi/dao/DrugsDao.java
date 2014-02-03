/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.util.List;

import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.bi.domain.DrugReport;
import com.pravat.meditrax.bi.domain.DrugSearchCriteria;

/**
 *
 * @author pandpr02
 */
public interface DrugsDao {
	boolean addDrug(Drug drug);
	Drug getDrug(String drugId);
	List<DrugDetails> searchDrugDetails(String searchKey);
	List<Drug> searchDrugs(String searchKey);
	boolean deleteDrug(long drugId);
	boolean updateDrug(Drug drug);
	List<DrugBatch> getDrugBatches(long drugId);
	void addDrugBatch(long drugId, DrugBatch batchDetails);
	void updateDrugBatch(long drugId, DrugBatch batchDetails);
	List<DrugReport> getReport(DrugSearchCriteria criteria);
}
