package org.almibe.multipage.skins;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;

public class MultiPageDisplaySkin extends SkinBase<MultiPageDisplay> {

    private final ScrollPane tabScrollPane = new ScrollPane();
    private final ImageView addTabButton = new ImageView(new Image(getClass().getResourceAsStream("tango/list-add32.png")));
    private final ImageView leftArrowButton = new ImageView(new Image(getClass().getResourceAsStream("tango/go-previous32.png")));
    private final ImageView rightArrowButton = new ImageView(new Image(getClass().getResourceAsStream("tango/go-next32.png")));
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

            tabScrollPane.setStyle("-fx-background: rgb(200,200,200);");

            header.setCenter(tabScrollPane);
            header.setRight(buttonControls);

            tabPane.setTop(header);
            tabPane.setCenter(content);

            tabArea.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());
            tabScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> checkArrows());

            addTabButton.setOnMouseClicked(event -> addPage());
            rightArrowButton.setOnMouseClicked(event -> scrollRight());
            leftArrowButton.setOnMouseClicked(event -> scrollLeft());

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
            if (tabArea.getWidth() - leftArrowButton.getImage().getWidth() - rightArrowButton.getImage().getWidth() < tabScrollPane.getWidth() && buttonControls.getChildren().contains(arrowsControls)) {
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
