package co.edu.uptc.aj.os.gui.base

import javax.swing.JTable
import javax.swing.table.TableModel

class QueueTable(dm: TableModel) : JTable(dm) {
    
    init {
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF)
    }
    
    override fun doLayout() {
        for (index in 0 until columnCount) {
            val column = getColumnModel().getColumn(index)
            column.resizable = false
            column.preferredWidth = ProcessRenderer.WIDTH
        }
        super.doLayout()
    }
    
    override fun setAutoResizeMode(mode: Int) {
        super.setAutoResizeMode(JTable.AUTO_RESIZE_OFF)
    }
    
    override fun getScrollableTracksViewportWidth(): Boolean = true
}