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

package extfx.scene.chart;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateAxisTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) {

        Locale.setDefault(Locale.ENGLISH);
        VBox root = new VBox();
        Scene scene = new Scene(root, 700, 600);

        primaryStage.setScene(scene);

        root.getChildren().add(createInverseChart());
        root.getChildren().add(createSampleChart());

//        root.getChildren().add(createMonthChartAutoRange());
//        root.getChildren().add(createMonthChartManualRange());
//        root.getChildren().add(createMonthChartNonFirstDay());
//        root.getChildren().add(createHourChart());
    }

    private LineChart<Date, Number> createSampleChart() {

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2012, 11, 15).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 5, 3).getTime(), 4));

        ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 0, 13).getTime(), 8));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 7, 27).getTime(), 4));

        series.add(new XYChart.Series<>("Series1", series1Data));
        series.add(new XYChart.Series<>("Series2", series2Data));

        NumberAxis numberAxis = new NumberAxis();
        DateAxis dateAxis = new DateAxis();
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);

        lineChart.setMaxWidth(600);

        return lineChart;
    }

    private LineChart<Number, Date> createInverseChart() {

        ObservableList<XYChart.Series<Number, Date>> series = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Number, Date>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Number, Date>(2, new GregorianCalendar(2012, 11, 15).getTime()));
        series1Data.add(new XYChart.Data<Number, Date>(4, new GregorianCalendar(2014, 5, 3).getTime()));

        ObservableList<XYChart.Data<Number, Date>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Number, Date>(8, new GregorianCalendar(2014, 0, 13).getTime()));
        series2Data.add(new XYChart.Data<Number, Date>(4, new GregorianCalendar(2014, 7, 27).getTime()));

        series.add(new XYChart.Series<>("Series1", series1Data));
        series.add(new XYChart.Series<>("Series2", series2Data));

        NumberAxis numberAxis = new NumberAxis();
        DateAxis dateAxis = new DateAxis();
        LineChart<Number, Date> lineChart = new LineChart<>(numberAxis, dateAxis, series);

        lineChart.setMaxWidth(600);

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

    private LineChart<Date, Number> createMonthChartManualRange() {

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Date, Number> series1 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 1).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 1).getTime(), 4));

        series1.setData(series1Data);

        series.add(series1);

        XYChart.Series<Date, Number> series2 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 15).getTime(), 20));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 5, 2).getTime(), 4));

        series2.setData(series2Data);

        series.add(series2);

        NumberAxis numberAxis = new NumberAxis();
        final DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRanging(false);
        dateAxis.setLowerBound(new GregorianCalendar(2010, 8, 28).getTime());
        dateAxis.setUpperBound(new GregorianCalendar(2014, 2, 4).getTime());
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

    private LineChart<Date, Number> createMonthChartNonFirstDay() {

        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Date, Number> series1 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 2).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 2).getTime(), 4));

        series1.setData(series1Data);

        series.add(series1);

        XYChart.Series<Date, Number> series2 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 0, 15).getTime(), 20));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 5, 2).getTime(), 4));

        series2.setData(series2Data);

        series.add(series2);

        NumberAxis numberAxis = new NumberAxis();
        final DateAxis dateAxis = new DateAxis();
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

        XYChart.Series<Date, Number> series1 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 1, 1, 9, 3, 1).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2013, 1, 1, 22, 4, 2).getTime(), 4));
                                                                    series1.setName("Series 1");
        series1.setData(series1Data);

        series.add(series1);

        lineChart.setData(series);
       lineChart.setMaxWidth(600);
        return lineChart;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    //@Test
    public void test() {

        DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRanging(false);
        dateAxis.setLowerBound(new GregorianCalendar(2008, 0, 2).getTime());
        dateAxis.setUpperBound(new GregorianCalendar(2018, 0, 2).getTime());
        dateAxis.setRange(dateAxis.autoRange(426.8671875), dateAxis.getAnimated());

        Date date = new GregorianCalendar(2009, 4, 23).getTime();
        double v = dateAxis.getDisplayPosition(date);
        Date dateResult = dateAxis.getValueForDisplay(v);
        Assert.assertTrue(dateAxis.isValueOnAxis(new GregorianCalendar(2009, 8, 4).getTime()));
        Assert.assertEquals(date.getTime(), dateResult.getTime());

    }
}
