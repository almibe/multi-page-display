/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.KeyEventDispatcher
import javax.swing.Icon
import javax.swing.JComponent
import kotlin.properties.Delegates

class Page(
        val component: JComponent,
        initialTitle: String,
        initialIcon: Icon?,
        val allowClose: (() -> Boolean)?,
        val keyEventDispatcher: KeyEventDispatcher?) {
    var title: String by Delegates.observable(initialTitle) {
        _, _, _ -> this.multiPageDisplay?.updatePage(this)
    }
    var icon: Icon? by Delegates.observable(initialIcon) {
        _, _, _ -> this.multiPageDisplay?.updatePage(this)
    }
    private var multiPageDisplay: MultiPageDisplay? = null

    fun setMultiPageDisplay(multiPageDisplay: MultiPageDisplay) {
        this.multiPageDisplay = multiPageDisplay
    }
}
