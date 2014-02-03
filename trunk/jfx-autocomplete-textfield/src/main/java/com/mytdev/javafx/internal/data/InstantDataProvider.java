package com.mytdev.javafx.internal.data;

import javafx.collections.ObservableList;

public interface InstantDataProvider<T> {
	 ObservableList<T> getData(String match);
}
