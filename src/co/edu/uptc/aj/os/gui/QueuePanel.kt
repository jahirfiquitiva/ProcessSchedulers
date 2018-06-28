package co.edu.uptc.aj.os.gui

import co.edu.uptc.aj.os.gui.base.ProcessRenderer
import co.edu.uptc.aj.os.gui.base.ProcessTableModel
import co.edu.uptc.aj.os.logic.Process
import java.awt.Color
import java.util.ArrayList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable

class QueuePanel : JPanel() {
    
    private val tableModel: ProcessTableModel
    private val table: JTable
    private val pane: JScrollPane
    private val processes = ArrayList<Process>()
    
    init {
        layout = null
        setSize(ProcessRenderer.WIDTH, ProcessRenderer.WIDTH)
        isOpaque = false
        background = Color.decode("#00000000")
        
        tableModel = ProcessTableModel(arrayOf(), 0)
        table = JTable(tableModel)
        
        table.tableHeader.defaultRenderer = ProcessRenderer()
        table.isOpaque = false
        table.background = Color.decode("#00000000")
        
        pane = JScrollPane(
            table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
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
            tableModel.addColumn(name)
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
        try {
            processes.removeAt(index)
        } catch (e: Exception) {
        }
        
        tableModel.removeColumn(index)
        updatePane()
    }
    
    fun clearTable() {
        var i = 0
        while (i < table.rowCount) {
            tableModel.removeRow(i)
            i -= 1
            i++
        }
        tableModel.columnCount = 0
        tableModel.removeColumn(0)
        updatePane(true)
    }
    
    private fun updatePane(cleared: Boolean = false) {
        pane.setBounds(
            0, 0, ProcessRenderer.WIDTH * tableModel.columnCount + (if (cleared) 0 else 1),
            ProcessRenderer.WIDTH - 5)
        revalidate()
        repaint()
    }
}