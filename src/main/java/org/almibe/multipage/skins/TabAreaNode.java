/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage.skins;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.almibe.multipage.Page;

import java.util.HashMap;
import java.util.Map;

public class TabAreaNode extends HBox {
    private final ObservableList<Page> pages = FXCollections.observableArrayList();
    private final Map<Page, Node> pageNodeMap = new HashMap<>();
    private final ObjectProperty<Page> selectedPage;
    private MouseEvent dragDetected;

    public TabAreaNode(ObjectProperty<Page> selectedPage) {
        super();
        this.selectedPage = selectedPage;
    }

    public Node pageToNode(Page page) {
        return pageNodeMap.get(page);
    }

    public void addPage(Page page) {
        addPage(pages.size(), page);
    }

    public void addPage(int index, Page page) {
        pages.add(index, page);
        Node node = new PageTabNode(page, selectedPage, this);
        pageNodeMap.put(page, node);
        getChildren().add(index, node);
        selectedPage.setValue(page);
        node.setOnMouseReleased(event -> event.consume());
        node.setOnDragDetected(event -> {
            node.startFullDrag();
            dragDetected = event;
            event.consume();
        });
        node.setOnMouseDragged(event -> event.consume());
        node.setOnMouseDragEntered(event -> {
            PageTabNode sourcePageTabNode = ((PageTabNode)event.getGestureSource());
            PageTabNode targetPageTabNode = ((PageTabNode)event.getTarget());
            Page sourcePage = sourcePageTabNode.getPage();
            Page targetPage = targetPageTabNode.getPage();
            if (sourcePage == targetPage) return;
            int sourceIndex = this.pages.indexOf(sourcePage);
            int targetIndex = this.pages.indexOf(targetPage);

            if (sourceIndex > targetIndex) {
                removePage(sourcePage);
                pages.add(targetIndex, sourcePage);
                getChildren().add(targetIndex, sourcePageTabNode);
                pageNodeMap.put(sourcePage, sourcePageTabNode);
                selectedPage.setValue(sourcePage);
                //MouseDragEvent.fireEvent(sourcePageTabNode,dragDetected);
            } else {
                for(int i = sourceIndex+1; i-1 < targetIndex; i++) {
                    Page removedPage = pages.remove(i);
                    Node pageNode = pageNodeMap.remove(removedPage);
                    getChildren().remove(pageNode);
                    pageNodeMap.put(removedPage, pageNode);
                    pages.add(i-1, removedPage);
                    getChildren().add(i-1, pageNode);
                }
                selectedPage.setValue(sourcePage);
                //MouseDragEvent.fireEvent(sourcePageTabNode,dragDetected);
            }
            node.setMouseTransparent(false);
            event.consume();
        });
        node.setOnMouseDragReleased(event -> event.consume());
    }

    public void removePage(Page page) {
        pages.remove(page);
        Node node = pageNodeMap.remove(page);
        if (selectedPage.get() == page) {
            int index = getChildren().indexOf(node);
            if (pages.size() == 0) {
                selectedPage.setValue(null);
            } else if (index == 0) {
                selectedPage.setValue(pages.get(index));
            } else {
                selectedPage.setValue(pages.get(index - 1));
            }
        }
        getChildren().remove(node);
    }

    public ReadOnlyListProperty<Page> getPages() {
        return new ReadOnlyListWrapper(pages);
    }
}
