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

package extfx.scene.control;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

public class DatePickerTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {



        final VBox root = new VBox(10);
        root.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        final DatePicker datePicker = new DatePicker();

        //datePicker.setEditable(false);
        datePicker.setOnHiding(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("hidden");
            }
        });
        datePicker.setMinDate(new Date());
        datePicker.errorProperty().addListener(new ChangeListener<DatePicker.Error>() {
            @Override
            public void changed(ObservableValue<? extends DatePicker.Error> observableValue, DatePicker.Error error, DatePicker.Error error2) {
                System.out.println(error2);
            }
        });
        Button button = new Button("Close");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                datePicker.setValue(null);
            }
        });

        root.getChildren().add(button);
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root.getChildren().add(new TextField());
        root.getChildren().add(new DatePicker());
        root.getChildren().add(datePicker);
        root.getChildren().add(new TextField());


        stage.show();
    }

}