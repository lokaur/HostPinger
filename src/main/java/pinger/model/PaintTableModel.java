package pinger.model;

import javax.swing.table.DefaultTableModel;

public class PaintTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    @Override
    public int getColumnCount() {
        return 1;
    }

    public void addRow(Host host) {
        super.addRow(host.toRowData());
    }
}
