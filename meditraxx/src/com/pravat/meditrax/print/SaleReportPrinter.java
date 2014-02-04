package com.pravat.meditrax.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.print.domain.PrintDimension;
import com.pravat.meditrax.print.domain.SaleReportPrintDomain;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.ux.domain.SaleReportTableRow;

public class SaleReportPrinter extends BasePrinter<SaleReportPrintDomain>{

	double total = 0;
	int lastRecordFetched;
	
	Map<Integer, Integer> pageIndexToRecordStartMap = new HashMap<Integer, Integer>();
	public SaleReportPrinter(SaleReportPrintDomain printDomain, StoreInfo info) {
		super(printDomain, info, 40, 10);
	}

	@Override
	public int printDetails(Graphics graphics, PageFormat pf, int pageIndex,
			PrintDimension pd) {

		
		

		System.out.println("Page index :" +pageIndex);
		// Draw all headers in the first page
		if(pageIndex == 0) {

			calculateIndexMap(printDomain.getTableView().getItems());
			// Store information and report title
			graphics.setFont(TITLE2);
			graphics.drawString(storeInfo.getStoreName(), pd.getColumn(0), pd.getRow(0));

			graphics.setFont(PG_TITLE);
			graphics.drawString("SALES REPORT", pd.getColumn(7), pd.getRow(0));

			graphics.setFont(PG_DATA);
			graphics.drawString(storeInfo.getAddress1(), pd.getColumn(0), pd.getRow(1));
			graphics.drawString(storeInfo.getAddress2(), pd.getColumn(0), pd.getRow(2));
			graphics.drawString("Phone: " + storeInfo.getPhone(), pd.getColumn(5), pd.getRow(2));

			graphics.drawString("From Date: " + Util.getDateTimeString(printDomain.getStartDate()), pd.getColumn(0), pd.getRow(4));
			graphics.drawString("To Date: " + Util.getDateTimeString(printDomain.getEndDate()), pd.getColumn(5), pd.getRow(4));

			// Table Label
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fillRect(pd.getStartCol(), pd.getRow(6) - 13, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getHeightPerRow());
			graphics.setColor(Color.BLACK);
			graphics.setFont(TAB_HEADER);
			graphics.drawString("Sl No", pd.getColumn(0), pd.getRow(6));
			graphics.drawString("Tx Id", pd.getColumn(1), pd.getRow(6));
			graphics.drawString("Date/Time", pd.getColumn(2), pd.getRow(6));
			graphics.drawString("Patient's Name", pd.getColumn(4), pd.getRow(6));
			graphics.drawString("Doctor's Name", pd.getColumn(6), pd.getRow(6));
			graphics.drawString("Amount", pd.getColumn(9), pd.getRow(6));
		} 

		graphics.setFont(PG_DATA);
		// All table data
		TableView<SaleReportTableRow> tableView = printDomain.getTableView();
		int tableStartRow = pageIndex == 0 ? 7 : 0; // start from 7th row for first page and 0th for rest pages
		int loopCount = 0;
		ObservableList<SaleReportTableRow> items = tableView.getItems();
		for (int rowCount = tableStartRow; lastRecordFetched < items.size(); rowCount++, loopCount ++) {

			if(rowCount > rowsPerpage) {
				return PAGE_EXISTS;
			} else {
				SaleReportTableRow row = items.get(lastRecordFetched);
				graphics.drawString(String.valueOf(row.getSlNo()), pd.getColumn(0), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getTransactionId(), pd.getColumn(1), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getDts().toString(), pd.getColumn(2), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getPatientName(), pd.getColumn(4), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getDoctorsName(), pd.getColumn(6), pd.getRow(tableStartRow + loopCount));
				total += row.getAmount();
				graphics.drawString(String.valueOf(row.getAmount()), pd.getColumn(9), pd.getRow(tableStartRow + loopCount));

				System.out.println("Row Count :" + rowCount + ", Page Index:" + pageIndex);
				lastRecordFetched ++;
			}
		}
		System.out.println("Printing footer");
		graphics.drawLine(pd.getStartCol(), pd.getRow(tableStartRow + loopCount), (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(tableStartRow + loopCount));
		String totalPrice = Util.getInMoneyFormatString(total);
		graphics.setFont(TITLE3);
		graphics.drawString("Total: " + totalPrice, pd.getColumn(8), pd.getRow(tableStartRow + loopCount + 1));

		return NO_SUCH_PAGE;

	}

	private void calculateIndexMap(ObservableList<SaleReportTableRow> items) {
		if(pageIndexToRecordStartMap.size() == 0) {
			pageIndexToRecordStartMap.put(0, 7);
			int size = items.size();
			int linesToPrint = size - 7;
			
			int numberOfPages = 1;
			
			if(linesToPrint > 0) {
				int pages = linesToPrint/rowsPerpage;
				pages ++;
				
				for(int count = 0; count < pages; count ++) {
					pageIndexToRecordStartMap.put(count + 1, linesToPrint);
				}
			}
			
		}
		
	}
}
