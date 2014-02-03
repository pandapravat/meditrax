package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.PartyService;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class AddPartyController implements Initializable {

	Log log = LogFactory.getLog(getClass()); 
	@FXML RestrictiveTextField partyNm;
	@FXML TextArea partyAddress;
	@FXML RestrictiveTextField contact;

	// service class for making all transactions
	PartyService partyService = ApplicationContextUtil.getInstance(PartyService.class);

	@FXML
	protected void addPartyAction(ActionEvent e) {
		Set<String> checkForErrors = checkForErrors();
		if(CollectionUtils.isEmpty(checkForErrors)) {

			// party id should be created by the service method
			Party party = new Party();
			party.setPartyName(partyNm.getText());
			party.setPartyAddress(partyAddress.getText());
			party.setContactNumber(contact.getText());

			try {
				partyService.addParty(party);
				Dialogs.showInformationDialog(MainController.getMangePartyStage(), "Party " + partyNm.getText() + " added successfully.", "Success!");
				clear();
			} catch(Exception ex) {
				log.error("Error while adding new drug", ex);
				Dialogs.showErrorDialog(MainController.getMangePartyStage(), "An error occurred while adding new party. Pease try again later." , "Error!");
			}
		} else {
			Dialogs.showErrorDialog(MainController.getMangePartyStage(), Util.getErrorString(checkForErrors).toString(), "Please correct the following errors");
		}
	}

	private Set<String> checkForErrors() {
		Set<String> errors = new HashSet<String>();
		if(Validator.isBlank(partyNm.getText())) {
			errors.add("Party name can't be blank");
		}
		// party address is optional, but can't more than specified characters
		if(!Validator.isBlank(partyAddress.getText())) {
			if(partyAddress.getText().length() > Constants.ADD_MAX_LEN) {
				errors.add("Party address can't be more than "  + Constants.ADD_MAX_LEN + "  characters");
			}
		}
		
		if(!Validator.isBlank(contact.getText())) {
			if(contact.getText().length() > Constants.CONTACT_NO_MAX_LEN) {
				errors.add("Contact number can't be more than "  + Constants.CONTACT_NO_MAX_LEN + "  characters");
			}
		}


		return errors;

	}

	@FXML
	protected void clearPartyAction(ActionEvent e) {
		clear();
	}

	private void clear() {
		partyNm.setText("");
		partyAddress.setText("");
		contact.setText("");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		partyNm.setMaxLength(Constants.PARTY_NM_MAX_LEN);
		contact.setMaxLength(Constants.CONTACT_NO_MAX_LEN);
		/*ObservableList<String> items = drugType.getItems();
		items.add("Select");
		DrugType[] values = DrugType.values();
		for (DrugType aDrugType : values) {
			items.add(aDrugType.getValue());
		}
		drugType.getSelectionModel().select(0);*/
	}

}
