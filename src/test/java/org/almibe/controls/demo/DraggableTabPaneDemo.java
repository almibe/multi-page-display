package org.almibe.controls.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.almibe.controls.DraggableTab;

public class DraggableTabPaneDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DraggableTab draggableTab = new DraggableTab("Hello Demo");

        primaryStage.setScene(new Scene(draggableTab, 300, 250));
        primaryStage.show();
    }
}
