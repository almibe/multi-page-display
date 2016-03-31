package org.almibe.controls.skins;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.almibe.controls.DraggableTab;

public class DraggableTabSkin extends HBox {
    private final Label text = new Label();
    private final Button closeButton = new Button("X");

    public DraggableTabSkin(DraggableTab draggableTab) {
        super();
        text.textProperty().bind(draggableTab.textProperty());
        getChildren().addAll(text, closeButton);
        setPadding(new Insets(10d));
        setSpacing(10d);
        setBorder(createBorder());
    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(10d, 10d, 0d, 0d, false), BorderWidths.DEFAULT));
    }
}
