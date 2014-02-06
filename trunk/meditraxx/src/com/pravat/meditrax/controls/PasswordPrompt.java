package com.pravat.meditrax.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;

public final class PasswordPrompt {
	
	
	public  String show(Stage owner, String  optionalMessage) {
		
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.initModality(Modality.APPLICATION_MODAL);
	    stage.initOwner(Meditrax.getPrimaryStage());
		stage.setScene(new Scene(showGUI(optionalMessage), 350, 200));
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.getIcons().add(new Image(Util.getAsInputStream("images/meditrax.png")));
		stage.setTitle("Meditrax - Enter password");
		stage.showAndWait();
		
		
		return pb.getText();
	}

	final PasswordField pb = new PasswordField();
	private VBox showGUI(String optionalMessage) {
		// label if any
		final Label message = new Label(optionalMessage);
		message.setTextFill(Color.rgb(21, 117, 84));

		
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10, 10, 10, 10));
//		vb.setV
		vb.setSpacing(10);
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);

		Label label = new Label("Enter Password");
		label.setFont(new Font("Tahoma", 14));
		
		Button okBut = new Button("OK");
		Button closeBut = new Button("Close");
		
		HBox hb1 = new HBox();
		hb1.setAlignment(Pos.CENTER);
		hb1.setSpacing(5);
		hb1.getChildren().addAll(okBut, closeBut);
		
		closeBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		okBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				UxUtils.getStage(event).close();
			}
		});
		
		pb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				UxUtils.getStage(event).close();
			}
		});

		hb.getChildren().addAll(label, pb);
		vb.getChildren().addAll(hb, message, hb1);
		
		return vb;
	}

}
