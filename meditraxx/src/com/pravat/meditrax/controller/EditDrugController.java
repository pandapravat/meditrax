package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.mytdev.javafx.internal.data.AfterSelectDataHadler;
import com.mytdev.javafx.internal.data.InstantDataProvider;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import com.pravat.meditrax.bi.DrugService;
import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugType;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class EditDrugController implements Initializable {

	Log log = LogFactory.getLog(getClass()); 

	@FXML
	AutoCompleteTextField<Drug> searchDrugTF;

	@FXML
	RestrictiveTextField drugNm;

	@FXML
	RestrictiveTextField mfrNm;

	@FXML
	TextField drugId;

	@FXML
	ComboBox<String> drugType;

	// initialise the drug service
	DrugService drugService = ApplicationContextUtil.getInstance(DrugService.class);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		drugNm.setMaxLength(Constants.DRG_NM_MAX_LEN);
		mfrNm.setMaxLength(Constants.MFR_NM_MAX_LEN);
		ObservableList<String> items = drugType.getItems();
		items.add("Select");
		DrugType[] values = DrugType.values();
		for (DrugType aDrugType : values) {
			items.add(aDrugType.getValue());
		}
		drugType.getSelectionModel().select(0);
		// initialise autocomplete field
		searchDrugTF.setDataProvider(new InstantDataProvider<Drug>() {
			
			@Override
			public ObservableList<Drug> getData(String arg0) {
				DrugsDao instance = ApplicationContextUtil.getInstance(DrugsDao.class);
				List<Drug> searchDrugs = instance.searchDrugs(arg0);
				ObservableList<Drug> arrayList = FXCollections.observableArrayList(searchDrugs);
				return arrayList;
			}
		});
		searchDrugTF.setAfterSelectDataHandler(afterSelectDataHadler);

	}
	@FXML
	protected void updateDrugAction(ActionEvent e) {
		
		Set<String> checkForErrors = checkForErrors();
		if(CollectionUtils.isEmpty(checkForErrors)) {

			if(Validator.isNumber(drugId.getText())) {
				Drug drug = new Drug();
				drug.setDrugId(Long.parseLong(drugId.getText()));
				drug.setMfr(mfrNm.getText());
				drug.setName(drugNm.getText());
				drug.setType(drugType.getValue());

				try {
					drugService.updateDrug(drug);
					Dialogs.showInformationDialog(MainController.getManageDrugStage(), "Drug " + drugNm.getText() + " updated successfully.", "Success!");
				} catch(Exception ex) {
					log.error("Error while adding new drug", ex);
					Dialogs.showErrorDialog(MainController.getManageDrugStage(), "An error occurred while updating new drug. Pease try again later." , "Error!");
				}
			}
		} else {
			Dialogs.showErrorDialog(MainController.getManageDrugStage(), Util.getErrorString(checkForErrors).toString(), "Please correct the following errors");
		}
	}
	
	private Set<String> checkForErrors() {

		Set<String> errors = new HashSet<String>();
		if(Validator.isBlank(drugNm.getText()) ) {
			errors.add("Drug name can't be blank");
		}
		if(Validator.isBlank(mfrNm.getText())) {
			errors.add("Manufacturer name can't be blank");
		}
		if(0 == drugType.getSelectionModel().getSelectedIndex()) {
			errors.add("Please select a drug type");
		}

		return errors;
	}
	@FXML
	protected void removeDrugction(ActionEvent e) {
		if(Validator.isNumber(drugId.getText())) {
			DialogResponse dialog = Dialogs.showWarningDialog(MainController.getManageDrugStage(), 
					"The selected drug will be permamnetly removed. "
					+ "Are you sure to perform this action?", "Confirm!", "Warning!", DialogOptions.YES_NO);
			if(DialogResponse.YES.equals(dialog)) {
				try {
					
					drugService.deleteDrug(Long.valueOf(drugId.getText()));
					Dialogs.showInformationDialog(MainController.getManageDrugStage(), "Drug Removed Successfully!!", "Success");
					clearForm();
				} catch(Exception ex) {
					log.error("Error deleting drug..", ex);
					Dialogs.showErrorDialog(thisStage, "Error removing drug. Please try again later..", "Error!!");
				}
			}
		}
	}
	
	@FXML
	protected void clearDrugction(ActionEvent e) {
		clearForm();
	}
	
	

	private void clearForm() {
		searchDrugTF.setText("");
		drugId.setText("");
		drugNm.setText("");
		mfrNm.setText("");
		drugType.getSelectionModel().select(0);
	}
	
	AfterSelectDataHadler<Drug> afterSelectDataHadler = new AfterSelectDataHadler<Drug>() {

		@Override
		public void handle(Drug drug) {
			drugId.setText(String.valueOf(drug.getDrugId()));
			drugNm.setText(drug.getName());
			mfrNm.setText(drug.getMfr());
			drugType.getSelectionModel().select(drug.getType());
		}
	};

	Stage thisStage;

	public void setThisStage(Stage thisStage) {
		this.thisStage = thisStage;
	}
}
