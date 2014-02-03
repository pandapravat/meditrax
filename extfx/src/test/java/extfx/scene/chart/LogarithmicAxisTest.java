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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.testng.annotations.Test;

import java.util.Locale;


public class LogarithmicAxisTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) {

        Locale.setDefault(Locale.ENGLISH);
        VBox root = new VBox();
        Scene scene = new Scene(root, 700, 600);

        primaryStage.setScene(scene);

        root.getChildren().add(createLogarithmicChart());

//        root.getChildren().add(createMonthChartAutoRange());
//        root.getChildren().add(createMonthChartManualRange());
//        root.getChildren().add(createMonthChartNonFirstDay());
//        root.getChildren().add(createHourChart());
    }

    @Test
    public void testLog() {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            double a = Math.log10(1000.0 / 10.0);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            double a = Math.log10(1000.0) - Math.log(10.0);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    private ObservableList<XYChart.Data<Number, Number>> series1Data = FXCollections.observableArrayList();

    private Node createLogarithmicChart() {

        VBox vBox = new VBox();

        ObservableList<XYChart.Series<Number, Number>> series = FXCollections.observableArrayList();

        final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Series 1");
       // series1Data.add(new XYChart.Data<Number, Number>(0.1, 1/10.0));

        for (int i = 1; i <= 10; i++) {
            series1Data.add(new XYChart.Data<Number, Number>(i, i/10.0));
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

        Button button = new Button("getValueForDisplay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(logarithmicAxis.getValueForDisplay(logarithmicAxis.getWidth()*0.8));
            }
        });
        lineChart.setMaxWidth(600);
        vBox.getChildren().add(lineChart);
        vBox.getChildren().add(button);

        return vBox;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
}
