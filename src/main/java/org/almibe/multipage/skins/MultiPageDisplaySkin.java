/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage.skins;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;

public class MultiPageDisplaySkin extends SkinBase<MultiPageDisplay> {

    private final ScrollPane tabScrollPane = new ScrollPane();
    private final ImageView addTabButtonImage = new ImageView(new Image(getClass().getResourceAsStream("material/ic_add_black_36dp.png")));
    private final Button addTabButton = new Button();
    private final ImageView downArrowButtonImage = new ImageView(new Image(getClass().getResourceAsStream("material/ic_keyboard_arrow_down_black_36dp.png")));
    private final Button downArrowButton = new Button();
    private final ContextMenu openPagesList = new ContextMenu();
    private final HBox buttonControls = new HBox(downArrowButton, addTabButton);
    private final BorderPane header = new BorderPane();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();
    private final TabAreaNode tabArea;
    private final MultiPageDisplay multiPageDisplay;

    public MultiPageDisplaySkin(MultiPageDisplay multiPageDisplay) {
        super(multiPageDisplay);
        this.multiPageDisplay = multiPageDisplay;
        this.tabArea = new TabAreaNode(multiPageDisplay.selectedPageProperty());
        start();
    }

    public void start() {
        Platform.runLater(() -> {
            addTabButton.setGraphic(addTabButtonImage);
            downArrowButton.setGraphic(downArrowButtonImage);

            addTabButton.setStyle("-fx-background-color: #FFFFFF;");
            downArrowButton.setStyle("-fx-background-color: #FFFFFF;");
            addTabButton.setPadding(new Insets(0));
            downArrowButton.setPadding(new Insets(0));
            addTabButton.setBorder(null);
            downArrowButton.setBorder(null);

            tabScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tabScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tabScrollPane.contentProperty().setValue(tabArea);

            content.setFitToHeight(true);
            content.setFitToWidth(true);

            tabScrollPane.setStyle("-fx-background: rgb(200,200,200);");

            header.setCenter(tabScrollPane);
            header.setRight(buttonControls);

            tabPane.setTop(header);
            tabPane.setCenter(content);

            addTabButton.setOnMouseClicked(event -> addPage());
            downArrowButton.setOnMouseClicked(event -> showDropDown());

            multiPageDisplay.selectedPageProperty().addListener((observable, oldPage, newPage) -> pageFocusChange(newPage));

            tabArea.getPages().addListener((observable, oldPages, newPages) -> {
                if (newPages.isEmpty()) {
                    content.setContent(new Pane());
                }
            });

            this.getChildren().add(tabPane);
        });
    }

    public void addPage() {
        tabArea.addPage(multiPageDisplay.getDefaultPageFactory().createDefaultPage());
    }

    public void addPage(Page page) {
        tabArea.addPage(page);
    }

    public void replacePage(Page oldPage, Page newPage) {
        int location = tabArea.getPages().indexOf(oldPage);
        tabArea.removePage(oldPage);
        tabArea.getPages().add(location, newPage);
    }

    public void removePage(Page page) {
        tabArea.removePage(page);
    }

    public ReadOnlyListProperty<Page> getPages() {
        return tabArea.getPages();
    }

    private void showDropDown() {
        openPagesList.getItems().clear();
        getPages().forEach(page -> {
            MenuItem menuItem = new MenuItem(page.getText());
            if (multiPageDisplay.selectedPageProperty().get() == page) {
                menuItem.setStyle("-fx-font-weight: bold;");
            }
            menuItem.setOnAction(event -> multiPageDisplay.setSelectedPage(page));
            openPagesList.getItems().add(menuItem);
        });
        openPagesList.show(downArrowButton, Side.BOTTOM, 0, 0);
    }

    private void pageFocusChange(Page page) {
        Node node = tabArea.pageToNode(page);

        content.setContent(page.getContent());
        Platform.runLater(() -> content.requestFocus());

        tabScrollPane.layout();
        double width = tabScrollPane.getContent().getBoundsInLocal().getWidth();

        double left = node.getBoundsInParent().getMinX();
        double right = node.getBoundsInParent().getMaxX();
        double center = (left + right) / 2d;

        tabScrollPane.hmaxProperty().setValue(width);

        if (center < (width/2d)) {
            tabScrollPane.setHvalue(left);
        } else {
            tabScrollPane.setHvalue(right);
        }
    }
}
