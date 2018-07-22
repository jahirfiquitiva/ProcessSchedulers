package co.edu.uptc.aj.os.gui

import co.edu.uptc.aj.os.gui.base.CustomLabel
import co.edu.uptc.aj.os.logic.FiFo
import co.edu.uptc.aj.os.logic.Process
import co.edu.uptc.aj.os.logic.ProcessManager
import co.edu.uptc.aj.os.logic.QueueListener
import co.edu.uptc.aj.os.logic.RoundRobin
import co.edu.uptc.aj.os.logic.SJF
import co.edu.uptc.aj.os.logic.Scheduler
import java.awt.Color
import java.awt.Toolkit
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.WindowConstants

class MainWindow(private var debug: Boolean = false) :
    JFrame(), QueueListener {
    
    private var man: ProcessManager? = null
    
    private val firstQueuePanel = QueuePanel()
    private val secondQueuePanel = QueuePanel()
    private val thirdQueuePanel = QueuePanel()
    
    private val panelChooser = LabelPanel(this)
    private val paneTable = TablePanel()
    
    internal val isExecuting: Boolean
        get() = man?.executing() ?: false
    
    internal val isFinished: Boolean
        get() = man?.isFinished() ?: true
    
    init {
        layout = null
        setSize(670, 600)
        title = "Proyecto Final - Sistemas Operativos"
        setLocationRelativeTo(null)
        isResizable = false
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        
        val dim = Toolkit.getDefaultToolkit().screenSize
        this.setLocation(
            dim.width / 2 - this.size.width / 2,
            dim.height / 2 - this.size.height / 2)
        
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                val opt = JOptionPane.showConfirmDialog(
                    null, "Seguro que desea cerrar?", "Cerrar", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE)
                if (opt == JOptionPane.YES_OPTION) {
                    man?.stop(true)
                    dispose()
                    super.windowClosing(e)
                }
            }
        })
        
        panelChooser.setBounds(0, 0, 800, 210)
        add(panelChooser)
        
        val queueX = 30
        
        firstQueuePanel.setQueueColor(Color.decode(FIRST_QUEUE_COLOR))
        firstQueuePanel.setBounds(queueX, 220, 600, 40)
        add(firstQueuePanel)
        
        secondQueuePanel.setQueueColor(Color.decode(SECOND_QUEUE_COLOR))
        secondQueuePanel.setBounds(queueX, 265, 620, 40)
        add(secondQueuePanel)
        
        thirdQueuePanel.setQueueColor(Color.decode(THIRD_QUEUE_COLOR))
        thirdQueuePanel.setBounds(queueX, 310, 620, 40)
        add(thirdQueuePanel)
        
        val tableTitle = CustomLabel("Tabla de Procesos Terminados")
        tableTitle.setBounds(30, 365, 300, 30)
        add(tableTitle)
        
        paneTable.setBounds(30, 400, 600, 150)
        add(paneTable)
    }
    
    internal fun init(f: Int, s: Int, t: Int): Boolean {
        if (f == s || s == t || f == t) {
            JOptionPane
                .showMessageDialog(
                    null, "Los algoritmos deben ser únicos para cada cola.", "Error",
                    JOptionPane.ERROR_MESSAGE)
            return false
        }
        
        val first = options(f)
        val second = options(s)
        val third = options(t)
        
        if (first == null || second == null || third == null) {
            JOptionPane
                .showMessageDialog(
                    null, "Al menos uno de los algoritmos es nulo.", "Error",
                    JOptionPane.ERROR_MESSAGE)
            return false
        }
        
        man = ProcessManager(options(f)!!, options(s)!!, options(t)!!, debug)
        man?.queueListener = this
        man?.start()
        paneTable.cleanTable()
        return true
    }
    
    fun stop() {
        man?.stop()
    }
    
    private fun options(o: Int): Scheduler? {
        return when (o) {
            1 -> SJF()
            2 -> FiFo()
            3 -> RoundRobin()
            else -> null
        }
    }
    
    fun execute() {
        isVisible = true
    }
    
    override fun onAddedToFirstQueue(process: Process, index: Int) {
        panelChooser.updateProcessCount(man?.count ?: 0)
        firstQueuePanel.addProcess(index, process)
    }
    
    override fun onAddedToSecondQueue(process: Process, index: Int) {
        secondQueuePanel.addProcess(index, process)
    }
    
    override fun onAddedToThirdQueue(process: Process, index: Int) {
        thirdQueuePanel.addProcess(index, process)
    }
    
    override fun onRemovedFromFirstQueue(process: Process) {
        firstQueuePanel.removeProcess(process)
    }
    
    override fun onRemovedFromSecondQueue(process: Process) {
        secondQueuePanel.removeProcess(process)
    }
    
    override fun onRemovedFromThirdQueue(process: Process) {
        thirdQueuePanel.removeProcess(process)
    }
    
    override fun onProcessFinished(process: Process) {
        firstQueuePanel.removeProcess(process)
        secondQueuePanel.removeProcess(process)
        thirdQueuePanel.removeProcess(process)
        paneTable.addRow(process)
        if (isFinished) {
            panelChooser.enableButton()
            firstQueuePanel.clearTable()
            secondQueuePanel.clearTable()
            thirdQueuePanel.clearTable()
        }
    }
    
    override fun updateTime() {
        panelChooser.updateTime(man?.getTime() ?: "00:00:00")
    }
    
    companion object {
        internal const val FIRST_QUEUE_COLOR = "#20BF6B"
        internal const val SECOND_QUEUE_COLOR = "#EB3B5A"
        internal const val THIRD_QUEUE_COLOR = "#4B7BEC"
    }
}
