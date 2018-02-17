package org.almibe.multipage;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

import java.util.Map;
import java.util.concurrent.Callable;

public class PageBuilder {
    private Node content;
    private ReadOnlyStringProperty title;
    private Node icon;
    private Callable<Boolean> allowClose;
    private Map<KeyCombination, Runnable> accelerators;

    public PageBuilder setContent(Node content) {
        this.content = content;
        return this;
    }

    public PageBuilder setTitle(ReadOnlyStringProperty title) {
        this.title = title;
        return this;
    }

    public PageBuilder setIcon(Node icon) {
        this.icon = icon;
        return this;
    }

    public PageBuilder setAllowClose(Callable<Boolean> allowClose) {
        this.allowClose = allowClose;
        return this;
    }

    public PageBuilder setAccelerators(Map<KeyCombination, Runnable> accelerators) {
        this.accelerators = accelerators;
        return this;
    }

    public Page createPage() {
        return new Page() {
            @Override public Node content() {
                return content;
            }
            @Override public ReadOnlyStringProperty title() {
                return title;
            }
            @Override public Node icon() {
                return icon;
            }
            @Override public Callable<Boolean> allowClose() {
                return allowClose;
            }
            @Override public Map<KeyCombination, Runnable> accelerators() {
                return accelerators;
            }
        };
    }
}
