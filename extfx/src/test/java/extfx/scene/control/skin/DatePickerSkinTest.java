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

package extfx.scene.control.skin;

import extfx.scene.control.CalendarView;
import extfx.scene.control.DatePicker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testng.Assert;

import java.text.NumberFormat;

/**
 * This test shall test, if the {@link extfx.scene.control.skin.NumberSpinnerSkin#dispose()} method is correctly implemented.
 *
 * @author Christian Schudt
 */
public class DatePickerSkinTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.gc();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(String.format("Start Memory: %s KB", startMemory / 1024));

        DatePicker datePicker = new DatePicker();

        Scene scene = new Scene(datePicker);
        stage.setScene(scene);

        for (int i = 0; i < 10000; i++) {
            DatePickerSkin skin = new DatePickerSkin(datePicker);
            skin.dispose();
        }
        System.gc();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(String.format("End Memory: %s KB", endMemory / 1024));

        // Allow a memory increase of 400%. This seems to be normal.
        // If the skin really leaked memory, e.g. because an unbind method was forget, it would increase by around 10000%.
        double memoryIncrease = 1.0 * endMemory / startMemory;
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        if ((memoryIncrease - 1) > 4) {
            Assert.fail(String.format("Test failed with memory increase of %s", numberFormat.format(memoryIncrease - 1)));
        } else {
            System.out.println(String.format("Test passed with memory increase of %s", numberFormat.format(memoryIncrease - 1)));
        }

        System.exit(0);
    }
}
