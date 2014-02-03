package com.pravat.meditrax.bi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.bi.domain.DrugReport;
import com.pravat.meditrax.bi.domain.DrugSearchCriteria;

@Component
@Transactional
public class DrugServiceImpl implements DrugService {

	@Autowired
	DrugsDao drugsDao;
	
	@Autowired
	AppConfigDao configDao;
	
	@Override
	public boolean addDrug(Drug drug) {
		// create a new sequence for the drug
		long newDrugId = configDao.getNextSeqVal("DRG_ID_SQ");
		drug.setDrugId(newDrugId);
		
		return drugsDao.addDrug(drug);
	}

	@Override
	public void deleteDrug(Long drugId) {
		Assert.notNull(drugId);
		drugsDao.deleteDrug(drugId);
	}

	@Override
	public void updateDrug(Drug drug) {
		drugsDao.updateDrug(drug);
	}

	@Override
	public List<DrugDetails> searchDrugDetails(String lowerCase) {
		return drugsDao.searchDrugDetails(lowerCase);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<DrugReport> getReport(Date expiryDateVal, int lessthanQty, long itemId) {
		if(null != expiryDateVal) {
			expiryDateVal.setHours(23);
			expiryDateVal.setMinutes(59);
			expiryDateVal.setSeconds(59);
		}
		
		DrugSearchCriteria criteria = new DrugSearchCriteria(expiryDateVal, lessthanQty, itemId);
		
		List<DrugReport> report = drugsDao.getReport(criteria);
		return report;
	}

}
