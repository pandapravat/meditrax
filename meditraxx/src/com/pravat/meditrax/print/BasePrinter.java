package com.pravat.meditrax.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;
import java.util.Locale;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;

import com.pravat.meditrax.bi.domain.StoreInfo;
import com.pravat.meditrax.print.domain.PrintDimension;
import com.pravat.meditrax.print.domain.PrintDomain;
import com.pravat.meditrax.util.Util;

public abstract class BasePrinter<T extends PrintDomain> implements Printable {
	
	Font PG_TITLE = new Font("Tahoma", Font.BOLD, 14);
	Font TITLE2 = new Font("Tahoma", Font.BOLD, 12);
	Font TITLE3 = new Font("Tahoma", Font.BOLD, 11);
	Font PG_DATA = new Font("Tahoma", Font.PLAIN, 10);
	Font TAB_HEADER = new Font("Tahoma", Font.BOLD, 10);

	T printDomain;
	StoreInfo storeInfo;
	protected int rowsPerpage;
	protected int colsPerPage;
	
	public BasePrinter(T printDomain, StoreInfo info, int rowsPerSheet, int colsPerPage) {
		this.rowsPerpage = rowsPerSheet;
		this.colsPerPage = colsPerPage;
		this.printDomain = printDomain;
		this.storeInfo = info;
	}
	public void printData(boolean test) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok) {
			PrintRequestAttributeSet pas = new HashPrintRequestAttributeSet();
			pas.add(new JobName("Sale_Receipt_" + Util.getDateTimeString(new Date()), Locale.US));
			pas.add(MediaSizeName.ISO_A4);
			job.print(pas);
		}
	};
	
	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex) {
		/*if (pageIndex > 0) {  We have only one page, and 'page' is zero-based 
			return NO_SUCH_PAGE;
		}*/

		// fonts
		graphics.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		/* User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		PrintDimension printDimension = new PrintDimension(pf, rowsPerpage, colsPerPage);
		return this.printDetails(graphics, pf, pageIndex, printDimension);
		
	}
	
	public abstract int printDetails(Graphics graphics, PageFormat pf, int pageIndex, PrintDimension dim);
	
}
