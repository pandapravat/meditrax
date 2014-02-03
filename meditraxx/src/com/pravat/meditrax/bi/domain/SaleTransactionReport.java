/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.domain;

import java.util.List;

/**
 *
 * @author pandpr02
 */
public class SaleTransactionReport {
    List<SaleTransactionInfo> salesTxList;

    public List<SaleTransactionInfo> getSalesTxList() {
        return salesTxList;
    }

    public void setSalesTxList(List<SaleTransactionInfo> salesTxList) {
        this.salesTxList = salesTxList;
    }
}
