package dev.shanuka.thesweetcupcakeshop.view.panels.dashboard;

import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.model.Order;
import dev.shanuka.thesweetcupcakeshop.service.AuthService;
import dev.shanuka.thesweetcupcakeshop.service.InventoryService;
import dev.shanuka.thesweetcupcakeshop.service.OrderService;
import dev.shanuka.thesweetcupcakeshop.util.AppConstants;
import dev.shanuka.thesweetcupcakeshop.util.Fonts;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import dev.shanuka.thesweetcupcakeshop.util.Messages;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Dashboard view for user dashboard
 *
 * @author Shanuka
 */
public class DashboardPanel extends javax.swing.JPanel {
    // Parent frame in which the current panel is mounted
    JFrame parentFrame;
    
    /**
     * Creates new form Dashboard
     * 
     * @param parentFrame Parent frame (for displaying error message dialogs)
     */
    public DashboardPanel(JFrame parentFrame) {
        initComponents();

        this.parentFrame = parentFrame;
        
        // Set custom fonts
        monthlySalesTitle.setFont(Fonts.robotoRegular.deriveFont(16f));
        monthlyOrdersTitle.setFont(Fonts.robotoRegular.deriveFont(16f));
        itemsListedTitle.setFont(Fonts.robotoRegular.deriveFont(16f));
        averageSalePrice.setFont(Fonts.robotoRegular.deriveFont(16f));

        // Show greeting to the currently logged in user
        greetingLabel.setText(String.format("Welcome Back, %s!", AuthService.loggedUser.getFirstName()));
        
        // Format the recent orders table
        Helpers.formatTable(recentOrdersTable);
        
        // Set column widths
        recentOrdersTable.getColumnModel().getColumn(0).setPreferredWidth(50); // Order ID
        recentOrdersTable.getColumnModel().getColumn(2).setPreferredWidth(400); // Item Name

        // Dashboard data must be updated within the constructor for the first time as formAncestorAdded causes a slight delay
        try {
            updateDashboardData();
        } catch (ApplicationError ex) {
            Messages.showError(parentFrame, "Application Error", "An unexpected error has occurred while fetching dashboard data");
        }
    }

    /**
     * Updates dashboard data including recent orders table and widget data
     */
    private void updateDashboardData() throws ApplicationError {
        // Clear the recent sales table before adding new rows
        DefaultTableModel model = (DefaultTableModel) recentOrdersTable.getModel(); // Get the table's model
        model.setRowCount(0);
        
        // Load recent sales data (upto 11)
        try {
            // Retrieve all orders
            List<Order> orders = OrderService.getAllOrders();

            // Sort orders by date (newest to oldest)
            orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            
            for (Order order : orders) {
                if (orders.indexOf(order) == 11) {
                    break;
                }

                addOrderToTable(order);
            }
        } catch (ApplicationError e) {
            System.err.println("An error has been occured while retrieving the orders!");
        }

        // Update Monthly Sales, Orders Received, Items Listed, and Average Sale Price widgets
        List<Order> sales = OrderService.getAllOrders();

        // Get the current month and year
        YearMonth currentMonth = YearMonth.now();

        // Filter out the orders placed within current month
        List<Order> monthlyOrders = sales.stream()
                .filter(order -> {
                    // Convert the order's Date to a YearMonth object
                    YearMonth orderMonth = YearMonth.from(
                            order.getDate().toInstant().atZone(ZoneId.systemDefault())
                    );

                    // Compare it to the current month
                    return currentMonth.equals(orderMonth);
                })
                .collect(Collectors.toList());

        // Calculate total monthly sales amount
        double monthlySalesValue = 0;

        for (Order order : monthlyOrders) {
            monthlySalesValue += order.getTotalAmount();
        }

        // Format and display the calculated widget data
        monthlySalesAmount.setText(AppConstants.NUMBER_FORMAT.format(monthlySalesValue) + " LKR");
        monthlyOrdersAmount.setText(AppConstants.NUMBER_FORMAT.format(monthlyOrders.size()) + " Orders");
        itemsListedAmount.setText(AppConstants.NUMBER_FORMAT.format(InventoryService.getAllItems().size()) + " Items");
        averageSaleAmount.setText(AppConstants.NUMBER_FORMAT.format(monthlySalesValue / monthlyOrders.size()) + " LKR");
    }

    /**
     * Adds a new order to the table.
     */
    private void addOrderToTable(Order order) {
        // Get the table's model
        DefaultTableModel model = (DefaultTableModel) recentOrdersTable.getModel();

        // Format the ID (makes it three digit)
        String formattedID = String.format("%03d", order.getId());

        //  Format the Date
        String formattedDate = AppConstants.DATE_FORMAT.format(order.getDate());

        // Format the Currency
        String formattedPrice = AppConstants.NUMBER_FORMAT.format(order.getItemPrice()) + " LKR";
        String formattedTotal = AppConstants.NUMBER_FORMAT.format(order.getTotalAmount()) + " LKR";

        // Create the data row with the formatted strings
        Object[] rowData = new Object[]{
            formattedID,
            formattedDate,
            order.getItem(),
            formattedPrice,
            order.getQuantity(),
            formattedTotal
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

        greetingLabel = new javax.swing.JLabel();
        monthlySales = new javax.swing.JPanel();
        monthlySalesTitle = new javax.swing.JLabel();
        monthlySalesIcon = new javax.swing.JLabel();
        monthlySalesAmount = new javax.swing.JLabel();
        monthlyOrders = new javax.swing.JPanel();
        monthlyOrdersTitle = new javax.swing.JLabel();
        monthlyOrdersIcon = new javax.swing.JLabel();
        monthlyOrdersAmount = new javax.swing.JLabel();
        itemsListed = new javax.swing.JPanel();
        itemsListedTitle = new javax.swing.JLabel();
        itemsListedIcon = new javax.swing.JLabel();
        itemsListedAmount = new javax.swing.JLabel();
        averageSalePrice = new javax.swing.JPanel();
        averageSalePriceTitle = new javax.swing.JLabel();
        averageSalePriceIcon = new javax.swing.JLabel();
        averageSaleAmount = new javax.swing.JLabel();
        recentSales = new javax.swing.JPanel();
        recentSalesTitle = new javax.swing.JLabel();
        recentSalesTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        recentOrdersTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(244, 244, 244));
        setMaximumSize(new java.awt.Dimension(1213, 900));
        setMinimumSize(new java.awt.Dimension(1213, 900));
        setPreferredSize(new java.awt.Dimension(1213, 900));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        setLayout(new java.awt.GridBagLayout());

        greetingLabel.setFont(new java.awt.Font("Roboto", 1, 28)); // NOI18N
        greetingLabel.setForeground(new java.awt.Color(66, 66, 66));
        greetingLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        greetingLabel.setText("Welcome Back, Sachintha!");
        greetingLabel.setToolTipText("");
        greetingLabel.setAlignmentY(0.0F);
        greetingLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(50, 70, 50, 0);
        add(greetingLabel, gridBagConstraints);
        greetingLabel.getAccessibleContext().setAccessibleName("Greeting Label");

        monthlySales.setBackground(new java.awt.Color(255, 255, 255));
        monthlySales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        monthlySales.setPreferredSize(new java.awt.Dimension(270, 200));

        monthlySalesTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        monthlySalesTitle.setForeground(new java.awt.Color(125, 125, 125));
        monthlySalesTitle.setText("Monthly Sales");

        monthlySalesIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dashboard/monthly_sales.png"))); // NOI18N

        monthlySalesAmount.setFont(new java.awt.Font("Roboto", 1, 30)); // NOI18N
        monthlySalesAmount.setForeground(new java.awt.Color(66, 66, 66));
        monthlySalesAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthlySalesAmount.setText("58,000 LKR");
        monthlySalesAmount.setFocusTraversalPolicyProvider(true);
        monthlySalesAmount.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        monthlySalesAmount.setMaximumSize(new java.awt.Dimension(270, 33));
        monthlySalesAmount.setMinimumSize(new java.awt.Dimension(270, 33));
        monthlySalesAmount.setPreferredSize(new java.awt.Dimension(270, 33));

        javax.swing.GroupLayout monthlySalesLayout = new javax.swing.GroupLayout(monthlySales);
        monthlySales.setLayout(monthlySalesLayout);
        monthlySalesLayout.setHorizontalGroup(
            monthlySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, monthlySalesLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(monthlySalesTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(monthlySalesIcon)
                .addGap(29, 29, 29))
            .addComponent(monthlySalesAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        monthlySalesLayout.setVerticalGroup(
            monthlySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlySalesLayout.createSequentialGroup()
                .addGroup(monthlySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(monthlySalesLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(monthlySalesTitle))
                    .addGroup(monthlySalesLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(monthlySalesIcon)))
                .addGap(34, 34, 34)
                .addComponent(monthlySalesAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(monthlySales, gridBagConstraints);

        monthlyOrders.setBackground(new java.awt.Color(255, 255, 255));
        monthlyOrders.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        monthlyOrders.setPreferredSize(new java.awt.Dimension(270, 200));

        monthlyOrdersTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        monthlyOrdersTitle.setForeground(new java.awt.Color(125, 125, 125));
        monthlyOrdersTitle.setText("Orders Received");

        monthlyOrdersIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dashboard/orders_received.png"))); // NOI18N

        monthlyOrdersAmount.setFont(new java.awt.Font("Roboto", 1, 30)); // NOI18N
        monthlyOrdersAmount.setForeground(new java.awt.Color(66, 66, 66));
        monthlyOrdersAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthlyOrdersAmount.setText("1,120 Orders");
        monthlyOrdersAmount.setFocusTraversalPolicyProvider(true);
        monthlyOrdersAmount.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        monthlyOrdersAmount.setMaximumSize(new java.awt.Dimension(270, 33));
        monthlyOrdersAmount.setMinimumSize(new java.awt.Dimension(270, 33));
        monthlyOrdersAmount.setPreferredSize(new java.awt.Dimension(270, 33));

        javax.swing.GroupLayout monthlyOrdersLayout = new javax.swing.GroupLayout(monthlyOrders);
        monthlyOrders.setLayout(monthlyOrdersLayout);
        monthlyOrdersLayout.setHorizontalGroup(
            monthlyOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, monthlyOrdersLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(monthlyOrdersTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(monthlyOrdersIcon)
                .addGap(29, 29, 29))
            .addComponent(monthlyOrdersAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        monthlyOrdersLayout.setVerticalGroup(
            monthlyOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyOrdersLayout.createSequentialGroup()
                .addGroup(monthlyOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(monthlyOrdersLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(monthlyOrdersTitle))
                    .addGroup(monthlyOrdersLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(monthlyOrdersIcon)))
                .addGap(34, 34, 34)
                .addComponent(monthlyOrdersAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(monthlyOrders, gridBagConstraints);

        itemsListed.setBackground(new java.awt.Color(255, 255, 255));
        itemsListed.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        itemsListed.setPreferredSize(new java.awt.Dimension(270, 200));

        itemsListedTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        itemsListedTitle.setForeground(new java.awt.Color(125, 125, 125));
        itemsListedTitle.setText("Items Listed");

        itemsListedIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dashboard/items_listed.png"))); // NOI18N

        itemsListedAmount.setFont(new java.awt.Font("Roboto", 1, 30)); // NOI18N
        itemsListedAmount.setForeground(new java.awt.Color(66, 66, 66));
        itemsListedAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemsListedAmount.setText("70 Items");
        itemsListedAmount.setFocusTraversalPolicyProvider(true);
        itemsListedAmount.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        itemsListedAmount.setMaximumSize(new java.awt.Dimension(270, 33));
        itemsListedAmount.setMinimumSize(new java.awt.Dimension(270, 33));
        itemsListedAmount.setPreferredSize(new java.awt.Dimension(270, 33));

        javax.swing.GroupLayout itemsListedLayout = new javax.swing.GroupLayout(itemsListed);
        itemsListed.setLayout(itemsListedLayout);
        itemsListedLayout.setHorizontalGroup(
            itemsListedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, itemsListedLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(itemsListedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(itemsListedIcon)
                .addGap(29, 29, 29))
            .addComponent(itemsListedAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        itemsListedLayout.setVerticalGroup(
            itemsListedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemsListedLayout.createSequentialGroup()
                .addGroup(itemsListedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(itemsListedLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(itemsListedTitle))
                    .addGroup(itemsListedLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(itemsListedIcon)))
                .addGap(34, 34, 34)
                .addComponent(itemsListedAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(itemsListed, gridBagConstraints);

        averageSalePrice.setBackground(new java.awt.Color(255, 255, 255));
        averageSalePrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        averageSalePrice.setPreferredSize(new java.awt.Dimension(270, 200));

        averageSalePriceTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        averageSalePriceTitle.setForeground(new java.awt.Color(125, 125, 125));
        averageSalePriceTitle.setText("Average Sale Price");

        averageSalePriceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dashboard/avg_sale_price.png"))); // NOI18N

        averageSaleAmount.setFont(new java.awt.Font("Roboto", 1, 30)); // NOI18N
        averageSaleAmount.setForeground(new java.awt.Color(66, 66, 66));
        averageSaleAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageSaleAmount.setText("700 LKR");
        averageSaleAmount.setFocusTraversalPolicyProvider(true);
        averageSaleAmount.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        averageSaleAmount.setMaximumSize(new java.awt.Dimension(270, 33));
        averageSaleAmount.setMinimumSize(new java.awt.Dimension(270, 33));
        averageSaleAmount.setPreferredSize(new java.awt.Dimension(270, 33));

        javax.swing.GroupLayout averageSalePriceLayout = new javax.swing.GroupLayout(averageSalePrice);
        averageSalePrice.setLayout(averageSalePriceLayout);
        averageSalePriceLayout.setHorizontalGroup(
            averageSalePriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, averageSalePriceLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(averageSalePriceTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(averageSalePriceIcon)
                .addGap(29, 29, 29))
            .addComponent(averageSaleAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        averageSalePriceLayout.setVerticalGroup(
            averageSalePriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(averageSalePriceLayout.createSequentialGroup()
                .addGroup(averageSalePriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(averageSalePriceLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(averageSalePriceTitle))
                    .addGroup(averageSalePriceLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(averageSalePriceIcon)))
                .addGap(34, 34, 34)
                .addComponent(averageSaleAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 24);
        add(averageSalePrice, gridBagConstraints);

        recentSales.setBackground(new java.awt.Color(255, 255, 255));
        recentSales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        recentSales.setPreferredSize(new java.awt.Dimension(1140, 528));

        recentSalesTitle.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        recentSalesTitle.setForeground(new java.awt.Color(125, 125, 125));
        recentSalesTitle.setText("Recent Sales");

        recentSalesTable.setBackground(new java.awt.Color(255, 255, 255));
        recentSalesTable.setPreferredSize(new java.awt.Dimension(1070, 470));
        recentSalesTable.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setDoubleBuffered(true);

        recentOrdersTable.setFont(Fonts.robotoRegular.deriveFont(16f));
        recentOrdersTable.setForeground(new java.awt.Color(66, 66, 66));
        recentOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Date", "Item", "Item Price", "Quantity", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recentOrdersTable.setRowHeight(40);
        recentOrdersTable.setSelectionBackground(new java.awt.Color(246, 246, 246));
        recentOrdersTable.setSelectionForeground(new java.awt.Color(66, 66, 66));
        jScrollPane2.setViewportView(recentOrdersTable);

        recentSalesTable.add(jScrollPane2, java.awt.BorderLayout.CENTER);

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
                .addComponent(recentSalesTable, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(recentSales, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Event: Refresh dashboard data when the panel is being mounted
    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        try {
            // Update dashboard data
            updateDashboardData();
        } catch (ApplicationError ex) {
            Messages.showError(parentFrame, "Application Error", "An unexpected error has occurred while fetching dashboard data");
        }
    }//GEN-LAST:event_formAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel averageSaleAmount;
    private javax.swing.JPanel averageSalePrice;
    private javax.swing.JLabel averageSalePriceIcon;
    private javax.swing.JLabel averageSalePriceTitle;
    private javax.swing.JLabel greetingLabel;
    private javax.swing.JPanel itemsListed;
    private javax.swing.JLabel itemsListedAmount;
    private javax.swing.JLabel itemsListedIcon;
    private javax.swing.JLabel itemsListedTitle;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel monthlyOrders;
    private javax.swing.JLabel monthlyOrdersAmount;
    private javax.swing.JLabel monthlyOrdersIcon;
    private javax.swing.JLabel monthlyOrdersTitle;
    private javax.swing.JPanel monthlySales;
    private javax.swing.JLabel monthlySalesAmount;
    private javax.swing.JLabel monthlySalesIcon;
    private javax.swing.JLabel monthlySalesTitle;
    private javax.swing.JTable recentOrdersTable;
    private javax.swing.JPanel recentSales;
    private javax.swing.JPanel recentSalesTable;
    private javax.swing.JLabel recentSalesTitle;
    // End of variables declaration//GEN-END:variables
}
