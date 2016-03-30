package org.almibe.controls;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.controls.skins.DraggableTabSkin;

public class DraggableTab extends Control {
    @Override
    protected Skin<?> createDefaultSkin() {
        return new DraggableTabSkin(this);
    }
}
