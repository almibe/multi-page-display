package org.almibe.controls.skins;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SkinBase;
import org.almibe.controls.DraggableTab;
import org.almibe.controls.DraggableTabPane;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {
    private final ObservableList<DraggableTab> tabs = FXCollections.observableArrayList();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);
    }
}
