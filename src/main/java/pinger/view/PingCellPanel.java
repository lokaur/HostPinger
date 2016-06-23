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

    private Bulb availableBulb;

    public PingCellPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(214, 217, 223));
        JPanel left = new JPanel(new FlowLayout());
        JPanel right = new JPanel(new FlowLayout());
        nameLabel = new JLabel("");
        uriLabel = new JLabel("");
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

        left.add(new JLabel("Имя: "));
        left.add(nameLabel);
        left.add(new JLabel(" Адрес: "));
        left.add(uriLabel);

        availableBulb.setMaximumSize(availableBulb.getPreferredSize());

        right.add(availableBulb);
        right.add(removeButton);

        add(left, BorderLayout.LINE_START);
        add(right, BorderLayout.LINE_END);
    }

    public void setHost(Host host) {
        System.out.println(host.getName());
        nameLabel.setText(host.getName());
        uriLabel.setText(host.getUri());
        availableBulb.setAvailable(host.isAvailable());
    }

    public Host getHost() {
        return new Host(nameLabel.getText(), uriLabel.getText(),  availableBulb.isAvailable());
    }
}
