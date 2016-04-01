package org.almibe.multipage.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.almibe.multipage.Page;
import org.almibe.multipage.MultiPageDisplay;

public class MultiPageDisplayDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MultiPageDisplay multiPageDisplay = new MultiPageDisplay(() -> new Page("Hey", new Label("Hey Content")));

        primaryStage.setScene(new Scene(multiPageDisplay, 300, 250));
        primaryStage.show();

        Platform.runLater(() -> {
            Page page = new Page("Hello Demo", new Label("Hello Demo Content"));
            Page page2 = new Page("Hello Demo?", new Label("Hello Demo Content?"));
            Page page3 = new Page("Hello Demo!", new Label("Hello Demo Content!"));
            Page page4 = new Page("Hello Demo?!?!!", new Label("Hello Demo Content?!?!!"));

            multiPageDisplay.getPages().addAll(page, page2, page3, page4);

        });
    }
}
