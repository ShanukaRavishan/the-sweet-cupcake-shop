package dev.shanuka.thesweetcupcakeshop.util;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer: Paints the JButton in the cell
 * 
 * @author Shanuka
 */
public class ButtonRenderer implements TableCellRenderer {
    @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }

            return new JButton("Select");
        }
}
