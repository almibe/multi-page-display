/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.almibe.multipage.MultiPageDisplay;
import org.almibe.multipage.Page;
import org.almibe.multipage.PageBuilder;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;

public class MultiPageDisplayDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JFrame frame = new JFrame("Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.getContentPane().add(create().getComponent());
            frame.pack();
            frame.setVisible(true);
            frame.setSize(1000,400);
        });
    }

    public static MultiPageDisplay create() {
        MultiPageDisplay multiPageDisplay = new MultiPageDisplay();

        Page page = new PageBuilder()
            .setTitle("Hello Demo 1")
            .setIcon(createImageView())
            .setContent(new JLabel("Hello Demo Content")).createPage();

        Page page2 = new PageBuilder()
            .setTitle("Hello Demo 2")
            .setIcon(createImageView())
            .setContent(new JTextField("Hello Demo Content?")).createPage();

        Page page3 = new PageBuilder()
            .setTitle("Hello Demo 3")
            .setIcon(createImageView())
            .setContent(new JLabel("Hello Demo Content!")).createPage();

        Page page4 = new PageBuilder()
            .setTitle("Hello Demo 4")
            .setIcon(createImageView())
            .setContent(new JLabel("Hello Demo Content?!?!!")).createPage();

        Page page5 = new PageBuilder()
            .setTitle("Prompt to close")
            .setIcon(createImageView())
            .setContent(new JLabel("Click yes to close"))
            .setAllowClose(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Tab?");
                alert.setHeaderText("Do you want to close this tab?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ButtonType.OK){
                    return false;
                }
                return true;
            }).createPage();

//            VBox node = new VBox(new Label("Test"));
//            node.setMaxHeight(Double.MAX_VALUE);
//            node.setMaxWidth(Double.MAX_VALUE);
//            VBox.setVgrow(node, Priority.ALWAYS);
//            node.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Page page6 = new PageBuilder()
            .setTitle("Test")
            .setIcon(createImageView())
            .setContent(new JLabel("Test")).createPage();

        Arrays.asList(page, page2, page3, page4, page5, page6).forEach(it -> multiPageDisplay.addPage(it));

        return multiPageDisplay;
    }

    static ImageIcon createImageView() {
        return new ImageIcon(MultiPageDisplayDemo.class.getResource("page_white_text.png"));
    }

    static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Hey Content"));
        return panel;
    }
}
