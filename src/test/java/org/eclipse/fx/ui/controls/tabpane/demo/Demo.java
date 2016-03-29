package org.eclipse.fx.ui.controls.tabpane.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.eclipse.fx.ui.controls.tabpane.DndTabPaneFactory;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox h = new HBox();

        {
            Pane pane = DndTabPaneFactory.createDefaultDnDPane(
            DndTabPaneFactory.FeedbackType.MARKER, this::setupTb1);
            HBox.setHgrow(pane, Priority.ALWAYS);
            h.getChildren().add(pane);
        }

        {
            Pane pane = DndTabPaneFactory.createDefaultDnDPane(
            DndTabPaneFactory.FeedbackType.MARKER, this::setupTb2);
            HBox.setHgrow(pane, Priority.ALWAYS);
            h.getChildren().add(pane);
        }

        final Scene scene = new Scene(h, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void setupTb1(TabPane tb) {
        {
            Tab tab = new Tab("T 1.1");
            Rectangle r = new Rectangle(100, 100);
            r.setFill(Color.GREEN);
            tab.setContent(new BorderPane(r));
            tb.getTabs().add(tab);
        }
    }

    private void setupTb2(TabPane tb) {
        {
            Tab tab = new Tab("Tab 1.2");
            Rectangle r = new Rectangle(100, 100);
            r.setFill(Color.RED);
            tab.setContent(new BorderPane(r));
            tb.getTabs().add(tab);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}