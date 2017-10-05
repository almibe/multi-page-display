/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder

class MultiPageDisplay(private val newPageAction: () -> Page) {
    private data class PageData(val id: String, val page: Page, val tabComponent: JPanel)

    private val container = JPanel(BorderLayout())
    private val tabPanel = JPanel()
    private val tabPanelViewPort = JViewport()
    private val header = JPanel(BorderLayout())
    private val dropDownButton = JLabel(createDownArrowImage())
    private val addButton = JLabel(createAddImage())
    private val buttonPanel = JPanel()
    private val bodyLayout = CardLayout()
    private val body = JPanel(bodyLayout)
    private val pages = arrayListOf<PageData>()

    val component: JComponent
        get() = container

    init {
        tabPanel.layout = BoxLayout(tabPanel, BoxLayout.X_AXIS)
        tabPanelViewPort.view = tabPanel

        dropDownButton.addMouseListener(DropDownListener(this))
        addButton.addMouseListener(AddTabListener(this))

        buttonPanel.add(dropDownButton)
        buttonPanel.add(addButton)

        header.add(tabPanelViewPort, BorderLayout.CENTER)
        header.add(buttonPanel, BorderLayout.EAST)

        container.add(header, BorderLayout.NORTH)
        container.add(body, BorderLayout.CENTER)
    }

    fun newPage() {
        val page = newPageAction()
        addPage(page)
        selectPage(page)
    }

    fun addPage(page: Page) {
        val id = UUID.randomUUID().toString()
        val tabComponent = createTabComponent(page, id)

        val pageData = PageData(id, page, tabComponent)
        pages.add(pageData)
        tabPanel.add(tabComponent)
        body.add(page.component(), id)

        if (pages.size == 1) {
            selectPage(pageData)
        }
    }

    private fun createTabComponent(page: Page, id: String): JPanel {
        val panel = JPanel(BorderLayout())
        val closeButton = JLabel(createCloseImage())
        val closeButtonPanel = JPanel()
        closeButton.border = LineBorder(Color.BLACK, 1)
        closeButton.isOpaque = true
        closeButton.background = Color.WHITE
        closeButtonPanel.isOpaque = false
        closeButtonPanel.add(closeButton)
        val icon = JLabel(page.icon())
        icon.border = EmptyBorder(10,10,10,10)
        panel.add(icon, BorderLayout.WEST)
        panel.add(JLabel(page.title()), BorderLayout.CENTER)
        panel.add(closeButtonPanel, BorderLayout.EAST)

        closeButton.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                SwingUtilities.invokeLater {
                    pages.removeIf { page -> page.id == id }
                    body.remove(page.component())
                    tabPanel.remove(panel)
                    tabPanel.validate()
                    tabPanel.repaint()
                }
            }

            override fun mousePressed(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })

        panel.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                selectPage(page)
            }

            override fun mousePressed(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })

        SwingUtilities.invokeLater {
            if (closeButtonPanel.width > closeButtonPanel.height) {
                closeButtonPanel.minimumSize = Dimension(closeButtonPanel.width, closeButtonPanel.width)
                closeButtonPanel.preferredSize = Dimension(closeButtonPanel.width, closeButtonPanel.width)
            } else {
                closeButtonPanel.minimumSize = Dimension(closeButtonPanel.height, closeButtonPanel.height)
                closeButtonPanel.preferredSize = Dimension(closeButtonPanel.height, closeButtonPanel.height)
            }
        }

        return panel
    }

    private fun createCloseImage(): ImageIcon {
        return ImageIcon(MultiPageDisplay::class.java.getResource("material/ic_close_black_18dp.png"))
    }

    private fun createDownArrowImage(): ImageIcon {
        return ImageIcon(MultiPageDisplay::class.java.getResource("material/ic_keyboard_arrow_down_black_24dp.png"))
    }

    private fun createAddImage(): ImageIcon {
        return ImageIcon(MultiPageDisplay::class.java.getResource("material/ic_add_black_24dp.png"))
    }

    private class DropDownListener(val multiPageDisplay: MultiPageDisplay): MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            multiPageDisplay.showDropDown()
        }
    }

    private class AddTabListener(val multiPageDisplay: MultiPageDisplay): MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            multiPageDisplay.newPage()
        }
    }

    fun showDropDown() {
        val menu = JPopupMenu()
        pages.forEach {
            val menuItem = JMenuItem(it.page.title())
            menuItem.addActionListener { _ ->
                selectPage(it)
            }
            menu.add(menuItem)
        }
        menu.show(dropDownButton, 0, dropDownButton.height)
    }

    fun selectPage(page: Page) {
        val pageData = pages.firstOrNull { it.page == page }
        if (pageData != null) {
            selectPage(pageData)
        }
    }

    private fun selectPage(pageData: PageData) {
        SwingUtilities.invokeLater {
            val pageId = pageData.id
            val tabComponent = pageData.tabComponent
            bodyLayout.show(body, pageId)
            pages.forEach { page ->
                page.tabComponent.background = Color(206, 206, 206)
            }
            tabComponent.background = Color.WHITE
            when {
                tabComponent.x < tabPanelViewPort.viewPosition.x ->
                    tabPanelViewPort.viewPosition = Point(tabComponent.x, 0)
                tabComponent.x + tabComponent.width > tabPanelViewPort.viewPosition.x + tabPanelViewPort.width ->
                    tabPanelViewPort.viewPosition = Point(tabComponent.x, 0)
                else -> {
                }
            }
        }
    }

    fun removePage(page: Page) {
        TODO()
    }
}
