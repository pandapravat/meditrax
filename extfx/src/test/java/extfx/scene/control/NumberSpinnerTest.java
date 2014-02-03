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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.CurrencyStringConverter;

public class NumberSpinnerTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        final VBox root = new VBox(10);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        final NumberSpinner numberSpinner = new NumberSpinner();
        numberSpinner.setMaxValue(100);
        numberSpinner.setMinValue(0);
        numberSpinner.setAlignment(Pos.CENTER_LEFT);
        numberSpinner.hAlignmentProperty().setValue(HPos.LEFT);
        numberSpinner.setNumberStringConverter(new CurrencyStringConverter());
        numberSpinner.setPromptText("test");
        root.getChildren().add(new TextField());
        root.getChildren().add(numberSpinner);
        final TextField textField = new TextField();
        root.getChildren().add(textField);

        Button btnChangeHAlignment = new Button();
        btnChangeHAlignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (numberSpinner.hAlignmentProperty().get() == HPos.LEFT)

                    numberSpinner.hAlignmentProperty().set(HPos.CENTER);
                else
                    numberSpinner.hAlignmentProperty().set(HPos.LEFT);
            }
        });
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root.getChildren().add(btnChangeHAlignment);
        stage.show();
    }
}