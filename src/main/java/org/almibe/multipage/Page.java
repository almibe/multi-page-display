/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class Page {
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private final ReadOnlyStringProperty title;
    private final EventHandler<Event> onCloseRequest;
    private final Node icon;

    public Page(ReadOnlyStringProperty title, Node icon, Node node) {
        this.title = title;
        this.content.setValue(node);
        this.icon = icon;
        this.onCloseRequest = null;
    }

    public Page(ReadOnlyStringProperty title, Node icon, Node node, EventHandler<Event> onCloseRequest) {
        this.title = title;
        this.content.setValue(node);
        this.icon = icon;
        this.onCloseRequest = onCloseRequest;
    }

    public Node getIcon() {
        return icon;
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public String getTitle() {
        return title.get();
    }

    public ReadOnlyStringProperty titleProperty() {
        return title;
    }

    public EventHandler<Event> getOnCloseRequest() {
        return onCloseRequest;
    }
}
