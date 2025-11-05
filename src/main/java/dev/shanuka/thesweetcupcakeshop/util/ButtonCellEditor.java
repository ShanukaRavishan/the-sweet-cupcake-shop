package dev.shanuka.thesweetcupcakeshop.util;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Cell editor for the button that handles clicks
 * 
 * @author Shanuka
 */
public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

        private JButton currentButton;

        @Override
        public Object getCellEditorValue() {
            return currentButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                currentButton = (JButton) value;
            } else {
                currentButton = new JButton("Select");
            }
            return currentButton;
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }
    }
