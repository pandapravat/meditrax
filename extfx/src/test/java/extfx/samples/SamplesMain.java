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

import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SamplesMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle("ExtFX Samples");

        final HBox root = new HBox(10);


        final ListView<Sample> sampleListView = new ListView<>();
        sampleListView.setItems(FXCollections.<Sample>observableArrayList(new RestrictiveTextFieldSample(), new NumberSpinnerSample(), new DatePickerSample(), new CalendarViewSample(), new InterpolatorSample(), new DateAxisSample(), new LogarithmicAxisSample()));
        sampleListView.setCellFactory(new Callback<ListView<Sample>, ListCell<Sample>>() {
            @Override
            public ListCell<Sample> call(ListView<Sample> sampleListView) {
                return new ListCell<Sample>() {
                    @Override
                    protected void updateItem(Sample item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        sampleListView.setPrefWidth(200);


        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.getChildren().add(sampleListView);
        root.getChildren().add(scrollPane);
        scrollPane.contentProperty().bind(new ObjectBinding<Node>() {
            {
                super.bind(sampleListView.getSelectionModel().selectedItemProperty());
            }

            @Override
            protected Node computeValue() {
                return (Node) sampleListView.getSelectionModel().selectedItemProperty().get();
            }
        });

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(0, 0.2);
        splitPane.getItems().addAll(sampleListView, scrollPane);

        Scene scene = new Scene(splitPane, 1024, 768);
        scene.getStylesheets().addAll(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}