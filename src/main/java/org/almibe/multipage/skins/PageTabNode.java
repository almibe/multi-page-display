package org.almibe.multipage.skins;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.almibe.multipage.Page;

public class PageTabNode extends GridPane {
    private final Page page;
    private final Label text = new Label();
    private final Image closeIcon = new Image(getClass().getResourceAsStream("famfamfamsilk/cross.png"));
    private final Button closeButton = new Button();
    private final ObjectProperty<Page> selectedPage;

    public PageTabNode(Page page, ObjectProperty<Page> selectedPage, TabAreaNode tabAreaNode) {
        super();
        this.page = page;
        this.selectedPage = selectedPage;
        text.textProperty().bind(page.textProperty());
        closeButton.setGraphic(new ImageView(closeIcon));

        getColumnConstraints().add(new ColumnConstraints(32));
        GridPane.setConstraints(page.getImageView(), 0, 0);

        getColumnConstraints().add(new ColumnConstraints(500));
        GridPane.setConstraints(text, 1, 0);

        getColumnConstraints().add(new ColumnConstraints(32));
        GridPane.setConstraints(closeButton, 2, 0);

        getChildren().addAll(page.getImageView(), text, closeButton);

        setPadding(new Insets(10d));
        setBorder(createBorder());
        this.setOnMouseClicked(event -> {
            this.selectedPage.set(page);
        });

        closeButton.setOnAction(event -> {
            if (page.getOnCloseRequest() != null) {
                page.getOnCloseRequest().handle(event);
                if (event.isConsumed()) {
                    return;
                }
            }
            tabAreaNode.removePage(page);
        });

        selectedPage.addListener((observable, oldValue, newValue) -> {
            if (page == oldValue) {
                this.backgroundProperty().setValue(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (page == newValue) {
                this.backgroundProperty().setValue(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(10d, 10d, 0d, 0d, false), BorderWidths.DEFAULT));
    }

    public Page getPage() {
        return page;
    }
}
