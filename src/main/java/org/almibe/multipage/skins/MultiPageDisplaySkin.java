package org.almibe.multipage.skins;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;

public class MultiPageDisplaySkin extends SkinBase<MultiPageDisplay> {

    private final ScrollPane tabScrollPane = new ScrollPane();
    private final Button addTabButton = new Button("+");
    private final Button leftArrowButton = new Button("<");
    private final Button rightArrowButton = new Button(">");
    private final HBox arrowsControls = new HBox(leftArrowButton, rightArrowButton);
    private final HBox buttonControls = new HBox(arrowsControls, addTabButton);
    private final BorderPane header = new BorderPane();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();
    private final TabAreaNode tabArea;

    public MultiPageDisplay getMultiPageDisplay() {
        return multiPageDisplay;
    }

    private final MultiPageDisplay multiPageDisplay;

    public MultiPageDisplaySkin(MultiPageDisplay multiPageDisplay) {
        super(multiPageDisplay);
        this.multiPageDisplay = multiPageDisplay;
        this.tabArea = new TabAreaNode(multiPageDisplay.selectedPageProperty(), tabPane);
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
        tabArea.addPage(multiPageDisplay.getDefaultPageFactory().createDefaultPage());
    }

    public void addPage(Page page) {
        tabArea.addPage(page);
    }

    public void removePage(Page page) {
        tabArea.removePage(page);
    }

    public ReadOnlyListProperty<Page> getPages() {
        return tabArea.getPages();
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
