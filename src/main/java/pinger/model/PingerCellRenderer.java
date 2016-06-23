package pinger.model;

import pinger.view.PingCellPanel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

public class PingerCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private static final long serialVersionUID = 1L;
    private PingCellPanel renderer = new PingCellPanel();
    private PingCellPanel editor = new PingCellPanel();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setHost((Host) value);
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getHost();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Host host = (Host) value;
        renderer.setHost(host);
        return renderer;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }
}
