package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Page {
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private final StringProperty text = new SimpleStringProperty();
    private final EventHandler<Event> onCloseRequest;
    private final ImageView imageView;

    public Page(String title, ImageView image, Node node) {
        text.setValue(title);
        content.setValue(node);
        imageView = image;
        onCloseRequest = null;
    }

    public Page(String title, ImageView image, Node node, EventHandler<Event> onCloseRequest) {
        text.setValue(title);
        content.setValue(node);
        imageView = image;
        this.onCloseRequest = onCloseRequest;
    }

    public ImageView getImageView() {
        return imageView;
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

    public EventHandler<Event> getOnCloseRequest() {
        return onCloseRequest;
    }
}
