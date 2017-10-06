/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.imageio.ImageIO
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

    private var selectedPage: PageData? = null
    private var dragPage: PageData? = null
    private var previousPanel: JPanel? = null

    val component: JComponent
        get() = container

    init {
        tabPanel.layout = BoxLayout(tabPanel, BoxLayout.X_AXIS)
        tabPanelViewPort.view = tabPanel
        tabPanelViewPort.addMouseWheelListener { e ->
            val newX = tabPanelViewPort.viewPosition.x + e.unitsToScroll*2
            if (newX > 0 && newX < tabPanel.width) {
                tabPanelViewPort.viewPosition = Point(newX, 0)
                tabPanelViewPort.updateUI()
            }
        }

        dropDownButton.addMouseListener(DropDownListener(this))
        addButton.addMouseListener(AddTabListener(this))

        buttonPanel.add(dropDownButton)
        buttonPanel.add(addButton)

        header.add(tabPanelViewPort, BorderLayout.CENTER)
        header.add(buttonPanel, BorderLayout.EAST)

        container.add(header, BorderLayout.NORTH)
        container.add(body, BorderLayout.CENTER)

        setupKeyboardShortcuts()
    }

    fun newPage() {
        val page = newPageAction()
        addPage(page)
        selectPage(page)
        tabPanelViewPort.updateUI()
        body.updateUI()
    }

    fun addPage(page: Page) {
        val existingPage = pages.find { pageData -> pageData.page == page }
        if (existingPage != null) {
            selectPage(existingPage)
        } else {
            val id = UUID.randomUUID().toString()
            val pageData = createTabComponent(page, id)
            pages.add(pageData)
            tabPanel.add(pageData.tabComponent)
            body.add(page.component, id)

            if (pages.size == 1) {
                selectPage(pageData)
            }
        }
    }

    private fun createTabComponent(page: Page, id: String): PageData {
        val panel = JPanel(BorderLayout())
        val closeButton = JLabel(createCloseImage())
        val closeButtonPanel = JPanel()
        closeButton.border = LineBorder(Color.BLACK, 1)
        closeButton.isOpaque = true
        closeButton.background = Color.WHITE
        closeButtonPanel.isOpaque = false
        closeButtonPanel.add(closeButton)
        val icon = if (page.icon != null) {
            JLabel(page.icon)
        } else {
            JLabel()
        }
        icon.border = EmptyBorder(10, 10, 10, 10)
        panel.add(icon, BorderLayout.WEST)
        panel.add(JLabel(page.title), BorderLayout.CENTER)
        panel.add(closeButtonPanel, BorderLayout.EAST)

        val pageData = PageData(id, page, panel)

        closeButton.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                checkRemovePage(page)
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

            override fun mousePressed(e: MouseEvent) {
                dragPage = pageData
            }
            override fun mouseReleased(e: MouseEvent) {
                dragPage = null
            }
            override fun mouseEntered(e: MouseEvent) {
                if (dragPage != null && dragPage?.tabComponent != panel && panel != previousPanel) {
                    handleTabDrag(pageData)
                }
                previousPanel = panel
            }
            override fun mouseExited(e: MouseEvent) {}
        })

        return pageData
    }

    private fun handleTabDrag(panel: PageData) {
        SwingUtilities.invokeLater {
            val panelIndex = tabPanel.components.indexOf(panel.tabComponent)
            tabPanel.remove(dragPage?.tabComponent)
            pages.remove(dragPage)
            tabPanel.add(dragPage?.tabComponent, panelIndex)
            pages.add(panelIndex, dragPage!!)
            tabPanel.updateUI()
        }
    }

    private fun createCloseImage(): ImageIcon {
        try {
            return ImageIcon(ImageIO.read(this.javaClass.getResourceAsStream("material/ic_close_black_18dp.png")))
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    private fun createDownArrowImage(): ImageIcon {
        try {
            return ImageIcon(ImageIO.read(this.javaClass.getResourceAsStream("material/ic_keyboard_arrow_down_black_24dp.png")))
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    private fun createAddImage(): ImageIcon {
        try {
            return ImageIcon(ImageIO.read(this.javaClass.getResourceAsStream("material/ic_add_black_24dp.png")))
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
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
            val menuItem = JMenuItem(it.page.title)
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
            selectedPage = pageData
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

    fun checkRemovePage(page: Page?) {
        if (page != null) {
            val allowClose = page.allowClose
            if (allowClose != null) {
                if (allowClose()) {
                    removePage(page)
                }
            } else {
                removePage(page)
            }
        }
    }

    fun removePage(page: Page?) {
        if (page != null) {
            SwingUtilities.invokeLater {
                val pageToRemove = pages.first { pageData -> pageData.page.component == page.component }
                if (pageToRemove == selectedPage) {
                    if (selectedPage == pages.last() && pages.size > 1) {
                        selectPage(pages[pages.lastIndex-1])
                    } else if (pages.size > 1) {
                        selectPage(pages[pages.indexOf(selectedPage!!) + 1])
                    } else {
                        selectedPage = null
                    }
                }
                pages.remove(pageToRemove)
                body.remove(pageToRemove.page.component)
                tabPanel.remove(pageToRemove.tabComponent)
                tabPanel.validate()
                tabPanel.repaint()
            }
        }
    }

    private fun setupKeyboardShortcuts() {
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { e ->
            when {
                standardKeyboardShortcut(e) -> true
                tabDefinedKeyboardShortcut(e) -> true
                else -> false
            }
        }
    }

    private fun standardKeyboardShortcut(e: KeyEvent): Boolean {
        if (e.id == KeyEvent.KEY_PRESSED && e.isControlDown) {
            return if (e.keyCode == KeyEvent.VK_T) {
                newPage()
                true
            } else if (e.keyCode == KeyEvent.VK_W) {
                checkRemovePage(selectedPage?.page)
                true
            } else if (e.keyCode == KeyEvent.VK_TAB && e.isShiftDown) {
                selectPrevPage()
                true
            } else if (e.keyCode == KeyEvent.VK_TAB) {
                selectNextPage()
                true
            } else {
                false
            }
        } else {
            return false
        }
    }

    private fun tabDefinedKeyboardShortcut(e: KeyEvent): Boolean {
        return if (selectedPage?.page?.keyEventDispatcher != null) {
            selectedPage?.page?.keyEventDispatcher!!.dispatchKeyEvent(e)
        } else {
            false
        }
    }

    private fun selectNextPage() {
        if (pages.isNotEmpty()) {
            SwingUtilities.invokeLater {
                if (selectedPage == null || selectedPage == pages.last()) {
                    selectPage(pages.first())
                } else {
                    selectPage(pages[pages.indexOf(selectedPage!!) + 1])
                }
            }
        }
    }

    private fun selectPrevPage() {
        if (pages.isNotEmpty()) {
            SwingUtilities.invokeLater {
                if (selectedPage == null || selectedPage == pages.first()) {
                    selectPage(pages.last())
                } else {
                    selectPage(pages[pages.indexOf(selectedPage!!) - 1])
                }
            }
        }
    }
}
