/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.controller;


import java.awt.print.PrinterException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.mytdev.javafx.internal.data.AfterSelectDataHadler;
import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.bi.SalesService;
import com.pravat.meditrax.bi.domain.DrugBatch;
import com.pravat.meditrax.bi.domain.DrugDetails;
import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleDetailsList;
import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionInfo;
import com.pravat.meditrax.exception.MeditraxException;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.print.SalesTransactionPrinter;
import com.pravat.meditrax.print.domain.SaleTransactionPrintDomain;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.ux.dataprovider.DrugItemDataProvider;
import com.pravat.meditrax.ux.domain.NewSaleTableData;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.DatePicker;
import extfx.scene.control.RestrictiveTextField;

/**
 *
 * @author pandpr02
 */
public class NewSalesController implements Initializable {

	Log log = LogFactory.getLog(getClass());

	@FXML Label grandTotalLabel;
	@FXML DatePicker currentDate;
	@FXML RestrictiveTextField patientName;
	@FXML RestrictiveTextField age;
	@FXML RestrictiveTextField prescribedBy;
	@FXML TableView<NewSaleTableData> table;
	ObservableList<NewSaleTableData>  data;
	@FXML Button removeBut;


	@FXML TableColumn<NewSaleTableData, String> itemCol;
	@FXML TableColumn<NewSaleTableData, String> qtyCol;
	@FXML TableColumn<NewSaleTableData, Integer> discPercentage;
	@FXML TableColumn<NewSaleTableData, String> tpCol;
	@FXML TableColumn<NewSaleTableData, Integer> slNoCol;



	// new row data
	@FXML AutoCompleteTextField<DrugDetails> itemAutoFill;
	@FXML TableColumn<NewSaleTableData, String> upCol;
	@FXML ComboBox<DrugBatch> allBatches;
	@FXML TextField unitPrice;
	@FXML TextField quantity;
	@FXML TextField totalPrice;
	@FXML RestrictiveTextField pctDisc;
	@FXML TextField drugId;
	@FXML TextField drugName;


	// Current selected batch
	DrugBatch currentBatch;

	SalesService salesService = ApplicationContextUtil.getInstance(SalesService.class);
	ApplicationService appService = ApplicationContextUtil.getInstance(ApplicationService.class);
	/*
	 * A Map which keeps track of the total number of drugs
	 * for a certain batchId
	 */
	Map<Long, Integer> batchIdToAvailCountMap = new HashMap<>();

	@FXML public void addItem(ActionEvent ae) {

		log.debug("Adding new Sales Row");
		try {
			// Create an hBox with the addable sales row and the delete button put
			// in an Hbox
			String text = itemAutoFill.getText();
			String unitPrice = this.unitPrice.getText();
			String quantity = this.quantity.getText();
			String totalPriceS = totalPrice.getText();
			String pctDiscS = pctDisc.getText();

			Set<String> errors = checkForFormErrors(unitPrice, quantity, totalPriceS);
			if(CollectionUtils.isEmpty(errors)) {
				calculateTotal();
				NewSaleTableData newRow = new NewSaleTableData(data.size() + 1, text, 
						Integer.valueOf(quantity), Float.valueOf(unitPrice), Double.valueOf(totalPriceS));
				int discPct = 0;
				if(StringUtils.isNotBlank(pctDiscS)) {
					discPct = Integer.valueOf(pctDiscS);
				}
				newRow.setDiscountPct(new SimpleIntegerProperty(discPct));
				newRow.setBatchId(currentBatch.getDrugBatchId());
				newRow.setItemId(Long.valueOf(drugId.getText()));
				data.add(newRow);

				//				recalculateGrandTotal();
				clearInputForm();
			} else {
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), Util.getErrorString(errors), "Please correct the errors");
			}

			log.debug("Added new Sales Row");
		} catch(Exception e) {
			throw new MeditraxException("Unable to add new sales row ", e);
		}


	}

	private Set<String> checkForFormErrors(String unitPrice, String quantity,
			String totalPriceS) {

		Set<String> errors = new HashSet<>();

		
		if(Validator.isBlank(totalPriceS)) {
			errors.add("Total Price can't be blank");
		}
		if(!Validator.isNumber(quantity)) {
			errors.add("Quantity should be number");
		}
		if(!Validator.isMoney(unitPrice)) {
			errors.add("Unit price is invalis");
		}
		if(null == currentBatch) {
			errors.add("Please select a batch.");
		} else {
			if(Validator.isNumber(quantity)) {

				// check if any previous addition might affect the total units
				Integer totalAddedQtyOfThisBatch = batchIdToAvailCountMap.get(currentBatch.getDrugBatchId());
				int remainingUnits = currentBatch.getAvlblUnits() - Integer.valueOf(quantity);
				if(null != totalAddedQtyOfThisBatch) {
					remainingUnits -= totalAddedQtyOfThisBatch;
				}
				if(remainingUnits < 0) {
					errors.add("Insufficient quntity in this batch. Check all the added items");
				}

			}
		}
		
		if(data.size() > Constants.MAX_SALE_ENTRIES) {
			errors.add("Maximum of " + Constants.MAX_SALE_ENTRIES + " items can be sold at one time");
		}

		return errors;
	}

	@FXML public void cancelSale(ActionEvent e) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(e), "Are you sure to cancel this transaction?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {
			
			mainInstance.handleHomeClick(new ActionEvent());
		}
	}
	
	@FXML public void addNewSale(ActionEvent e) {

		Set<String> errors = checkForErrors();
		if(!CollectionUtils.isEmpty(errors)) {
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), Util.getErrorString(errors).toString(), "Please correct the following errors!!", "Error");
		}
		else {

			SaleTransaction saleTransaction = new SaleTransaction();
			// Tranction Info
			SaleTransactionInfo addNewInfo = saleTransaction.addNewInfo();
			addNewInfo.setPatientName(patientName.getText());
			addNewInfo.setPatientAge(Short.valueOf(age.getText()));
			addNewInfo.setDoctorsName(prescribedBy.getText());
			addNewInfo.setDate(currentDate.getValue());
			addNewInfo.setAmount(Double.valueOf(grandTotalLabel.getText()));

			// transaction id, sale_dtls_id should be set by the service
//			long timeInMillis = Calendar.getInstance().getTimeInMillis();
//			addNewInfo.setTransactionId(timeInMillis);

			// SaleDetails
			SaleDetailsList details = saleTransaction.getDetails();
//			int count = 0;
			for (NewSaleTableData aData : data) {
				SaleDetails newSaleDetails = details.addNewSaleDetails();
				newSaleDetails.setDrugId(aData.getItemId());
//				newSaleDetails.setId(timeInMillis + count);
				newSaleDetails.setQuantity(aData.getQunatity());
				newSaleDetails.setUnitPrice(aData.getUnitPrice());
				newSaleDetails.setBatchId(aData.getBatchId());
				newSaleDetails.setName(aData.getItem());
				newSaleDetails.setDiscountPct(aData.getDiscountPct());
//				count ++;
			}

			try {
				// create the transaction
				log.info("Trying to add sale trasaction : " + saleTransaction);
				salesService.addNewSale(saleTransaction);
				log.info("Finished sale trasaction ");
				
				// show confirmation dialog
				DialogResponse response = Dialogs.showConfirmDialog(Meditrax.getPrimaryStage(), "Transaction processed successfully. Do you want to print receipt?", "Success!!", "Information", DialogOptions.YES_NO);
				if(DialogResponse.YES.equals(response)) {
					
					SaleTransactionPrintDomain domain = new SaleTransactionPrintDomain();
					domain.setSaleTx(saleTransaction);
					SalesTransactionPrinter printer = new SalesTransactionPrinter(domain, appService.getStoreInfo());
					printer.printData(false);
				}
				clearWin();

			} catch(Exception ex) {
				log.error("Error while creating transaction", ex);
				if(ex instanceof PrinterException) {
					Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "There was an error while sending the job to the printer", "Failed", "Error!!");
				} else {
					Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "There was an error while creating transaction. Please exit the application and try again", "Failed", "Error!!");
				}
			}
		}
	} 

	private void clearWin() {
		clearInputForm();
		clearInfo();
		clearData();

	}

	private void clearData() {
		data.clear();

	}

	private void clearInfo() {
		patientName.clear();
		prescribedBy.clear();
		age.clear();
	}

	private Set<String> checkForErrors() {

		Set<String> errors = new HashSet<String>();
		if(StringUtils.isBlank(patientName.getText())) {
			errors.add("Patient Name can't be blank");
		}
		if(!Validator.isNumber(age.getText())) {
			errors.add("Age should be number");
		}
		if(StringUtils.isBlank(prescribedBy.getText())) {
			errors.add("Doctor's Name can't be blank");
		}
		if(null == currentDate.getValue() || Constants.DFLT_DT.equals(currentDate.getValue()) ) {
			errors.add("Sale date is invalid");
		}
		if(CollectionUtils.isEmpty(data)) {
			errors.add("Please add at least one sale entry.");
		} 

		return errors;
	}

	private void clearInputForm() {

		itemAutoFill.setText("");
		unitPrice.clear();
		quantity.clear();
		totalPrice.clear();
		drugId.clear();
		drugName.clear();
		currentBatch = null;
		allBatches.getItems().clear();
		pctDisc.clear();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				itemAutoFill.requestFocus();
			}
		});
	}

	@FXML public void removeItem(ActionEvent ae) {
		log.debug("Adding new Sales Row");

		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		DialogResponse response = Dialogs.showConfirmDialog(Meditrax.getPrimaryStage(), "Are you sure to remove the selected item", "Are you sure", "Confirm!!");
		if(DialogResponse.YES.equals(response)) {
			// Show alert first
			if(-1 != selectedIndex) {
				data.remove(selectedIndex);
				recalculateSlNO();
				table.getSelectionModel().clearSelection();
			}

			log.debug("removed Sales Row at index:" + selectedIndex);
		}
	}


	// if items are added or removed to the table data, recalculate the sl no
	private void recalculateSlNO() {
		ObservableList<NewSaleTableData> items = table.getItems();
		int slNo = 1;
		for (NewSaleTableData newSaleTableData : items) {
			newSaleTableData.setSlNo(slNo);
			slNo++;
		}

	}


	InstantDataProvider<DrugDetails> prov = new DrugItemDataProvider();  
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// set the date as current
		currentDate.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
		currentDate.setValue(new Date());

		// initialise handler for 
		allBatches.valueProperty().addListener(allBatchesComboChangeListener);

		// age initialization
		age.setMaxLength(3);
		age.setRestrict("[0-9]");
		
		pctDisc.setMaxLength(2);
		pctDisc.setRestrict("[0-9]");
		
		patientName.setMaxLength(Constants.NM_MAX_LEN);
		prescribedBy.setMaxLength(Constants.NM_MAX_LEN);

		// initialise autocomplete text
		itemAutoFill.setDataProvider(prov);
		itemAutoFill.setAfterSelectDataHandler(afterListViewDataSelectHandler);

		// intialize listener for quantity text field
		quantity.textProperty().addListener(unitsTextChangeListener);
		pctDisc.textProperty().addListener(discPctChangeListener);
		table.getSelectionModel().getSelectedIndices().addListener(rowSelectHandler);

		// table initialization
		data = FXCollections.observableArrayList();
		table.setItems(data);
		slNoCol.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, Integer>("slNo"));
		itemCol.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, String>("item"));
		qtyCol.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, String>("qunatity"));
		upCol.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, String>("unitPrice"));
		tpCol.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, String>("totalPrice"));
		discPercentage.setCellValueFactory(new PropertyValueFactory<NewSaleTableData, Integer>("discountPct"));

		// adding a listener to the table data
		data.addListener(new ListChangeListener<NewSaleTableData>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends NewSaleTableData> c) {
				ObservableList<? extends NewSaleTableData> list = c.getList();

				/*
				 * When an item is added or removed, update the 
				 * batchIdToAvailCountMap properly. Also, update the grand total
				 */
				batchIdToAvailCountMap.clear();
				double grandTotPrice = 0.0;
				for (NewSaleTableData newSaleTableData : list) {
					Integer integer = batchIdToAvailCountMap.get(newSaleTableData.getBatchId());
					int newValue = newSaleTableData.getQunatity();
					if(null != integer) {
						newValue += integer;
						batchIdToAvailCountMap.remove(newSaleTableData.getBatchId());
					} 
					batchIdToAvailCountMap.put(newSaleTableData.getBatchId(), newValue);

					grandTotPrice = grandTotPrice + newSaleTableData.getTotalPrice();
				}
				grandTotalLabel.setText(Util.getInMoneyFormatString(grandTotPrice));


			}

		});
	}



	private void calculateTotal() {
		if( Validator.isNumber(quantity.getText()) && Validator.isMoney(unitPrice.getText())) {
			int qty = Integer.valueOf(quantity.getText());
			float up = Float.valueOf(unitPrice.getText());
			String discPct = pctDisc.getText();
			
			int discPctI = 0;
			if(Validator.isNumber(discPct)) {
				discPctI = Integer.valueOf(discPct);
			}

			this.totalPrice.setText(Util.calculatePrice(up, qty, discPctI));
		} else {
			totalPrice.setText("");
		}
	}

	private ChangeListener<String> unitsTextChangeListener = new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			calculateTotal();
		}

	};
	private ChangeListener<String> discPctChangeListener = new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			calculateTotal();
		}

	};



	private AfterSelectDataHadler<DrugDetails> afterListViewDataSelectHandler = new AfterSelectDataHadler<DrugDetails>() {
		@Override
		public void handle(DrugDetails selectedDrug) {
			allBatches.getItems().clear();
			allBatches.getItems().addAll(selectedDrug.getAllBatches());
			drugId.setText(String.valueOf(selectedDrug.getTargetDrug().getDrugId()));
			drugName.setText(selectedDrug.getTargetDrug().getName());
		}
	};

	private ListChangeListener<Integer> rowSelectHandler = new ListChangeListener<Integer>() {

		@Override
		public void onChanged(javafx.collections.ListChangeListener.Change<? extends Integer> arg0) {

			if(!CollectionUtils.isEmpty(arg0.getList())) {
				removeBut.setDisable(false);
			} else {
				removeBut.setDisable(true);
			}
		}

	};

	private ChangeListener<DrugBatch> allBatchesComboChangeListener = new ChangeListener<DrugBatch>() {

		@Override
		public void changed(ObservableValue<? extends DrugBatch> observable,
				DrugBatch oldValue, DrugBatch newValue) {
			DrugBatch selectedItem = newValue;
			if(null != selectedItem) {

				currentBatch = selectedItem;
				unitPrice.setText(String.valueOf(selectedItem.getRetailPrice()));
				//				batchId.setText(String.valueOf(selectedItem.getDrugBatchId()));
				calculateTotal();
			}
		}
	};

	MainController mainInstance;

	public void setMainInstance(MainController mainInstance) {
		this.mainInstance = mainInstance;
	}

}
