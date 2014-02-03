package com.pravat.meditrax.controller;

import java.net.URL;
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
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import com.pravat.meditrax.bi.PurchaseService;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.bi.domain.PuchaseTransactionInfo;
import com.pravat.meditrax.bi.domain.PurchaseTransaction;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.ux.dataprovider.PartyInstantDataProvider;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class PurchaseReturnController implements Initializable{

	Log log = LogFactory.getLog(getClass());
	PurchaseService instance = ApplicationContextUtil.getInstance(PurchaseService.class);

	@FXML AutoCompleteTextField<Party> partyNameSrch;
	@FXML ComboBox<PuchaseTransactionInfo> transactionInfo;
	@FXML TextField partyNameText;
	@FXML TextField partyIdText;
	@FXML TextArea partyAddress;

	@FXML TextField itemNm;
	@FXML TextField purchsePrice;
	@FXML TextField balPrc;
	@FXML RestrictiveTextField returnUnits;


	PuchaseTransactionInfo selectedTransactionInfo;
	@FXML public void onReturnButAction(ActionEvent ae) {
		Set<String> errors = checkForErrors();

		if(CollectionUtils.isEmpty(errors)) {
			PurchaseTransaction transaction = new PurchaseTransaction();
			transaction.setBatchId(selectedTransactionInfo.getBatchDetails().getDrugBatchId());
			transaction.setDrugId(selectedTransactionInfo.getDrugId());
			transaction.getParty().setPartyId(selectedTransactionInfo.getPartyId());
			int returnQty = Integer.valueOf(returnUnits.getText());
			transaction.setQuantity(Integer.valueOf(returnUnits.getText()));
			transaction.setAmount(Double.valueOf(balPrc.getText()));
			transaction.setTransactionDate(new Date());
			int avlblUnits = selectedTransactionInfo.getBatchDetails().getAvlblUnits();
			try {
				instance.addNewReturn(transaction, avlblUnits - returnQty);

				Dialogs.showInformationDialog(Meditrax.getPrimaryStage(), "Return processed successfully", "Transaction processed SuccessFully", "Success!!");
				clearAll();
				//clearItemDetails();
				//clearBatchDetails();

			} catch(Exception ex) {
				log.error("Error while creating transaction", ex);
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Error occurred while creating the transaction. Please try again later", "Error");
			}
		} else {
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), Util.getErrorString(errors), "Please correct the following errors!");
		}
	}

	private void clearAll() {
		partyNameText.clear();partyAddress.clear();partyNameSrch.setText("");
		partyAddress.clear();
		selectedTransactionInfo = null;
		transactionInfo.getItems().clear();
		itemNm.clear();
		purchsePrice.clear();
		balPrc.clear();returnUnits.clear();

	}

	private Set<String> checkForErrors() {
		Set<String> errorList = new HashSet<>();

		if(null == selectedTransactionInfo) {
			errorList.add(Constants.ERR_SELECT_BATCH);
		}
		if(Validator.isBlank(returnUnits.getText())) {
			errorList.add(Constants.ERR_ITM_NM_BLANK);
		} else {
			if(Integer.valueOf(returnUnits.getText()) > selectedTransactionInfo.getBatchDetails().getAvlblUnits()) {
				errorList.add(Constants.ERR_QTY_MORE_THAN_AVLBL);
			}
		}
		


		return errorList;
	}

	@FXML public void onCancelButAction(ActionEvent ae) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure to cancel?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {
			mainInstance.handleHomeClick(new ActionEvent());
		}
	}
	@FXML public void onResetButAction(ActionEvent ae) {
		clearAll();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		partyNameSrch.setDataProvider(new PartyInstantDataProvider());
		partyNameSrch.setAfterSelectDataHandler(new AfterSelectDataHadler<Party>() {

			@Override
			public void handle(Party selectedItem) {
				partyNameText.setText(selectedItem.getPartyName());
				partyIdText.setText(String.valueOf(selectedItem.getPartyId()));
				partyAddress.setText(selectedItem.getPartyAddress());

				List<PuchaseTransactionInfo> fetchPurchaseDetails = instance.fetchPurchaseDetails(selectedItem.getPartyId());
				transactionInfo.getItems().clear();
				transactionInfo.getItems().addAll(fetchPurchaseDetails);
			}
		});

		transactionInfo.valueProperty().addListener(new ChangeListener<PuchaseTransactionInfo>() {

			@Override
			public void changed(
					ObservableValue<? extends PuchaseTransactionInfo> observable,
					PuchaseTransactionInfo oldValue,
					PuchaseTransactionInfo newValue) {
				if(null != newValue) {
					itemNm.setText(newValue.getDrugName());
					purchsePrice.setText(String.valueOf(newValue.getBatchDetails().getPurchasePrice()));
					selectedTransactionInfo = newValue;
				}
				//				returnUnits.setText();
			}
		});

		returnUnits.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(null != selectedTransactionInfo && org.apache.commons.lang.StringUtils.isNotBlank(newValue)) {
					double returnPrice = selectedTransactionInfo.getBatchDetails().getPurchasePrice() * Integer.valueOf(newValue);
					double inMoneyFormat = Util.getInMoneyFormat(returnPrice);

					balPrc.setText(String.valueOf(inMoneyFormat));
				}
			}
		});
	}


	MainController mainInstance;
	public void setMainInstance(MainController mainInstance) {
		this.mainInstance = mainInstance;
	}

}
