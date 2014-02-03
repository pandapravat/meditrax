package com.pravat.meditrax.print;

import static com.pravat.meditrax.util.Util.getInMoneyFormatString;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Date;

import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.bi.domain.SaleDetailsList;
import com.pravat.meditrax.bi.domain.SaleTransaction;
import com.pravat.meditrax.bi.domain.SaleTransactionInfo;
import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.print.domain.PrintDimension;
import com.pravat.meditrax.print.domain.SaleTransactionPrintDomain;
import com.pravat.meditrax.util.Util;

public class SalesTransactionPrinter extends BasePrinter<SaleTransactionPrintDomain> {


	public SalesTransactionPrinter(SaleTransactionPrintDomain transaction, StoreInfo storeInfo) {
		super(transaction, storeInfo, 30, 9);
	}
	
	
	@Override
	public void printDetails(Graphics graphics, PageFormat pf, int pageIndex, PrintDimension pd)  {
		
		SaleTransaction transaction = printDomain.getSaleTx();
		// Print store details
		SaleTransactionInfo info = transaction.getInfo();
		graphics.setFont(new Font("Tahoma", Font.BOLD, 12));
		graphics.drawString(storeInfo.getStoreName(), pd.getColumn(0), pd.getRow(0));
		graphics.drawString("INVOICE", pd.getColumn(6), pd.getRow(0));
		graphics.setFont(new Font("Tahoma", Font.BOLD, 10));

		graphics.drawString(storeInfo.getAddress1(),  pd.getColumn(0), pd.getRow(1));
		graphics.drawString(storeInfo.getAddress2(),  pd.getColumn(0), pd.getRow(2));
		graphics.drawString("Date:" + Util.getDateTimeString(info.getDate()), pd.getColumn(6), pd.getRow(1));
		
		graphics.drawString("",  pd.getColumn(0), pd.getRow(2));
		graphics.drawString("Invoice#:" + info.getTransactionId(), pd.getColumn(6), pd.getRow(2));
		
		graphics.drawString("Phone: " +storeInfo.getPhone(),  pd.getColumn(0), pd.getRow(3));
//		graphics.drawLine(startX, getNextRow(), (int)pf.getWidth() - startX, getCurrRow());
		graphics.drawLine(pd.getStartCol(), pd.getRow(3) + 5, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(3) + 5);

		graphics.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		// Print patient Details
		graphics.drawString("Name:" + info.getPatientName(),  pd.getColumn(0), pd.getRow(4));
		graphics.drawString("Age:" + info.getPatientAge(),  pd.getColumn(6), pd.getRow(4));
		graphics.drawString("Doctor's Name:" + info.getDoctorsName(), pd.getColumn(0), pd.getRow(5));
		
		graphics.setFont(new Font("Tahoma", Font.BOLD, 10));
		graphics.drawString("PARTICULARS", pd.getColumn(4), pd.getRow(6));
		// Print sale Details
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(pd.getStartCol(), pd.getRow(7) - 13, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getHeightPerRow());
		graphics.setColor(Color.BLACK);
		//graphics.drawLine(pd.getStartCol(), pd.getRow(7) - 13, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(7) - 13);
		graphics.drawString("Item#",  pd.getColumn(0), pd.getRow(7));
		graphics.drawString("Desc",  pd.getColumn(1), pd.getRow(7));
		graphics.drawString("Qty",  pd.getColumn(5), pd.getRow(7));
		graphics.drawString("Unit Prc",  pd.getColumn(6), pd.getRow(7));
		graphics.drawString("Disc %",  pd.getColumn(7), pd.getRow(7));
		graphics.drawString("Total",  pd.getColumn(8), pd.getRow(7));
		//graphics.drawLine(pd.getStartCol(), pd.getRow(7) + 5, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(7) + 5);
		
		graphics.setFont(new Font("Tahoma", Font.PLAIN, 10));
		SaleDetailsList details = transaction.getDetails();
		int rowCount = 9;
		double total = 0;
		for (SaleDetails saleDetails : details.getSaleDetails()) {
			
			graphics.drawString(""+saleDetails.getDrugId(),  pd.getColumn(0), pd.getRow(rowCount));
			graphics.drawString(saleDetails.getName(),  pd.getColumn(1), pd.getRow(rowCount));
			graphics.drawString(String.valueOf(saleDetails.getQuantity()),  pd.getColumn(5), pd.getRow(rowCount));
			graphics.drawString(getInMoneyFormatString(saleDetails.getUnitPrice()),  pd.getColumn(6), pd.getRow(rowCount));
			graphics.drawString(String.valueOf(saleDetails.getDiscountPct()), pd.getColumn(7), pd.getRow(rowCount));
			String rowTotal = Util.calculatePrice(saleDetails.getUnitPrice(), saleDetails.getQuantity(), saleDetails.getDiscountPct());
			graphics.drawString(rowTotal,  pd.getColumn(8), pd.getRow(rowCount));
			
			total += Double.valueOf(rowTotal);
			
			rowCount ++;
			if(rowCount > 18) { // print max 10 rows
				break;
			}
		}
		String totalS = getInMoneyFormatString(total);
		graphics.setFont(new Font("Tahoma", Font.BOLD, 10));
		graphics.drawLine(pd.getStartCol(), pd.getRow(rowCount) + 5, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(rowCount) + 5);
		graphics.drawString("S.Total: Rs",  pd.getColumn(7), pd.getRow(rowCount+1));
		graphics.drawString(totalS,  pd.getColumn(8), pd.getRow(rowCount+1));
		graphics.drawString("G.Total: Rs",  pd.getColumn(7), pd.getRow(rowCount+2));
		graphics.drawString(totalS,  pd.getColumn(8), pd.getRow(rowCount+2));
		
		graphics.setFont(new Font("Tahoma", Font.PLAIN, 10));
		graphics.drawLine(pd.getStartCol(), pd.getRow(pd.getlastRow() - 3) + 5, (int)pd.getWidth() - (2*pd.getStartCol()), pd.getRow(pd.getlastRow() - 3) + 5);
		graphics.drawString("Thank you for your business!!", pd.getColumn(0), pd.getRow(pd.getlastRow() - 2));
		
	}

	String storeName = "Good Luck Medicines";
	String storeAddress = "1234 Valley Lake Drive";
	String storeAddress2 = "#706, Scahumburg - IL 60195";

	public static void main(String[] args) {
		
		SaleTransaction createTestData = createTestData();
		StoreInfo storeInfo2 = new StoreInfo();
		storeInfo2.setStoreName("<STORE PLACE HOLDER>");
		storeInfo2.setAddress1("Sample Address Line1");
		storeInfo2.setAddress2("Sample Address Line2");
		storeInfo2.setPhone("<Plhone placeholder>");
		
		SaleTransactionPrintDomain domain = new SaleTransactionPrintDomain();
		domain.setSaleTx(createTestData);
		SalesTransactionPrinter printer = new SalesTransactionPrinter(domain, storeInfo2);
		try {
			printer.printData(true);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static SaleTransaction createTestData() {
		SaleTransaction saleTransaction = new SaleTransaction();
//		printer.setTransaction(saleTransaction);
		
		SaleTransactionInfo addNewInfo = saleTransaction.addNewInfo();
		addNewInfo.setPatientName("Partha Sarathi Nanda");
		addNewInfo.setPatientAge((short)30);
		addNewInfo.setDoctorsName("Bhagyadhar Ojha");
		addNewInfo.setDate(new Date());
		
		SaleDetailsList details = saleTransaction.getDetails();
		for (int i = 0; i < 5; i++) {
			
			SaleDetails addNewSaleDetails = details.addNewSaleDetails();
			addNewSaleDetails.setName("Napthlon" + i);
			addNewSaleDetails.setQuantity(10 + i);
			addNewSaleDetails.setUnitPrice(5.20 + i);
		}
		
		return saleTransaction;
	}

	
	
}
