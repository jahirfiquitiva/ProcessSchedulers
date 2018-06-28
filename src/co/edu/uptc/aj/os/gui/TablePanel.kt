package co.edu.uptc.aj.os.gui

import co.edu.uptc.aj.os.logic.Process
import java.awt.BorderLayout
import java.util.ArrayList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.DefaultTableModel

class TablePanel : JPanel() {
    private var table: JTable? = null
    private var dtm: DefaultTableModel? = null
    
    init {
        begin()
        layout = BorderLayout()
        addComponents()
    }
    
    private fun addComponents() {
        add(JScrollPane(table), BorderLayout.CENTER)
    }
    
    private fun begin() {
        val titles = arrayOf(
            "Proceso", "Tamaño", "Prioridad", "T LLegada", "T Espera", "T Inicio", "T Retorno")
        dtm = DefaultTableModel(titles, 0)
        table = JTable(dtm)
        table?.fillsViewportHeight = true
        table?.isEnabled = false
    }
    
    fun addRow(pro: Process) {
        val row =
            arrayOf(pro.name, pro.length, pro.priority, pro.arrival, pro.wait, pro.start, pro.end)
        dtm?.addRow(row)
    }
    
    fun cleanTable() {
        var i = 0
        while (i < (table?.rowCount ?: 0)) {
            dtm?.removeRow(i)
            i -= 1
            i++
        }
    }
    
    fun repaintTable(processes: ArrayList<Process>) {
        cleanTable()
        for (pro in processes) {
            addRow(pro)
        }
    }
}