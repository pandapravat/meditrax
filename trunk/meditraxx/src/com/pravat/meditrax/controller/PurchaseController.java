/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.mytdev.javafx.internal.data.AfterSelectDataHadler;
import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import com.pravat.meditrax.bi.PurchaseService;
import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.ux.dataprovider.DrugInstantDataProvider;
import com.pravat.meditrax.ux.dataprovider.PartyInstantDataProvider;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.DatePicker;
import extfx.scene.control.RestrictiveTextField;

/**
 *
 * @author pandpr02
 */
public class PurchaseController implements Initializable{

	Log log = LogFactory.getLog(getClass());
	
	

	@FXML AutoCompleteTextField<Party> partyNameSrch;
	@FXML TextField partyNameText;
	@FXML TextField partyIdText;
	@FXML TextArea partyAddress;
	@FXML DatePicker datePicker; // transaction

	// Item controls
	@FXML AutoCompleteTextField<Drug> itemNmSrch;
	@FXML ComboBox<DrugBatch> itemDetailsCombo;
	@FXML TextField itemNm;
	@FXML TextField itemManufacturer;
	@FXML TextField itemType;
	@FXML TextField purchasePrice;
	@FXML TextField retailPrice;
	@FXML DatePicker expiryDatePicker;
	@FXML RestrictiveTextField totalUnits;
	@FXML TextField totalPrice;
	@FXML TextField itemId;
	@FXML TextField itemBatchId;
	@FXML TextField existingQty;

	// buttons
	@FXML Button purchaseOkBut;
	@FXML Button purchaseCancelBut;
	@FXML Button purchaseResetBut;
	
	PurchaseService service = ApplicationContextUtil.getInstance(PurchaseService.class);

	@FXML public void onTypeChanged(ActionEvent ke) {
//		validateType();
	}

	@FXML public void onDatePickerAction(ActionEvent ke) {
//		validateDate();
	}

	@FXML
	public void onResetButAction(ActionEvent ke) {
		clearPartyDetails();
		clearItemDetails();
		clearBatchDetails();
	}
	
	@FXML
	public void onCancelButAction(ActionEvent ke) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ke), "Are you sure to cancel?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {
			mainInstance.handleHomeClick(new ActionEvent());
		}
	}
	@FXML
	public void onPrintButtonClick(ActionEvent ke) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ke), "Are you sure to print?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {
			//todo
		}
	}
	

	
	public void onOkButAction(ActionEvent ke) {
		
		Set<String> errorList = checkForErrors();

		if(!errorList.isEmpty()) {
			
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), Util.getErrorString(errorList), "Please correct the following errors!");
			
		} else {
			// Do processing
			if(Validator.isNumber(itemId.getText())) {
				long itemIdVal = Long.valueOf(itemId.getText());
				String itemBatchIdText = itemBatchId.getText();
				long itemBatchIdVal = -1L; // default
				int exitstingQty = 0;
				boolean newBatch = false;
				if(Validator.isBlank(itemBatchIdText)) {
					newBatch = true;
				} else {
					itemBatchIdVal = Long.valueOf(itemBatchIdText);
					exitstingQty  = Integer.valueOf(existingQty.getText());
				}

				long  partyIdVal = Long.valueOf(partyIdText.getText());
				Date transactDt = datePicker.getValue();
				double totalAmout = Double.valueOf(totalPrice.getText());
				int quantity = Integer.valueOf(totalUnits.getText());
				// create new batch
				Double unitPP = Double.valueOf(purchasePrice.getText());
				Double unitRP = Double.valueOf(retailPrice.getText());
				Date expiryDate = expiryDatePicker.getValue();

				DrugBatch drugBatch = new DrugBatch();
				drugBatch.setAvlblUnits(quantity + exitstingQty);
				drugBatch.setDrugBatchId(itemBatchIdVal);
				drugBatch.setExpiryDate(expiryDate);
				drugBatch.setPurchasePrice(unitPP);
				drugBatch.setRetailPrice(unitRP);

				try {
					
					service.addNewPurchase(partyIdVal, itemIdVal, totalAmout, quantity, transactDt, drugBatch, newBatch);
					
					// show confirmation and ask if want to add new
					DialogResponse showConfirmDialog = Dialogs.showConfirmDialog(Meditrax.getPrimaryStage(), "Do you want to add another for same party?", "Transaction Created SuccessFully", "Success!!", DialogOptions.YES_NO);
					clearItemDetails();
					clearBatchDetails();
					if(DialogResponse.NO.equals(showConfirmDialog)) {
						clearPartyDetails();
					} 
				} catch(Exception ex) {
					log.error("Error while creating transaction", ex);
					Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Error occurred while creating the transaction. Please try again later", "Error");
				}
			}
		}
	}

	private Set<String> checkForErrors() {
		
		Set<String> errorList = new HashSet<>();
		
		if(Validator.isBlank(partyNameText.getText())) {
			errorList.add(Constants.ERR_PARTY_NM_BLANK);
		}
		if(Constants.DFLT_DT.equals(datePicker.getValue())) {
			errorList.add(Constants.ERR_TRNCT_DT_INVALID);
		}
		if(Validator.isBlank(itemNm.getText())) {
			errorList.add(Constants.ERR_ITM_NM_BLANK);
		}
		if(Validator.isBlank(itemType.getText())) {
			errorList.add(Constants.ERR_ITM_TYP_BLANK);
		}
		if(Validator.isBlank(itemManufacturer.getText())) {
			errorList.add(Constants.ERR_MFR_NM_BLANK);
		}
		if(Validator.isBlank(retailPrice.getText())) {
			errorList.add(Constants.ERR_RTL_UNIT_PRC_BLANK);
		}
		if(Constants.DFLT_DT.equals(expiryDatePicker.getValue())) {
			errorList.add(Constants.ERR_EXP_DT_INVALID);
		}
		
		if(!Validator.isNumber(totalUnits.getText())) {
			errorList.add(Constants.ERR_TOTAL_UNITS_NUMBER);
		}
		
		if(!Validator.isMoney(totalPrice.getText())) {
			errorList.add(Constants.ERR_TOTAL_PRICE_NOT_DOUBLE);
		}
		
		if(!Validator.isMoney(purchasePrice.getText())) {
			errorList.add(Constants.ERR_PURCHASE_UNIT_PRICE_NOT_DOUBLE);
		}
		
		return errorList;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Make total units a numeric field
		totalUnits.setMaxLength(10);
		totalUnits.setRestrict("[0-9]");

		//initialize date picker
		initialiseDatePicker();
		// add change listeners to textfields
		itemNmSrch.setDataProvider(drugsDataProvider);
		itemNmSrch.setAfterSelectDataHandler(drugSelectDataHandler);
		partyNameSrch.setDataProvider(partyInstantDataProvider);;
		partyNameSrch.setAfterSelectDataHandler(partySelectDataHandler);
		itemDetailsCombo.valueProperty().addListener(itemDetailsChangeListener);
	}

	private void initialiseDatePicker() {
		datePicker.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
		datePicker.setValue(new Date());
		expiryDatePicker.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
		expiryDatePicker.setValue(Constants.DFLT_DT);
	}


	AfterSelectDataHadler<Drug> drugSelectDataHandler = new AfterSelectDataHadler<Drug>() {

		@Override
		public void handle(Drug selectedDrug) {

			itemManufacturer.setText(selectedDrug.getMfr());
			String type = selectedDrug.getType();
			itemType.setText(type);
			itemNm.setText(selectedDrug.getName());
			itemId.setText(String.valueOf(selectedDrug.getDrugId()));
			clearBatchDetails(); // clear batch details
			disableBatchDtls(true); // disable batch details also
			itemDetailsCombo.getItems().clear();
			itemDetailsCombo.getItems().add(DrugBatch.DFLT_BATCH); // default meaning a new batch

			DrugsDao instance = ApplicationContextUtil.getInstance(DrugsDao.class);
			List<DrugBatch> batches = instance.getDrugBatches(selectedDrug.getDrugId());
			if(!CollectionUtils.isEmpty(batches)) {
				itemDetailsCombo.getItems().addAll(batches);
			}

		}
	};

	AfterSelectDataHadler<Party> partySelectDataHandler = new AfterSelectDataHadler<Party>() {

		@Override
		public void handle(Party party) {
			partyAddress.setText(party.getPartyAddress());
			partyNameText.setText(party.getPartyName());
			partyIdText.setText(String.valueOf(party.getPartyId()));
		}
	};

	InstantDataProvider<Party> partyInstantDataProvider = new PartyInstantDataProvider();

	InstantDataProvider<Drug> drugsDataProvider = new DrugInstantDataProvider();

	UnitChangeListener unitChangeListener = new UnitChangeListener();
	// When a selection is made from the batch details
	ChangeListener<DrugBatch> itemDetailsChangeListener =  new ChangeListener<DrugBatch>(){

		@Override
		public void changed(ObservableValue<? extends DrugBatch> observable,
				DrugBatch oldValue, DrugBatch newValue) {
			
			DrugBatch selectedItem = newValue;
			
			// remove the text change listenre always. Then set the listener as required
			totalUnits.textProperty().removeListener(unitChangeListener);
			
			if(null != selectedItem) { // for the first time when prompt text is only displayed 
				if(selectedItem.getDrugBatchId() != DrugBatch.DFLT) { // When an existing batch is selected
					// remove the listener and add again with the batchMode as true
					unitChangeListener.setBatchMode(true);
					totalUnits.textProperty().addListener(unitChangeListener);
					totalPrice.textProperty().removeListener(totalPriceChangeListener);
					
					purchasePrice.setText(String.valueOf(selectedItem.getPurchasePrice()));
					retailPrice.setText(String.valueOf(selectedItem.getRetailPrice()));
					expiryDatePicker.setValue(selectedItem.getExpiryDate());
					itemBatchId.setText(String.valueOf(selectedItem.getDrugBatchId()));
					existingQty.setText(String.valueOf(selectedItem.getAvlblUnits()));
					retailPrice.setDisable(true);
					expiryDatePicker.setDisable(true);
					purchasePrice.setDisable(true);
					totalPrice.setDisable(true);
//					unitChangeListener.setBatchMode(true);
				} else { // When a new Batch is selected
					clearBatchDetails();
					// register the required listeners
					unitChangeListener.setBatchMode(false);
					totalUnits.textProperty().addListener(unitChangeListener);
					totalPrice.textProperty().addListener(totalPriceChangeListener);
					
					// enable or disable fields
					retailPrice.setDisable(false);
					expiryDatePicker.setDisable(false);
					purchasePrice.setDisable(true);
					totalPrice.setDisable(false);
					
				}
			}			
		}
		
	};
	
	
	ChangeListener<String> totalPriceChangeListener = new ChangeListener<String>() {

		@Override
		public void changed(
				ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			calculateUnitPurchasePrice();
		}
	};
	
	
	public class UnitChangeListener implements ChangeListener<String>{

		private boolean batchMode;
		
		public boolean isBatchMode() {
			return batchMode;
		}

		public void setBatchMode(boolean batchMode) {
			this.batchMode = batchMode;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			
			if(batchMode)  {
				calculateTotalPrice();
			} else {
				calculateUnitPurchasePrice();
			}
		}

	/*	public UnitChangeListener(boolean batchMode) {
			this.batchMode = batchMode;
		}
		*/
	}
	
	private void calculateTotalPrice() {
		if(Validator.isMoney(purchasePrice.getText()) && Validator.isNumber(totalUnits.getText())) {
			double totalPriced = Double.valueOf(purchasePrice.getText()) * Integer.valueOf(totalUnits.getText());
			double inMoneyFormat = Util.getInMoneyFormat(totalPriced);
			totalPrice.setText(String.valueOf(inMoneyFormat));
		}
		
	}
	private void calculateUnitPurchasePrice() {
		if(Validator.isMoney(totalPrice.getText()) && Validator.isNumber(totalUnits.getText())) {
			double unitPurchasePrc = Double.valueOf(totalPrice.getText()) / Integer.valueOf(totalUnits.getText());
			double inMoneyFormat = Util.getInMoneyFormat(unitPurchasePrc);
			purchasePrice.setText(String.valueOf(inMoneyFormat));
		}
		
	}
	private void clearBatchDetails() {
		purchasePrice.clear();
		retailPrice.clear();
		expiryDatePicker.setValue(Constants.DFLT_DT);
		itemBatchId.clear();
		totalPrice.clear();
		itemDetailsCombo.getItems().clear();
	}

	private void disableBatchDtls(boolean disble) {
		purchasePrice.setDisable(disble);
		retailPrice.setDisable(disble);
		expiryDatePicker.setDisable(disble);
	}

	private void clearItemDetails() {
		itemType.clear();
		itemNm.clear();
		itemId.clear();
		totalUnits.clear();
		itemManufacturer.clear();
		itemNmSrch.setText("");
		existingQty.clear();
		itemBatchId.clear();
	}
	
	private void clearPartyDetails() {
		partyNameSrch.setText("");
		datePicker.setValue(Constants.DFLT_DT);
		partyIdText.clear();
		partyNameText.clear();
		datePicker.setValue(Constants.DFLT_DT);
		partyAddress.clear();
	}

	public void setMainInstance(MainController mainController) {
		this.mainInstance = mainController;
		
	}

	MainController mainInstance;
}
