package co.edu.uptc.aj.os.gui

import co.edu.uptc.aj.os.gui.base.ProcessRenderer
import co.edu.uptc.aj.os.gui.base.ProcessTableModel
import co.edu.uptc.aj.os.gui.base.QueueTable
import co.edu.uptc.aj.os.ignored
import co.edu.uptc.aj.os.logic.Process
import java.awt.Color
import java.awt.Dimension
import java.util.ArrayList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import kotlin.math.min

class QueuePanel : JPanel() {
    
    private val tableModel: ProcessTableModel
    private val table: QueueTable
    private val pane: JScrollPane
    private val processes = ArrayList<Process>()
    
    init {
        layout = null
        setSize(ProcessRenderer.WIDTH, ProcessRenderer.WIDTH)
        isOpaque = false
        background = Color.decode("#00000000")
        
        tableModel = ProcessTableModel(arrayOf(), 0)
        table = QueueTable(tableModel)
        
        table.tableHeader.defaultRenderer = ProcessRenderer()
        
        table.isOpaque = false
        table.background = Color.decode("#00000000")
        table.preferredScrollableViewportSize =
            Dimension(ProcessRenderer.WIDTH * 10, ProcessRenderer.WIDTH)
        table.autoResizeMode = JTable.AUTO_RESIZE_OFF
        table.rowSelectionAllowed = false
        table.columnSelectionAllowed = false
        table.tableHeader.reorderingAllowed = false
        table.tableHeader.resizingAllowed = false
        
        pane = JScrollPane(
            table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS)
        pane.isOpaque = false
        pane.background = Color.decode("#00000000")
        updatePane()
        add(pane)
    }
    
    fun setQueueColor(color: Color) {
        val renderer = ProcessRenderer()
        renderer.borderColor = color
        table.tableHeader.defaultRenderer = renderer
    }
    
    fun addProcess(index: Int, process: Process) {
        if (index < 0) return
        processes.add(index, process)
        updateProcesses()
    }
    
    private fun updateProcesses() {
        clearTable()
        for ((name) in processes) {
            ignored { tableModel.addColumn(name) }
            updatePane()
        }
    }
    
    fun removeProcess(process: Process) {
        val cols = table.columnModel.columns
        var index = -1
        var count = 0
        while (cols.hasMoreElements()) {
            val colTitle = cols.nextElement().headerValue.toString()
            if (process.name.equals(colTitle, ignoreCase = true)) {
                index = count
                break
            }
            count += 1
        }
        if (index < 0) return
        ignored { processes.removeAt(index) }
        ignored { tableModel.removeColumn(index) }
        updatePane()
    }
    
    fun clearTable() {
        for (i in 0 until table.rowCount) {
            ignored { tableModel.removeRow(0) }
        }
        tableModel.columnCount = 0
        ignored { tableModel.removeColumn(0) }
        updatePane(true)
    }
    
    private fun updatePane(cleared: Boolean = false) {
        val maxWidth = min(
            ProcessRenderer.WIDTH * tableModel.columnCount + (if (cleared) 0 else 1),
            width)
        pane.setBounds(0, 0, maxWidth, ProcessRenderer.WIDTH - 5)
        table.preferredScrollableViewportSize =
            Dimension(ProcessRenderer.WIDTH * 10, ProcessRenderer.WIDTH)
        table.autoResizeMode = JTable.AUTO_RESIZE_OFF
        ignored { revalidate() }
        ignored { repaint() }
    }
}