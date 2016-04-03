package org.almibe.multipage.skins;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.almibe.multipage.Page;

import java.util.HashMap;
import java.util.Map;

public class TabAreaNode extends HBox {
    private final ObservableList<Page> pages = FXCollections.observableArrayList();
    private final Map<Page, Node> pageNodeMap = new HashMap<>();
    private final ObjectProperty<Page> selectedPage;
    private final BorderPane tabPane;
    private MouseEvent dragDetected;

    public TabAreaNode(ObjectProperty<Page> selectedPage, BorderPane tabPane) {
        super();
        this.selectedPage = selectedPage;
        this.tabPane = tabPane;
    }

    public void addPage(Page page) {
        pages.add(page);
        Node node = new PageTabNode(page, selectedPage, this);
        pageNodeMap.put(page, node);
        getChildren().add(node);
        selectedPage.setValue(page);
        tabPane.setCenter(page.getContent());
        //TODO add DnD support
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
                selectedPage.setValue(sourcePage);
                MouseDragEvent.fireEvent(sourcePageTabNode,dragDetected);
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
                MouseDragEvent.fireEvent(sourcePageTabNode,dragDetected);
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
                tabPane.setCenter(new Pane());
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
