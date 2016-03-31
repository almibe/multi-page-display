package org.almibe.controls.skins;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTab;
import org.almibe.controls.DraggableTabPane;

import java.util.function.Function;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {

    private final ScrollPane header = new ScrollPane();
    private final HBox headerContent = new HBox();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);

        Bindings.bindContent(headerContent.getChildren(), MappedList.map(draggableTabPane.getTabs(),
            (Function <DraggableTab, Node>) draggableTab -> new DraggableTabSkin(draggableTab, this)));

        content.contentProperty().bind(Bindings.select(draggableTabPane.selectedTabProperty(), "content"));

        header.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.contentProperty().setValue(headerContent);

        tabPane.setTop(header);
        tabPane.setCenter(content);

        this.getChildren().add(tabPane);
    }
}
