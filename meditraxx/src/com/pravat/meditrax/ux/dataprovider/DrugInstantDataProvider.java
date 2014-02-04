package com.pravat.meditrax.ux.dataprovider;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.commons.lang.StringUtils;

import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.util.ApplicationContextUtil;

public class DrugInstantDataProvider implements InstantDataProvider<Drug>{
	@Override
	public ObservableList<Drug> getData(String arg0) {
		DrugsDao instance = ApplicationContextUtil.getInstance(DrugsDao.class);
		ObservableList<Drug> list = FXCollections.observableArrayList();
		if(StringUtils.isNotBlank(arg0)) {
			List<Drug> partyByName = instance.searchDrugs(arg0);
			list.addAll(partyByName);
		}
		return list;
	}
	
	public DrugInstantDataProvider() {
		
	}
	
	public DrugInstantDataProvider(boolean fetchUnAvlbl) {
		this.fetchUnAvlbl = fetchUnAvlbl;
	}
	
	boolean fetchUnAvlbl = false;
}
