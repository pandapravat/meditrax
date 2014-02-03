package com.pravat.meditrax.bi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.bi.dao.PartyDao;
import com.pravat.meditrax.bi.domain.Party;

@Controller
@Transactional
public class PartyServiceImpl implements PartyService {
	@Autowired PartyDao dao;
	@Autowired AppConfigDao configDao;
	
	@Override
	public boolean addParty(Party party) {
		long nextPartyId = configDao.getNextSeqVal("PARTY_ID_SQ");
		party.setPartyId(nextPartyId);
		return dao.addParty(party);
	}

	@Override
	public void removeParty(Long partyId) {
		Assert.notNull(partyId);
		dao.removeParty(partyId);
	}

	@Override
	public void updateParty(Party party) {
		dao.updateParty(party);
	}
}
