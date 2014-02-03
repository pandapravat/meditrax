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
import com.pravat.meditrax.bi.PartyService;
import com.pravat.meditrax.bi.dao.PartyDao;
import com.pravat.meditrax.bi.domain.Party;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class EditPartyController implements Initializable{

	Log log = LogFactory.getLog(getClass()); 
	@FXML AutoCompleteTextField<Party> searchPartyTF;
	@FXML TextField partyId;
	@FXML RestrictiveTextField partyName;
	@FXML TextArea address;
	@FXML RestrictiveTextField contactNumber;
	
	PartyService partyService;
	public EditPartyController() {
		partyService = ApplicationContextUtil.getInstance(PartyService.class);
	}

	@FXML protected void removePartyction(ActionEvent ae) {

		Set<String> checkForErrors = checkForErrors();
		if(!CollectionUtils.isEmpty(checkForErrors)) {
			Dialogs.showErrorDialog(MainController.getMangePartyStage(), Util.getErrorString(checkForErrors).toString(), "Please correct the following errors");
		} else {
			DialogResponse response = Dialogs.showWarningDialog(MainController.getMangePartyStage(), "This action will remove the party. Are you sure", "Are you sure?", "Warning", DialogOptions.YES_NO);
			if(DialogResponse.YES.equals(response)) {
				try {
					partyService.removeParty(Long.valueOf(partyId.getText()));
					Dialogs.showInformationDialog(MainController.getMangePartyStage(), "Party removed successfully", "Succes!!");
				} catch(Exception es) {
					log.error("Error while removing party", es);
					Dialogs.showErrorDialog(MainController.getMangePartyStage(), "An error occurred while removing party. Please try again later", "Error!!");
				}
			}
		}


	}
	private Set<String> checkForErrors() {
		Set<String> errors = new HashSet<String>();
		if(!Validator.isNumber(partyId.getText())) {
			errors.add("Party id should be number");
		}
		if(Validator.isBlank(partyName.getText())) {
			errors.add("Party name can't be blank");
		}
		// party address is optional, but can't more than specified characters
		if(!Validator.isBlank(address.getText())) {
			if(address.getText().length() > Constants.ADD_MAX_LEN) {
				errors.add("Party address can't be more than "  + Constants.ADD_MAX_LEN + "  characters");
			}
		}
		if(!Validator.isBlank(contactNumber.getText())) {
			if(contactNumber.getText().length() > Constants.CONTACT_NO_MAX_LEN) {
				errors.add("Contact number can't be more than "  + Constants.CONTACT_NO_MAX_LEN + "  characters");
			}
		}
		
		

		return errors;

	}
	@FXML protected void updatePartyAction(ActionEvent ae) {
		Set<String> checkForErrors = checkForErrors();
		if(!CollectionUtils.isEmpty(checkForErrors)) {
			Dialogs.showErrorDialog(MainController.getMangePartyStage(), Util.getErrorString(checkForErrors).toString(), "Please correct the following errors");
		} else {
			Party party = new Party();
			party.setPartyId(Long.valueOf(partyId.getText()));
			party.setPartyName(partyName.getText());
			party.setPartyAddress(address.getText());
			party.setContactNumber(contactNumber.getText());

			try {
				partyService.updateParty(party);
				Dialogs.showInformationDialog(MainController.getMangePartyStage(), "Party Updated successfully", "Succes!!");
			} catch(Exception ex) {
				log.error("Error while updating party", ex);
				Dialogs.showErrorDialog(MainController.getMangePartyStage(), "An error occurred while updating party. Please try again later", "Error!!");
			}
		}
	}
	@FXML protected void clearPartyAction(ActionEvent ae) {
		clear();
	}


	private void clear() {
		searchPartyTF.setText("");
		partyId.setText("");
		partyName.setText("");
		address.setText("");
		contactNumber.setText("");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		partyName.setMaxLength(Constants.PARTY_NM_MAX_LEN);
		contactNumber.setMaxLength(Constants.CONTACT_NO_MAX_LEN);
		// initialise autocomplete field
		searchPartyTF.setDataProvider(new InstantDataProvider<Party>() {

			@Override
			public ObservableList<Party> getData(String arg0) {
				PartyDao instance = ApplicationContextUtil.getInstance(PartyDao.class);
				List<Party> searchDrugs = instance.searchPartyByName(arg0);

				ObservableList<Party> arrayList = FXCollections.observableArrayList(searchDrugs);
				return arrayList;
			}
		});
		searchPartyTF.setAfterSelectDataHandler(afterSelectDataHadler);

	}

	AfterSelectDataHadler<Party> afterSelectDataHadler = new AfterSelectDataHadler<Party>() {

		@Override
		public void handle(Party party) {
			partyId.setText(String.valueOf(party.getPartyId()));
			partyName.setText(party.getPartyName());
			address.setText(party.getPartyAddress());
			contactNumber.setText(party.getContactNumber());
		}
	};

}
