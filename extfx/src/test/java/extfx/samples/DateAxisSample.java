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

import extfx.scene.chart.DateAxis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Christian Schudt
 */
public class DateAxisSample extends VBox implements Sample {

    public DateAxisSample() {

        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        Label label = new Label("Click on the charts to see some animation.");
        final LineChart<Number, Date> lineChart = createInverseChart();
        getChildren().addAll(label, createSampleChart(), createHourChart(), lineChart, createMonthChartAutoRange());

    }

    private LineChart<Date, Number> createSampleChart() {

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        final ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2012, 11, 15).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 5, 3).getTime(), 4));

        final ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 0, 13).getTime(), 8));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 7, 27).getTime(), 4));

        series.add(new XYChart.Series<>("Series1", series1Data));
        series.add(new XYChart.Series<>("Series2", series2Data));

        NumberAxis numberAxis = new NumberAxis();
        DateAxis dateAxis = new DateAxis();
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2015, 1, 2, 0, 0, 0).getTime(), 33));
                series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2015, 1, 2, 0, 0, 0).getTime(), 44));

            }
        });
        return lineChart;
    }

    private LineChart<Number, Date> createInverseChart() {

        final ObservableList<XYChart.Series<Number, Date>> series = FXCollections.observableArrayList();

        final ObservableList<XYChart.Data<Number, Date>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Number, Date>(2, new GregorianCalendar(2012, 11, 15).getTime()));
        series1Data.add(new XYChart.Data<Number, Date>(4, new GregorianCalendar(2014, 5, 3).getTime()));

        final ObservableList<XYChart.Data<Number, Date>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Number, Date>(8, new GregorianCalendar(2014, 0, 13).getTime()));
        series2Data.add(new XYChart.Data<Number, Date>(4, new GregorianCalendar(2014, 7, 27).getTime()));

        series.add(new XYChart.Series<>("Series1", series1Data));
        series.add(new XYChart.Series<>("Series2", series2Data));

        NumberAxis numberAxis = new NumberAxis();
        DateAxis dateAxis = new DateAxis();
        LineChart<Number, Date> lineChart = new LineChart<>(numberAxis, dateAxis, series);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                series1Data.add(new XYChart.Data<Number, Date>(40, new GregorianCalendar(2014, 3, 1).getTime()));
                series2Data.add(new XYChart.Data<Number, Date>(30, new GregorianCalendar(2015, 8, 1).getTime()));
            }
        });

        return lineChart;
    }

    private LineChart<Date, Number> createMonthChartAutoRange() {

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Date, Number> series1 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 1).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 1).getTime(), 4));

        series1.setData(series1Data);

        series.add(series1);

        XYChart.Series<Date, Number> series2 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 1).getTime(), 20));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 5, 1).getTime(), 4));

        series2.setData(series2Data);

        series.add(series2);

        NumberAxis numberAxis = new NumberAxis();
        final DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRanging(true);
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                series1.getData().add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 3, 1).getTime(), 80d));
                series1.getData().add(new XYChart.Data<Date, Number>(new GregorianCalendar(2079, 8, 1).getTime(), 60d));
            }
        });

        lineChart.setData(series);

        return lineChart;
    }

    private LineChart<Date, Number> createHourChart() {
        NumberAxis numberAxis = new NumberAxis();
        DateAxis dateAxis = new DateAxis();
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis);

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Date, Number> series1 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 1, 1, 9, 3, 1).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 1, 1, 22, 4, 2).getTime(), 4));
        series1.setName("Series 1");
        series1.setData(series1Data);

        series.add(series1);
        lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                series1.getData().add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 1, 2, 10, 0, 0).getTime(), 80d));

            }
        });
        lineChart.setData(series);
        return lineChart;
    }

    @Override
    public String getName() {
        return "DateAxis";
    }
}
