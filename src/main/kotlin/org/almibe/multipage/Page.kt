/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import java.awt.KeyEventDispatcher
import javax.swing.Icon
import javax.swing.JComponent

class Page (
    val component: JComponent,
    val title: String,
    val icon: Icon?,
    val allowClose: (() -> Boolean)?,
    val keyEventDispatcher: KeyEventDispatcher?
)
