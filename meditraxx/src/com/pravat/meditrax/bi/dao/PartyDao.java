/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.util.List;

import com.pravat.meditrax.bi.domain.Party;

/**
 *
 * @author pandpr02
 */
public interface PartyDao {
    boolean addParty(Party party);
    boolean updateParty(Party party);
    boolean removeParty(long partyId);
    List<Party> searchPartyByName(String key);
}
