package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.util.ApplicationContextUtil;

public class WelcomeController implements Initializable{
	
	@FXML Label welcomeMsg;
	@FXML Label addressLine1;
	@FXML Label addressLine2;
	ApplicationService appSrvc = ApplicationContextUtil.getInstance(ApplicationService.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String text = welcomeMsg.getText();
		String configuredStoreName = appSrvc.getConfigParam(ApplicationService.CLIENT_NM);
		String resolvedMsg = text.replace(":storeName:", configuredStoreName);
		welcomeMsg.setText(resolvedMsg);
		addressLine1.setText(appSrvc.getConfigParam(ApplicationService.CLIENT_ADD_1));
		addressLine2.setText(appSrvc.getConfigParam(ApplicationService.CLIENT_ADD_2));
	}

}
