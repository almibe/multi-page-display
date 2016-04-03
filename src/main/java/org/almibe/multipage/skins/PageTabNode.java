package org.almibe.multipage.skins;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.almibe.multipage.Page;

public class PageTabNode extends GridPane {
    private final Page page;
    private final Label text = new Label();
    private final Image closeIcon = new Image(getClass().getResourceAsStream("tango/emblem-unreadable16.png"));
    private final ImageView closeButton = new ImageView(closeIcon);
    private final ObjectProperty<Page> selectedPage;

    public PageTabNode(Page page, ObjectProperty<Page> selectedPage, TabAreaNode tabAreaNode) {
        super();
        this.page = page;
        this.selectedPage = selectedPage;

        text.textProperty().bind(page.textProperty());

        getColumnConstraints().add(new ColumnConstraints(20));
        GridPane.setConstraints(page.getImageView(), 0, 0);

        getColumnConstraints().add(new ColumnConstraints(200));
        GridPane.setConstraints(text, 1, 0);

        getColumnConstraints().add(new ColumnConstraints(20));
        GridPane.setConstraints(closeButton, 2, 0);

        getChildren().addAll(page.getImageView(), text, closeButton);

        setPadding(new Insets(10d));
        setBorder(createBorder());
        this.setOnMouseClicked(event -> {
            this.selectedPage.set(page);
        });

        closeButton.setOnMouseClicked(event -> {
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
                text.setFont(Font.font(text.getFont().getFamily(), FontWeight.NORMAL, text.getFont().getSize()));
            }
            if (page == newValue) {
                this.backgroundProperty().setValue(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, text.getFont().getSize()));
            }
        });
    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(0d, 0d, 0d, 0d, false), new BorderWidths(0,1,0,1)));
    }

    public Page getPage() {
        return page;
    }
}
