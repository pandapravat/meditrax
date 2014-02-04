/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.controller;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.ApplicationService;
import com.pravat.meditrax.bi.SalesService;
import com.pravat.meditrax.bi.dao.SalesDao;
import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleTransactionInfo;
import com.pravat.meditrax.bi.domain.SaleTransactionReport;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.print.SaleReportPrinter;
import com.pravat.meditrax.print.domain.SaleReportPrintDomain;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Constants;
import com.pravat.meditrax.util.PropertyLoader;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.ux.domain.SaleReportTableRow;

import extfx.scene.control.DatePicker;

/**
 *
 * @author pandpr02
 */
public class SalesReportController implements Initializable {

	Log log = LogFactory.getLog(getClass());

	@FXML DatePicker fromDate;
	@FXML DatePicker toDate;
	@FXML Label totalSale;
	@FXML TableView<SaleReportTableRow> table;
	@FXML TableColumn<SaleReportTableRow, Integer> slNo;
	@FXML TableColumn<SaleReportTableRow, String> transactionId;
	@FXML TableColumn<SaleReportTableRow, Date> dts;
	@FXML TableColumn<SaleReportTableRow, Double> amount;
	@FXML TableColumn<SaleReportTableRow, String> patientName;
	@FXML TableColumn<SaleReportTableRow, String> doctorsName;
	@FXML Button goButton;


	SalesService salesService = ApplicationContextUtil.getInstance(SalesService.class);
	ApplicationService appService = ApplicationContextUtil.getInstance(ApplicationService.class);
	public Button getGoButton() {
		return goButton;
	}

	ObservableList<SaleReportTableRow> transactionData = FXCollections.observableArrayList();



	private void showTxDetails() {
		SaleReportTableRow selectedItem = table.getSelectionModel().getSelectedItem();
		if(null != selectedItem) {
			SalesDao instance = ApplicationContextUtil.getInstance(SalesDao.class);
			List<SaleDetails> saleDetails = instance.getSaleDetails(Long.valueOf(selectedItem.getTransactionId()));

			FXMLLoader fxmlLoader = new FXMLLoader(Util.getAsResource(Constants.VIEW_TX_DTLS), PropertyLoader.getBundle());
			Parent load;
			try {
				load = (Parent) fxmlLoader.load();
				SaleTrasactionDetailsController controller = fxmlLoader.getController();
				controller.setSaleDetails(selectedItem.getTransactionId(), saleDetails);
				// make the stage modal
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(Meditrax.getPrimaryStage());
				stage.setScene(new Scene(load));
				stage.setResizable(false);
				stage.setTitle("Transaction Details!!");
				stage.show();
			} catch (IOException e) {
				log.error("Could not load handleManageParty", e);
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An Error Occurred while loading Manage party stage", "Error!!");
			}

		} else {
			Dialogs.showWarningDialog(Meditrax.getPrimaryStage(), "Please select a transaction to view details.", "View Details");
		}
	}

	@FXML public void onHomeButtonClick(ActionEvent ae) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure to cancel?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {

			mainInstance.handleHomeClick(new ActionEvent());
		}
	}

	@FXML public void onPrintButtonAction(ActionEvent ae) {
		Date fromDateVal = fromDate.getValue();
		Date toDateVal = toDate.getValue();
		Set<String> errors = checkForErrors();
		// If there are no errors, ask for print confirmation and send to the printer
		if(CollectionUtils.isEmpty(errors)) {

			DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure to print?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
			if(DialogResponse.YES.equals(confirmResp)) {
				Util.setToStartOfDay(fromDateVal);
				Util.setToEndOfDay(toDateVal);
				SaleReportPrintDomain printDomain = new SaleReportPrintDomain();
				printDomain.setStartDate(fromDateVal);
				printDomain.setEndDate(toDateVal);
				printDomain.setTableView(table);
				SaleReportPrinter printer = new SaleReportPrinter(printDomain, appService.getStoreInfo());
				try {
					printer.printData(false);
				} catch (PrinterException e) {
					// Show error if there is a problem while printing
					Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "There was a problem while printing the document. Please try again later", "Error!!");
				}
			}
		} else {
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), Util.getErrorString(errors), "Please correct the following errors!!", "Error");
		}
	}

	private Set<String> checkForErrors() {
		Set<String> errorList = new HashSet<>();


		if(null == fromDate.getValue()) {
			errorList.add("Invalid from date");
		}
		if(null == toDate.getValue()) {
			errorList.add("Invalid to date");
		}

		if(CollectionUtils.isEmpty(table.getItems())) {
			errorList.add("Nothing to print..");
		}	
		return errorList;

	}

	@FXML public void showFilterData(ActionEvent ae) {
		Date fromDateVal = fromDate.getValue();
		Date toDateVal = toDate.getValue();

		if(null != fromDateVal && null != toDateVal) {

			try {
				SaleTransactionReport salesReport = salesService.getSalesReport(fromDateVal, toDateVal);

				List<SaleTransactionInfo> salesTxList = salesReport.getSalesTxList();
				if(!CollectionUtils.isEmpty(salesTxList)) {

					double total = 0;
					int count = 0;
					transactionData.clear();
					for (SaleTransactionInfo saleTransactionInfo : salesTxList) {
						SaleReportTableRow row = new SaleReportTableRow();
						row.setSlNo(count + 1);
						row.setDts(saleTransactionInfo.getDate());
						row.setAmount(saleTransactionInfo.getAmount());
						row.setDoctorsName(saleTransactionInfo.getDoctorsName());
						row.setPatientName(saleTransactionInfo.getPatientName());
						row.setTransactionId(String.valueOf(saleTransactionInfo.getTransactionId()));

						total += saleTransactionInfo.getAmount();

						transactionData.add(row);
						count ++;
					}

					totalSale.setText(Util.getInMoneyFormatString(total));
				} else {
					table.setPlaceholder(new Text("No Data Found"));
					log.info("No data found from " + fromDate + " to " + toDate);
				}

			} catch(Exception e) {
				log.error("An error occcurred while displaying transaction", e);
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An error occurren while fetching transaction Data. Please try again later", "Error Occurred", "Error");
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fromDate.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
		toDate.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
		table.setItems(transactionData);

		table.setTooltip(new Tooltip("Double click on any row to see details."));
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1) {
					showTxDetails();
				}

			}
		});


		// Set the factory for the table columns
		slNo.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, Integer>("slNo"));
		transactionId.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, String>("transactionId"));
		dts.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, Date>("dts"));
		patientName.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, String>("patientName"));
		doctorsName.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, String>("doctorsName"));
		amount.setCellValueFactory(new PropertyValueFactory<SaleReportTableRow, Double>("amount"));
	}



	public void setFromDate(Date fromDate) {
		this.fromDate.setValue(fromDate);
	}

	public void setToDate(Date toDate) {
		this.toDate.setValue(toDate);
	}
	public void setMainInstance(MainController mainController) {
		this.mainInstance = mainController;
	}
	MainController mainInstance;


}
