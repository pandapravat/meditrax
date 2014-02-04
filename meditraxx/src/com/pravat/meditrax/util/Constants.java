/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.util;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author pandpr02
 */
public class Constants {
    public static final String TEST="TEST";
    public static final String REMOVE_SALES_ITEM_IMAGE_VIEWF = "fxml/removeSalesItemImageView.fxml";
    public static final String ADDABLE_SALES_ROW = "fxml/addableSalesRow.fxml";
    public static final String MAIN_WITH_WELCOME_WIN = "fxml/mainwithwelcomewin.fxml";
    public static final String SALES_WIN = "fxml/saleswin.fxml";
    public static final String WELCOME_WIN = "fxml/welcomewin.fxml";
    public static final String PURCHASE_WIN = "fxml/purchaseWin.fxml";
    public static final String ABOUT_WIN = "fxml/about.fxml";
    public static final String SALES_RPT_WIN = "fxml/salesReportWin.fxml";
    public static final String ADD_DRUG = "fxml/addDrug.fxml";
    public static final String EDIT_DRUG = "fxml/editDrug.fxml";
    public static final String ADD_PARTY = "fxml/addParty.fxml";
    public static final String EDIT_PARTY = "fxml/editParty.fxml";
    public static final String MANAGE_DRUG = "fxml/manageDrugs.fxml";
    public static final String MANAGE_PARTY = "fxml/manageParty.fxml";
    public static final String DRUG_RPT_WIN = "fxml/drugReportWin.fxml";
    public static final String VIEW_TX_DTLS = "fxml/viewSaleTxDetails.fxml";
    public static final String CONFIGURE_APP = "fxml/configure.fxml";
    public static final String SETTINGS_VIEW = "fxml/settingsView.fxml";
    public static final String PURCHASE_RPT_WIN = "fxml/purchaseReportWin.fxml";
    public static final String PURCHASE_RTRN_WIN = "fxml/purchaseReturnWin.fxml";
    public static final String ADMIN_QUERY_VIEW = "fxml/sequelEditor.fxml";
    public static final String BACKUP_VIEW = "fxml/manageBackup.fxml";
    
    
    // Errors
    public static final String ERR_TYPE_BLANK = "Please select a type";
    public static final String ERR_TOTAL_PRICE_NOT_DOUBLE = "Total price should be number";
    public static final String ERR_MFR_NM_BLANK = "Manufacturer Name can't be blank";
    public static final String ERR_ITM_NM_BLANK = "Item Name can't be blank";
    public static final String ERR_UNITS_NOT_NUMBER = "Items can't be number";
    public static final String ERR_PARTY_NM_BLANK = "Party Name can't be blank";
    public static final String ERR_INVALID_DATE = "Date is invalid";
    public static final String ERR_EXP_DT_INVALID = "Expiry date is invalid";
	public static final String ERR_ITM_TYP_BLANK = "Item type can't be blank";
	public static final String ERR_RTL_UNIT_PRC_BLANK = "Unit retail price can't be blank";
	public static final String ERR_TOTAL_UNITS_NUMBER = "Total units should be a number";
	public static final String ERR_PURCHASE_UNIT_PRICE_NOT_DOUBLE = "Unit Purchase Price should be number";
	public static final String ERR_TRNCT_DT_INVALID = "Transaction Date is invalid";
	public static final String ERR_SELECT_BATCH = "Please select a batch.";
	public static final String ERR_QTY_MORE_THAN_AVLBL = "Can not return more than purchases units.";
	
	public static final Date DFLT_DT = new GregorianCalendar(9999, 11, 31).getTime();
	public static final String MASTER_PASS = "555555";
	public static final int DRG_NM_MAX_LEN = 15;
	public static final int MFR_NM_MAX_LEN = 20;
	public static final int PARTY_NM_MAX_LEN = 40;
	public static final int CONTACT_NO_MAX_LEN = 15;
	public static final int STORE_NM_MAX_LEN = 30;
	public static final int ADD_LN1_MAX_LEN = 40;
	public static final int ADD_LN2_MAX_LEN = 40;
	public static final int NM_MAX_LEN = 20;
	public static final int ADRS_MAX_LEN = 60;
	public static final int MAX_SALE_ENTRIES = 10;
	public static final int MIN_PWD_LEN = 6;
	public static final int MAX_PWD_LEN = 20;
    
}
