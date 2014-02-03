package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.bi.domain.AppConfig;
import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.RestrictiveTextField;

public class AppConfigurationController implements Initializable {
	
	Log log = LogFactory.getLog(getClass());
	@FXML RestrictiveTextField storeName; // Max 30 chars
	@FXML RestrictiveTextField storeAddress1; // Max 40 chars
	@FXML RestrictiveTextField storeAddress2; // Max 40 chars
	@FXML RestrictiveTextField phoneNumber; // Max 15 chars
	@FXML PasswordField userPass;
	@FXML PasswordField confirmUserPass;
	@FXML PasswordField adminPass;
	@FXML PasswordField confirmAdminPass;
	@FXML PasswordField masterPassTF;
	@FXML Label versionInfo;

	ApplicationService appService = ApplicationContextUtil.getInstance(ApplicationService.class);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		storeName.setMaxLength(Constants.STORE_NM_MAX_LEN);
		storeAddress1.setMaxLength(Constants.ADD_LN1_MAX_LEN);
		storeAddress2.setMaxLength(Constants.ADD_LN2_MAX_LEN);
		phoneNumber.setMaxLength(Constants.CONTACT_NO_MAX_LEN);
	}
	@FXML public void configureApp(javafx.event.ActionEvent ae){
		Set<String> errors = checkForErrors();
		if(!CollectionUtils.isEmpty(errors)) {
			Dialogs.showErrorDialog(UxUtils.getStage(ae), Util.getErrorString(errors).toString(), "Please correct the following errors", "Error!!");
		} else {
			DialogResponse showConfirmDialog = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure to set up the application with the above details.", "Config");
			if(DialogResponse.YES.equals(showConfirmDialog)) {
				AppConfig appConfig = new AppConfig();
				StoreInfo storeInfo = appConfig.addNewStoreInfo();
				storeInfo.setStoreName(storeName.getText());
				storeInfo.setAddress1(storeAddress1.getText());
				storeInfo.setAddress2(storeAddress2.getText());
				storeInfo.setPhone(phoneNumber.getText());
				appConfig.setPassword(userPass.getText());
				appConfig.setAdminPassWord(adminPass.getText());
				appConfig.setCreateDBMode(createDbMode);
				appConfig.setVersion(versionInfo.getText());
				
				try {
					appService.setUpApplication(appConfig);
					Dialogs.showInformationDialog(UxUtils.getStage(ae), "Application setup complete. Please restart the application to continue!", "Success!!");
					UxUtils.getStage(ae).close();
				} catch(Exception e) {
					log.error("An error occurred", e);
					Dialogs.showErrorDialog(UxUtils.getStage(ae), "Could not perform the operation", "ERROR!!");
					System.exit(0);
				}
			}
		}
	}

	@FXML public void cancelConfigure(javafx.event.ActionEvent ae) {
		DialogResponse showConfirmDialog = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure to exit set up?", "Confirm Cancel.");
		if(DialogResponse.YES.equals(showConfirmDialog)) {
			UxUtils.getStage(ae).close();
		}
	}
	private Set<String> checkForErrors() {
		Set<String> errors = new HashSet<>();

		if(Validator.isBlank(storeName.getText())){
			errors.add("Store Name can't be blank");
		}
		if(Validator.isBlank(storeAddress1.getText())){
			errors.add("Store address can't be blank");
		}
		if(Validator.isBlank(userPass.getText()) || userPass.getText().length() < Constants.MIN_PWD_LEN || userPass.getText().length() > Constants.MAX_PWD_LEN){
			errors.add("User password should be between " + Constants.MIN_PWD_LEN + " to " + Constants.MAX_PWD_LEN + " characters");
		}
		else if(Validator.isBlank(confirmUserPass.getText())){
			errors.add("Confirm password can't be blank");
		} 
		else if(!confirmUserPass.getText().equals(userPass.getText())) {
			errors.add("User Password and confirm password don't match");
		}
		// validate admin pass
		if(Validator.isBlank(adminPass.getText()) || adminPass.getText().length() < Constants.MIN_PWD_LEN || adminPass.getText().length() > Constants.MAX_PWD_LEN){
			errors.add("Admin password should be between " + Constants.MIN_PWD_LEN + " to " + Constants.MAX_PWD_LEN + " characters");
		}
		else if(Validator.isBlank(confirmAdminPass.getText())){
			errors.add("Confirm admin password can't be blank");
		} else if(!confirmAdminPass.getText().equals(adminPass.getText())) {
			errors.add("Admin Password and confirm password don't match");
		}
		if(!masterPassTF.getText().equals(appService.getSuperAdminPass())){
			errors.add("Incorrect master password.");
		}

		return errors;
	}
	
	public void setCreateDbMode(boolean b) {
		createDbMode = b;
	}

	boolean createDbMode = false;
}
