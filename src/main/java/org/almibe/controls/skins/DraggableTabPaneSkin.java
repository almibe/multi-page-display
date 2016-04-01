package org.almibe.controls.skins;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTab;
import org.almibe.controls.DraggableTabPane;

import java.util.function.Function;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {

    private final ScrollPane tabScrollPane = new ScrollPane();
    private final HBox tabArea = new HBox();
    private final Button addTabButton = new Button("+");
    private final Button leftArrowButton = new Button("<");
    private final Button rightArrowButton = new Button(">");
    private final HBox arrowsControls = new HBox(leftArrowButton, rightArrowButton, addTabButton);
    private final BorderPane header = new BorderPane();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);

        Bindings.bindContent(tabArea.getChildren(), MappedList.map(draggableTabPane.getTabs(),
            (Function <DraggableTab, Node>) draggableTab -> new DraggableTabSkin(draggableTab, this)));

        content.contentProperty().bind(Bindings.select(draggableTabPane.selectedTabProperty(), "content"));

        tabScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tabScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tabScrollPane.contentProperty().setValue(tabArea);

        header.setCenter(tabScrollPane);
        header.setRight(arrowsControls);

        tabPane.setTop(header);
        tabPane.setCenter(content);

        tabArea.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());
        tabScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());

        this.getChildren().add(tabPane);
    }

    private void checkArrows() {
        if (tabArea.getWidth() > tabScrollPane.getWidth()) {
            if (!arrowsControls.getChildren().contains(leftArrowButton)) {
                arrowsControls.getChildren().add(0,leftArrowButton);
                arrowsControls.getChildren().add(1,rightArrowButton);
                leftArrowButton.setVisible(true);
                rightArrowButton.setVisible(true);
                System.out.println("show arrowsControls");
            }
        } else {
            if (arrowsControls.getChildren().contains(leftArrowButton)) {
                arrowsControls.getChildren().remove(leftArrowButton);
                arrowsControls.getChildren().remove(rightArrowButton);
                leftArrowButton.setVisible(false);
                rightArrowButton.setVisible(false);
                System.out.println("hide arrowsControls");
            }
        }
    }
}
