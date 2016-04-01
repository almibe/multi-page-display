package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.multipage.skins.MultiPageDisplaySkin;

public class MultiPageDisplay extends Control {
    private final ObservableList<Page> pages = FXCollections.observableArrayList();
    //TODO maybe replace below with ObjectProperty<SingleSelectionModel<DraggableTab>> like TabPane uses?
    private final ObjectProperty<Page> selectedTab = new SimpleObjectProperty<>();
    private final DefaultPageFactory defaultPageFactory;

    public MultiPageDisplay(DefaultPageFactory defaultPageFactory) {
        this.defaultPageFactory = defaultPageFactory;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MultiPageDisplaySkin(this);
    }

    public ObservableList<Page> getPages() {
        return pages;
    }

    public Page getSelectedTab() {
        return selectedTab.get();
    }

    public ObjectProperty<Page> selectedTabProperty() {
        return selectedTab;
    }

    public DefaultPageFactory getDefaultPageFactory() {
        return defaultPageFactory;
    }

    public void setSelectedTab(Page selectedTab) {
        this.selectedTab.set(selectedTab);
    }
}