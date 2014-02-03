package com.pravat.meditrax.ux.domain;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.pravat.meditrax.util.Util;

public class DrugReportTableRow {
	private SimpleLongProperty drgId;
	private SimpleStringProperty drgNm; // the item description
	private SimpleIntegerProperty count;
	private SimpleObjectProperty<Date> expDt;
	public long getDrgId() {

		return drgId.get();
	}
	public void setDrgId(SimpleLongProperty drgId) {
		this.drgId = drgId;
	}
	public String getDrgNm() {
		return drgNm.get();
	}
	public void setDrgNm(SimpleStringProperty drgNm) {
		this.drgNm = drgNm;
	}
	public int getCount() {
		return count.get();
	}
	public void setCount(SimpleIntegerProperty count) {
		this.count = count;
	}
	public Date getExpDt() {
		return new Date(expDt.get().getTime()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String toString() {
				return Util.getDateTimeString(this);
			}
		};
	}
	public void setExpDt(SimpleObjectProperty<Date> expDt) {
		this.expDt = expDt;
	}


}
