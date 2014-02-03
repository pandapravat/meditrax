package com.pravat.meditrax.ux.dataprovider;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.commons.lang.StringUtils;

import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.pravat.meditrax.bi.dao.PartyDao;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.util.ApplicationContextUtil;

public class PartyInstantDataProvider implements InstantDataProvider<Party>{
	@Override
	public ObservableList<Party> getData(String arg0) {
		PartyDao instance = ApplicationContextUtil.getInstance(PartyDao.class);
		ObservableList<Party> list = FXCollections.observableArrayList();
		if(StringUtils.isNotBlank(arg0)) {
			List<Party> partyByName = instance.searchPartyByName(arg0.toLowerCase());
			list.addAll(partyByName);
		}
		return list;
	}
}
