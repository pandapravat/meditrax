/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.controller;


import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pravat.meditrax.exception.MeditraxException;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.AuthUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.PropertyLoader;
import com.pravat.meditrax.util.Util;

/**
 *
 * @author pandpr02
 */
public class MainController implements Initializable {

	Log log = LogFactory.getLog(getClass());
	@FXML Pane placeHolder;

	@FXML protected void handleHomeClick(ActionEvent event) {
		log.debug("loading home");
		placeHolder.getChildren().clear();
		try {
			Node load = FXMLLoader.load(Util.getAsResource(Constants.WELCOME_WIN),
					PropertyLoader.getBundle());
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			log.error("Unable to load home window ", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load home window ", "Error!!");
		}

		log.debug("loaded new home");
	}

	@FXML protected void handleNewSales(ActionEvent event) {
		log.debug("loading new sales");
		placeHolder.getChildren().clear();
		try {
			FXMLLoader loader = new FXMLLoader(Util.getAsResource(Constants.SALES_WIN), PropertyLoader.getBundle());
			Node load = (Node) loader.load();
			NewSalesController controller = loader.getController();
			controller.setMainInstance(this);
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			log.error("Unable to load sles window ", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load sles window ", "Error!!");
		}

		log.debug("Loaded Sales Window");
	}
	@FXML protected void handleNewPurchase(ActionEvent event) {
		log.debug("loading new purchase");
		placeHolder.getChildren().clear();
		try {
			FXMLLoader loader = new FXMLLoader(Util.getAsResource(Constants.PURCHASE_WIN), PropertyLoader.getBundle());
			Node load = (Node) loader.load();
			PurchaseController controller = loader.getController();
			controller.setMainInstance(this);
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			log.error("Could not load purchase window", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load purchase window ", "Error!!");
		}

		log.debug("loaded new purchase");
	}




	@FXML protected void handleManageDrug(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.MANAGE_DRUG), PropertyLoader.getBundle());
		Parent load;
		try {
			load = (Parent) fxmlLoader.load();
			// make the stage modal
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Meditrax.getPrimaryStage());
			stage.setScene(new Scene(load));
			stage.setResizable(false);
			stage.setTitle("Manage Drugs!!");
			stage.show();
			mangeDrugStage = stage;
		} catch (IOException e) {
			log.error("Could not load handleManageDrug", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading Manage Drug stage", "Error!!");
		}
		log.debug("loaded add new drug");
	}

	@FXML protected void handleManageParty(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.MANAGE_PARTY), PropertyLoader.getBundle());
		Parent load;
		try {
			load = (Parent) fxmlLoader.load();
			// make the stage modal
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Meditrax.getPrimaryStage());
			stage.setScene(new Scene(load));
			stage.setResizable(false);
			stage.setTitle("Manage Party!!");
			stage.show();
			mangePartyStage = stage;
		} catch (IOException e) {
			log.error("Could not load handleManageParty", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading Manage party stage", "Error!!");
		}
		log.debug("loaded add new drug");
	}

	@FXML protected void handleSettings(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.SETTINGS_VIEW), PropertyLoader.getBundle());
		Parent load;
		try {
			load = (Parent) fxmlLoader.load();
			// make the stage modal
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Meditrax.getPrimaryStage());
			stage.setScene(new Scene(load));
			stage.setResizable(false);
			stage.setTitle("Settings!");
			stage.show();

			// set the stage in controller
			AppSettingsController controller = fxmlLoader.getController();
			controller.setParentStage(stage);
		} catch (IOException e) {
			log.error("Could not load handleManageParty", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading Manage party stage", "Error!!");
		}
		log.debug("loaded add new drug");
	}

	// Drug availability report
	@FXML protected void  handleDrugRpt(ActionEvent event) {
		try {
			Node load = FXMLLoader.load(Util.getAsResource(Constants.DRUG_RPT_WIN),
					PropertyLoader.getBundle());
			placeHolder.getChildren().clear();
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			log.error("Could not load handleDrugRpt window", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load Drug Report window ", "Error!!");
		}
	}

	@FXML protected void handleExit(ActionEvent event) {

		DialogResponse showConfirmDialog = Dialogs.showConfirmDialog(Meditrax.getPrimaryStage(), "Are you sure?", "Exit");
		if(DialogResponse.YES.equals(showConfirmDialog)) {
			Util.prepareExit();
			System.exit(0);
		}
	}

	/***************************************************************************************
	 **********************************SALES REPORTS******************************************
	 ****************************************************************************************/
	@FXML protected void handleCustomSalesRpt(ActionEvent event) {
		log.debug("..loading new custom sales report");
		intialiseCustomReportScene(null, null, event);

		log.debug("..loaded custom sales report screen");
	}
	@FXML protected void handleWeeklySalesRpt(ActionEvent event) {
		log.debug("..loading new custom sales report");
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_MONTH, -7);
		intialiseCustomReportScene(instance.getTime(), new Date(), event);

		log.debug("..loaded custom sales report screen");
	}
	@FXML protected void handleMonthlySalesRpt(ActionEvent event) {
		log.debug("..loading new custom sales report");
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.MONTH, -1);
		intialiseCustomReportScene(instance.getTime(), new Date(), event);

		log.debug("..loaded custom sales report screen");
	}

	private void intialiseCustomReportScene(Date fromDate, Date toDate, Event event) throws MeditraxException {
		placeHolder.getChildren().clear();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.SALES_RPT_WIN), PropertyLoader.getBundle());
			Pane load = (Pane)fxmlLoader.load();
			placeHolder.getChildren().add(load);

			//initialise controller if required
			SalesReportController controller = fxmlLoader.getController();
			controller.setMainInstance(this);

			if(null != fromDate)
				controller.setFromDate(fromDate);
			if(null != toDate)
				controller.setToDate(toDate);
			controller.getGoButton().fireEvent(new ActionEvent() );

		} catch(Exception e) {
			log.error("Unable to load custom sales scene ", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load custom sales scene ", "Error!!");
			throw new MeditraxException("Unable to load custom sales scene ", e);
		}
	}

	/***************************************************************************************
	 **********************************PURCHASE REPORTS******************************************
	 ****************************************************************************************/
	@FXML protected void onPurchaseReportAction(ActionEvent event) {
		log.debug("..loading custom purchase report");
		intialiseCustomPurchaseReport(null, null, event);
		log.debug("..loaded custom purchase report screen");
	}

	private void intialiseCustomPurchaseReport(Date fromDate, Date toDate, Event event) throws MeditraxException {
		placeHolder.getChildren().clear();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.PURCHASE_RPT_WIN), PropertyLoader.getBundle());
			Pane load = (Pane)fxmlLoader.load();
			placeHolder.getChildren().clear();
			placeHolder.getChildren().add(load);

			//initialise controller if required
			PurchaseReportController controller = fxmlLoader.getController();

			controller.setMainInstance(this);
			if(null != fromDate)
				controller.setFromDate(fromDate);
			if(null != toDate)
				controller.setToDate(toDate);
			controller.getGoButton().fireEvent(new ActionEvent());

		} catch(Exception e) {
			log.error("Unable to load custom purchase scene ", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load custom purchase report screen", "Error!!");
			throw new MeditraxException("Unable to load custom purchase report screen", e);
		}
	}

	// Purchase return
	@FXML protected void onPurchaseReturnAction(ActionEvent event) {
		log.debug("..loading purchase return scene report");
		placeHolder.getChildren().clear();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.PURCHASE_RTRN_WIN), PropertyLoader.getBundle());
			Pane load = (Pane)fxmlLoader.load();
			PurchaseReturnController controller = fxmlLoader.getController();
			controller.setMainInstance(this);
			placeHolder.getChildren().clear();
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			log.error("Unable to load purchase return screen", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Unable to load purchase return screen", "Error!!");
			throw new MeditraxException("Unable to load purchase return screen", e);
		}
		log.debug("..loaded purchase return scene report");
	}
	/**********************************ABOUT*****************************************/
	@FXML protected void handleAboutClick(ActionEvent event) {

		log.debug("loading new about");
		placeHolder.getChildren().clear();
		try {
			Node load = FXMLLoader.load(Util.getAsResource(Constants.ABOUT_WIN),
					PropertyLoader.getBundle());
			placeHolder.getChildren().add(load);
		} catch(Exception e) {
			throw new MeditraxException("Unable to load about window ", e);
		}

		log.debug("loaded about screen");
	}

	public static Stage getManageDrugStage() {
		return mangeDrugStage;
	}

	private static Stage mangeDrugStage;
	private static Stage mangePartyStage;

	public static Stage getMangePartyStage() {
		return mangePartyStage;
	}


	/*******************************************************************************************
	 * ***************************************CREATE BACKUP*************************************
	 * *****************************************************************************************/
	@FXML protected void createBackUp(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.BACKUP_VIEW), PropertyLoader.getBundle());
		Parent load;
		try {
			load = (Parent) fxmlLoader.load();
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Meditrax.getPrimaryStage());
			stage.setScene(new Scene(load));
			stage.setResizable(false);
			stage.setTitle("Create Backup");
			stage.show();
		}catch (IOException e) {
			log.error("Could not load showQueryView", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading showQueryView stage", "Error!!");
		}
	}

	/*******************************************************************************************
	 * *******************************QUERY VIEW - SUPER ADMIN**********************************
	 * *****************************************************************************************/

	@FXML protected void showQueryView(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.ADMIN_QUERY_VIEW), PropertyLoader.getBundle());
		Parent load;
		try {
			load = (Parent) fxmlLoader.load();
			// make the stage modal
			Stage stage = new Stage(StageStyle.DECORATED);
			//			stage.initModality(Modality.WINDOW_MODAL);
			//		    stage.initOwner(Meditrax.getPrimaryStage());
			stage.setScene(new Scene(load));
//			stage.setResizable(false);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			// full screens
			stage.setX(primaryScreenBounds.getMinX());
			stage.setY(primaryScreenBounds.getMinY());
			stage.setWidth(primaryScreenBounds.getWidth());
			stage.setHeight(primaryScreenBounds.getHeight());
			stage.setTitle("SQL Editor..");
			stage.show();
			
			SuperAdminController controller = fxmlLoader.getController();
			controller.additionalInit();
		} catch (IOException e) {
			log.error("Could not load showQueryView", e);
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading showQueryView stage", "Error!!");
		}
		log.debug("loaded showQueryView");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(!AuthUtil.isSuperAdmin()) {
			// show queryview for admin user only
			queryViewMI.getParentMenu().getItems().removeAll(queryViewMI);
		}
		if(!AuthUtil.isAdmin()) {
			// show queryview for admin user only
			ObservableList<Menu> menus = menubar.getMenus();
			menus.remove(adminMenu);
		}
	}

	@FXML MenuItem queryViewMI;
	@FXML MenuBar menubar;
	@FXML Menu adminMenu;
}
