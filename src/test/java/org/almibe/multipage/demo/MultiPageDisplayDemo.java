package org.almibe.multipage.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;

import java.util.Arrays;
import java.util.Optional;

public class MultiPageDisplayDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MultiPageDisplay multiPageDisplay = new MultiPageDisplay(() -> new Page("Hey", new Label("Hey Content")));

        primaryStage.setScene(new Scene(multiPageDisplay, 700, 450));
        primaryStage.show();

        Platform.runLater(() -> {
            Page page = new Page("Hello Demo 1", new Label("Hello Demo Content"));
            Page page2 = new Page("Hello Demo 2", new Label("Hello Demo Content?"));
            Page page3 = new Page("Hello Demo 3", new Label("Hello Demo Content!"));
            Page page4 = new Page("Hello Demo 4", new Label("Hello Demo Content?!?!!"));
            Page page5 = new Page("Prompt to close", new Label("Click yes to close"), event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Tab?");
                alert.setHeaderText("Do you want to close this tab?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ButtonType.OK){
                    event.consume();
                }
            });

            Arrays.asList(page, page2, page3, page4, page5).forEach(it -> multiPageDisplay.addPage(it));
        });
    }
}
