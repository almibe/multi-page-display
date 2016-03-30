package org.almibe.controls.skins;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTab;

public class DraggableTabSkin extends SkinBase<DraggableTab> {
    private final Label text = new Label();
    private final Button closeButton = new Button("X");
    private final HBox layout = new HBox();

    public DraggableTabSkin(DraggableTab draggableTab) {
        super(draggableTab);
        text.textProperty().bind(draggableTab.textProperty());
        layout.getChildren().addAll(text, closeButton);
        getChildren().add(layout);
    }
}
