package org.almibe.multipage.skins;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
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
    private final Map<Page, Node> pageNodeMap = new HashMap<>();

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
            multiPageDisplay.getPages().addListener((ListChangeListener<? super Page>) c -> {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        //for (int i = c.getFrom(); i < c.getTo(); ++i) {
                            System.out.println("permutate");
                        //}
                    } else if (c.wasUpdated()) {
                        System.out.println("update");
                    } else {
                        for (Page remitem : c.getRemoved()) {
                            Node node = pageNodeMap.remove(remitem);
                            if (multiPageDisplay.getSelectedPage() == remitem) {
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
                        for (Page addedPage : c.getAddedSubList()) {
                            Node node = new PageTabSkin(addedPage, this);
                            pageNodeMap.put(addedPage, node);
                            tabArea.getChildren().add(node);
                            multiPageDisplay.setSelectedPage(addedPage);
                            tabPane.setCenter(content);
                        }
                    }
                }
            });

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

    private void addPage() {
        Platform.runLater(() -> multiPageDisplay.getPages().add(multiPageDisplay.getDefaultPageFactory().createDefaultPage()));
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
        Platform.runLater(() -> {
            tabScrollPane.setHvalue(tabScrollPane.getHvalue() + tabScrollPane.getHmax() * .1);
        });
    }

    private void scrollLeft() {
        Platform.runLater(() -> {
            tabScrollPane.setHvalue(tabScrollPane.getHvalue() - tabScrollPane.getHmax() * .1);
        });
    }
}
