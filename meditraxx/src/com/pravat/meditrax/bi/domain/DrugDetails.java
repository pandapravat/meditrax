package com.pravat.meditrax.bi.domain;

import java.util.ArrayList;
import java.util.List;

public class DrugDetails {

	Drug targetDrug;
	List<DrugBatch> allBatches;
	
	
	public Drug getTargetDrug() {
		return targetDrug;
	}
	public void setTargetDrug(Drug targetDrug) {
		this.targetDrug = targetDrug;
	}
	public List<DrugBatch> getAllBatches() {
		return allBatches;
	}
	public void setAllBatches(List<DrugBatch> allBatches) {
		this.allBatches = allBatches;
	}
	
	public DrugBatch addNewBatch() {
		if(null == allBatches) {
			allBatches = new ArrayList<DrugBatch>();
		}

		DrugBatch drugBatch = new DrugBatch();
		allBatches.add(drugBatch);
		return drugBatch;
	}
	
	@Override
	public String toString() {
		return targetDrug.getName() +" ( " + targetDrug.getType() + " ) ";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((targetDrug == null) ? 0 : targetDrug.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrugDetails other = (DrugDetails) obj;
		if (targetDrug == null) {
			if (other.targetDrug != null)
				return false;
		} else if (!targetDrug.equals(other.targetDrug))
			return false;
		return true;
	}
	
	
}
