package pinger.view;

import pinger.model.Bulb;
import pinger.model.Host;
import pinger.model.JsonWriter;
import pinger.model.PaintTableModel;

import javax.swing.*;
import java.awt.*;

public class PingCellPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel nameLabel;

    private JLabel uriLabel;

    private JLabel portLabel;

    private Bulb availableBulb;

    public PingCellPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        nameLabel = new JLabel("");
        uriLabel = new JLabel("");
        portLabel = new JLabel("");
        availableBulb = new Bulb(false);
        JButton removeButton = new JButton("Удалить");
        removeButton.addActionListener(e -> {
            JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());
            int row = table.getEditingRow();
            PaintTableModel model = (PaintTableModel) table.getModel();
            model.removeRow(row);
            JsonWriter.saveVectorToJson(model.getDataVector());
            model = new PaintTableModel();
            try {
                for (Host host : JsonWriter.loadHostsList()) {
                    model.addRow(host);
                }
            } catch (Exception ignored) {}

            table.setModel(model);
        });

        add(new JLabel("Имя: "));
        add(nameLabel);
        add(new JLabel(" Адрес: "));
        add(uriLabel);
        add(new JLabel(" Порт: "));
        add(portLabel);
        availableBulb.setMaximumSize(availableBulb.getPreferredSize());
        add(availableBulb);
        add(Box.createHorizontalStrut(100));
        add(removeButton);
    }

    public void setHost(Host host) {
        nameLabel.setText(host.getName());
        uriLabel.setText(host.getUri());
        portLabel.setText(String.valueOf(host.getPort()));
        availableBulb.setAvailable(host.isAvailable());
    }

    public Host getHost() {
        return new Host(nameLabel.getText(), uriLabel.getText(), Integer.valueOf(portLabel.getText()),
                availableBulb.isAvailable());
    }
}
