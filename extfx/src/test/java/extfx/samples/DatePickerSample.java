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
import extfx.scene.control.NumberSpinner;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.util.converter.PercentageStringConverter;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Christian Schudt
 */
public class DatePickerSample extends VBox implements Sample {

    public DatePickerSample() {

        setPadding(new Insets(10, 10, 10, 10));

        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(new Callback<CalendarView, DateCell>() {
            @Override
            public DateCell call(CalendarView calendarView) {
                DateCell dateCell = new DateCell() {
                    @Override
                    protected void updateItem(Date date, boolean empty) {
                        super.updateItem(date, empty);
                        calendar.setTime(date);
                        getStyleClass().remove("myBirthDay");
                        setTooltip(null);
                        if (calendar.get(Calendar.MONTH) == Calendar.MAY && calendar.get(Calendar.DATE) == 3) {
                            setTooltip(new Tooltip("This is my birthday"));
                            getStyleClass().addAll("myBirthDay");
                        }
                    }
                };

                return dateCell;
            }
        });

        datePicker.setMaxWidth(150);
        getChildren().add(datePicker);

    }

    @Override
    public String getName() {
        return "DatePicker";
    }
}
