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
public class SaleDetails {
    
    long id;
    long drugId;
    int quantity;
    long batchId;
	double unitPrice;
    String name;
    int discountPct;
    
    public int getDiscountPct() {
		return discountPct;
	}

	public void setDiscountPct(int discountPct) {
		this.discountPct = discountPct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SaleDetails(long id, long drugId, int quantity, double unitPrice) {
        this.id = id;
        this.drugId = drugId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
    public SaleDetails() {
    }
    
    
    public long getDrugId() {
        return drugId;
    }
    
    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "Drug Id : " + drugId + " || Name:" + name+ " || Quantity : " + quantity + " || MRP : " + unitPrice;
	}
    
    
}
