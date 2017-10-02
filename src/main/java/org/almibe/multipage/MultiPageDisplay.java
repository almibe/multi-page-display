/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

public class MultiPageDisplay {
    private final JPanel container = new JPanel(new BorderLayout());
    private final JPanel header = new JPanel();
    private final CardLayout bodyLayout = new CardLayout();
    private final JPanel body = new JPanel(bodyLayout);
    private final BiMap<String, Page> pages = HashBiMap.create();

    public MultiPageDisplay() {
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        container.add(header, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);
    }

    public JComponent getComponent() {
        return container;
    }

    public void addPage(Page page) {
        final String id = UUID.randomUUID().toString();
        pages.put(id, page);
        header.add(createTabComponent(page, id));
        body.add(page.component(), id);
    }

    private JPanel createTabComponent(Page page, String id) {
        final JPanel panel = new JPanel(new BorderLayout());
        final JLabel closeButton = new JLabel(createCloseImage());
        closeButton.setBorder(new LineBorder(Color.BLACK, 1));
        panel.add(new JLabel(page.icon()), BorderLayout.WEST);
        panel.add(new JLabel(page.title()), BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.EAST);

        closeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    pages.remove(id);
                    body.remove(page.component());
                    header.remove(panel);
                    header.validate();
                    header.repaint();
                });
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bodyLayout.show(body, pages.inverse().get(page));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return panel;
    }

    private ImageIcon createCloseImage() {
        return new ImageIcon(MultiPageDisplay.class.getResource("material/ic_close_black_18dp.png"));
    }

//    public void replacePage(Page oldPage, Page newPage) {
//        multiPageDisplaySkin.replacePage(oldPage, newPage);
//    }
//
//    public void removePage(Page page) {
//        multiPageDisplaySkin.removePage(page);
//    }
//
//    public Page getSelectedPage() {
//        return selectedPage.get();
//    }
//
//    public ObjectProperty<Page> selectedPageProperty() {
//        return selectedPage;
//    }
//
//    public NewPageAction getNewPageAction() {
//        return newPageAction;
//    }
//
//    public void setSelectedPage(Page selectedPage) {
//        this.selectedPage.set(selectedPage);
//    }
}
