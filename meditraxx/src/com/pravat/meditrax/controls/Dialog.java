/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.controls;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;

/**
 *
 * @author pandpr02
 */
public class Dialog {
    
    @FXML Label messageLabel;
    @FXML Label detailsLabel;

    
    public void show(Stage stage, String details) {

    	FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource("fxml/dialog.fxml"));
//    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);

    	Parent load;
    	try {
    		load = (Parent) fxmlLoader.load();
    		if(null != details) detailsLabel.setText(details);
    		Scene scene = new Scene(load);
    		stage.setScene(scene);
//    		stage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }
    
    @FXML public void okButtonClick(ActionEvent ae) {
    	UxUtils.getStage(ae).close();
    }
    
    
    
}
