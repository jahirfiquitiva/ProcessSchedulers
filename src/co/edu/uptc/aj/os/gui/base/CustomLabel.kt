package co.edu.uptc.aj.os.gui.base

import java.awt.Font
import javax.swing.JLabel

class CustomLabel(title: String) : JLabel(title) {
    var isBold = false
        set(value) {
            field = value
            updateFont()
        }
    
    override fun setText(text: String?) {
        updateFont()
        super.setText(text)
    }
    
    private fun updateFont() {
        font = Font("Arial", if (isBold) Font.BOLD else Font.PLAIN, 14)
    }
}