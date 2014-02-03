/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.ux.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author pandpr02
 */
public class NewSaleTableData {

	private SimpleIntegerProperty slNo;
	private SimpleStringProperty item; // the item description
	private IntegerProperty qunatity;
	private IntegerProperty discountPct;
	private FloatProperty unitPrice;
	private DoubleProperty totalPrice;
	
	
	private long itemId;
	private long batchId;
//	private ImageView deleteImage;

	public int getDiscountPct() {
		return discountPct.get();
	}
	public void setDiscountPct(IntegerProperty discountPct) {
		this.discountPct = discountPct;
	}
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public NewSaleTableData(SimpleStringProperty item, IntegerProperty qunatity, FloatProperty unitPrice, DoubleProperty totalPrice) {
		this.item = item;
		this.qunatity = qunatity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}
	public NewSaleTableData(String item, int qunatity, float unitPrice, double totalPrice) {

		this.item = new SimpleStringProperty(item);
		this.qunatity = new SimpleIntegerProperty(qunatity);
		this.unitPrice = new SimpleFloatProperty(unitPrice);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
	}
	public NewSaleTableData(int slNo, String item, int qunatity, float unitPrice, double totalPrice) {

		this.slNo = new SimpleIntegerProperty(slNo);
		this.item = new SimpleStringProperty(item);
		this.qunatity = new SimpleIntegerProperty(qunatity);
		this.unitPrice = new SimpleFloatProperty(unitPrice);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
	}



	public int getSlNo() {
		return this.slNo.get();
	}

	public void setSlNo(int slNo) {
		this.slNo.set(slNo);
	}
	public String getItem() {
		return item.get();
	}

	public void setItem(String item) {
		this.item.set(item);;
	}

	public int getQunatity() {
		return qunatity.get();
	}

	public void setQunatity(int qunatity) {
		this.qunatity.set(qunatity);
	}

	public float getUnitPrice() {
		return unitPrice.get();
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice.set(unitPrice);
	}

	public double getTotalPrice() {
		return totalPrice.get();
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice.set(totalPrice);
	}
//	public ImageView getDeleteImage() {
//		/*try {
//			deleteImage = FXMLLoader.load(Util.getAsResource(Constants.REMOVE_SALES_ITEM_IMAGE_VIEWF));
//			
//		} catch (IOException e) {
//			throw new MediaException("Image View could not be loaded..", e);
//		}*/
//		return deleteImage;
//	}
}
