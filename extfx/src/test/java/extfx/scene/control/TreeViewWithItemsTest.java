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

import extfx.util.HierarchyData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class TreeViewWithItemsTest extends Application {

    private ObservableList<MyData> items1 = FXCollections.observableArrayList();

    private ObservableList<MyData> items2 = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        final VBox root = new VBox(10);

        MyData myData1 = new MyData("Node 1", 45);
        MyData myData11 = new MyData("Node 1.1", 3);
        MyData myData12 = new MyData("Node 1.2", 5);
        MyData myData13 = new MyData("Node 1.3", 4);
        MyData myData14 = new MyData("Node 1.4", 1);

        myData1.getChildren().addAll(myData11, myData12, myData13, myData14);

        MyData myData2 = new MyData("Node 2", 34);
        items1.addAll(myData1, myData2);

        MyData myData3 = new MyData("Node 3", 2);
        MyData myData31 = new MyData("Node 3.1", 1);
        myData3.getChildren().addAll(myData31);

        MyData myData4 = new MyData("Node 4", 66);
        items2.addAll(myData3, myData4);

        final TreeViewWithItems<MyData> treeViewWithItems = new TreeViewWithItems<>(new TreeItem<>(new MyData("root", 2)));
        treeViewWithItems.setItems(items1);
        treeViewWithItems.setShowRoot(false);

        root.getChildren().addAll(treeViewWithItems);

        Button btnChangeRoot = new Button("Change Root");
        btnChangeRoot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                treeViewWithItems.setRoot(new TreeItem<>(new MyData("newRoot", 4)));
            }
        });
        root.getChildren().addAll(btnChangeRoot);

        Button btnResetItems = new Button("Set items 2");
        btnResetItems.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                treeViewWithItems.setItems(null);
            }
        });

        Button btnRemoveItem = new Button("Remove an item");
        btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (treeViewWithItems.getItems().size() > 0)
                    treeViewWithItems.getItems().remove(0);
            }
        });

        Button btnSort1 = new Button("Sort by Name");
        btnSort1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sort1(items1);
            }
        });

        Button btnSort2 = new Button("Sort by Integer");
        btnSort2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sort2(items1);
            }
        });

//        System.gc();
//        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        System.out.println(String.format("Start Memory: %s KB", startMemory / 1024));
//
//        for (int i = 0; i < 100000; i++) {
//            //items1 = FXCollections.observableArrayList();
//            MyData myData5 = new MyData("Node 1");
//            MyData myData51 = new MyData("Node 1.1");
//            myData5.getChildren().addAll(myData51);
//            //treeViewWithItems.setItems(items1);
//            MyData myData6 = new MyData("Node 2");
//            items1.addAll(myData5, myData6);
//            items1.addAll(new MyData("test"));
//            items1.clear();
//        }
//        System.gc();
//        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        System.out.println(String.format("End Memory: %s KB", endMemory / 1024));


        root.getChildren().addAll(btnRemoveItem);
        root.getChildren().addAll(btnResetItems);
        root.getChildren().addAll(btnSort1, btnSort2);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void sort1(ObservableList<MyData> list) {
        FXCollections.sort(list, new Comparator<MyData>() {
            @Override
            public int compare(MyData o1, MyData o2) {
                return o1.value.compareTo(o2.value);
            }
        });
        for (MyData myData : list) {
            sort1(myData.getChildren());
        }
    }

    private void sort2(ObservableList<MyData> list) {
        FXCollections.sort(list, new Comparator<MyData>() {
            @Override
            public int compare(MyData o1, MyData o2) {
                return Integer.compare(o1.value2, o2.value2);
            }
        });
        for (MyData myData : list) {
            sort2(myData.getChildren());
        }
    }

    private class MyData implements HierarchyData<MyData> {

        private String value;

        private int value2;

        private ObservableList<MyData> children = FXCollections.observableArrayList();

        public MyData(String value, int value2) {
            this.value = value;
            this.value2 = value2;
        }

        @Override
        public ObservableList<MyData> getChildren() {
            return children;
        }

        public String toString() {
            return value + " (" + value2 + ")";
        }
    }
}