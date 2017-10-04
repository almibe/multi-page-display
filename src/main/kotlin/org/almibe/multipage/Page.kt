/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage

import javafx.scene.input.KeyCombination
import java.util.concurrent.Callable
import javax.swing.ImageIcon
import javax.swing.JComponent

interface Page {
    fun component(): JComponent
    fun title(): String
    fun icon(): ImageIcon
    fun allowClose(): Callable<Boolean>
    fun accelerators(): Map<KeyCombination, Runnable>
}
