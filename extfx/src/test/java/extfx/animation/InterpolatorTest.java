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

package extfx.animation;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.CurrencyStringConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian Schudt
 */
public class InterpolatorTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        ObservableList<Interpolator> interpolators = FXCollections.observableArrayList();

        interpolators.add(new BackInterpolator());
        interpolators.add(new BounceInterpolator());
        interpolators.add(new ElasticInterpolator());
        interpolators.add(new ExponentialInterpolator());
        interpolators.add(new QuadraticInterpolator());
        interpolators.add(new CubicInterpolator());
        interpolators.add(new QuarticInterpolator());
        interpolators.add(new QuinticInterpolator());
        interpolators.add(new SineInterpolator());
        interpolators.add(new CircularInterpolator());

        final Map<Interpolator, Animation> transitionMap = new HashMap<>();
        final Map<Interpolator, BooleanProperty> booleanPropertyMap = new HashMap<>();

        ListView<Interpolator> listView = new ListView<>();
        listView.setItems(interpolators);
        listView.setCellFactory(new Callback<ListView<Interpolator>, ListCell<Interpolator>>() {
            @Override
            public ListCell<Interpolator> call(ListView<Interpolator> interpolatorListView) {
                return new ListCell<Interpolator>() {
                    @Override
                    protected void updateItem(Interpolator item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(null);
                        if (item != null && !empty) {
                            Circle circle = new Circle(20, Color.RED);

                            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), circle);
                            translateTransition.setFromX(0);
                            translateTransition.setToX(500);
                            translateTransition.setInterpolator(item);

                            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(2), circle);
                            translateTransition1.setFromX(500);
                            translateTransition1.setToX(0);
                            translateTransition1.setInterpolator(item);

                            SequentialTransition sequentialTransition = new SequentialTransition();
                            sequentialTransition.getChildren().addAll(translateTransition, translateTransition1);

                            transitionMap.put(item, sequentialTransition);
                            final BooleanProperty checked = new SimpleBooleanProperty(true);
                            booleanPropertyMap.put(item, checked);
                            CheckBox checkBox = new CheckBox();
                            circle.fillProperty().bind(new ObjectBinding<Paint>() {
                                {
                                    super.bind(checked);
                                }

                                @Override
                                protected Paint computeValue() {
                                    return checked.get() ? Color.RED : Color.GRAY;
                                }
                            });
                            checkBox.selectedProperty().bindBidirectional(checked);

                            setText(item.getClass().getSimpleName());

                            setGraphic(HBoxBuilder.create().spacing(50).alignment(Pos.CENTER).children(checkBox, circle).build());
                        }
                    }
                };
            }
        });
        listView.setMinHeight(462);
        listView.maxHeightProperty().bind(listView.minHeightProperty());

        listView.setMinWidth(700);

        Button button = new Button("Animate");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Map.Entry<Interpolator, Animation> animation : transitionMap.entrySet()) {
                    BooleanProperty booleanProperty = booleanPropertyMap.get(animation.getKey());
                    if (booleanProperty.get()) {
                        animation.getValue().playFromStart();
                    }
                }
            }
        });


        final Path path = new Path();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Interpolator>() {
            @Override
            public void changed(ObservableValue<? extends Interpolator> observableValue, Interpolator interpolator, Interpolator interpolator2) {
                int maxX = 200;
                path.getElements().clear();
                MoveTo moveTo = new MoveTo(0, 0);
                path.getElements().add(moveTo);
                for (int i = 0; i < maxX; i++) {
                    double value = interpolator2.interpolate((double) i, (double) maxX, i / (double) maxX);
                    path.getElements().add(new LineTo(i, value));

                }
            }
        });

        listView.getSelectionModel().selectFirst();

        root.getChildren().add(button);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPrefWidth(1040);

        root.getChildren().addAll(HBoxBuilder.create().spacing(10).children(listView, path).build());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
