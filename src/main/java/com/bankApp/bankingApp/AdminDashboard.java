package com.bankApp.bankingApp;

import com.bankApp.bankingApp.model.Transaction;
import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.service.AuthService;
import com.bankApp.bankingApp.service.TransactionService;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class AdminDashboard extends JFrame {

    private JPanel welcomePanel;
    private JLabel hellonameLabel;
    private JLabel welcomeLabel;
    private JPanel mainPanel;

    private JPanel transactionsPanel;
    private JScrollPane transactionsScrollPanel;
    private JTable transactionsTable;

    private JButton logoutBtn;

    private JPanel adminPanel;
    private JComboBox viewBox;

    private JPanel viewPanel;

    private JPanel usersPanel;
    private JTable userTable;
    private JScrollPane userScrollPane;

    private JPanel transactionsperuserpanel;
    private JComboBox userBox;
    private JScrollPane transactionsperuserScrollPanel;
    private JTable transactionsperuserTable;

    private JPanel logoutPanel;

    private final AuthService authService;
    private final TransactionService transactionService;

    private List<User> allUsers;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    AdminDashboard() {

        authService = new AuthService();
        transactionService = new TransactionService();

        setTitle("MLBB - Admin Dashboard");
        setContentPane(adminPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        logoutBtn.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        logoutBtn.addActionListener(e -> {

            new MainDashboard();
            dispose();

        });


        viewBox.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );

        viewBox.addActionListener(
                e -> updateViewPanel()
        );

        userBox.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );

        userBox.addActionListener(
                e -> loadTransactionsPerUser()
        );


        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/images/wallet.png"
                        )
                )
        );

        setIconImage(icon.getImage());


        ImageIcon backgroundIcon = new ImageIcon(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/images/background.png"
                        )
                )
        );

        Image backgroundImage =
                backgroundIcon.getImage();


        JPanel backgroundPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                g.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            }
        };


        backgroundPanel.setLayout(
                new BorderLayout()
        );

        backgroundPanel.add(
                adminPanel,
                BorderLayout.CENTER
        );

        adminPanel.setOpaque(false);

        setContentPane(backgroundPanel);

        setupTables();


        loadUsers();

        loadAdminName();

        updateViewPanel();


        setVisible(true);
    }

    private void loadAdminName() {

        List<User> users = allUsers;

        // If you have a logged-in admin User, use that User instead.
        // For now, find the admin account from the loaded users.

        if (users == null || users.isEmpty()) {
            hellonameLabel.setText("Hello");
            return;
        }

        for (User user : users) {

            if ("admin".equalsIgnoreCase(user.getRole())) {

                String firstName =
                        getFirstName(user.getName());

                if (firstName.isEmpty()) {
                    hellonameLabel.setText("Hello");
                } else {
                    hellonameLabel.setText(
                            "Hello, " + firstName
                    );
                }

                return;
            }
        }

        hellonameLabel.setText("Hello");
    }
    private String getTransactionDetails(
            Transaction transaction
    ) {

        String type = transaction.getType();
        String mobileNumber = transaction.getUserNumber();

        if (type == null) {
            return "";
        }

        switch (type.toUpperCase()) {

            case "CASH_IN":
                return "Cash In";

            case "TRANSFER":
                return "Transfer to " + mobileNumber;

            case "RECEIVED":
                return "Received from " + mobileNumber;

            default:
                return type;
        }
    }

    // =============================================================
    // TABLE SETUP
    // =============================================================

    private void setupTables() {

        Color blue = new Color(21, 101, 192);


        // ---------------------------------------------------------
        // ALL USERS TABLE
        // ---------------------------------------------------------

        userTable.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                                "Name",
                                "Email",
                                "Number",
                                "Balance"
                        }
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                }
        );


        userTable.getTableHeader()
                .setReorderingAllowed(false);

        userTable.getTableHeader()
                .setResizingAllowed(false);

        userTable.setRowHeight(25);


        // ---------------------------------------------------------
        // ALL TRANSACTIONS TABLE
        // ---------------------------------------------------------

        transactionsTable.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                                "Date",
                                "Name",
                                "Type",
                                "Amount"
                        }
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                }
        );


        transactionsTable.getTableHeader()
                .setReorderingAllowed(false);

        transactionsTable.getTableHeader()
                .setResizingAllowed(false);

        transactionsTable.setRowHeight(25);


        // ---------------------------------------------------------
        // TRANSACTIONS PER USER TABLE
        // ---------------------------------------------------------

        transactionsperuserTable.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                                "Date",
                                "Details",
                                "Amount"
                        }
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                }
        );


        transactionsperuserTable.getTableHeader()
                .setReorderingAllowed(false);

        transactionsperuserTable.getTableHeader()
                .setResizingAllowed(false);

        transactionsperuserTable.setRowHeight(25);


        // ---------------------------------------------------------
        // HEADER COLORS
        // ---------------------------------------------------------

        userTable.getTableHeader()
                .setBackground(blue);

        userTable.getTableHeader()
                .setForeground(Color.WHITE);


        transactionsTable.getTableHeader()
                .setBackground(blue);

        transactionsTable.getTableHeader()
                .setForeground(Color.WHITE);


        transactionsperuserTable.getTableHeader()
                .setBackground(blue);

        transactionsperuserTable.getTableHeader()
                .setForeground(Color.WHITE);
    }


    // =============================================================
    // LOAD ALL USERS
    // =============================================================

    private void loadUsers() {

        try {

            allUsers = authService.getAllUsers();

            DefaultTableModel model =
                    (DefaultTableModel)
                            userTable.getModel();

            model.setRowCount(0);

            for (User user : allUsers) {

                model.addRow(
                        new Object[]{
                                user.getName(),
                                user.getEmail(),
                                user.getNumber(),
                                String.format(
                                        "₱%.2f",
                                        user.getBalance()
                                )
                        }
                );
            }


            userBox.removeAllItems();

            for (User user : allUsers) {

                String firstName =
                        getFirstName(user.getName());

                userBox.addItem(
                        new UserComboItem(
                                user.getId(),
                                firstName
                        )
                );
            }

            // Load admin greeting
            loadAdminName();

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =============================================================
    // GET FIRST WORD OF NAME
    // =============================================================

    private String getFirstName(String fullName) {

        if (fullName == null ||
                fullName.trim().isEmpty()) {

            return "";
        }

        return fullName
                .trim()
                .split("\\s+")[0];
    }


    // =============================================================
    // UPDATE VIEW PANEL
    // =============================================================

    private void updateViewPanel() {

        String selected =
                String.valueOf(
                        viewBox.getSelectedItem()
                );


        // ---------------------------------------------------------
        // HIDE EVERYTHING
        // ---------------------------------------------------------

        usersPanel.setVisible(false);
        transactionsPanel.setVisible(false);
        transactionsperuserpanel.setVisible(false);

        setPanelEnabled(
                usersPanel,
                false
        );

        setPanelEnabled(
                transactionsPanel,
                false
        );

        setPanelEnabled(
                transactionsperuserpanel,
                false
        );


        userBox.setVisible(false);


        // ---------------------------------------------------------
        // VIEW ALL USERS
        // ---------------------------------------------------------

        if ("View All Users".equalsIgnoreCase(selected)) {

            usersPanel.setVisible(true);

            setPanelEnabled(
                    usersPanel,
                    true
            );

            loadUsers();
        }


        // ---------------------------------------------------------
        // VIEW ALL TRANSACTIONS
        // ---------------------------------------------------------

        else if ("View All Transactions"
                .equalsIgnoreCase(selected)) {

            transactionsPanel.setVisible(true);

            setPanelEnabled(
                    transactionsPanel,
                    true
            );

            loadAllTransactions();
        }


        // ---------------------------------------------------------
        // VIEW TRANSACTIONS PER USER
        // ---------------------------------------------------------

        else if ("View Transactions per user"
                .equalsIgnoreCase(selected)) {

            transactionsperuserpanel.setVisible(true);

            setPanelEnabled(
                    transactionsperuserpanel,
                    true
            );

            userBox.setVisible(true);

            loadTransactionsPerUser();
        }


        viewPanel.revalidate();
        viewPanel.repaint();
    }


    // =============================================================
    // LOAD ALL TRANSACTIONS
    // =============================================================

    private void loadAllTransactions() {

        try {

            List<Transaction> transactions =
                    transactionService
                            .getAllTransactions();


            DefaultTableModel model =
                    (DefaultTableModel)
                            transactionsTable.getModel();

            model.setRowCount(0);


            for (Transaction transaction :
                    transactions) {

                String userName =
                        getUserNameById(
                                transaction.getUserId()
                        );


                model.addRow(
                        new Object[]{
                                formatDate(transaction),
                                userName,
                                transaction.getType(),
                                String.format(
                                        "₱%.2f",
                                        transaction.getAmount()
                                )
                        }
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =============================================================
    // GET USER NAME FROM USER ID
    // =============================================================

    private String getUserNameById(int userId) {

        if (allUsers == null) {
            return "";
        }

        for (User user : allUsers) {

            if (user.getId() == userId) {

                return user.getName();
            }
        }

        return "";
    }


    // =============================================================
    // LOAD TRANSACTIONS PER USER
    // =============================================================

    private void loadTransactionsPerUser() {

        if (!transactionsperuserpanel.isVisible()) {
            return;
        }


        Object selected =
                userBox.getSelectedItem();


        if (!(selected instanceof UserComboItem)) {

            clearTransactionsPerUserTable();

            return;
        }


        UserComboItem selectedUser =
                (UserComboItem) selected;


        try {

            List<Transaction> transactions =
                    transactionService.getTransactions(
                            selectedUser.getUserId()
                    );


            DefaultTableModel model =
                    (DefaultTableModel)
                            transactionsperuserTable
                                    .getModel();

            model.setRowCount(0);


            for (Transaction transaction :
                    transactions) {

                model.addRow(
                        new Object[]{
                                formatDate(transaction),
                                getTransactionDetails(transaction),
                                String.format(
                                        "₱%.2f",
                                        transaction.getAmount()
                                )
                        }
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =============================================================
    // CLEAR TRANSACTIONS PER USER
    // =============================================================

    private void clearTransactionsPerUserTable() {

        DefaultTableModel model =
                (DefaultTableModel)
                        transactionsperuserTable
                                .getModel();

        model.setRowCount(0);
    }


    // =============================================================
    // FORMAT DATE
    // =============================================================

    private String formatDate(Transaction transaction) {

        if (transaction.getDate() == null) {
            return "";
        }

        return transaction
                .getDate()
                .format(DATE_FORMATTER);
    }


    // =============================================================
    // ENABLE / DISABLE PANEL
    // =============================================================

    private void setPanelEnabled(
            JPanel panel,
            boolean enabled
    ) {

        panel.setEnabled(enabled);

        for (Component component :
                panel.getComponents()) {

            component.setEnabled(enabled);

            if (component instanceof JPanel) {

                setPanelEnabled(
                        (JPanel) component,
                        enabled
                );
            }
        }
    }


    // =============================================================
    // USER COMBO BOX ITEM
    // =============================================================

    private static class UserComboItem {

        private final int userId;
        private final String firstName;


        public UserComboItem(
                int userId,
                String firstName
        ) {

            this.userId = userId;
            this.firstName = firstName;
        }


        public int getUserId() {
            return userId;
        }


        @Override
        public String toString() {
            return firstName;
        }
    }


    // =============================================================
    // MAIN
    // =============================================================

    public static void main(String[] args) {

        try {

            FlatDarkLaf.setup();

        } catch (Exception e) {

            System.err.println(
                    "Failed to initialize FlatLaf"
            );
        }


        SwingUtilities.invokeLater(
                AdminDashboard::new
        );
    }
}