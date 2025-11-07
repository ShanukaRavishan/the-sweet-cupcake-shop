package dev.shanuka.thesweetcupcakeshop.view.panels.dashboard;

import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.Item;
import dev.shanuka.thesweetcupcakeshop.service.InventoryService;
import dev.shanuka.thesweetcupcakeshop.util.AppConstants;
import dev.shanuka.thesweetcupcakeshop.util.ButtonCellEditor;
import dev.shanuka.thesweetcupcakeshop.util.ButtonRenderer;
import dev.shanuka.thesweetcupcakeshop.util.Fonts;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import dev.shanuka.thesweetcupcakeshop.util.Messages;
import dev.shanuka.thesweetcupcakeshop.util.PlaceholderManager;
import dev.shanuka.thesweetcupcakeshop.view.panels.dashboard.dialogs.AddItemDialog;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shanuka
 */
public class InventoryPanel extends javax.swing.JPanel {
    // Parent frame in which the current panel is mounted
    JFrame parentFrame;

    /**
     * Creates new form InventoryPanel
     *
     * @param parentFrame Parent frame (for displaying error message dialogs)
     */
    public InventoryPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        
        initComponents();

        // Format the items table
        Helpers.formatTable(itemsTable);

        // Set column widths
        itemsTable.getColumnModel().getColumn(0).setPreferredWidth(40); // Item ID
        itemsTable.getColumnModel().getColumn(1).setPreferredWidth(400); // Item Name
        itemsTable.getColumnModel().getColumn(3).setPreferredWidth(40); // Item Price
        itemsTable.getColumnModel().getColumn(4).setPreferredWidth(40); // Delete Item Button

        // Add renderer & editor for button column
        itemsTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        itemsTable.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor());

        // Update data shown in the sales table
        updateItemsList();
    }

    
    /**
 * Clears all rows from the items table and safely restores 
 * the button renderer and editor for the last column.
 */
private void clearItemsTable() {
    // Create a fresh model with the same columns
    DefaultTableModel emptyModel = new DefaultTableModel(
            new Object[]{"Item ID", "Item Name", "Category", "Price", ""}, 0
    ) {
        // Only the last column (button) should be editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    // Assign the new model to the table
    itemsTable.setModel(emptyModel);

    // Restore button renderer and editor for the last column
    itemsTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
    itemsTable.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor());
}

    
    /**
     * Updates the items list by reloading all items from the inventory.
     */
    private void updateItemsList() {
        clearItemsTable(); // Clear the items table before adding new rows

        try {
            // Retrieve all items
            List<Item> items = InventoryService.getAllItems();

            // Sort items alphabetically by name
            items.sort(Comparator.comparing(
                    item -> item.getName() != null ? item.getName().toLowerCase() : ""
            ));

            // Add items to the table
            for (Item item : items) {
                addOrderToTable(item);
            }

        } catch (ApplicationError e) {
            Messages.showError(this, "Application Error", "An unexpected error has occurred while fetching items data");
        }
    }

    // Event: Delete button click event for each row
    private void handleItemDelete(Item item) {
        try {
            // Delete the selected item
            String deletedItem = InventoryService.removeItem(item.getId());

            // Show delection successful message
            Messages.showSuccess(this, "Item Deleted Successfully", "The item \"" + deletedItem + "\" has been deleted successfully");

            // Refresh the list of items
            updateItemsList();
        } catch (ApplicationError | NotFoundError ex) {
            Messages.showError(this, "Application Error", "An unexpected error has occurred while deleting the item");
        }
    }

    /**
     * Updates the items table based on the given search query.
     */
    private void updateSearchResults(String query) {
        clearItemsTable(); // Clear the items table before adding new rows

        if (query == null || query.isBlank()) {
            updateItemsList();
            return;
        }

        List<String> searchKeywords = List.of(query.split("\\s+"));
        List<Item> matches = new ArrayList<>();

        try {
            List<Item> allItems = InventoryService.getAllItems();

            for (Item item : allItems) {
                String itemName = item.getName() != null ? item.getName().toLowerCase() : "";
                String category = item.getCategory() != null ? item.getCategory().toLowerCase() : "";

                for (String keyword : searchKeywords) {
                    keyword = keyword.toLowerCase();
                    if ((itemName.contains(keyword) || category.contains(keyword)) && !matches.contains(item)) {
                        matches.add(item);
                    }
                }
            }

            // Sort results alphabetically by name
            matches.sort(Comparator.comparing(
                    i -> i.getName() != null ? i.getName().toLowerCase() : ""
            ));

        } catch (ApplicationError e) {
            Messages.showError(this, "Application Error", "An unexpected error occurred while searching items");
            return;
        }

        for (Item item : matches) {
            addOrderToTable(item);
        }
    }

    /**
     * Adds a new order to the table.
     */
    private void addOrderToTable(Item item) {
        // Get the table's model
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();

        // Format the ID (makes it three digit)
        String formattedID = String.format("%03d", item.getId());

        // Format the Currency
        String formattedPrice = AppConstants.NUMBER_FORMAT.format(item.getPrice()) + " LKR";

        // Create the delete item button
        JButton deleteBtn = new JButton();
        deleteBtn.setBackground(new java.awt.Color(255, 255, 255));
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_items/delete_item.png")));
        deleteBtn.setBorder(null);
        deleteBtn.setPreferredSize(new java.awt.Dimension(28, 28));

        // Click event for the button
        deleteBtn.addActionListener((ActionEvent e) -> handleItemDelete(item));

        // Create the data row with the formatted strings and delete item button
        Object[] rowData = new Object[]{
            formattedID,
            item.getName(),
            item.getCategory(),
            formattedPrice,
            deleteBtn
        };

        // Add the row to the model
        model.addRow(rowData);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        manageSalesLabel = new javax.swing.JLabel();
        recentSales = new javax.swing.JPanel();
        recentSalesTitle = new javax.swing.JLabel();
        recentSalesTable = new javax.swing.JPanel();
        recentOrdersScrollPane = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        addItemButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        searchIcon = new javax.swing.JLabel();
        searchInput = new javax.swing.JTextField();

        setBackground(new java.awt.Color(244, 244, 244));
        setMaximumSize(new java.awt.Dimension(1213, 900));
        setMinimumSize(new java.awt.Dimension(1213, 900));
        setLayout(new java.awt.GridBagLayout());

        manageSalesLabel.setFont(new java.awt.Font("Roboto", 1, 28)); // NOI18N
        manageSalesLabel.setForeground(new java.awt.Color(66, 66, 66));
        manageSalesLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        manageSalesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_sales/sale_icon.png"))); // NOI18N
        manageSalesLabel.setText("Manage Items");
        manageSalesLabel.setToolTipText("");
        manageSalesLabel.setAlignmentY(0.0F);
        manageSalesLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(50, 70, 50, 0);
        add(manageSalesLabel, gridBagConstraints);

        recentSales.setBackground(new java.awt.Color(255, 255, 255));
        recentSales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        recentSales.setPreferredSize(new java.awt.Dimension(1140, 528));

        recentSalesTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        recentSalesTitle.setForeground(new java.awt.Color(125, 125, 125));
        recentSalesTitle.setText("Recent Sales");

        recentSalesTable.setBackground(new java.awt.Color(255, 255, 255));
        recentSalesTable.setPreferredSize(new java.awt.Dimension(1070, 470));
        recentSalesTable.setLayout(new java.awt.BorderLayout());

        recentOrdersScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        recentOrdersScrollPane.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        recentOrdersScrollPane.setDoubleBuffered(true);

        itemsTable.setFont(Fonts.robotoRegular.deriveFont(16f));
        itemsTable.setForeground(new java.awt.Color(66, 66, 66));
        itemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Category", "Price", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemsTable.setRowHeight(40);
        itemsTable.setSelectionBackground(new java.awt.Color(246, 246, 246));
        itemsTable.setSelectionForeground(new java.awt.Color(66, 66, 66));
        recentOrdersScrollPane.setViewportView(itemsTable);

        recentSalesTable.add(recentOrdersScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout recentSalesLayout = new javax.swing.GroupLayout(recentSales);
        recentSales.setLayout(recentSalesLayout);
        recentSalesLayout.setHorizontalGroup(
            recentSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentSalesLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(recentSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recentSalesTitle)
                    .addComponent(recentSalesTable, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        recentSalesLayout.setVerticalGroup(
            recentSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentSalesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(recentSalesTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recentSalesTable, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(recentSales, gridBagConstraints);

        addItemButton.setBackground(new java.awt.Color(154, 2, 21));
        addItemButton.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        addItemButton.setForeground(new java.awt.Color(255, 255, 255));
        addItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_items/item.png"))); // NOI18N
        addItemButton.setText("Add Item");
        addItemButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(154, 2, 21), 1, true));
        addItemButton.setIconTextGap(6);
        addItemButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        addItemButton.setMaximumSize(new java.awt.Dimension(155, 50));
        addItemButton.setMinimumSize(new java.awt.Dimension(155, 50));
        addItemButton.setPreferredSize(new java.awt.Dimension(134, 50));
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        add(addItemButton, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        searchIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_items/search_icon.png"))); // NOI18N
        searchIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        searchInput.setBackground(new java.awt.Color(255, 255, 255));
        searchInput.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        searchInput.setForeground(new java.awt.Color(113, 113, 113));
        searchInput.setText("Search for an item...");
        searchInput.setBorder(null);
        searchInput.setName("itemSearchField"); // NOI18N
        searchInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchInputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchInputFocusLost(evt);
            }
        });
        searchInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInputKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(searchIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
            .addComponent(searchInput)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Event: Add item button has been clicked
    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        JDialog dialog = new JDialog(parentFrame, "Search Items", true);
        dialog.setResizable(false);

        dialog.getContentPane().add(new AddItemDialog(() -> {
            updateItemsList();
            dialog.dispose();
        }));
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }//GEN-LAST:event_addItemButtonActionPerformed

    // Event: Search input has gained focus
    private void searchInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchInputFocusGained
        PlaceholderManager.removePlaceholder(searchInput);
    }//GEN-LAST:event_searchInputFocusGained

    // Event: Search input has lost focus
    private void searchInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchInputFocusLost
        PlaceholderManager.addPlaceholder(searchInput);
    }//GEN-LAST:event_searchInputFocusLost

    // Event: Search query is being entered
    private void searchInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputKeyReleased
        String query = searchInput.getText().trim();
        updateSearchResults(query);
    }//GEN-LAST:event_searchInputKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemButton;
    private javax.swing.JTable itemsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel manageSalesLabel;
    private javax.swing.JScrollPane recentOrdersScrollPane;
    private javax.swing.JPanel recentSales;
    private javax.swing.JPanel recentSalesTable;
    private javax.swing.JLabel recentSalesTitle;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTextField searchInput;
    // End of variables declaration//GEN-END:variables
}
