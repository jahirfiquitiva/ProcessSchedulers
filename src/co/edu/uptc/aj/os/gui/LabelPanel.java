package co.edu.uptc.aj.os.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import co.edu.uptc.aj.os.gui.base.CustomButton;
import co.edu.uptc.aj.os.gui.base.CustomLabel;

public class LabelPanel extends JPanel {

    private static final int SIZE = 50;

    private JComboBox<String> jComboBox11;
    private JComboBox<String> jComboBox12;
    private JComboBox<String> jComboBox13;

    public int first = 1, second = 1, third = 1;
    private int x = 30;
    private int y = 30;
    private MainWindow m;

    private CustomLabel title;
    private CustomLabel timer;
    private CustomButton start;
    private boolean buttonEnabled = true;

    @SuppressWarnings("unchecked")
    public int aEvent(ActionEvent event) {
        try {
            JComboBox<String> combo = (JComboBox<String>) event.getSource();
            return combo.getSelectedIndex() + 1;
        } catch (Exception e) {
            return -1;
        }
    }

    public LabelPanel(MainWindow main) {
        setLayout(null);
        setSize(SIZE, SIZE);
        m = main;

        CustomLabel credits = new CustomLabel(
            "<html>" +
                "<b>Presentado por:</b><br>Adriana Mireya Perez - 201521800<br>" +
                "Jahir Fiquitiva Ricaurte - 201521721</html>");
        credits.setBounds(385, 20, 300, 100);
        add(credits);

        String[] schedule = new String[]{"SJF", "FIFO", "Round Robin"};

        CustomLabel queue1 = new CustomLabel("Queue 1 (Quantum 2):");
        queue1.setBold(true);
        queue1.setForeground(Color.decode(MainWindow.FIRST_QUEUE_COLOR));
        queue1.setBounds(x, y, 600, 20);
        add(queue1);

        x += 160;
        jComboBox11 = new JComboBox<>(schedule);
        jComboBox11.addActionListener(event -> first = aEvent(event));
        jComboBox11.setBounds(x, y, 150, 20);

        add(jComboBox11);

        x -= 160;
        y += 30;

        CustomLabel queue2 = new CustomLabel("Queue 2 (Quantum 4):");
        queue2.setBold(true);
        queue2.setForeground(Color.decode(MainWindow.SECOND_QUEUE_COLOR));
        queue2.setBounds(x, y, 600, 20);

        add(queue2);

        x += 160;
        jComboBox12 = new JComboBox<>(schedule);
        jComboBox12.addActionListener(event -> second = aEvent(event));
        jComboBox12.setBounds(x, y, 150, 20);

        add(jComboBox12);

        x -= 160;
        y += 30;

        CustomLabel queue3 = new CustomLabel("Queue 3 (Quantum 8):");
        queue3.setBold(true);
        queue3.setForeground(Color.decode(MainWindow.THIRD_QUEUE_COLOR));
        queue3.setBounds(x, y, 600, 20);

        add(queue3);

        x += 160;
        jComboBox13 = new JComboBox<>(schedule);
        jComboBox13.addActionListener(event -> third = aEvent(event));
        jComboBox13.setBounds(x, y, 150, 20);

        add(jComboBox13);

        x -= 80;
        y += 40;

        timer = new CustomLabel("Tiempo Actual: 00:00:00");
        timer.setBounds(30, y, 200, 35);
        add(timer);

        start = new CustomButton("Start");
        start.setBounds(x + 150, y, 70, 35);
        start.setBackground(Color.decode("#4285f4"));
        start.setForeground(Color.WHITE);
        start.addActionListener(event -> {
            if (!m.isFinished() && !m.isExecuting()) {
                start.setBackground(Color.decode("#778ca3"));
                return;
            }
            if (!buttonEnabled) return;
            start.setBackground(Color.decode("#4285f4"));
            if (m.isExecuting()) {
                m.stop();
                buttonEnabled = false;
                start.setBackground(Color.decode("#778ca3"));
                start.setText("Start");
            } else {
                if (m.init(first, second, third))
                    start.setText("Stop");
            }
        });
        add(start);

        x -= 80;
        y += 50;
        title = new CustomLabel("Procesos (0):");
        title.setBounds(x, y, 150, 20);
        add(title);
    }

    public void enableButton() {
        if (start == null) return;
        this.buttonEnabled = true;
        start.setBackground(Color.decode("#4285f4"));
        start.setText("Start");
        updateTime("00:00:00");
        updateProcessCount(0);
    }

    public void updateProcessCount(int count) {
        title.setText("Procesos (" + String.valueOf(count) + "):");
    }

    public void updateTime(String newTime) {
        timer.setText("Timepo Actual: " + newTime);
    }
}