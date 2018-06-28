package co.edu.uptc.aj.os.gui

import co.edu.uptc.aj.os.gui.base.CustomButton
import co.edu.uptc.aj.os.gui.base.CustomLabel
import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.JComboBox
import javax.swing.JPanel

class LabelPanel(private val m: MainWindow) : JPanel() {
    
    private val jComboBox11: JComboBox<String>
    private val jComboBox12: JComboBox<String>
    private val jComboBox13: JComboBox<String>
    
    private var first = 1
    private var second = 1
    private var third = 1
    private var xCoordinate = 30
    private var yCoordinate = 30
    
    private val title: CustomLabel
    private val timer: CustomLabel
    private val start: CustomButton?
    private var buttonEnabled = true
    
    init {
        layout = null
        setSize(SIZE, SIZE)
        
        val credits = CustomLabel(
            "<html>" +
                "<b>Presentado por:</b><br>Adriana Mireya Perez - 201521800<br>" +
                "Jahir Fiquitiva Ricaurte - 201521721</html>")
        credits.setBounds(385, 20, 300, 100)
        add(credits)
        
        val schedule = arrayOf("SJF", "FIFO", "Round Robin")
        
        val queue1 = CustomLabel("Queue 1 (Quantum 2):")
        queue1.isBold = true
        queue1.foreground = Color.decode(MainWindow.FIRST_QUEUE_COLOR)
        queue1.setBounds(xCoordinate, yCoordinate, 600, 20)
        add(queue1)
        
        xCoordinate += 160
        jComboBox11 = JComboBox(schedule)
        jComboBox11.addActionListener { event -> first = aEvent(event) }
        jComboBox11.setBounds(xCoordinate, yCoordinate, 150, 20)
        
        add(jComboBox11)
        
        xCoordinate -= 160
        yCoordinate += 30
        
        val queue2 = CustomLabel("Queue 2 (Quantum 4):")
        queue2.isBold = true
        queue2.foreground = Color.decode(MainWindow.SECOND_QUEUE_COLOR)
        queue2.setBounds(xCoordinate, yCoordinate, 600, 20)
        
        add(queue2)
        
        xCoordinate += 160
        jComboBox12 = JComboBox(schedule)
        jComboBox12.addActionListener { event -> second = aEvent(event) }
        jComboBox12.setBounds(xCoordinate, yCoordinate, 150, 20)
        
        add(jComboBox12)
        
        xCoordinate -= 160
        yCoordinate += 30
        
        val queue3 = CustomLabel("Queue 3 (Quantum 8):")
        queue3.isBold = true
        queue3.foreground = Color.decode(MainWindow.THIRD_QUEUE_COLOR)
        queue3.setBounds(xCoordinate, yCoordinate, 600, 20)
        
        add(queue3)
        
        xCoordinate += 160
        jComboBox13 = JComboBox(schedule)
        jComboBox13.addActionListener { event -> third = aEvent(event) }
        jComboBox13.setBounds(xCoordinate, yCoordinate, 150, 20)
        
        add(jComboBox13)
        
        xCoordinate -= 80
        yCoordinate += 40
        
        timer = CustomLabel("Tiempo Actual: 00:00:00")
        timer.setBounds(30, yCoordinate, 200, 35)
        add(timer)
        
        start = CustomButton("Start")
        start.setBounds(xCoordinate + 150, yCoordinate, 70, 35)
        start.background = Color.decode("#4285f4")
        start.foreground = Color.WHITE
        start.addActionListener { onButtonPressed() }
        add(start)
        
        xCoordinate -= 80
        yCoordinate += 50
        title = CustomLabel("Procesos (0):")
        title.setBounds(xCoordinate, yCoordinate, 150, 20)
        add(title)
    }
    
    fun enableButton() {
        if (start == null) return
        this.buttonEnabled = true
        start.background = Color.decode("#4285f4")
        start.text = "Start"
    }
    
    fun updateProcessCount(count: Int) {
        title.text = "Procesos (" + count.toString() + "):"
    }
    
    fun updateTime(newTime: String) {
        timer.text = "Timepo Actual: $newTime"
    }
    
    private fun aEvent(event: ActionEvent): Int {
        return try {
            ((event.source as? JComboBox<*>)?.selectedIndex ?: -2) + 1
        } catch (e: Exception) {
            -1
        }
    }
    
    private fun onButtonPressed() {
        start ?: return
        if (!m.isFinished && !m.isExecuting) {
            start.background = Color.decode("#778ca3")
            return
        }
        if (!buttonEnabled) return
        start.background = Color.decode("#4285f4")
        if (m.isExecuting) {
            m.stop()
            buttonEnabled = false
            start.background = Color.decode("#778ca3")
            start.text = "Start"
        } else {
            updateTime("00:00:00")
            updateProcessCount(0)
            if (m.init(first, second, third))
                start.text = "Stop"
        }
    }
    
    companion object {
        private val SIZE = 50
    }
}