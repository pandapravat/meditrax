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
import extfx.scene.control.NumberSpinner;
import extfx.scene.control.RestrictiveTextField;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.util.converter.PercentageStringConverter;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author Christian Schudt
 */
public class RestrictiveTextFieldSample extends VBox implements Sample {

    public RestrictiveTextFieldSample() {

        setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        RestrictiveTextField numericTextField = new RestrictiveTextField();
        numericTextField.setRestrict("[0-9]");

        gridPane.add(numericTextField, 0, 0);
        gridPane.add(new Label("Numeric TextField"), 1, 0);

        RestrictiveTextField maxLengthTextField = new RestrictiveTextField();
        maxLengthTextField.setMaxLength(5);

        gridPane.add(maxLengthTextField, 0, 1);
        gridPane.add(new Label("Max length = 5"), 1, 1);

        getChildren().add(gridPane);

    }

    @Override
    public String getName() {
        return "RestrictiveTextField";
    }
}
