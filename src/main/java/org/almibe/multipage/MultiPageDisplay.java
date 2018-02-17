/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.multipage.skins.MultiPageDisplaySkin;

public class MultiPageDisplay extends Control {
    private final ObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();
    private final NewPageAction newPageAction;
    private final MultiPageDisplaySkin multiPageDisplaySkin;

    public MultiPageDisplay(NewPageAction newPageAction) {
        multiPageDisplaySkin = new MultiPageDisplaySkin(this);
        this.newPageAction = newPageAction;
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

    public void replacePage(Page oldPage, Page newPage) {
        multiPageDisplaySkin.replacePage(oldPage, newPage);
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

    public NewPageAction getNewPageAction() {
        return newPageAction;
    }

    public void setSelectedPage(Page selectedPage) {
        this.selectedPage.set(selectedPage);
    }
}
