package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.dao.PurchaseDao;
import com.pravat.meditrax.bi.domain.PurchaseTransaction.PUR_TX_TYP;
import com.pravat.meditrax.bi.domain.PurchaseTransactionReport;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.ux.domain.PurchaseReportTableRow;

import extfx.scene.control.DatePicker;

public class PurchaseReportController implements Initializable{
	Log log = LogFactory.getLog(getClass());

	@FXML DatePicker fromDate;
	@FXML DatePicker toDate;
	@FXML Label totalSale;
	@FXML TableView<PurchaseReportTableRow> table;
	@FXML TableColumn<PurchaseReportTableRow, Integer> slNo;
	@FXML TableColumn<PurchaseReportTableRow, String> transactionId;
	@FXML TableColumn<PurchaseReportTableRow, Date> dts;
	@FXML TableColumn<PurchaseReportTableRow, Double> totAmt;
	@FXML TableColumn<PurchaseReportTableRow, String> partyName;
	@FXML TableColumn<PurchaseReportTableRow, String> itemName;
	@FXML TableColumn<PurchaseReportTableRow, Integer> totalQty;
	@FXML TableColumn<PurchaseReportTableRow, String> isReturn;
	@FXML Button goButton;
	
	ObservableList<PurchaseReportTableRow> tableData = FXCollections.observableArrayList();
	@FXML public void onHomeButtonClick(ActionEvent ae) {
		DialogResponse confirmResp = Dialogs.showConfirmDialog(UxUtils.getStage(ae), "Are you sure?", "Are you sure?", "Confirm", DialogOptions.YES_NO);
		if(DialogResponse.YES.equals(confirmResp)) {
			
			mainInstance.handleHomeClick(new ActionEvent());
		}
	}
	
	@SuppressWarnings("deprecation")
	@FXML public void showFilterData(ActionEvent ae) {
		Date fromDateVal = fromDate.getValue();
		Date toDateVal = toDate.getValue();

		if(null != fromDateVal && null != toDateVal) {

			// set the from date as start of day and end date as end of the day
			fromDateVal.setHours(0);
			fromDateVal.setMinutes(0);
			fromDateVal.setSeconds(0);
			toDateVal.setHours(23);
			fromDateVal.setMinutes(59);
			fromDateVal.setSeconds(59);

			try {
				PurchaseDao instance = ApplicationContextUtil.getInstance(PurchaseDao.class);
				List<PurchaseTransactionReport> purchaseReport = instance.getPurchaseReport(fromDateVal, toDateVal);

				if(!CollectionUtils.isEmpty(purchaseReport)) {

					double total = 0;
					int count = 0;
					for (PurchaseTransactionReport report : purchaseReport) {
						PurchaseReportTableRow row = new PurchaseReportTableRow();
						row.setSlNo(new SimpleIntegerProperty(count + 1));
						row.setDts(new SimpleObjectProperty<Date>(report.getDts()));
						row.setAmount(new SimpleDoubleProperty(report.getAmount()));
						row.setPartyName(new SimpleStringProperty(report.getPartyName()));
						row.setItemName(new SimpleStringProperty(report.getItemName()));
						row.setTransactionId(new SimpleStringProperty(String.valueOf(report.getTransactionId())));
						row.setTotalQty(new SimpleIntegerProperty(report.getQuantity()));

						PUR_TX_TYP txType = report.getTxType();
						String isReturn = PUR_TX_TYP.PUR_RETURN.equals(txType) ? "YES" : "NO";
						row.setIsReturn(new SimpleStringProperty(isReturn));
						
						// total should be summed up fro purchases and substracted for returns
						if(PUR_TX_TYP.PURCHASE.equals(report.getTxType())) { 
							total += report.getAmount();
						} else {
							total -= report.getAmount();
						}

						tableData.add(row);
						count ++;
					}

					totalSale.setText(Util.getInMoneyFormatString(total));
				} else {
					table.setPlaceholder(new Text("No Data Found"));
					log.info("No purchase data found from " + fromDate + " to " + toDate);
				}

			} catch(Exception e) {
				log.error("An error occcurred while displaying transaction", e);
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An error occurred while fetching transaction Data. Please try again later", "Error Occurred", "Error");
			}
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.setItems(tableData);
		
		// map cell factory with table columns
		initialiseTableColumnsFactory();
	}


	public DatePicker getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate.setValue(fromDate);;
	}


	public DatePicker getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate.setValue(toDate);
	}


	public Button getGoButton() {
		return goButton;
	}


	private void initialiseTableColumnsFactory() {
		slNo.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, Integer>("slNo"));
		transactionId.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, String>("transactionId"));
		dts.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, Date>("dts"));
		partyName.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, String>("partyName"));
		itemName.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, String>("itemName"));
		totAmt.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, Double>("amount"));
		totalQty.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, Integer>("totalQty"));
		isReturn.setCellValueFactory(new PropertyValueFactory<PurchaseReportTableRow, String>("isReturn"));
	}


	public void setMainInstance(MainController mainController) {
		this.mainInstance = mainController;
		
	}
	MainController mainInstance;
}
