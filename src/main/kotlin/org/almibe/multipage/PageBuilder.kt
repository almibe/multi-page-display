/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.KeyEventDispatcher
import javax.swing.ImageIcon
import javax.swing.JComponent

class PageBuilder {
    private var content: JComponent? = null
    private var title: String? = null
    private var icon: ImageIcon? = null
    private var allowClose: (() -> Boolean)? = null
    private var keyEventDispatcher: KeyEventDispatcher? = null

    fun setContent(content: JComponent): PageBuilder {
        this.content = content
        return this
    }

    fun setTitle(title: String): PageBuilder {
        this.title = title
        return this
    }

    fun setIcon(icon: ImageIcon): PageBuilder {
        this.icon = icon
        return this
    }

    fun setAllowClose(allowClose: () -> Boolean): PageBuilder {
        this.allowClose = allowClose
        return this
    }

    fun setKeyEventDispatcher(keyEventDispatcher: KeyEventDispatcher): PageBuilder {
        this.keyEventDispatcher = keyEventDispatcher
        return this
    }

    fun createPage(): Page {
        return Page(content!!, title!!, icon, allowClose, keyEventDispatcher)
    }
}
