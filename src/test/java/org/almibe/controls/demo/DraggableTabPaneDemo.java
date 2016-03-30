package org.almibe.controls.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.almibe.controls.DraggableTab;
import org.almibe.controls.DraggableTabPane;

public class DraggableTabPaneDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DraggableTabPane draggableTabPane = new DraggableTabPane();
        DraggableTab draggableTab = new DraggableTab("Hello Demo", new Label("Hello Demo Content"));
        DraggableTab draggableTab2 = new DraggableTab("Hello Demo?", new Label("Hello Demo Content?"));
        DraggableTab draggableTab3 = new DraggableTab("Hello Demo!", new Label("Hello Demo Content!"));
        DraggableTab draggableTab4 = new DraggableTab("Hello Demo?!?!!", new Label("Hello Demo Content?!?!!"));

        draggableTabPane.getTabs().addAll(draggableTab, draggableTab2, draggableTab3, draggableTab4);

        primaryStage.setScene(new Scene(draggableTabPane, 300, 250));
        primaryStage.show();
    }
}
