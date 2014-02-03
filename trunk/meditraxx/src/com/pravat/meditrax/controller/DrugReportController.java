package com.pravat.meditrax.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.mytdev.javafx.internal.data.AfterSelectDataHadler;
import com.mytdev.javafx.scene.control.AutoCompleteTextField;
import com.pravat.meditrax.bi.DrugService;
import com.pravat.meditrax.bi.domain.Drug;
import com.pravat.meditrax.bi.domain.DrugReport;
import com.pravat.meditrax.main.Meditrax;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.ux.dataprovider.DrugInstantDataProvider;
import com.pravat.meditrax.ux.domain.DrugReportTableRow;
import com.pravat.meditrax.validator.Validator;

import extfx.scene.control.DatePicker;
import extfx.scene.control.RestrictiveTextField;

public class DrugReportController implements Initializable{

	Log log = LogFactory.getLog(getClass()); 

	@FXML
	TableView<DrugReportTableRow> table;
	
	@FXML
	TableColumn<DrugReportTableRow, Long> drgIdCol;
	
	@FXML
	TableColumn<DrugReportTableRow, String> drgNmCol;
	
	@FXML
	TableColumn<DrugReportTableRow, Integer> countCol;
	
	@FXML
	TableColumn<DrugReportTableRow, Date> expDtCol;
	
	@FXML CheckBox qtyRestriction;
	@FXML RestrictiveTextField lessThanTf;
	@FXML DatePicker expiryDatePicker;
	@FXML AutoCompleteTextField<Drug> drgNmSrchTF;
	@FXML TextField ItmNmTF;
	@FXML TextField itmIdTF;

	DrugService service = ApplicationContextUtil.getInstance(DrugService.class);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lessThanTf.setMaxLength(3);
		lessThanTf.setRestrict("[0-9]");
		expiryDatePicker.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
		
		drgNmSrchTF.setDataProvider(new DrugInstantDataProvider());
		drgNmSrchTF.setAfterSelectDataHandler(new AfterSelectDataHadler<Drug>() {
			
			@Override
			public void handle(Drug selectedItem) {
				if(null != selectedItem) {
					itmIdTF.setText(String.valueOf(selectedItem.getDrugId()));
					ItmNmTF.setText(selectedItem.getName());
				}
				
			}
		});
		
		initiliseTableColumns();
	}

	private void initiliseTableColumns() {
		drgIdCol.setCellValueFactory(new PropertyValueFactory<DrugReportTableRow, Long>("drgId"));
		drgNmCol.setCellValueFactory(new PropertyValueFactory<DrugReportTableRow, String>("drgNm"));
		countCol.setCellValueFactory(new PropertyValueFactory<DrugReportTableRow, Integer>("count"));
		expDtCol.setCellValueFactory(new PropertyValueFactory<DrugReportTableRow, Date>("expDt"));
	}

	@FXML
	protected void onQtyRestrictionAction(ActionEvent e) {
		boolean selected = qtyRestriction.isSelected();
		if(selected) {
			lessThanTf.setDisable(false);
		} else {
			lessThanTf.setDisable(true);
			lessThanTf.clear();
		}
	}
	@FXML
	protected void clearSearchForm(ActionEvent ae) {
		lessThanTf.clear();
		expiryDatePicker.setValue(null);;
		drgNmSrchTF.setText("");
		ItmNmTF.clear();;
		itmIdTF.clear();;
	}
	

	@FXML
	protected void showFilterData(ActionEvent ae) {
		Date expiryDateVal = expiryDatePicker.getValue();
		int lessthanQty = 0;
		long itemId = 0;
		if(qtyRestriction.isSelected()) {
			if(Validator.isNumber(lessThanTf.getText())) {
				lessthanQty = Integer.valueOf(lessThanTf.getText());
			}
		}
		
		if(Validator.isNumber(itmIdTF.getText())) {
			itemId = Long.valueOf(itmIdTF.getText());
		}
		
		// show error if no criteria is selected
		if(null == expiryDateVal && 0 == lessthanQty && 0L == itemId) {
			Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "Please select atleast one valid criteria", "Error!");
		} else {
			log.debug("Selected expiry date : " + expiryDateVal + ", Less than qty:" + lessthanQty);

			try {
				List<DrugReport> report = service.getReport(expiryDateVal, lessthanQty, itemId);
				table.getItems().clear();
				if(!CollectionUtils.isEmpty(report)) {
					for (DrugReport drugReport : report) {
						DrugReportTableRow row = new DrugReportTableRow();
						row.setDrgId(new SimpleLongProperty(drugReport.getDrugId()));
						row.setDrgNm(new SimpleStringProperty(drugReport.getDrugNm()));
						row.setCount(new SimpleIntegerProperty(drugReport.getCount()));
						row.setExpDt(new SimpleObjectProperty<Date>(drugReport.getExpiryDate()));
						table.getItems().add(row);
					}
				} else {
					table.setPlaceholder(new Text("No Data Found"));
				}
				
			} catch (Exception e) {
				log.error("Error while fetching drug report", e);
				Dialogs.showErrorDialog(Meditrax.getPrimaryStage(), "An error occurred while fetching drug report.", "Error!");
			}
		}
	}
	
	
}
