package org.almibe.multipage.skins;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.almibe.multipage.Page;

public class PageTabSkin extends HBox {
    private final Label text = new Label();
    private final Button closeButton = new Button("X");
    private final MultiPageDisplaySkin multiPageDisplaySkin;

    public PageTabSkin(Page page, MultiPageDisplaySkin multiPageDisplaySkin) {
        super();
        this.multiPageDisplaySkin = multiPageDisplaySkin;
        text.textProperty().bind(page.textProperty());
        getChildren().addAll(text, closeButton);
        setPadding(new Insets(10d));
        setSpacing(10d);
        setBorder(createBorder());
        this.setOnMouseClicked(event -> {
            multiPageDisplaySkin.getSkinnable().setSelectedTab(page);
        });
    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(10d, 10d, 0d, 0d, false), BorderWidths.DEFAULT));
    }
}
