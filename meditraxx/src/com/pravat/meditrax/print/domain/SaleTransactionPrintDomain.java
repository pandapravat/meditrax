package com.pravat.meditrax.print.domain;

import com.pravat.meditrax.bi.domain.SaleTransaction;

public class SaleTransactionPrintDomain implements PrintDomain{
	
	SaleTransaction saleTx;

	public SaleTransaction getSaleTx() {
		return saleTx;
	}

	public void setSaleTx(SaleTransaction saleTx) {
		this.saleTx = saleTx;
	}

}
