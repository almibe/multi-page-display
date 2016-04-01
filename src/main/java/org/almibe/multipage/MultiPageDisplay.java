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
    private final ObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();
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

    public Page getSelectedPage() {
        return selectedPage.get();
    }

    public ObjectProperty<Page> selectedPageProperty() {
        return selectedPage;
    }

    public DefaultPageFactory getDefaultPageFactory() {
        return defaultPageFactory;
    }

    public void setSelectedPage(Page selectedPage) {
        this.selectedPage.set(selectedPage);
    }
}
