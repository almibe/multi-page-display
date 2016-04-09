package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.multipage.skins.MultiPageDisplaySkin;

public class MultiPageDisplay extends Control {
    private final ObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();
    private final DefaultPageFactory defaultPageFactory;
    private final MultiPageDisplaySkin multiPageDisplaySkin;

    public MultiPageDisplay(DefaultPageFactory defaultPageFactory) {
        multiPageDisplaySkin = new MultiPageDisplaySkin(this);
        this.defaultPageFactory = defaultPageFactory;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return multiPageDisplaySkin;
    }

    public ReadOnlyListProperty<Page> getPages() {
        return multiPageDisplaySkin.getPages();
    }

    public void addPage() {
        multiPageDisplaySkin.addPage();
    }

    public void addPage(Page page) {
        multiPageDisplaySkin.addPage(page);
    }

    public void removePage(Page page) {
        multiPageDisplaySkin.removePage(page);
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
