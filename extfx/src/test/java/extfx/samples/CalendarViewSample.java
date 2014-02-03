/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013, Christian Schudt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package extfx.samples;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;
import extfx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * @author Christian Schudt
 */
public class CalendarViewSample extends VBox implements Sample {

    public CalendarViewSample() {

        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        ComboBox<Locale> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(Locale.getAvailableLocales()));
        FXCollections.sort(comboBox.getItems(), new Comparator<Locale>() {
            @Override
            public int compare(Locale o1, Locale o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        comboBox.setPromptText("Choose a locale");
        CalendarView calendarView = new CalendarView();
        calendarView.localeProperty().bind(comboBox.valueProperty());


        getChildren().addAll(comboBox, calendarView);

        calendarView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(calendarView, Priority.ALWAYS);

    }

    @Override
    public String getName() {
        return "CalendarView";
    }
}
