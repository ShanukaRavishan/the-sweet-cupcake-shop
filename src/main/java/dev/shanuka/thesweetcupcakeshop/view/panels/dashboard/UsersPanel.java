package dev.shanuka.thesweetcupcakeshop.view.panels.dashboard;

import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.User;
import dev.shanuka.thesweetcupcakeshop.service.UserService;
import dev.shanuka.thesweetcupcakeshop.util.ButtonCellEditor;
import dev.shanuka.thesweetcupcakeshop.util.ButtonRenderer;
import dev.shanuka.thesweetcupcakeshop.util.Fonts;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import dev.shanuka.thesweetcupcakeshop.util.Messages;
import dev.shanuka.thesweetcupcakeshop.view.panels.dashboard.dialogs.AddUserDialog;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * User management tab of the manager dashboard
 *
 * @author Shanuka
 */
public class UsersPanel extends javax.swing.JPanel {

    // Parent frame in which the current panel is mounted
    JFrame parentFrame;

    /**
     * Creates new form UsersPanel
     *
     * @param parentFrame Parent frame (for displaying error message dialogs)
     */
    public UsersPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        initComponents();

        // Format the users table
        Helpers.formatTable(usersTable);

        // Setup button renderer/editor for delete button
        usersTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        usersTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor());

        // Load initial list of users
        updateUsersList();
    }

    /**
     * Clears all rows from the users table and safely restores the button renderer and editor for the last column
     */
    private void clearUsersTable() {
        DefaultTableModel emptyModel = new DefaultTableModel(
                new Object[]{"User ID", "Role", "First Name", "Last Name", "Email", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the Delete column is editable
            }
        };

        usersTable.setModel(emptyModel);

        // Restore button renderer and editor
        usersTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        usersTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor());
    }

    /**
     * Updates the users table with the latest data from the data store.
     */
    private void updateUsersList() {
        clearUsersTable();
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();

        try {
            List<User> users = UserService.getAllUsers();

            for (User user : users) {
                
                // Create the delete item button
                JButton deleteBtn = new JButton();
                deleteBtn.setBackground(new java.awt.Color(255, 255, 255));
                deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_items/delete_item.png")));
                deleteBtn.setBorder(null);
                deleteBtn.setPreferredSize(new java.awt.Dimension(28, 28));

                // Click event for the button
                deleteBtn.addActionListener((ActionEvent e) -> handleDeleteUser(user));

                model.addRow(new Object[]{
                    user.getId(),
                    user.getRole(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getemail(),
                    deleteBtn
                });
            }

        } catch (ApplicationError e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load users: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles delete button click event for users table.
     */
    private void handleDeleteUser(User user) {
        int userId = user.getId();
        String fullName = user.getFirstName() + " " + user.getLastName();

        // Confirm user's name before deleting their account
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete user \"" + fullName + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String deletedUser = UserService.removeUser(userId);
                JOptionPane.showMessageDialog(this,
                        "User \"" + deletedUser + "\" deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                updateUsersList(); // Update the users table

            } catch (NotFoundError |ApplicationError ex) {
                Messages.showError(this, "Application Error", "An unexpected error occurred while deleting user");
            }
        }
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

        usersPanel = new javax.swing.JPanel();
        usersTablePanel = new javax.swing.JPanel();
        usersTableScrollPane = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        addUserBtn = new javax.swing.JButton();
        manageUsersLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 244, 244));
        setMaximumSize(new java.awt.Dimension(1213, 900));
        setMinimumSize(new java.awt.Dimension(1213, 900));
        setLayout(new java.awt.GridBagLayout());

        usersPanel.setBackground(new java.awt.Color(255, 255, 255));
        usersPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        usersPanel.setPreferredSize(new java.awt.Dimension(1140, 528));

        usersTablePanel.setBackground(new java.awt.Color(255, 255, 255));
        usersTablePanel.setPreferredSize(new java.awt.Dimension(1070, 470));
        usersTablePanel.setLayout(new java.awt.BorderLayout());

        usersTableScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        usersTableScrollPane.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        usersTableScrollPane.setDoubleBuffered(true);
        usersTableScrollPane.setPreferredSize(new java.awt.Dimension(470, 420));

        usersTable.setFont(Fonts.robotoRegular.deriveFont(16f));
        usersTable.setForeground(new java.awt.Color(66, 66, 66));
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Role", "First Name", "Last Name", "Email", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersTable.setRowHeight(40);
        usersTable.setSelectionBackground(new java.awt.Color(246, 246, 246));
        usersTable.setSelectionForeground(new java.awt.Color(66, 66, 66));
        usersTableScrollPane.setViewportView(usersTable);

        usersTablePanel.add(usersTableScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout usersPanelLayout = new javax.swing.GroupLayout(usersPanel);
        usersPanel.setLayout(usersPanelLayout);
        usersPanelLayout.setHorizontalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usersPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(usersTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        usersPanelLayout.setVerticalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usersPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(usersTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
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
        add(usersPanel, gridBagConstraints);

        addUserBtn.setBackground(new java.awt.Color(154, 2, 21));
        addUserBtn.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        addUserBtn.setForeground(new java.awt.Color(255, 255, 255));
        addUserBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_users/add_user.png"))); // NOI18N
        addUserBtn.setText("Add User");
        addUserBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(154, 2, 21), 1, true));
        addUserBtn.setIconTextGap(6);
        addUserBtn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        addUserBtn.setMaximumSize(new java.awt.Dimension(155, 50));
        addUserBtn.setMinimumSize(new java.awt.Dimension(155, 50));
        addUserBtn.setPreferredSize(new java.awt.Dimension(140, 50));
        addUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        add(addUserBtn, gridBagConstraints);

        manageUsersLabel.setFont(new java.awt.Font("Roboto", 1, 28)); // NOI18N
        manageUsersLabel.setForeground(new java.awt.Color(66, 66, 66));
        manageUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        manageUsersLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/manage_sales/sale_icon.png"))); // NOI18N
        manageUsersLabel.setText("Manage Users");
        manageUsersLabel.setToolTipText("");
        manageUsersLabel.setAlignmentY(0.0F);
        manageUsersLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(50, 70, 50, 0);
        add(manageUsersLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Event: Add user button has been clicked
    private void addUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserBtnActionPerformed
        JDialog dialog = new JDialog(parentFrame, "Add New User", true);
        dialog.setResizable(false);

        // Initialize and display the add user dialog
        dialog.getContentPane().add(new AddUserDialog(() -> {
            // Update users table once a new user has been added
            updateUsersList();
            
            dialog.dispose();
        }));
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }//GEN-LAST:event_addUserBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserBtn;
    private javax.swing.JLabel manageUsersLabel;
    private javax.swing.JPanel usersPanel;
    private javax.swing.JTable usersTable;
    private javax.swing.JPanel usersTablePanel;
    private javax.swing.JScrollPane usersTableScrollPane;
    // End of variables declaration//GEN-END:variables
}
