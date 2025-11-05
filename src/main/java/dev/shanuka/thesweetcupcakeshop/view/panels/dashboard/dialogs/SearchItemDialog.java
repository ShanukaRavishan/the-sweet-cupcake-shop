/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dev.shanuka.thesweetcupcakeshop.view.panels.dashboard.dialogs;

import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.model.Item;
import dev.shanuka.thesweetcupcakeshop.model.Order;
import dev.shanuka.thesweetcupcakeshop.service.InventoryService;
import dev.shanuka.thesweetcupcakeshop.util.AppConstants;
import dev.shanuka.thesweetcupcakeshop.util.ButtonCellEditor;
import dev.shanuka.thesweetcupcakeshop.util.ButtonRenderer;
import dev.shanuka.thesweetcupcakeshop.util.Fonts;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import dev.shanuka.thesweetcupcakeshop.util.Messages;
import dev.shanuka.thesweetcupcakeshop.util.PlaceholderManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nethmina
 */
public class SearchItemDialog extends javax.swing.JPanel {
    Function setSelectedItem;
    
    @FunctionalInterface
    interface Function {
        void execute(Item item);
    }
    
    /**
     * Creates new form SearchItemDialog
     */
    public SearchItemDialog(Function setSelectedItemFunction) {
        initComponents();

        // Bring focus to the main window
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setSelectedItem = setSelectedItemFunction;
        
        // Format the search results table
        Helpers.formatTable(searchResultTable);

        // Set column widths
        searchResultTable.getColumnModel().getColumn(0).setPreferredWidth(300);  // Item Name
        searchResultTable.getColumnModel().getColumn(1).setPreferredWidth(40); // Item Price
        searchResultTable.getColumnModel().getColumn(2).setPreferredWidth(30);  // Button

        // Add renderer & editor for button column
        searchResultTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        searchResultTable.getColumnModel().getColumn(2).setCellEditor(new ButtonCellEditor());
        
        // Make table cells non focusable to remove cell focus borders
        searchResultTable.setFocusable(false);
    }

    /**
     * Updates search results table based on the given query
     */
    private void updateSearchResult(String query) {
        // First, clear the search results table before adding new rows
        DefaultTableModel model = (DefaultTableModel) searchResultTable.getModel(); // Get the table's model
        model.setRowCount(0);
        
        if(query.isBlank()) return; // Skip empty queries

        // Retrieve the list of items that matches the given query
        List<String> searchKeywords = Arrays.asList(query.split(" "));

        List<Item> matches = new ArrayList<Item>();

        try {
            for (Item item : InventoryService.getAllItems()) {
                for (String word : Arrays.asList(item.getName())) {
                    for (String keyword : searchKeywords) {
                        if(word.toLowerCase().contains(keyword.toLowerCase()) && !matches.contains(item)) matches.add(item);
                    }
                }
            }
        } catch (ApplicationError e) {
            Messages.showError(this, "Application Error", "An unexpected error has occurred while fetching item data");
        }
        
        // Add all matching items to the search result
        for(Item item : matches) {
            addResultToTable(item);
        }
    }

    /**
     * Adds a new order to the table.
     */
    private void addResultToTable(Item item) {
        // Get the table's model
        DefaultTableModel model = (DefaultTableModel) searchResultTable.getModel();

        // Format the item price
        String formattedPrice = AppConstants.NUMBER_FORMAT.format(item.getPrice()) + " LKR";
        
        // Create the select item button
        JButton selectBtn = new JButton();
        selectBtn.setBackground(new java.awt.Color(255, 255, 255));
        selectBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/select_icon.png")));
        selectBtn.setBorder(null);
        selectBtn.setPreferredSize(new java.awt.Dimension(28, 28));

        // Click event for the button
        selectBtn.addActionListener((ActionEvent e) -> handleItemSelect(item));

        // Create the data row with the formatted strings and select item button
        Object[] rowData = new Object[]{
            item.getName(),
            formattedPrice,
            selectBtn
        };

        // Add the row to the model
        model.addRow(rowData);
    }

    // Event: Select button click event for each row
    private void handleItemSelect(Item item) {
        // Update the selected item
        setSelectedItem.execute(item);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogTitle = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        searchInput = new javax.swing.JTextField();
        itemNameTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchResultTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(244, 244, 244));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        dialogTitle.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dialogTitle.setForeground(new java.awt.Color(66, 66, 66));
        dialogTitle.setText("Search Item");

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(97, 11, 21), 1, true));
        searchPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchPanelMouseClicked(evt);
            }
        });

        searchBtn.setBackground(new java.awt.Color(97, 11, 21));
        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/search_icon.png"))); // NOI18N
        searchBtn.setBorder(null);

        searchInput.setBackground(new java.awt.Color(255, 255, 255));
        searchInput.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        searchInput.setForeground(new java.awt.Color(204,204,204));
        searchInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchInput)
                .addContainerGap())
        );

        itemNameTitle.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        itemNameTitle.setForeground(new java.awt.Color(66, 66, 66));
        itemNameTitle.setText("Matches");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(70, 73, 75));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setDoubleBuffered(true);

        searchResultTable.setFont(Fonts.robotoRegular.deriveFont(16f));
        searchResultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Item Price", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchResultTable.setDoubleBuffered(true);
        searchResultTable.setMinimumSize(new java.awt.Dimension(90, 0));
        searchResultTable.setRowHeight(40);
        searchResultTable.setSelectionBackground(new java.awt.Color(246, 246, 246));
        searchResultTable.setSelectionForeground(new java.awt.Color(66, 66, 66));
        searchResultTable.getTableHeader().setResizingAllowed(false);
        searchResultTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(searchResultTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemNameTitle)
                    .addComponent(dialogTitle)
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(dialogTitle)
                .addGap(28, 28, 28)
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(itemNameTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Event: Search input has gained focus
    private void searchInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchInputFocusGained
        PlaceholderManager.removePlaceholder(searchInput);
    }//GEN-LAST:event_searchInputFocusGained

    // Event: Search input has lost focus
    private void searchInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchInputFocusLost
        PlaceholderManager.addPlaceholder(searchInput);
    }//GEN-LAST:event_searchInputFocusLost

    // Focus search input when its parent panel is clicked
    private void searchPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchPanelMouseClicked
        searchInput.requestFocus();
    }//GEN-LAST:event_searchPanelMouseClicked

    // Event: When the search query is being entered
    private void searchInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputKeyReleased
        String query = searchInput.getText();
        updateSearchResult(query);
    }//GEN-LAST:event_searchInputKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dialogTitle;
    private javax.swing.JLabel itemNameTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchInput;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTable searchResultTable;
    // End of variables declaration//GEN-END:variables
}
