package com.pravat.meditrax.bi;

import com.pravat.meditrax.bi.domain.Party;

public interface PartyService {

	boolean addParty(Party party);

	void removeParty(Long valueOf);

	void updateParty(Party party);

}
