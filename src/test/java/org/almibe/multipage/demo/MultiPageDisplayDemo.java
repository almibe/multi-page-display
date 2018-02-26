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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;
import org.almibe.multipage.PageBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MultiPageDisplayDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MultiPageDisplay multiPageDisplay = new MultiPageDisplay(mpd ->
            mpd.addPage(new PageBuilder()
                .setTitle(new SimpleStringProperty("Hey"))
                .setIcon(createImageView())
                .setContent(new Label("Hey Content")).createPage())
        );

        Map<KeyCombination, Runnable> accelerators = new HashMap<>();
        accelerators.put(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN),
            () -> System.out.println("Hello Accelerator!"));

        primaryStage.setScene(new Scene(multiPageDisplay, 700, 450));
        primaryStage.show();

        Platform.runLater(() -> {

            Page page = new PageBuilder()
                .setTitle(new SimpleStringProperty("Hello Demo 1"))
                .setIcon(createImageView())
                .setContent(new Label("Hello Demo Content")).createPage();

            Page page2 = new PageBuilder()
                .setTitle(new SimpleStringProperty("Hello Demo 2"))
                .setIcon(createImageView())
                .setContent(new Label("Hello Demo Content?")).createPage();

            Page page3 = new PageBuilder()
                .setTitle(new SimpleStringProperty("Hello Demo 3"))
                .setIcon(createImageView())
                .setContent(new Label("Hello Demo Content!")).createPage();

            Page page4 = new PageBuilder()
                .setTitle(new SimpleStringProperty("Hello Demo 4"))
                .setIcon(createImageView())
                .setContent(new Label("Hello Demo Content With Accelerator?!?!!"))
                .setAccelerators(accelerators).createPage();

            Page page5 = new PageBuilder()
                .setTitle(new SimpleStringProperty("Prompt to close"))
                .setIcon(createImageView())
                .setContent(new Label("Click yes to close"))
                .setAllowClose(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Close Tab?");
                    alert.setHeaderText("Do you want to close this tab?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK){
                        return false;
                    }
                    return true;
                }).createPage();

            VBox node = new VBox(new Label("Test"));
            node.setMaxHeight(Double.MAX_VALUE);
            node.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(node, Priority.ALWAYS);
            node.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            Page page6 = new PageBuilder()
                .setTitle(new SimpleStringProperty("Test"))
                .setIcon(createImageView())
                .setContent(node).createPage();

            Arrays.asList(page, page2, page3, page4, page5, page6).forEach(it -> multiPageDisplay.addPage(it));
        });
    }

    ImageView createImageView() {
        return new ImageView(new Image(getClass().getResourceAsStream("page_white_text.png")));
    }
}
