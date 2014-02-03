package com.pravat.meditrax.print.domain;

import java.awt.print.PageFormat;

public class PrintDimension {

	int leftMargin;
	int topMargin;
	int widthPerColumn;
	int heightPerRow;
	public int getHeightPerRow() {
		return heightPerRow;
	}
	double width;
	int apprxTotRows;
	
	public PrintDimension(PageFormat pf, int apprxRows, int apprxCols) {
		width = pf.getWidth();
		leftMargin = 20;
		topMargin = 40;
		
		int apprxTotCols = apprxCols; // refer to doc
		int availablePrintWid = (int)(width - 10 - 10);// let margin 10 + right margin 10
		widthPerColumn = availablePrintWid / apprxTotCols;
		int availPrintHeight = (int)(pf.getHeight() - 30 - 30);
		apprxTotRows = apprxRows;
		heightPerRow = availPrintHeight/apprxTotRows;
	}
	
	public int getColumn(int index) {
		return leftMargin + index * widthPerColumn;
	}
	public int getRow(int index) {
		return topMargin + index * heightPerRow;
	}
	
	public int getStartCol() {
		return leftMargin;
	}
	public double getWidth() {
		return width;
	}
	public int getlastRow() {
		return apprxTotRows;
	}

}
