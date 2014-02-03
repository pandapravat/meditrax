/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.pravat.meditrax.util;

import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 *
 * @author pandpr02
 */
public class UxUtils {
    
    // because of an fx bug. We need to add the anti class for this to take effect
    public static void removeUIControlStyle(Control control, String className, String antiClassName) {
        if(control.getStyleClass().contains(className)) {
            control.getStyleClass().remove(className);
            control.getStyleClass().add(antiClassName);
        }
    }
    
    public static void addUIStyle(Control control, String className) {
        if(!control.getStyleClass().contains(className))
            control.getStyleClass().add(className);
    }

	public static Stage getStage(Event ae) {
		
		Control source = (Control) ae.getSource();
		return getStage(source);
	}
    
	public static Stage getStage(Parent source) {
		
		Stage window = (Stage) source.getScene().getWindow();
		return window;
	}
}
