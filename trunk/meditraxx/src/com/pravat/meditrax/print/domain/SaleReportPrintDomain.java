package com.pravat.meditrax.print.domain;

import java.util.Date;

import com.pravat.meditrax.ux.domain.SaleReportTableRow;

import javafx.scene.control.TableView;


public class SaleReportPrintDomain implements PrintDomain {
	
	Date startDate;
	Date endDate;
	TableView<SaleReportTableRow> tableView;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public TableView<SaleReportTableRow> getTableView() {
		return tableView;
	}
	public void setTableView(TableView<SaleReportTableRow> tableView) {
		this.tableView = tableView;
	}
}
