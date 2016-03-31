package org.almibe.controls.skins;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTabPane;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {

    private final ObservableObjectValue<Node> selectedScene; //TODO figure out how to set correctly
    private final ObservableList<Node> headerControls; //TODO figure out how to set correctly

    private final ScrollPane header = new ScrollPane();
    private final HBox headerContent = new HBox();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);

        selectedScene = Bindings.select(draggableTabPane.selectedTabProperty(), "content");
        headerControls = MappedList.map(draggableTabPane.getTabs(), draggableTab -> new DraggableTabSkin(draggableTab));

        header.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.contentProperty().setValue(headerContent);

        tabPane.setTop(header);
        tabPane.setCenter(content);

        this.getChildren().add(tabPane);
    }
}
