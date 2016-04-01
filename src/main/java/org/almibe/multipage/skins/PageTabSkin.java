package org.almibe.multipage.skins;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.almibe.multipage.Page;

public class PageTabSkin extends HBox {
    private final Page page;
    private final Label text = new Label();
    private final Button closeButton = new Button("X");
    private final MultiPageDisplaySkin multiPageDisplaySkin;

    public PageTabSkin(Page page, MultiPageDisplaySkin multiPageDisplaySkin) {
        super();
        this.page = page;
        this.multiPageDisplaySkin = multiPageDisplaySkin;
        text.textProperty().bind(page.textProperty());
        getChildren().addAll(text, closeButton);
        setPadding(new Insets(10d));
        setSpacing(10d);
        setBorder(createBorder());
        this.setOnMouseClicked(event -> {
            multiPageDisplaySkin.getSkinnable().setSelectedTab(page);
        });
        closeButton.setOnAction(event -> multiPageDisplaySkin.getMultiPageDisplay().getPages().remove(page));
        multiPageDisplaySkin.getSkinnable().selectedTabProperty().addListener((observable, oldValue, newValue) -> {
            if (page == oldValue) {
                this.backgroundProperty().setValue(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (page == newValue) {
                this.backgroundProperty().setValue(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        //TODO add dnd support

    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(10d, 10d, 0d, 0d, false), BorderWidths.DEFAULT));
    }
}
