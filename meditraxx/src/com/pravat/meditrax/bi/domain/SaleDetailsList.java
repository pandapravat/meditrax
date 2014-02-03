/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.bi.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pandpr02
 */
public class SaleDetailsList {

    List<SaleDetails> saleDetails;
    
    public List<SaleDetails> getSaleDetails() {
        return saleDetails;
    }
    
    public void setSaleDetails(List<SaleDetails> saleDetails) {
        this.saleDetails = saleDetails;
    }
    
    public SaleDetails addNewSaleDetails() {
        if(null == saleDetails) saleDetails = new ArrayList<>();
        SaleDetails saleDetails1 = new SaleDetails();
        saleDetails.add(saleDetails1);
        return saleDetails1;
    }

	@Override
	public String toString() {
		return "SaleDetailsList [saleDetails=" + saleDetails + "]";
	}
    
    
}
