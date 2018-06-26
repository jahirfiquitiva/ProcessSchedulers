package co.edu.uptc.aj.os.gui.base

import java.util.Vector

import javax.swing.table.DefaultTableModel

class ProcessTableModel : DefaultTableModel {
    constructor() {}
    
    constructor(rowCount: Int, columnCount: Int) : super(rowCount, columnCount) {}
    
    constructor(columnNames: Vector<*>, rowCount: Int) : super(columnNames, rowCount) {}
    
    constructor(columnNames: Array<Any>, rowCount: Int) : super(columnNames, rowCount) {}
    
    constructor(data: Vector<*>, columnNames: Vector<*>) : super(data, columnNames) {}
    
    constructor(data: Array<Array<Any>>, columnNames: Array<Any>) : super(data, columnNames) {}
    
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
}
