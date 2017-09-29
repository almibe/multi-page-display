/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

import javax.swing.*;

public class MultiPageDisplay {
    private final DnDTabbedPane tabbedPane = new DnDTabbedPane();

    public MultiPageDisplay() {
    }

    public JComponent getComponent() {
        return tabbedPane;
    }

    public void addPage(Page page) {
        tabbedPane.addTab(page.title(), page.icon(), page.component());
    }

//    public void replacePage(Page oldPage, Page newPage) {
//        multiPageDisplaySkin.replacePage(oldPage, newPage);
//    }
//
//    public void removePage(Page page) {
//        multiPageDisplaySkin.removePage(page);
//    }
//
//    public Page getSelectedPage() {
//        return selectedPage.get();
//    }
//
//    public ObjectProperty<Page> selectedPageProperty() {
//        return selectedPage;
//    }
//
//    public NewPageAction getNewPageAction() {
//        return newPageAction;
//    }
//
//    public void setSelectedPage(Page selectedPage) {
//        this.selectedPage.set(selectedPage);
//    }
}
