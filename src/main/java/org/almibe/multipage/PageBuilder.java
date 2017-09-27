package org.almibe.multipage;

import javafx.scene.input.KeyCombination;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.Callable;

public class PageBuilder {
    private JComponent content;
    private String title;
    private ImageIcon icon;
    private Callable<Boolean> allowClose;
    private Map<KeyCombination, Runnable> accelerators;

    public PageBuilder setContent(JComponent content) {
        this.content = content;
        return this;
    }

    public PageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PageBuilder setIcon(ImageIcon icon) {
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
            @Override public JComponent component() {
                return content;
            }
            @Override public String title() {
                return title;
            }
            @Override public ImageIcon icon() {
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
