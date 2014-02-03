/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax;

import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.pravat.meditrax.util.Util;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author pandpr02
 */
public class MeditraxPreloader extends Preloader {
    
    ProgressBar bar;
    Stage stage;
    
    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
//        p.setBottom(bar);
        VBox hBox = new VBox();
        Label label2 = new Label("Meditrax");
        label2.setFont(new Font("Tahoma", 18));
        ObservableList<Node> children = hBox.getChildren();
        children.add(label2);
        children.add(bar);
        p.setCenter(hBox);
        
        Label label = new Label("Initializin Meditraxx..Please wait");
        p.setBottom(label);
        Scene scene = new Scene(p, 700, 450);
        scene.getStylesheets().add(Util.getAsResource("styles/all-win.css").toExternalForm());
        return scene;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.initStyle(StageStyle.UNDECORATED);
        this.stage = stage;
        stage.setScene(createPreloaderScene());  
        
        stage.show();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        
        bar.setProgress(pn.getProgress());
    }    
    
}
