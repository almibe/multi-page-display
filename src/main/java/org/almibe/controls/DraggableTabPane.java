package org.almibe.controls;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.controls.skins.DraggableTabPaneSkin;

public class DraggableTabPane extends Control {
    @Override
    protected Skin<?> createDefaultSkin() {
        return new DraggableTabPaneSkin(this);
    }
}
