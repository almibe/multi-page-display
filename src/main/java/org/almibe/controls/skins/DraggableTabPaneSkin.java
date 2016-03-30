package org.almibe.controls.skins;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTabPane;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {

    private final ScrollPane header = new ScrollPane();
    private final HBox headerContent = new HBox();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);
        Bindings.bindContent(headerContent.getChildren(), draggableTabPane.getTabs());
        header.contentProperty().setValue(headerContent);
        this.getChildren().add(header);
    }
}
