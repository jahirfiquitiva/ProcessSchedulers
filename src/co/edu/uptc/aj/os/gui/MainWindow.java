package co.edu.uptc.aj.os.gui;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import co.edu.uptc.aj.os.gui.base.CustomLabel;
import co.edu.uptc.aj.os.logic.FiFo;
import co.edu.uptc.aj.os.logic.Process;
import co.edu.uptc.aj.os.logic.ProcessManager;
import co.edu.uptc.aj.os.logic.QueueListener;
import co.edu.uptc.aj.os.logic.RoundRobin;
import co.edu.uptc.aj.os.logic.SJF;
import co.edu.uptc.aj.os.logic.Scheduler;

public class MainWindow extends JFrame implements QueueListener {

    static final String FIRST_QUEUE_COLOR = "#20BF6B";
    static final String SECOND_QUEUE_COLOR = "#EB3B5A";
    static final String THIRD_QUEUE_COLOR = "#4B7BEC";

    private ProcessManager man;

    private QueuePanel firstQueuePanel = new QueuePanel();
    private QueuePanel secondQueuePanel = new QueuePanel();
    private QueuePanel thirdQueuePanel = new QueuePanel();

    private LabelPanel panelChooser = new LabelPanel(this);
    private TablePanel paneTable = new TablePanel();

    private boolean debug = false;

    public MainWindow() {
        this(false);
    }

    public MainWindow(boolean debug) {
        this.debug = debug;

        setLayout(null);
        setSize(670, 600);
        setTitle("Proyecto Final - Sistemas Operativos");
        setLocationRelativeTo(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                         dim.height / 2 - this.getSize().height / 2);

        panelChooser.setBounds(0, 0, 800, 210);
        add(panelChooser);

        int queueX = 30;

        firstQueuePanel.setQueueColor(Color.decode(FIRST_QUEUE_COLOR));
        firstQueuePanel.setBounds(queueX, 220, 1000, 40);
        add(firstQueuePanel);

        secondQueuePanel.setQueueColor(Color.decode(SECOND_QUEUE_COLOR));
        secondQueuePanel.setBounds(queueX, 265, 1000, 40);
        add(secondQueuePanel);

        thirdQueuePanel.setQueueColor(Color.decode(THIRD_QUEUE_COLOR));
        thirdQueuePanel.setBounds(queueX, 310, 1000, 40);
        add(thirdQueuePanel);

        CustomLabel tableTitle = new CustomLabel("Tabla de Procesos Terminados");
        tableTitle.setBounds(30, 365, 300, 30);
        add(tableTitle);

        paneTable.setBounds(30, 400, 600, 150);
        add(paneTable);
    }

    @SuppressWarnings("ConstantConditions")
    boolean init(int f, int s, int t) {
        if (f == s || s == t || f == t) {
            JOptionPane
                .showMessageDialog(null, "Los algoritmos deben ser únicos para cada cola.", "Error",
                                   JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Scheduler first = options(f);
        Scheduler second = options(s);
        Scheduler third = options(t);

        if (first == null || second == null || third == null) {
            JOptionPane
                .showMessageDialog(null, "Al menos uno de los algoritmos es nulo.", "Error",
                                   JOptionPane.ERROR_MESSAGE);
            return false;
        }

        man = new ProcessManager(options(f), options(s), options(t), debug);
        man.setQueueListener(this);
        man.start();
        paneTable.cleanTable();
        return true;
    }

    boolean isExecuting() {
        if (man == null) return false;
        return man.executing();
    }

    boolean isFinished() {
        if (man == null) return true;
        return man.isFinished();
    }

    public void stop() {
        man.stop();
    }

    private Scheduler options(int o) {
        switch (o) {
            case 1:
                return new SJF();
            case 2:
                return new FiFo();
            case 3:
                return new RoundRobin();
        }
        return null;
    }

    public void execute() {
        setVisible(true);
    }

    @Override
    public void onAddedToFirstQueue(@NotNull Process process, int index) {
        panelChooser.updateProcessCount(man.getCount());
        firstQueuePanel.addProcess(index, process);
    }

    @Override
    public void onAddedToSecondQueue(@NotNull Process process, int index) {
        secondQueuePanel.addProcess(index, process);
    }

    @Override
    public void onAddedToThirdQueue(@NotNull Process process, int index) {
        thirdQueuePanel.addProcess(index, process);
    }

    @Override
    public void onRemovedFromFirstQueue(@NotNull Process process) {
        firstQueuePanel.removeProcess(process);
    }

    @Override
    public void onRemovedFromSecondQueue(@NotNull Process process) {
        secondQueuePanel.removeProcess(process);
    }

    @Override
    public void onRemovedFromThirdQueue(@NotNull Process process) {
        thirdQueuePanel.removeProcess(process);
    }

    @Override
    public void onProcessFinished(@NotNull Process process) {
        firstQueuePanel.removeProcess(process);
        secondQueuePanel.removeProcess(process);
        thirdQueuePanel.removeProcess(process);
        paneTable.addRow(process);
        if (isFinished()) {
            panelChooser.enableButton();
            firstQueuePanel.clearTable();
            secondQueuePanel.clearTable();
            thirdQueuePanel.clearTable();
        }
    }

    @Override
    public void updateTime() {
        panelChooser.updateTime(man.getTime());
    }
}
