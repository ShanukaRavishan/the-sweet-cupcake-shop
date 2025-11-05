package dev.shanuka.thesweetcupcakeshop.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.EventObject;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * A utility class providing common static helper functions for the application
 *
 * @author Shanuka
 */
public final class Helpers {
    private Helpers() { }

    /**
     * Generates the first available integer ID from a collection of used IDs
     * 
     * If used IDs are [1, 2, 4], returns 3
     * If IDs are continuous (e.g. [1, 2, 3]), returns the next id (4)
     * If no IDs are provided or the collection is null, returns 1
     * If IDs are [2, 3, 4], returns 1
     *
     * @param usedIds A Collection of integer IDs that are already in use
     * @return The first available (smallest) positive integer ID
     */
    public static int generateNextAvailableId(Collection<Integer> usedIds) {
        // If the provided collection is null or empty, the first ID is 1
        if (usedIds == null || usedIds.isEmpty()) {
            return 1;
        }

        // Add all unique, positive IDs into a sorted set
        Set<Integer> sortedIds = new TreeSet<>();
        for (Integer id : usedIds) {
            if (id != null && id > 0) {
                sortedIds.add(id);
            }
        }

        int expectedId = 1;

        // Find the first missing id by iterating through the sorted set
        for (Integer id : sortedIds) {
            if (id > expectedId) {
                
                // A gap was found. The current expectedId is the first available.
                break;
            }

            if (id.equals(expectedId)) {
                // This ID exists, so increment to check for the next one
                expectedId++;
            }
        }

        // If no gap was found, expectedId will be max id + 1
        return expectedId;
    }
    
    /**
     * Applies a set of styles to the given table
     *
     * @param table Table to stylize
     */
    public static void formatTable(JTable table) {
        // Our custom renderer will pull these settings
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        header.setReorderingAllowed(false);
        header.setForeground(new Color(66, 66, 66));
        header.setFont(Fonts.robotoMedium.deriveFont(16f));

        // Create and set a custom header renderer to remove vertical lines between columns
        DefaultTableCellRenderer customHeaderRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // Apply settings from the header object
                c.setBackground(header.getBackground());
                c.setForeground(header.getForeground());
                c.setFont(header.getFont());

                if (c instanceof javax.swing.JLabel) {
                    JLabel label = (javax.swing.JLabel) c;

                    // Replace the default border
                    label.setBorder(new EmptyBorder(0, 4, 0, 0));

                    // Set alignment
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                }
                return c;
            }
        };

        // Set this as the new default renderer for the header
        table.getTableHeader().setDefaultRenderer(customHeaderRenderer);

        // Configure cell renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setFont(Fonts.robotoRegular.deriveFont(16f));
        table.setDefaultRenderer(String.class, cellRenderer); // Apply to all String columns

        // Set data row height to match padding
        table.setRowHeight(35);
    }
}
