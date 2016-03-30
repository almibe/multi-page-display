package org.almibe.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.controls.skins.DraggableTabPaneSkin;

public class DraggableTabPane extends Control {
    private final ObservableList<DraggableTab> tabs = FXCollections.observableArrayList();
    //TODO maybe replace below with ObjectProperty<SingleSelectionModel<DraggableTab>> like TabPane uses?
    private final ObjectProperty<DraggableTab> selectedTab = new SimpleObjectProperty<>();

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DraggableTabPaneSkin(this);
    }

    public ObservableList<DraggableTab> getTabs() {
        return tabs;
    }

    public DraggableTab getSelectedTab() {
        return selectedTab.get();
    }

    public ObjectProperty<DraggableTab> selectedTabProperty() {
        return selectedTab;
    }

    public void setSelectedTab(DraggableTab selectedTab) {
        this.selectedTab.set(selectedTab);
    }
}
