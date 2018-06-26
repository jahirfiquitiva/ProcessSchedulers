package co.edu.uptc.aj.os.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import co.edu.uptc.aj.os.gui.base.ProcessRenderer;
import co.edu.uptc.aj.os.gui.base.ProcessTableModel;
import co.edu.uptc.aj.os.logic.Process;

@SuppressWarnings("SuspiciousNameCombination")
public class QueuePanel extends JPanel {

    private ProcessTableModel tableModel;
    private JTable table;
    private JScrollPane pane;

    private ArrayList<Process> processes = new ArrayList<>();

    public QueuePanel() {
        setLayout(null);
        setSize(ProcessRenderer.WIDTH, ProcessRenderer.WIDTH);
        setOpaque(false);
        setBackground(Color.decode("#00000000"));

        tableModel = new ProcessTableModel(new Object[]{}, 0);
        table = new JTable(tableModel);

        table.getTableHeader().setDefaultRenderer(new ProcessRenderer());
        table.setOpaque(false);
        table.setBackground(Color.decode("#00000000"));

        pane = new JScrollPane(table);
        pane.setOpaque(false);
        pane.setBackground(Color.decode("#00000000"));
        updatePane();
        add(pane);
    }

    public void setQueueColor(Color color) {
        ProcessRenderer renderer = new ProcessRenderer();
        renderer.setBorderColor(color);
        table.getTableHeader().setDefaultRenderer(renderer);
    }

    public void addProcess(int index, Process process) {
        if (index < 0) return;
        processes.add(index, process);
        updateProcesses();
    }

    private void updateProcesses() {
        clearTable();
        for (Process pro : processes) {
            tableModel.addColumn(pro.getName());
            updatePane();
        }
    }

    public void removeProcess(Process process) {
        Enumeration<TableColumn> cols = table.getColumnModel().getColumns();
        int index = -1;
        int count = 0;
        while (cols.hasMoreElements()) {
            String colTitle = cols.nextElement().getHeaderValue().toString();
            if (process.getName().equalsIgnoreCase(colTitle)) {
                index = count;
                break;
            }
            count += 1;
        }
        if (index < 0) return;
        try {
            processes.remove(index);
        } catch (Exception e) {
        }
        tableModel.removeColumn(index);
        updatePane();
    }

    public void clearTable() {
        for (int i = 0; i < table.getRowCount(); i++) {
            tableModel.removeRow(i);
            i -= 1;
        }
        tableModel.setColumnCount(0);
        tableModel.removeColumn(0);
        updatePane();
    }

    private void updatePane() {
        pane.setBounds(0, 0, (ProcessRenderer.WIDTH * tableModel.getColumnCount()) + 1,
                       ProcessRenderer.WIDTH - 5);
        revalidate();
        repaint();
    }
}