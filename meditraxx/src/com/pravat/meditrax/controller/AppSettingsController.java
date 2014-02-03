package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.Util;

public class AppSettingsController implements Initializable{

	@FXML ImageView imagePlaceHolder;
	@FXML ComboBox<String> styleSelect;
	@FXML PasswordField newPassword;
	@FXML PasswordField confirmNewPass;
	
	String initialStyleValue;
	ApplicationService instance = ApplicationContextUtil.getInstance(ApplicationService.class);
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Set<String> allStyleKeys = Util.getAllStyleKeys();
		styleSelect.getItems().clear();
		styleSelect.getItems().addAll(allStyleKeys);
		
		// seelct the existing image and property
		String existingBgKey = instance.getConfigParam(ApplicationService.PREF_BG);
		initialStyleValue = existingBgKey;
		if(StringUtils.isNotBlank(existingBgKey)) {
			styleSelect.getSelectionModel().select(existingBgKey);
			String backGroundByKey = Util.getBackGroundByKey(existingBgKey);
			imagePlaceHolder.setImage(new Image(backGroundByKey));
		}

		// set the listener to take effect when the combo is changed
		styleSelect.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				System.out.println(newValue);
				String backGroundByKey = Util.getBackGroundByKey(newValue);
				imagePlaceHolder.setImage(new Image(backGroundByKey));
			}
		});
	}

	@FXML public void onOkClick(ActionEvent ae) {

		Set<String> errors = checkForErrors();
		if(!CollectionUtils.isEmpty(errors)) {
			Dialogs.showErrorDialog(parentStage, Util.getErrorString(errors), "Please correct the errors", "Error");
		} else {
			ApplicationService instance = ApplicationContextUtil.getInstance(ApplicationService.class);
			try {
				instance.updateSettings(styleSelect.getSelectionModel().getSelectedItem(), newPassword.getText());
				Dialogs.showInformationDialog(parentStage, "Setting updated successfully. Please restart the application for the changes to reflect.", "Success!!");
				parentStage.close();
			}catch(Exception e) {
				log.error("Error while updating settings", e);
				Dialogs.showErrorDialog(parentStage, "Error while updating settings", "Error");
			}
		}

	}
	private Set<String> checkForErrors() {
		Set<String> errors = new HashSet<>();

		boolean changed = false;
		String selectedItem = styleSelect.getSelectionModel().getSelectedItem();
		if(!initialStyleValue.equals(selectedItem)) {
			changed = true;
		}
		
		

		else if(!(StringUtils.isBlank(newPassword.getText()) && StringUtils.isBlank(confirmNewPass.getText()))) {
			changed = true;
			if(newPassword.getText().length() < Constants.MIN_PWD_LEN || newPassword.getText().length() > Constants.MAX_PWD_LEN){
				errors.add("New password should be between " + Constants.MIN_PWD_LEN + " to " + Constants.MAX_PWD_LEN + " characters");
			}
			else if((StringUtils.isNotBlank(newPassword.getText()) || StringUtils.isNotBlank(confirmNewPass.getText()))
					&& !StringUtils.equals(newPassword.getText(), confirmNewPass.getText())){
				errors.add("New Password and confirm new password don't match");
			} else if(newPassword.getText().length() < 6) {
				errors.add("Password should be atleast 6 characters");
			}
		}
		if( CollectionUtils.isEmpty(errors) & !changed) {
			errors.add("Nothing to change");
		}
		return errors;
	}

	@FXML public void onCancelClick(ActionEvent ae) {
		parentStage.close();
	}

	public void setParentStage(Stage stage) {
		this.parentStage = stage;
	}
	org.apache.commons.logging.Log log = LogFactory.getLog(getClass());
	Stage parentStage;

}
