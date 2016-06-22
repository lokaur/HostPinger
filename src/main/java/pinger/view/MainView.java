package pinger.view;

import pinger.controller.PingerController;
import pinger.model.Host;
import pinger.model.PaintTableModel;
import pinger.model.PingerCellRenderer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MainView extends JFrame {
    private JTable hostsTable;

    private JButton buttonStart;

    private JButton buttonAddHost;

    private JPanel rootPanel;

    private PingerController controller;

    private PaintTableModel tableModel;

    public MainView(PingerController controller) {
        super("Host pinger");
        this.controller = controller;
        setContentPane(rootPanel);
        createUIComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    public void reload() throws Exception {
        tableModel = new PaintTableModel();
        hostsTable.setModel(tableModel);
        controller.getHosts().forEach(tableModel::addRow);

        ((AbstractTableModel) hostsTable.getModel()).fireTableDataChanged();
    }

    private void createUIComponents() {
        buttonStart.addActionListener(e -> {
            if (buttonStart.getText().equals("Старт")) {
                controller.startHostPinger();
                buttonStart.setText("Стоп");
            } else {
                controller.stopPingerThread();
                controller.setHostsUnavailable();
                try {
                    reload();
                } catch (Exception ignored) {
                }
                buttonStart.setText("Старт");
            }
        });

        buttonAddHost.addActionListener(e -> new AddHost(controller));

        tableModel = new PaintTableModel();
        for (Host host : controller.getHosts()) {
            tableModel.addRow(host);
        }

        hostsTable.setModel(tableModel);
        hostsTable.setRowHeight(new PingCellPanel().getPreferredSize().height);
        hostsTable.setTableHeader(null);
        PingerCellRenderer pingerCellRenderer = new PingerCellRenderer();
        hostsTable.setDefaultRenderer(Object.class, pingerCellRenderer);
        hostsTable.setDefaultEditor(Object.class, pingerCellRenderer);

        hostsTable.setPreferredScrollableViewportSize(hostsTable.getPreferredSize());
    }
}
