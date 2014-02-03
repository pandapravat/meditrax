package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.Pane;

import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.PropertyLoader;
import com.pravat.meditrax.util.Util;

public class ManagePartyController implements Initializable {
	
	Log log = LogFactory.getLog(getClass());
	@FXML
	Pane addPartyTabContent;
	
	@FXML
	Pane editPartyTabContent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize the tabs
		try {
			
			
			// include add party
			URL addPartyRsc = Util.getAsResource(Constants.ADD_PARTY);
			Parent addPartyWin = FXMLLoader.load(addPartyRsc, PropertyLoader.getBundle());
			addPartyTabContent.getChildren().clear();
			addPartyTabContent.getChildren().add(addPartyWin);
			
			URL editPartyRsc = Util.getAsResource(Constants.EDIT_PARTY);
			Parent editPartyWin = FXMLLoader.load(editPartyRsc, PropertyLoader.getBundle());
			editPartyTabContent.getChildren().clear();
			editPartyTabContent.getChildren().add(editPartyWin);
			
			
		} catch (Exception e) {
			log.error("Error while loading Manage Party screen!", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Error while loading Manage Party screen!", "Error!");
		}
	}
}
