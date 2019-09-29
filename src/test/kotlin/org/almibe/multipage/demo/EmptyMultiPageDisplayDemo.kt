/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage.demo

import org.almibe.multipage.MultiPageDisplay
import org.almibe.multipage.PageBuilder
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val frame = JFrame("Demo")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        frame.contentPane.add(create().component)
        frame.pack()
        frame.isVisible = true
        frame.setSize(1000, 400)
    }
}

private fun create(): MultiPageDisplay {
    return MultiPageDisplay {
        PageBuilder()
                .setTitle("Hello Demo")
                .setIcon(createImageView())
                .setContent(JLabel("Hello Demo Content")).createPage()
    }
}

private fun createImageView(): ImageIcon {
    return ImageIcon(EmptyMultiPageDisplayDemo::class.java.getResource("page_white_text.png"))
}

object EmptyMultiPageDisplayDemo