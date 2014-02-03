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

import extfx.scene.chart.LogarithmicAxis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * @author Christian Schudt
 */
public class LogarithmicAxisSample extends VBox implements Sample {

    public LogarithmicAxisSample() {

        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);


        getChildren().addAll(createLogarithmicChart1(), createLogarithmicChart2());

    }

    private LineChart<Number, Number> createLogarithmicChart1() {

        ObservableList<XYChart.Series<Number, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Series 1");

        final ObservableList<XYChart.Data<Number, Number>> series1Data = FXCollections.observableArrayList();

        for (int i = 1; i <= 10; i++) {
            series1Data.add(new XYChart.Data<Number, Number>(i, i / 10.0));
        }

        series1.setData(series1Data);

        series.add(series1);
        NumberAxis numberAxis = new NumberAxis();
        final LogarithmicAxis logarithmicAxis = new LogarithmicAxis();

        logarithmicAxis.setAutoRanging(true);
        LineChart<Number, Number> lineChart = new LineChart<>(logarithmicAxis, numberAxis);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                series1Data.add(new XYChart.Data<Number, Number>(1000, 1000));
            }
        });
        lineChart.setData(series);

        return lineChart;
    }

    private LineChart<Number, Number> createLogarithmicChart2() {

        ObservableList<XYChart.Series<Number, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Series 1");

        final ObservableList<XYChart.Data<Number, Number>> series1Data = FXCollections.observableArrayList();

        for (int i = 1; i <= 100; i++) {
            series1Data.add(new XYChart.Data<Number, Number>(i, i));
        }

        series1.setData(series1Data);

        series.add(series1);
        NumberAxis numberAxis = new NumberAxis();
        final LogarithmicAxis logarithmicAxis = new LogarithmicAxis();

        logarithmicAxis.setAutoRanging(true);
        LineChart<Number, Number> lineChart = new LineChart<>(numberAxis, logarithmicAxis);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                series1Data.add(new XYChart.Data<Number, Number>(1000, 1000));
            }
        });
        lineChart.setData(series);

        return lineChart;
    }

    @Override
    public String getName() {
        return "LogarithmicAxis";
    }
}
