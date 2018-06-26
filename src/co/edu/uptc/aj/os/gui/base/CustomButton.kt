package co.edu.uptc.aj.os.gui.base

import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.border.Border

class CustomButton(title: String) : JButton(title) {
    
    init {
        isOpaque = true
        border = BorderFactory.createLineBorder(background, 2, true)
        font = Font("Arial", Font.BOLD, 14)
    }
    
    override fun setText(text: String?) {
        super.setText(text?.toUpperCase())
    }
    
    override fun setBackground(bg: Color?) {
        super.setBackground(bg)
        border = null
    }
    
    override fun setBorder(border: Border?) {
        super.setBorder(BorderFactory.createLineBorder(background, 2, true))
    }
}
