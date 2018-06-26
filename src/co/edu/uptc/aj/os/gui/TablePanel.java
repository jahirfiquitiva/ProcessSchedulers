package co.edu.uptc.aj.os.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.aj.os.logic.Process;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel dtm;

    public TablePanel() {
        begin();
        setLayout(new BorderLayout());
        addComponents();
    }

    private void addComponents() {
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void begin() {
        String[] titles =
            {"Proceso", "Tamaño", "Prioridad", "T LLegada", "T Espera", "T Inicio", "T Retorno"};
        dtm = new DefaultTableModel(titles, 0);
        table = new JTable(dtm);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
    }

    public void addRow(Process pro) {
        Object[] row = {pro.getName(), pro.getLength(), pro.getPriority(), pro.getArrival(),
                        pro.getWait(), pro.getStart(), pro.getEnd()};
        dtm.addRow(row);
    }

    public void cleanTable() {
        for (int i = 0; i < table.getRowCount(); i++) {
            dtm.removeRow(i);
            i -= 1;
        }
    }

    public void repaintTable(ArrayList<Process> processes) {
        cleanTable();
        for (Process pro : processes) {
            addRow(pro);
        }
    }
}