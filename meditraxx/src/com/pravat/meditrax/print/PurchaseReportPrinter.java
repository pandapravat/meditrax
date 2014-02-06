package com.pravat.meditrax.print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.ObservableList;

import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.print.domain.PrintDimension;
import com.pravat.meditrax.print.domain.PurchaseReportPrintDomain;
import com.pravat.meditrax.util.Util;
import com.pravat.meditrax.ux.domain.PurchaseReportTableRow;

public class PurchaseReportPrinter extends BasePrinter<PurchaseReportPrintDomain>{

	Map<Integer, TableRecordBounds> pageIndexToRecordStartMap = new HashMap<Integer, TableRecordBounds>();
	public PurchaseReportPrinter(PurchaseReportPrintDomain printDomain, StoreInfo info) {
		super(printDomain, info, 11);
	}
	@Override
	public int printDetails(Graphics graphics, PageFormat pf, int pageIndex,
			PrintDimension pd) {
		
		if(!CollectionUtils.isEmpty(pageIndexToRecordStartMap) && null == pageIndexToRecordStartMap.get(pageIndex)) {
			return NO_SUCH_PAGE;
		}
		int tableStartRow = 7;
		System.out.println("Page index :" +pageIndex);
		
		// Draw all headers in the first page
		if(pageIndex == 0) {

			calculateIndexMap(printDomain.getTableView().getItems(), tableStartRow);
			// Store information and report title
			graphics.setFont(TITLE2);
			graphics.drawString(storeInfo.getStoreName(), pd.getColumn(0), pd.getRow(0));

			graphics.setFont(PG_TITLE);
			graphics.drawString("PURCHASE REPORT", pd.getColumn(7), pd.getRow(0));

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
			graphics.drawString("Party Name", pd.getColumn(4), pd.getRow(6));
			graphics.drawString("Item Name", pd.getColumn(6), pd.getRow(6));
			graphics.drawString("Qty", pd.getColumn(8), pd.getRow(6));
			graphics.drawString("Price", pd.getColumn(9), pd.getRow(6));
			graphics.drawString("Return?", pd.getColumn(10), pd.getRow(6));
		} 

		graphics.setFont(PG_DATA);
		// All table data
		TableRecordBounds tableRecordBounds = pageIndexToRecordStartMap.get(pageIndex);
		int minRow = tableRecordBounds.getMinRow();
		int maxRow = tableRecordBounds.getMaxRow();
		ObservableList<PurchaseReportTableRow> items = printDomain.getTableView().getItems();
		tableStartRow = pageIndex == 0 ? tableStartRow : 0;
		int loopCount = 0;
		for (int rowCount = minRow; rowCount <= maxRow; rowCount++, loopCount++) {

				PurchaseReportTableRow row = items.get(rowCount);
				graphics.drawString(String.valueOf(row.getSlNo()), pd.getColumn(0), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getTransactionId(), pd.getColumn(1), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getDts().toString(), pd.getColumn(2), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getPartyName(), pd.getColumn(4), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getItemName(), pd.getColumn(6), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(String.valueOf(row.getTotalQty()), pd.getColumn(8), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(String.valueOf(row.getAmount()), pd.getColumn(9), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(row.getIsReturn(), pd.getColumn(10), pd.getRow(tableStartRow + loopCount));
				graphics.drawString(String.valueOf(row.getAmount()), pd.getColumn(9), pd.getRow(tableStartRow + loopCount));

		}
		// Print the footer only for the last row
		if(maxRow == items.size() - 1) {
			graphics.drawLine(pd.getStartCol(), pd.getRow(tableStartRow + loopCount), (int)pd.getWidth() - pd.getStartCol(), pd.getRow(tableStartRow + loopCount));
			
			graphics.drawString("Total: " + printDomain.getTotalprice(), pd.getColumn(9), pd.getRow(tableStartRow + loopCount + 1));
		}
		return PAGE_EXISTS;

	}
	private void calculateIndexMap(List<PurchaseReportTableRow> items, int tableStartRow) {
		if(pageIndexToRecordStartMap.size() == 0) {
			int size = items.size();
			int printableRecordSize = size - (rowsPerpage - tableStartRow) ;
			
			int pages = 1;
			if(printableRecordSize > 0) {
				pages += Math.ceil(((float)printableRecordSize)/rowsPerpage);
			}
			int lastRecord = 0;
			for(int count = 0; count < pages; count ++) {
				TableRecordBounds rowBounds = null;
				if(count == 0) {
					lastRecord = rowsPerpage - tableStartRow - 1;
					if(lastRecord > size - 1) lastRecord =  size - 1;
					rowBounds = new TableRecordBounds(0, lastRecord);
				} else {
					int nextLast = lastRecord + rowsPerpage;
					if(nextLast > size -1) nextLast = size -1;
					rowBounds = new TableRecordBounds(lastRecord + 1, nextLast);
					lastRecord = nextLast;
				}
				pageIndexToRecordStartMap.put(count, rowBounds);
			}
			
		}
		for(Entry<Integer, TableRecordBounds> anEntry:pageIndexToRecordStartMap.entrySet()) {
			System.out.println(anEntry.getKey() + "====" + anEntry.getValue());
		}
	}

}
