package org.almibe.multipage.skins;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;

import java.util.HashMap;
import java.util.Map;

public class MultiPageDisplaySkin extends SkinBase<MultiPageDisplay> {

    private final ScrollPane tabScrollPane = new ScrollPane();
    private final HBox tabArea = new HBox();
    private final Button addTabButton = new Button("+");
    private final Button leftArrowButton = new Button("<");
    private final Button rightArrowButton = new Button(">");
    private final HBox arrowsControls = new HBox(leftArrowButton, rightArrowButton);
    private final HBox buttonControls = new HBox(arrowsControls, addTabButton);
    private final BorderPane header = new BorderPane();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();
    private final ObservableList<Page> pages = FXCollections.observableArrayList();
    private final Map<Page, Node> pageNodeMap = new HashMap<>();
    private MouseEvent dragDetected;

    public MultiPageDisplay getMultiPageDisplay() {
        return multiPageDisplay;
    }

    private final MultiPageDisplay multiPageDisplay;

    public MultiPageDisplaySkin(MultiPageDisplay multiPageDisplay) {
        super(multiPageDisplay);
        this.multiPageDisplay = multiPageDisplay;

        start();
    }

    public void start() {
        Platform.runLater(() -> {
            content.contentProperty().bind(Bindings.select(multiPageDisplay.selectedPageProperty(), "content"));

            content.contentProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> content.requestFocus());
            });

            tabScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tabScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tabScrollPane.contentProperty().setValue(tabArea);

            header.setCenter(tabScrollPane);
            header.setRight(buttonControls);

            tabPane.setTop(header);
            tabPane.setCenter(content);

            tabArea.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());
            tabScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());

            addTabButton.setOnAction(event -> addPage());
            rightArrowButton.setOnAction(event -> scrollRight());
            leftArrowButton.setOnAction(event -> scrollLeft());

            this.getChildren().add(tabPane);

            checkArrows();
        });
    }

    public void addPage() {
        Platform.runLater(() -> addPage(multiPageDisplay.getDefaultPageFactory().createDefaultPage()));
    }

    public void addPage(Page page) {
        Platform.runLater(() -> {
            addPageNode(page);
        });
    }

    public void removePage(Page page) {
        pages.remove(page);
        Node node = pageNodeMap.remove(page);
        if (multiPageDisplay.getSelectedPage() == page) {
            int index = tabArea.getChildren().indexOf(node);
            if (multiPageDisplay.getPages().size() == 0) {
                tabPane.setCenter(new Pane());
            } else if (index == 0) {
                multiPageDisplay.setSelectedPage(multiPageDisplay.getPages().get(index));
            } else {
                multiPageDisplay.setSelectedPage(multiPageDisplay.getPages().get(index - 1));
            }
        }
        tabArea.getChildren().remove(node);
    }

    public ReadOnlyListProperty<Page> getPages() {
        return new ReadOnlyListWrapper(pages);
    }

    private void addPageNode(Page addedPage) {
        pages.add(addedPage);
        Node node = new PageTabSkin(addedPage, this);
        pageNodeMap.put(addedPage, node);
        tabArea.getChildren().add(node);
        multiPageDisplay.setSelectedPage(addedPage);
        tabPane.setCenter(content);
        //TODO add DnD support
        node.setOnMouseReleased(event -> event.consume());
        node.setOnDragDetected(event -> {
            node.startFullDrag();
            dragDetected = event;
            event.consume();
        });
        node.setOnMouseDragged(event -> event.consume());
        node.setOnMouseDragEntered(event -> {
            PageTabSkin sourcePageTabSkin = ((PageTabSkin)event.getGestureSource());
            PageTabSkin targetPageTabSkin = ((PageTabSkin)event.getTarget());
            Page sourcePage = sourcePageTabSkin.getPage();
            Page targetPage = targetPageTabSkin.getPage();
            if (sourcePage == targetPage) return;
            int sourceIndex = this.pages.indexOf(sourcePage);
            int targetIndex = this.pages.indexOf(targetPage);

            if (sourceIndex > targetIndex) {
                removePage(sourcePage);
                pages.add(targetIndex, sourcePage);
                tabArea.getChildren().add(targetIndex, sourcePageTabSkin);
                multiPageDisplay.setSelectedPage(sourcePage);
                MouseDragEvent.fireEvent(sourcePageTabSkin,dragDetected);
            } else {
                for(int i = sourceIndex+1; i-1 < targetIndex; i++) {
                    Page removedPage = multiPageDisplay.getPages().remove(i);
                    Node pageNode = pageNodeMap.remove(removedPage);
                    tabArea.getChildren().remove(pageNode);
                    tabArea.getChildren().add(i-1, pageNode);
                }
                multiPageDisplay.setSelectedPage(sourcePage);
                MouseDragEvent.fireEvent(sourcePageTabSkin,dragDetected);
            }
            node.setMouseTransparent(false);
            event.consume();
        });
        node.setOnMouseDragReleased(event -> event.consume());
    }

    private void checkArrows() {
        Platform.runLater(() -> {
            if (tabArea.getWidth() > tabScrollPane.getWidth() && !buttonControls.getChildren().contains(arrowsControls)) {
                buttonControls.getChildren().add(0, arrowsControls);
                return;
            }
            if (tabArea.getWidth() - leftArrowButton.getWidth() - rightArrowButton.getWidth() < tabScrollPane.getWidth() && buttonControls.getChildren().contains(arrowsControls)) {
                buttonControls.getChildren().remove(arrowsControls);
            }
        });
    }

    private void scrollRight() {
        double scrollAmount = 500d/tabArea.widthProperty().doubleValue();
        Platform.runLater(() -> {
            tabScrollPane.setHvalue(tabScrollPane.getHvalue() + scrollAmount);
        });
    }

    private void scrollLeft() {
        double scrollAmount = 500d/tabArea.widthProperty().doubleValue();
        Platform.runLater(() -> {
            tabScrollPane.setHvalue(tabScrollPane.getHvalue() - scrollAmount);
        });
    }
}
