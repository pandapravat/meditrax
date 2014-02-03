/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.bi.domain;

/**
 *
 * @author pandpr02
 */
public class SaleTransaction {
    
    SaleTransactionInfo info;
	SaleDetailsList details;

    public SaleTransactionInfo getInfo() {
        return info;
    }

    public void setInfo(SaleTransactionInfo info) {
        this.info = info;
    }
    
    
    public SaleDetailsList getDetails() {
        if(null == this.details) this.details = new SaleDetailsList();
        return details;
    }
    
    public void setDetails(SaleDetailsList details) {
        this.details = details;
    }
    
    public SaleTransactionInfo addNewInfo() {
        this.info = new SaleTransactionInfo();
        return info;
    }
    
    @Override
   	public String toString() {
   		return "SaleTransaction [info=" + info + ", details=" + details + "]";
   	}
    
}
