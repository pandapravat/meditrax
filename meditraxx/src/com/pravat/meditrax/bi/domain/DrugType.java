package com.pravat.meditrax.bi.domain;

public enum DrugType {

	TABLET("Tablet"), CAPSULE("Capsule"), LOTION("Lotion"), OTHER("Other");
	private String value;
	DrugType(String name) {
		this.value = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
	
}
