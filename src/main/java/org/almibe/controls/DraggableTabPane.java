package org.almibe.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.controls.skins.DraggableTabPaneSkin;

public class DraggableTabPane extends Control {
    private final ObservableList<DraggableTab> tabs = FXCollections.observableArrayList();

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DraggableTabPaneSkin(this);
    }

    public ObservableList<DraggableTab> getTabs() {
        return tabs;
    }
}
