package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.DrugService;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugType;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class AddDrugController implements Initializable {

	Log log = LogFactory.getLog(getClass()); 
	@FXML
	RestrictiveTextField drugNm;

	@FXML
	RestrictiveTextField mfrNm;

	@FXML
	ComboBox<String> drugType;

	// initialize drug service
	DrugService drugService = ApplicationContextUtil.getInstance(DrugService.class);
	@FXML
	protected void addDrugAction(ActionEvent e) {

		Set<String> errors = checkForErrors();
		if(CollectionUtils.isEmpty(errors)) {

			// Drug id will be created by the sequence and set in the drug
			Drug drug = new Drug();
			drug.setMfr(mfrNm.getText());
			drug.setName(drugNm.getText());
			drug.setType(drugType.getValue());

			try {
				drugService.addDrug(drug);
				Dialogs.showInformationDialog(MainController.getManageDrugStage(), "Drug " + drugNm.getText() + " added successfully.", "Success!");
				clear();
			} catch(Exception ex) {
				log.error("Error while adding new drug", ex);
				Dialogs.showErrorDialog(MainController.getManageDrugStage(), "An error occurred while adding new drug. Pease try again later." , "Error!");
			}
		} else {
			Dialogs.showErrorDialog(MainController.getManageDrugStage(), Util.getErrorString(errors).toString(), "Please correct the following errors");
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
	protected void clearDrugAction(ActionEvent e) {
		clear();
	}

	private void clear() {
		drugNm.setText("");
		mfrNm.setText("");
		drugType.getSelectionModel().select(0);
	}
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
	}
}
