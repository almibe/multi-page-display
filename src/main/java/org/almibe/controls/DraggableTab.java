package org.almibe.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.almibe.controls.skins.DraggableTabSkin;

public class DraggableTab extends Control {
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private final StringProperty text = new SimpleStringProperty();

    public DraggableTab(String title, Node node) {
        text.setValue(title);
        content.setValue(node);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DraggableTabSkin(this);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node node) {
        content.setValue(node);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String newValue) {
        text.set(newValue);
    }
}
