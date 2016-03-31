package org.almibe.controls.skins;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.almibe.controls.DraggableTab;
import org.almibe.controls.DraggableTabPane;

public class DraggableTabPaneSkin extends SkinBase<DraggableTabPane> {

    //private final ObservableObjectValue<Node> selectedScene; //TODO figure out how to set correctly
    private final ObservableList<Node> headerControls = FXCollections.observableArrayList(); //TODO figure out how to set correctly

    private final ScrollPane header = new ScrollPane();
    private final HBox headerContent = new HBox();
    private final ScrollPane content = new ScrollPane();
    private final BorderPane tabPane = new BorderPane();

    public DraggableTabPaneSkin(DraggableTabPane draggableTabPane) {
        super(draggableTabPane);

        //selectedScene = Bindings.select(draggableTabPane.selectedTabProperty(), "content");
        draggableTabPane.getTabs().addListener((ListChangeListener<DraggableTab>) c -> {
            while (c.next()) {
                if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {

                    }
                } else if (c.wasUpdated()) {

                } else {
                    for (DraggableTab remitem : c.getRemoved()) {

                    }
                    for (DraggableTab draggableTab : c.getAddedSubList()) {
                        DraggableTabSkin skin = new DraggableTabSkin(draggableTab, this);
                        headerContent.getChildren().add(skin);
                    }
                }
            }
        });

        header.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        header.contentProperty().setValue(headerContent);

        tabPane.setTop(header);
        tabPane.setCenter(content);

        this.getChildren().add(tabPane);
    }
}
