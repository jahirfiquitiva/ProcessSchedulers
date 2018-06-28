package co.edu.uptc.aj.os.gui.base

import javax.swing.table.DefaultTableModel

class ProcessTableModel(columnNames: Array<Any>, rowCount: Int) :
    DefaultTableModel(columnNames, rowCount) {
    
    fun removeColumn(column: Int): Boolean {
        return try {
            while (rowCount > 0) {
                removeRow(0)
            }
            columnIdentifiers.removeAt(column)
            fireTableStructureChanged()
            true
        } catch (ignored: Exception) {
            false
        }
        
    }
    
    override fun isCellEditable(row: Int, column: Int): Boolean = false
}
