/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.bi.dao.AppConfigDao;
import com.pravat.meditrax.controller.AppConfigurationController;
import com.pravat.meditrax.controls.Dialog;
import com.pravat.meditrax.controls.PasswordPrompt;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.AuthUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.PropertyLoader;
import com.pravat.meditrax.util.Util;

/**
 *
 * @author pandpr02
 */
public class Meditrax extends Application {

	//    Node placeHolder = null;

	public static Stage thisStage;
	@Override
	public void start(Stage stage) throws Exception {
		thisStage = stage;

		setPreloaderNotification(0.2);
		int initializeApp = initializeApplication();

		if(INTITIALIZED == initializeApp) {
			showApplication(stage);
		} else if(NOT_INIITIALIZED == initializeApp) {
			showInitializeAppScreen(stage);
		} else if(DUPLICATE_INSTANCE == initializeApp) {
			showDuplicateInstanceError(stage);
			//			System.exit(0);
		} else if(OTHER_ERROR == initializeApp) {
			showOtherError(stage);
			//			System.exit(0);
		}
		setPreloaderNotification(1.0);

		stage.getIcons().add(new Image(Util.getAsInputStream("images/meditrax.png")));

		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				DialogResponse showConfirmDialog = Dialogs.showConfirmDialog(thisStage, "Are you sure?", "Exit");
				if(DialogResponse.YES.equals(showConfirmDialog)) {
					Util.prepareExit();
					System.exit(0);
				} else {
					event.consume();
				}
			}
		});

	}


	private void setPreloaderNotification(double d) {
		notifyPreloader(new Preloader.ProgressNotification(d));
	}


	private void showOtherError(Stage stage) {

		Dialog dialog = new Dialog();
		dialog.show(stage, "An internal error occurred. Please check with the administrator!!");
	}


	private void showDuplicateInstanceError(Stage stage) {
		Dialog dialog = new Dialog();
		dialog.show(stage, "An instance of the application might already be running. Please close the application before proceeding!!");
	}


	private void showInitializeAppScreen(Stage stage) {

		Parent root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.CONFIGURE_APP), PropertyLoader.getBundle());
			root = (Parent) fxmlLoader.load();
			AppConfigurationController controller = fxmlLoader.getController();
			controller.setCreateDbMode(true);
			//			Screen primary = Screen.getPrimary();
			Scene scene = new Scene(root);
			stage.setTitle("Configure Meditrax");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void showApplication(Stage stage) throws IOException {

		// load the configuration parameters for this client
		ApplicationService instance = ApplicationContextUtil.getInstance(ApplicationService.class);
		instance.loadConfig();

		// before showing the application ask for password. 
		//This is important the we show this before loading the main win
		showPasswordConfirmation(stage);
		Parent root = FXMLLoader.load(Util.getAsResource(Constants.MAIN_WITH_WELCOME_WIN), 
				PropertyLoader.getBundle());

		// setup the primary screen
		Scene scene = new Scene(root);

		// set the scene background
		setBackground(scene.getRoot(), instance);
		stage.setTitle("MediTrax - " + instance.getConfigParam(ApplicationService.CLIENT_NM));
		stage.setScene(scene);
		
		// fit the stage on the screen
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
	}


	private void setBackground(Parent parent, ApplicationService instance) {

		String property = instance.getConfigParam(ApplicationService.PREF_BG);
		String form = Util.getBackGroundByKey(property);
		parent.setStyle("-fx-background-image: url('" + form + "');");
	}

	/**
	 * Shows the password to enter into the application. If wrong password is entered
	 * for 3 times, it exits the application.
	 */
	private void showPasswordConfirmation(Stage stage) {
		boolean authenticated = false;
		boolean exit = false;
		int count = 1;
		do{
			// Show password for three times
			PasswordPrompt passwordPrompt = new PasswordPrompt();
			String message = "";
			if(count > 1) {
				message = "Invalid login attempt. (" + count + " of 3)";
			}
			String showInputDialog = passwordPrompt.show(stage, message);
			authenticated = AuthUtil.authenticateAndSet(showInputDialog);
			count ++;
			if(count > 3) {
				exit = true;
			}

		} while(!authenticated && !exit);
		if(exit) {
			stage.close();
			System.exit(0);
		}
	}


	private int initializeApplication() {

		setPreloaderNotification(0.4);
		int returnCode = OTHER_ERROR;
		try {
			ApplicationContextUtil.initSpring();

			AppConfigDao instance = ApplicationContextUtil.getInstance(AppConfigDao.class);
			setPreloaderNotification(0.6);
			// try to get some data from table
			instance.pingDb();
			returnCode =  INTITIALIZED;

		} catch (Exception e) {
			e.printStackTrace();
			writeExceptionStack(e);

			SQLException se = getSQLExceptionIfAny(e);
			if(null == se) {
				returnCode = OTHER_ERROR;
			} else {
				if(40000 == se.getErrorCode()) { // when another instance is running
					returnCode = DUPLICATE_INSTANCE;
				} else if("42X05".equals(se.getSQLState())){
					returnCode = NOT_INIITIALIZED;
				}

			}/*
			if(e instanceof DataAccessException) {
				DataAccessException dae = (DataAccessException) e;
				Throwable mostSpecificCause = dae.getMostSpecificCause();
				if(mostSpecificCause instanceof StandardException) {
					StandardException sqlException = (StandardException) mostSpecificCause;
					int errorCode = sqlException.getErrorCode();
					if(40000 == errorCode) { // when another instance is running
						returnCode = DUPLICATE_INSTANCE;
					}
					else if("42X05".equals(sqlException.getSQLState())){
						returnCode = NOT_INIITIALIZED;
					}
				} else if(mostSpecificCause instanceof SQLException) {

					SQLException sqlException = (SQLException) mostSpecificCause;
					if(40000 == sqlException.getErrorCode()) { // when another instance is running
						returnCode = DUPLICATE_INSTANCE;
					}
				}

			}  
			else if(e instanceof CannotCreateTransactionException) {

				CannotCreateTransactionException exception = (CannotCreateTransactionException) e;

				Throwable mostSpecificCause = exception.getMostSpecificCause();

				returnCode = OTHER_ERROR;
			}*/
		}

		setPreloaderNotification(0.7);
		return returnCode;
	}


	private SQLException getSQLExceptionIfAny(Throwable e) {
		Throwable cause = e.getCause();
		if(null != cause) {
			if(cause instanceof SQLException) {
				return (SQLException) cause;
			} else {
				return getSQLExceptionIfAny(cause);
			}
		}
		return null;
	}


	private void writeExceptionStack(Exception e) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(System.getProperty("user.home") + "/meditraxStartUpErr.txt", true);
			fileWriter.write("============================================\n");
			fileWriter.write(Calendar.getInstance().toString());
			fileWriter.write("\n========================================");
			e.printStackTrace(new PrintWriter(fileWriter));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
			if(null != fileWriter)
				try {
					fileWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
	}

	public static final int INTITIALIZED = 0;
	public static final int NOT_INIITIALIZED = 1;
	public static final int DUPLICATE_INSTANCE = 2;
	public static final int OTHER_ERROR = 3;

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return thisStage;
	}

}
