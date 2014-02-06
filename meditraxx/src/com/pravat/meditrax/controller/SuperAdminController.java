package com.pravat.meditrax.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.util.Callback;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.dao.SuperAdminDao;
import com.pravat.meditrax.bi.dao.SuperAdminDaoImpl.QueryResult;
import com.pravat.meditrax.bi.dao.SuperAdminDaoImpl.QueryRow;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.UxUtils;

public class SuperAdminController implements Initializable{

	@SuppressWarnings("rawtypes")
	@FXML TableView theTable;
	@FXML TextField affectedRows;
	@FXML TextArea query;
	@FXML TabPane tabPane;
	@FXML TextArea errorsText;

	@FXML public void onSQLRun(javafx.event.ActionEvent ae) {
		String text = query.getSelectedText();
		System.out.println("came here");
		runSql(text);
	}

	private void runSql(String text) {
		if(StringUtils.isNotBlank(text)) {
			errorsText.clear();
			errorsText.appendText("\nRunning sql.. \n" + text);
			String upperCase = text.trim().toUpperCase();
			boolean update= !(upperCase.startsWith("SELECT") || upperCase.startsWith("VALUES"));
			try {
				SuperAdminDao instance = ApplicationContextUtil.getInstance(SuperAdminDao.class);
				if(update) {
					int runUpdateStmt = instance.runUpdateStmt(text);
					affectedRows.setText(String.valueOf(runUpdateStmt));
					errorsText.appendText("\nSuccessful. Affected rows=" + runUpdateStmt);
				} else {
					QueryResult runQuery = instance.runQuery(text);
					if(null != runQuery) {
						addColumns(theTable, runQuery.getColumnNames());
						if(!CollectionUtils.isEmpty(runQuery.getValues())) {
							for(QueryRow queryRow : runQuery.getValues()) {
								ObservableList<String> rowData = FXCollections.observableArrayList();
								for (Object columnVal : queryRow.getColumnValues()) {
									if(null == columnVal) rowData.add("");
									else rowData.add(columnVal.toString());
								}
								theTable.getItems().add(rowData);
								tabPane.getSelectionModel().select(0);
							}
						}
					} else {
						errorsText.appendText("\nNo Data Found");
						tabPane.getSelectionModel().select(1);
					}
					errorsText.appendText("\nSuccessful...");
					affectedRows.clear();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				ex.printStackTrace(printWriter);
				errorsText.appendText("\n\n" + stringWriter.toString());
				printWriter.close();
				tabPane.getSelectionModel().select(1);
			}
		}
		else {
			errorsText.setText("Put some query and select and click Run SQL");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addColumns(TableView targetTable, List<String> columnNames) {

		targetTable.getColumns().clear();
		targetTable.getItems().clear();
		int colCount = 0;
		for (String aColumnName : columnNames) {
			TableColumn col = new TableColumn<>(aColumnName);
			targetTable.getColumns().add(col);
			final int colIdx = colCount;
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>,ObservableValue<String>>(){                   
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                             
					return new SimpleStringProperty(param.getValue().get(colIdx).toString());                       
				}                   
			});
			col.setEditable(true);
			colCount++;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("VALUES (NEXT VALUE FOR PURCHASE_TX_ID_SQ)").append("\n");
		stringBuilder.append("SELECT * FROM SALE_TRANSACTION").append("\n");
		stringBuilder.append("SELECT * FROM PURCHASE_TRANSACTION").append("\n");
		stringBuilder.append("SELECT * FROM DRUG_BATCH)").append("\n");
		stringBuilder.append("SELECT * FROM APP_CONFIG").append("\n");
		stringBuilder.append("SELECT * FROM SALE_DTLS").append("\n");

		query.setText(stringBuilder.toString());


	}

	@FXML public void close(ActionEvent ae) {
		UxUtils.getStage(ae).close();
	}

	public void additionalInit() {
		query.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_ANY), new Runnable() {

			@Override
			public void run() {
				
				String selectedText = query.getSelectedText();
				runSql(selectedText);
			}
		});
	}

	Log log = LogFactory.getLog(getClass());
}
