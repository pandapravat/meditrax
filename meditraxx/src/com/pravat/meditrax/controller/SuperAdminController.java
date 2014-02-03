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
import javafx.util.Callback;

import org.apache.commons.lang.StringUtils;
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

	@SuppressWarnings("unchecked")
	@FXML public void onSQLRun(javafx.event.ActionEvent ae) {


		String text = query.getSelectedText();
		System.out.println("came here");
		if(StringUtils.isNotBlank(text)) {

			String upperCase = text.trim().toUpperCase();
			boolean update= !(upperCase.startsWith("SELECT") || upperCase.startsWith("VALUES"));
			try {
				SuperAdminDao instance = ApplicationContextUtil.getInstance(SuperAdminDao.class);
				if(update) {
					int runUpdateStmt = instance.runUpdateStmt(text);
					affectedRows.setText(String.valueOf(runUpdateStmt));
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
						errorsText.setText("No Data Found");
						tabPane.getSelectionModel().select(1);
					}

					affectedRows.clear();
				}
				errorsText.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				ex.printStackTrace(printWriter);
				errorsText.setText(stringWriter.toString());
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

	}

	@FXML public void close(ActionEvent ae) {
		UxUtils.getStage(ae).close();
	}






}
