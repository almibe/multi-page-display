package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.multipage.skins.MultiPageDisplaySkin;

public class MultiPageDisplay extends Control {
    private final ObservableList<Page> tabs = FXCollections.observableArrayList();
    //TODO maybe replace below with ObjectProperty<SingleSelectionModel<DraggableTab>> like TabPane uses?
    private final ObjectProperty<Page> selectedTab = new SimpleObjectProperty<>();

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MultiPageDisplaySkin(this);
    }

    public ObservableList<Page> getTabs() {
        return tabs;
    }

    public Page getSelectedTab() {
        return selectedTab.get();
    }

    public ObjectProperty<Page> selectedTabProperty() {
        return selectedTab;
    }

    public void setSelectedTab(Page selectedTab) {
        this.selectedTab.set(selectedTab);
    }
}
