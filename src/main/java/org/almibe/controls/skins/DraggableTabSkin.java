package org.almibe.controls.skins;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.almibe.controls.DraggableTab;

public class DraggableTabSkin extends SkinBase<DraggableTab> {
    private final Label text = new Label();
    private final Button closeButton = new Button("X");
    private final HBox layout = new HBox();

    public DraggableTabSkin(DraggableTab draggableTab) {
        super(draggableTab);
        text.textProperty().bind(draggableTab.textProperty());
        layout.getChildren().addAll(text, closeButton);
        layout.setPadding(new Insets(10d));
        layout.setSpacing(10d);
        layout.setBorder(createBorder());
        FlowPane region = new FlowPane();
        region.getChildren().addAll(layout);
        getChildren().add(region);
    }

    private Border createBorder() {
        return new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            new CornerRadii(10d, 10d, 0d, 0d, false), BorderWidths.DEFAULT));
    }
}
