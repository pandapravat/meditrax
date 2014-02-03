/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pravat.meditrax.bi.domain.Party;

/**
 *
 * @author pandpr02
 */
@Component
public class PartyDaoImpl extends AbstractDaoBase implements PartyDao{

    @Override
    public boolean addParty(Party party) {
        String sql = queryMap.get("meditrax.insertparty");
        int update = template.update(sql, party.getPartyId(), party.getPartyName(), party.getPartyAddress(), party.getContactNumber());
        return update == 1;
    }

    @Override
    public boolean updateParty(Party party) {
        String sql = queryMap.get("meditrax.updateparty");
        int update = template.update(sql, party.getPartyName(), party.getPartyAddress(), party.getContactNumber(), party.getPartyId());
        
        return update == 1;
    }
    

    @Override
    public boolean removeParty(long partyId) {
    	String sql = queryMap.get("meditrax.removeparty");
    	
    	int update = template.update(sql, partyId);
    	
    	return update == 1;
    }

	@Override
	public List<Party> searchPartyByName(String key) {
		String sql = queryMap.get("meditrax.searchparty");
		sql = sql.replace(":searchString", key);
		List<Party> query = template.query(sql, new RowMapper<Party>() {

			@Override
			public Party mapRow(ResultSet rs, int arg1) throws SQLException {
				
				long partyId = rs.getLong("PRTY_ID");
				String name = rs.getString("PRTY_NM");
				String address = rs.getString("PRTY_ADD");
				String contact = rs.getString("CNTCT_NO");
				
				Party party = new Party();
				party.setPartyId(partyId);
				party.setPartyName(name);
				party.setPartyAddress(address);
				party.setContactNumber(contact);
				return party;
			}
			
		});
		
		return query;
	}
    
}
