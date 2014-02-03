package com.pravat.meditrax.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;

import javafx.scene.control.TableView;

import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.print.domain.PrintDimension;
import com.pravat.meditrax.print.domain.SaleReportPrintDomain;
import com.pravat.meditrax.ux.domain.SaleReportTableRow;

public class SaleReportPrinter extends BasePrinter<SaleReportPrintDomain>{

	public SaleReportPrinter(SaleReportPrintDomain printDomain, StoreInfo info) {
		super(printDomain, info, 30, 9);
	}

	@Override
	public void printDetails(Graphics graphics, PageFormat pf, int pageIndex,
			PrintDimension pd) {
		
		// Store information and report title
		graphics.setFont(new Font("Tahoma", Font.BOLD, 12));
		graphics.drawString(storeInfo.getStoreName(), pd.getColumn(0), pd.getRow(0));
		
		graphics.setFont(new Font("Tahoma", Font.BOLD, 14));
		graphics.drawString("SALE REPORT", pd.getColumn(5), pd.getRow(0));
		
		graphics.setFont(new Font("Tahoma", Font.PLAIN, 11));
		graphics.drawString(storeInfo.getAddress1(), pd.getColumn(0), pd.getRow(1));
		graphics.drawString(storeInfo.getAddress2(), pd.getColumn(0), pd.getRow(2));
		graphics.drawString(storeInfo.getPhone(), pd.getColumn(5), pd.getRow(2));
		
		// Table Label
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(pd.getStartCol(), pd.getRow(6) - 13, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getHeightPerRow());
		graphics.setColor(Color.BLACK);
		graphics.drawString("Sl No", pd.getColumn(0), pd.getRow(6));
		graphics.drawString("Tx Id", pd.getColumn(1), pd.getRow(6));
		graphics.drawString("Date/Time", pd.getColumn(2), pd.getRow(6));
		graphics.drawString("Patient's Name", pd.getColumn(3), pd.getRow(6));
		graphics.drawString("Doctor's Name", pd.getColumn(5), pd.getRow(6));
		graphics.drawString("Amount", pd.getColumn(7), pd.getRow(6));
		
		// All table data
		TableView<SaleReportTableRow> tableView = printDomain.getTableView();
		int tableStartRow = 7;
		int count = 0;
		for (SaleReportTableRow row : tableView.getItems()) {
			graphics.drawString(String.valueOf(row.getSlNo()), pd.getColumn(0), pd.getRow(tableStartRow + count));
			graphics.drawString(row.getTransactionId(), pd.getColumn(1), pd.getRow(tableStartRow + count));
			graphics.drawString(row.getDts().toString(), pd.getColumn(2), pd.getRow(tableStartRow + count));
			graphics.drawString(row.getPatientName(), pd.getColumn(3), pd.getRow(tableStartRow + count));
			graphics.drawString(row.getDoctorsName(), pd.getColumn(5), pd.getRow(tableStartRow + count));
			graphics.drawString(String.valueOf(row.getAmount()), pd.getColumn(7), pd.getRow(tableStartRow + count));
			count ++;
		}
	}

	
	

}
