/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.ux.dataprovider;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.commons.lang.StringUtils;

import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.pravat.meditrax.bi.DrugService;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.util.ApplicationContextUtil;

/**
 *
 * @author pandpr02
 */
public class DrugItemDataProvider implements InstantDataProvider<DrugDetails>{
    
    
    @Override
    public ObservableList<DrugDetails> getData(String match) {
        
    	DrugService instance = ApplicationContextUtil.getInstance(DrugService.class);

    	ObservableList<DrugDetails> arrayList = FXCollections.observableArrayList();
        if(StringUtils.isNotBlank(match)) {
        	List<DrugDetails> drugs = instance.searchDrugDetails(match.toLowerCase());
//        	for (DrugDetails drugDetails : drugs) {
//				drugDetails.getAllBatches()
//			}
        	arrayList.addAll(drugs);
        	
//        	List<String> items =  new ArrayList<>();
//        	for (Drug drug : drugs) {
//        		data.add(drug.getName() + " (" + drug.getType() + ")");
//			}
        }
        return arrayList;
    }
}
