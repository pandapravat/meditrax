package com.pravat.meditrax.print.domain;

import java.util.Date;

import javafx.scene.control.TableView;

import com.pravat.meditrax.ux.domain.PurchaseReportTableRow;

public class PurchaseReportPrintDomain implements PrintDomain{


	Date startDate;
	Date endDate;
	String totalprice;
	TableView<PurchaseReportTableRow> tableView;

	public TableView<PurchaseReportTableRow> getTableView() {
		return tableView;
	}
	public void setTableView(TableView<PurchaseReportTableRow> tableView) {
		this.tableView = tableView;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
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
	
}
