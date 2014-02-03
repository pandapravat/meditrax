package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.Pane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.PropertyLoader;
import com.pravat.meditrax.util.Util;

public class ManageDrugsController implements Initializable{

	Log log = LogFactory.getLog(getClass());

	@FXML
	Pane manageDrugPane;
	
	@FXML
	Pane addDrugTabContent;
	
	@FXML
	Pane editDrugTabContent;
	
	@FXML
	Pane addPartyTabContent;
	
	@FXML
	Pane editPartyTabContent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize the tabs
		try {
			URL asResource = Util.getAsResource(Constants.ADD_DRUG);
			Parent addDrug = FXMLLoader.load(asResource, PropertyLoader.getBundle());
			addDrugTabContent.getChildren().clear();
			addDrugTabContent.getChildren().add(addDrug);
			
			// include edit drug
			URL editResource = Util.getAsResource(Constants.EDIT_DRUG);
			Parent editDrug = FXMLLoader.load(editResource, PropertyLoader.getBundle());
			editDrugTabContent.getChildren().clear();
			editDrugTabContent.getChildren().add(editDrug);
			
			
			
		} catch (Exception e) {
			log.error("Error while loading Manage drugs screen!", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Error while loading Manage drugs screen!", "Error!");
		}
	}



}
