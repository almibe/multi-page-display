package org.almibe.multipage.demo

import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.TextArea
import org.almibe.multipage.MultiPageDisplay
import org.almibe.multipage.Page
import org.almibe.multipage.PageBuilder
import javax.swing.*

object EmptyMultiPageDisplayDemo {

    @JvmStatic
    fun main(args: Array<String>) {
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
        val multiPageDisplay = MultiPageDisplay {
            PageBuilder()
                    .setTitle("Hello Demo")
                    .setIcon(createImageView())
                    .setContent(JLabel("Hello Demo Content")).createPage()
        }

        //multiPageDisplay.newPage()

        return multiPageDisplay
    }

    private var x = 0
    private fun createAnonPage(): Page {
        return PageBuilder()
            .setTitle("Anon " + ++x)
            .setIcon(createImageView())
            .setContent(JLabel("Hello Demo Content " + x)).createPage()
    }

    private fun createImageView(): ImageIcon {
        return ImageIcon(EmptyMultiPageDisplayDemo::class.java.getResource("page_white_text.png"))
    }

    private fun createJavaFXContent(): JPanel {
        val panel = JPanel()
        val jfxPanel = JFXPanel()

        panel.add(jfxPanel)
        val scene = createScene()
        jfxPanel.scene = scene
        return panel
    }

    private fun createScene(): Scene {
        val root = Group()
        val scene = Scene(root)
        val text = TextArea("Sample text")

        root.children.add(text)
        return scene
    }
}