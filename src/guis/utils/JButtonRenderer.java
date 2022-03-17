package guis.utils;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Renders a cell as a JButton element before the user interacts with the row
 */
public class JButtonRenderer implements TableCellRenderer {

    private final JButton button;
    
    public JButtonRenderer(String text) {
        this.button = new JButton(text);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this.button;
    }
    
}
