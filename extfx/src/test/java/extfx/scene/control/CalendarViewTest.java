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
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        final VBox root = new VBox(10);
        root.setPadding(new Insets(20, 20, 20, 20));
        final CalendarView calendarView = new CalendarView(Locale.forLanguageTag("en"));
        //calendarView.setMinDate(new Date());
        Calendar calendar = (Calendar) calendarView.getCalendar().clone();
        //calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        //calendarView.setMaxDate(calendar.getTime());
        //calendarView.setSelectedDate(new Date());
        calendarView.setDayCellFactory(new Callback<CalendarView, DateCell>() {
            @Override
            public DateCell call(final CalendarView calendarView) {
                DateCell dateCell = new DateCell() {

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        getStyleClass().remove("test");
                        setStyle(null);
                        calendar.setTime(item);
                        if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DATE) == 3) {
                            getStyleClass().addAll("test");
                        }
                        setTooltip(new Tooltip("test"));

                    }
                };
                return dateCell;
            }
        });
        //calendarView.setShowWeeks(true);
        VBox.setVgrow(calendarView, Priority.ALWAYS);

        Label label = new Label();
        label.textProperty().bind(new StringBinding() {
            {
                super.bind(calendarView.selectedDateProperty());
            }

            @Override
            protected String computeValue() {
                if (calendarView.selectedDateProperty().get() != null) {
                    return calendarView.selectedDateProperty().get().toString();
                }
                return "";
            }
        });


        root.getChildren().addAll(calendarView, label);


        //setUserAgentStylesheet(STYLESHEET_CASPIAN);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}