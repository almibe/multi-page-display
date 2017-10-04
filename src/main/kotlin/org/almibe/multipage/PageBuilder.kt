package org.almibe.multipage

import javafx.scene.input.KeyCombination
import java.util.concurrent.Callable
import javax.swing.ImageIcon
import javax.swing.JComponent

class PageBuilder {
    private var content: JComponent? = null
    private var title: String? = null
    private var icon: ImageIcon? = null
    private var allowClose: Callable<Boolean>? = null
    private var accelerators: Map<KeyCombination, Runnable>? = null

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

    fun setAllowClose(allowClose: Callable<Boolean>): PageBuilder {
        this.allowClose = allowClose
        return this
    }

    fun setAccelerators(accelerators: Map<KeyCombination, Runnable>): PageBuilder {
        this.accelerators = accelerators
        return this
    }

    fun createPage(): Page {
        return object : Page {
            override fun component(): JComponent {
                return content!!
            }

            override fun title(): String {
                return title!!
            }

            override fun icon(): ImageIcon {
                return icon!!
            }

            override fun allowClose(): Callable<Boolean> {
                return allowClose!!
            }

            override fun accelerators(): Map<KeyCombination, Runnable> {
                return accelerators!!
            }
        }
    }
}
