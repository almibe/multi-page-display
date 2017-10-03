/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.*
import javax.swing.border.LineBorder

class MultiPageDisplay {
    private data class PageData(val id: String, val page: Page, val tabComponent: JPanel)

    private val container = JPanel(BorderLayout())
    private val header = JPanel()
    private val bodyLayout = CardLayout()
    private val body = JPanel(bodyLayout)
    private val pages = arrayListOf<PageData>()

    val component: JComponent
        get() = container

    init {
        header.layout = BoxLayout(header, BoxLayout.X_AXIS)
        container.add(header, BorderLayout.NORTH)
        container.add(body, BorderLayout.CENTER)
    }

    fun addPage(page: Page) {
        val id = UUID.randomUUID().toString()
        val tabComponent = createTabComponent(page, id)

        pages.add(PageData(id, page, tabComponent))
        header.add(tabComponent)
        body.add(page.component(), id)
    }

    private fun createTabComponent(page: Page, id: String): JPanel {
        val panel = JPanel(BorderLayout())
        val closeButton = JLabel(createCloseImage())
        closeButton.border = LineBorder(Color.BLACK, 1)
        panel.add(JLabel(page.icon()), BorderLayout.WEST)
        panel.add(JLabel(page.title()), BorderLayout.CENTER)
        panel.add(closeButton, BorderLayout.EAST)

        closeButton.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                SwingUtilities.invokeLater {
                    pages.removeIf { page -> page.id == id }
                    body.remove(page.component())
                    header.remove(panel)
                    header.validate()
                    header.repaint()
                }
            }

            override fun mousePressed(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })

        panel.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                val pageId = pages.first { it.page == page }.id
                bodyLayout.show(body, pageId)
                pages.forEach {
                    page -> page.tabComponent.background = Color(206,206,206)
                }
                panel.background = Color.WHITE
            }

            override fun mousePressed(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })

        return panel
    }

    private fun createCloseImage(): ImageIcon {
        return ImageIcon(MultiPageDisplay::class.java.getResource("material/ic_close_black_18dp.png"))
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
