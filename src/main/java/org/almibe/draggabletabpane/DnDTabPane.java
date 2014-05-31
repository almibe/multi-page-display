package org.almibe.draggabletabpane;

import javafx.scene.control.Skin;
import javafx.scene.control.TabPane;

public class DnDTabPane extends TabPane {
    public DnDTabPane() {
        super();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TabPaneSkinNew(this);
    }
}