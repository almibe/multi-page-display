package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class Page {
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private final StringProperty text = new SimpleStringProperty();

    public Page(String title, Node node) {
        text.setValue(title);
        content.setValue(node);
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