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

import extfx.scene.control.NumberSpinner;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.util.converter.PercentageStringConverter;

import java.text.NumberFormat;

/**
 * @author Christian Schudt
 */
public class NumberSpinnerSample extends VBox implements Sample {

    public NumberSpinnerSample() {

        setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        NumberSpinner numberSpinner1 = new NumberSpinner();
        numberSpinner1.setValue(-2);

        gridPane.add(numberSpinner1, 0, 0);
        gridPane.add(new Label("Default NumberSpinner"), 1, 0);

        NumberSpinner numberSpinner2 = new NumberSpinner();
        numberSpinner2.setValue(10);
        numberSpinner2.setMaxValue(10);
        gridPane.add(numberSpinner2, 0, 1);
        gridPane.add(new Label("NumberSpinner with max value 10"), 1, 1);

        NumberSpinner numberSpinner3 = new NumberSpinner();
        numberSpinner3.setNumberStringConverter(new PercentageStringConverter());
        numberSpinner3.setMinValue(0);
        numberSpinner3.setMaxValue(1);
        numberSpinner3.setStepWidth(0.01);
        numberSpinner3.setValue(0.45);
        gridPane.add(numberSpinner3, 0, 2);
        gridPane.add(new Label("NumberSpinner with PercentageStringConverter"), 1, 2);


        NumberSpinner numberSpinner4 = new NumberSpinner();
        numberSpinner4.setNumberStringConverter(new CurrencyStringConverter());
        numberSpinner4.setMinValue(0);
        numberSpinner4.setMaxValue(1);
        numberSpinner4.setStepWidth(0.01);
        numberSpinner4.setValue(0.74);
        numberSpinner4.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(numberSpinner4, 0, 3);
        gridPane.add(new Label("NumberSpinner with CurrencyStringConverter and right text alignment"), 1, 3);

        NumberSpinner numberSpinner5 = new NumberSpinner();
        NumberFormat decimalNumberFormat = NumberFormat.getNumberInstance();
        decimalNumberFormat.setMinimumFractionDigits(1);
        numberSpinner5.setNumberStringConverter(new NumberStringConverter(decimalNumberFormat));
        numberSpinner5.setMinValue(0);
        numberSpinner5.setMaxValue(10);
        numberSpinner5.setStepWidth(0.01);
        numberSpinner5.setValue(9.24);
        numberSpinner5.setHAlignment(HPos.RIGHT);
        gridPane.add(numberSpinner5, 0, 4);
        gridPane.add(new Label("Right-aligned NumberSpinner with decimal format"), 1, 4);

        NumberSpinner numberSpinner6 = new NumberSpinner();
        numberSpinner6.setMinValue(0);
        numberSpinner6.setMaxValue(10);
        numberSpinner6.setValue(5);
        numberSpinner6.setEditable(false);
        numberSpinner6.setHAlignment(HPos.CENTER);

        gridPane.add(numberSpinner6, 0, 5);
        gridPane.add(new Label("Centered non-editable NumberSpinner"), 1, 5);


        getChildren().add(gridPane);
    }

    @Override
    public String getName() {
        return "NumberSpinner";
    }
}
