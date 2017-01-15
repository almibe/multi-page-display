/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        MultiPageDisplay multiPageDisplay = new MultiPageDisplay(mpd -> mpd.addPage(new Page(new SimpleStringProperty("Hey"), createImageView(), new Label("Hey Content"))));

        primaryStage.setScene(new Scene(multiPageDisplay, 700, 450));
        primaryStage.show();

        Platform.runLater(() -> {
            Page page = new Page(new SimpleStringProperty("Hello Demo 1"), createImageView(),new Label("Hello Demo Content"));
            Page page2 = new Page(new SimpleStringProperty("Hello Demo 2"), createImageView(), new Label("Hello Demo Content?"));
            Page page3 = new Page(new SimpleStringProperty("Hello Demo 3"), createImageView(), new Label("Hello Demo Content!"));
            Page page4 = new Page(new SimpleStringProperty("Hello Demo 4"), createImageView(), new Label("Hello Demo Content?!?!!"));
            Page page5 = new Page(new SimpleStringProperty("Prompt to close"), createImageView(), new Label("Click yes to close"), () -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Tab?");
                alert.setHeaderText("Do you want to close this tab?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ButtonType.OK){
                    return false;
                }
                return true;
            });
            VBox node = new VBox(new Label("Test"));
            node.setMaxHeight(Double.MAX_VALUE);
            node.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(node, Priority.ALWAYS);
            node.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            Page page6 = new Page(new SimpleStringProperty("Test"), createImageView(), node);

            Arrays.asList(page, page2, page3, page4, page5, page6).forEach(it -> multiPageDisplay.addPage(it));
        });
    }

    ImageView createImageView() {
        return new ImageView(new Image(getClass().getResourceAsStream("page_white_text.png")));
    }
}
