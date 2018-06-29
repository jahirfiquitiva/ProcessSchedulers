package co.edu.uptc.aj.os.gui.base

import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.table.TableCellRenderer

class ProcessRenderer : JLabel(), TableCellRenderer {
    
    var borderColor: Color = Color.decode("#4285f4")
        set(value) {
            field = value
            border = BorderFactory.createLineBorder(value, 3)
        }
    
    companion object {
        const val WIDTH: Int = 40
        val PERFECT_SIZE: Dimension = Dimension(WIDTH, WIDTH - 10)
    }
    
    init {
        initSize()
        font = Font("Arial", Font.BOLD, 14)
        foreground = Color.BLACK
        border = BorderFactory.createLineBorder(borderColor, 3)
    }
    
    private fun initSize() {
        preferredSize = PERFECT_SIZE
        maximumSize = PERFECT_SIZE
        minimumSize = PERFECT_SIZE
    }
    
    override fun getTableCellRendererComponent(
        table: JTable, value: Any,
        isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
                                              ): Component {
        text = value.toString().toUpperCase()
        initSize()
        return this
    }
    
    override fun setText(text: String?) {
        super.setText(text?.toUpperCase())
        initSize()
        horizontalAlignment = SwingConstants.CENTER
        verticalAlignment = SwingConstants.CENTER
    }
    
    override fun resize(d: Dimension?) {
        initSize()
        return
    }
    
    override fun resize(width: Int, height: Int) {
        initSize()
        return
    }
    
    override fun repaint() {
        super.repaint()
        initSize()
    }
    
    override fun revalidate() {
        super.revalidate()
        initSize()
    }
    
    override fun invalidate() {
        super.invalidate()
        initSize()
    }
}