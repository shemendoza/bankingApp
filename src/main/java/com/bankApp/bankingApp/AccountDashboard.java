package com.bankApp.bankingApp;

import com.bankApp.bankingApp.model.Transaction;
import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.service.TransactionService;
import com.bankApp.bankingApp.service.AuthService;
import com.bankApp.bankingApp.service.TransferService;
import com.bankApp.bankingApp.service.CashInService;
import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.*;
import java.util.Objects;
import java.util.List;

public class AccountDashboard extends JFrame {
    private JPanel accountPanel;
    private JPanel bottombuttonsPanel;
    private JButton mainBtn;
    private JPanel welcomePanel;
    private JLabel hellonameLabel;
    private JLabel welcomeLabel;
    private JPanel balancePanel;
    private JLabel availablebalanceLabel;
    private JLabel balanceText;
    private JLabel balanceicon;
    private JButton cashinBtn;
    private JButton cashoutBtn;
    private JButton profileBtn;
    private JTable recentTable;
    private JLabel recentLabel;
    private JPanel recenttransactionsPanel;
    private JScrollPane recentScrollPanel;
    private JPanel contentPanel;
    private JButton historyBtn;
    private JPanel mainPanel;
    private JPanel alltransactionsPanel;
    private JLabel historyLabel;
    private JButton recentBtn;
    private JScrollPane historyScrollPanel;
    private JTable historyTable;
    private JPanel cashinPanel;
    private JLabel cashinLabel;
    private JLabel cashinamountLabel;
    private JLabel errorcashinlabel;
    private JPanel maincashinPanel;
    private JTextField cashinamountField;
    private JButton submitcashinBtn;
    private JButton cancelcashinBtn;
    private JPanel maincashoutPanel;
    private JPanel cashoutPanel;
    private JLabel cashoutLabel;
    private JLabel mobileLabel;
    private JTextField mobileField;
    private JLabel errorcashoutLabel;
    private JButton submitcashoutBtn;
    private JButton cancelcashoutBtn;
    private JLabel cashoutamountLabel;
    private JTextField cashoutamountField;
    private JPanel mainprofilePanel;
    private JPanel profilePanel;
    private JLabel profileLabel;
    private JLabel nameLabel;
    private JLabel numberLabel;
    private JLabel emailLabel;
    private JLabel nameText;
    private JLabel numberText;
    private JButton changempinBtn;
    private JLabel emailText;
    private JButton logoutBtn;

    private final User user;
    private final TransactionService transactionService;
    private final AuthService authService;
    private final TransferService transferService;
    private final CashInService cashInService;

    AccountDashboard(User user) {
        this.user = user;
        this.transactionService = new TransactionService();
        this.authService = new AuthService();
        this.transferService = new TransferService();
        this.cashInService = new CashInService();

        setTitle("MLBB - Account Dashboard");
        setContentPane(accountPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        historyBtn.putClientProperty("JButton.buttonType", "roundRect");
        recentBtn.putClientProperty("JButton.buttonType", "roundRect");
        Color blue = new Color(21, 101, 192);

        recentTable.getTableHeader().setBackground(blue);
        recentTable.getTableHeader().setForeground(Color.WHITE);

        historyTable.getTableHeader().setBackground(blue);
        historyTable.getTableHeader().setForeground(Color.WHITE);

        //cash in panel
        cashinamountField.putClientProperty("JComponent.roundRect", true);
        cashinamountField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        submitcashinBtn.putClientProperty("JButton.buttonType", "roundRect");
        cancelcashinBtn.putClientProperty("JButton.buttonType", "roundRect");
        addTextOutline(cashinamountLabel, new Color(0,60,170), 1);

        //cash out panel
        cashoutamountField.putClientProperty("JComponent.roundRect", true);
        cashoutamountField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mobileField.putClientProperty("JComponent.roundRect", true);
        mobileField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        submitcashoutBtn.putClientProperty("JButton.buttonType", "roundRect");
        cancelcashoutBtn.putClientProperty("JButton.buttonType", "roundRect");
        addTextOutline(cashoutamountLabel, new Color(0,60,170), 1);
        addTextOutline(mobileLabel, new Color(0,60,170), 1);

        //profile panel
        changempinBtn.putClientProperty("JButton.buttonType", "roundRect");
        logoutBtn.putClientProperty("JButton.buttonType", "roundRect");
        addTextOutline(nameLabel, new Color(0,60,170), 1);
        addTextOutline(emailLabel, new Color(0,60,170), 1);
        addTextOutline(numberLabel, new Color(0,60,170), 1);

        logoutBtn.addActionListener(e -> {
            new MainDashboard();
            dispose();
        });

        changempinBtn.addActionListener(e -> changeMpin());

        submitcashinBtn.addActionListener(e -> processCashIn());

        cancelcashinBtn.addActionListener(e -> {
            cashinamountField.setText("");
            errorcashinlabel.setText("");
        });

        submitcashoutBtn.addActionListener(
                e -> processTransfer()
        );

        cancelcashoutBtn.addActionListener(e -> {
            mobileField.setText("");
            cashoutamountField.setText("");
            errorcashoutLabel.setText("");
        });

        balancePanel.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );
        recenttransactionsPanel.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );
        alltransactionsPanel.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );

        bottombuttonsPanel.setBorder(
                new FlatLineBorder(
                        new Insets(1, 1, 1, 1),
                        new Color(21, 101, 192),
                        1,
                        20
                )
        );
        // Window icon
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(
                        getClass().getResource("/images/wallet.png")
                )
        );
        setIconImage(icon.getImage());

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(
                Objects.requireNonNull(
                        getClass().getResource("/images/background.png")
                )
        );

        Image backgroundImage = backgroundIcon.getImage();

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

        backgroundPanel.setLayout(new BorderLayout());

        backgroundPanel.add(accountPanel, BorderLayout.CENTER);

        accountPanel.setOpaque(false);

        setContentPane(backgroundPanel);



        mainBtn.addActionListener(e -> {
            selectButton(mainBtn);
            showPanel(mainPanel);
        });

        cashinBtn.addActionListener(e -> {
            selectButton(cashinBtn);
            showPanel(maincashinPanel);
        });

        cashoutBtn.addActionListener(e -> {
            selectButton(cashoutBtn);
            showPanel(maincashoutPanel);
        });

        profileBtn.addActionListener(e -> {
            selectButton(profileBtn);
            showPanel(mainprofilePanel);
        });

        historyBtn.addActionListener(e -> {
            recenttransactionsPanel.setVisible(false);
            alltransactionsPanel.setVisible(true);
        });

        recentBtn.addActionListener(e -> {
            recenttransactionsPanel.setVisible(true);
            alltransactionsPanel.setVisible(false);
        });
        setupTables();
        loadUserDetails();
        loadTransactions();

        setupCashInField();
        setupTransferFields();

        setVisible(true);
    }
    private void selectButton(JButton selectedButton) {

        mainBtn.setSelected(false);
        cashinBtn.setSelected(false);
        cashoutBtn.setSelected(false);
        profileBtn.setSelected(false);

        selectedButton.setSelected(true);
    }
    private void showPanel(JPanel panelToShow) {

        mainPanel.setVisible(false);
        maincashinPanel.setVisible(false);
        maincashoutPanel.setVisible(false);
        mainprofilePanel.setVisible(false);

        mainPanel.setEnabled(false);
        maincashinPanel.setEnabled(false);
        maincashoutPanel.setEnabled(false);
        mainprofilePanel.setEnabled(false);

        panelToShow.setVisible(true);

        setPanelEnabled(panelToShow, true);
    }
    private void addTextOutline(JLabel label, Color outlineColor, float thickness) {

        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {

            @Override
            public void paint(Graphics g, JComponent c) {

                JLabel lbl = (JLabel) c;

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );

                /*
                 * Paint the icon normally.
                 */
                Icon icon = lbl.getIcon();

                if (icon != null) {

                    int iconX = 0;
                    int iconY = (lbl.getHeight() - icon.getIconHeight()) / 2;

                    switch (lbl.getHorizontalAlignment()) {

                        case SwingConstants.CENTER:
                            iconX = (lbl.getWidth() - icon.getIconWidth()) / 2;
                            break;

                        case SwingConstants.RIGHT:
                            iconX = lbl.getWidth() - icon.getIconWidth();
                            break;

                        default:
                            iconX = 0;
                            break;
                    }

                    icon.paintIcon(lbl, g2, iconX, iconY);
                }


                /*
                 * Get text.
                 */
                String text = lbl.getText();

                if (text == null || text.isEmpty()) {
                    g2.dispose();
                    return;
                }


                Font font = lbl.getFont();
                FontMetrics fm = g2.getFontMetrics(font);

                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x;

                /*
                 * Position text.
                 */
                if (lbl.getIcon() != null) {

                    // Leave space for the icon
                    int gap = lbl.getIconTextGap();

                    int iconWidth = lbl.getIcon().getIconWidth();

                    x = iconWidth + gap;

                } else {

                    switch (lbl.getHorizontalAlignment()) {

                        case SwingConstants.CENTER:
                            x = (lbl.getWidth() - textWidth) / 2;
                            break;

                        case SwingConstants.RIGHT:
                            x = lbl.getWidth() - textWidth;
                            break;

                        default:
                            x = 0;
                            break;
                    }
                }


                int y =
                        (lbl.getHeight() - textHeight) / 2
                                + fm.getAscent();


                g2.setFont(font);


                /*
                 * Draw outline.
                 */
                g2.setColor(outlineColor);

                for (float dx = -thickness;
                     dx <= thickness;
                     dx++) {

                    for (float dy = -thickness;
                         dy <= thickness;
                         dy++) {

                        if (dx != 0 || dy != 0) {

                            g2.drawString(
                                    text,
                                    (int) (x + dx),
                                    (int) (y + dy)
                            );
                        }
                    }
                }


                /*
                 * Draw normal text.
                 */
                g2.setColor(lbl.getForeground());

                g2.drawString(
                        text,
                        x,
                        y
                );


                g2.dispose();
            }
        });

        label.repaint();
    }
    private void setPanelEnabled(JPanel panel, boolean enabled) {
        panel.setEnabled(enabled);

        for (Component component : panel.getComponents()) {
            component.setEnabled(enabled);

            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, enabled);
            }
        }
    }

    private void loadUserDetails() {

        String fullName = user.getName();

        if (fullName != null && !fullName.trim().isEmpty()) {

            String firstName = fullName.trim().split("\\s+")[0];

            hellonameLabel.setText("Hello, " + firstName);

        } else {

            hellonameLabel.setText("Hello");
        }

        // Balance
        balanceText.setText(
                String.format("₱%,.2f", user.getBalance())
        );

        // Profile
        nameText.setText(user.getName());
        numberText.setText(user.getNumber());
        emailText.setText(user.getEmail());
    }
    private void setupTables() {

        recentTable.setModel(
                new javax.swing.table.DefaultTableModel(
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

        historyTable.setModel(
                new javax.swing.table.DefaultTableModel(
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

        recentTable.getTableHeader().setReorderingAllowed(false);
        recentTable.getTableHeader().setResizingAllowed(false);

        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setResizingAllowed(false);
        recentTable.setRowHeight(25);
        historyTable.setRowHeight(25);
    }
    private void loadTransactions() {

        List<Transaction> transactions =
                transactionService.getTransactions(user.getId());

        loadRecentTransactions(transactions);
        loadHistoryTransactions(transactions);
    }
    private void loadRecentTransactions(
            List<Transaction> transactions
    ) {

        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel)
                        recentTable.getModel();

        model.setRowCount(0);

        int limit = Math.min(5, transactions.size());

        for (int i = 0; i < limit; i++) {

            Transaction transaction = transactions.get(i);

            model.addRow(new Object[]{
                    formatDate(transaction),
                    getTransactionDetails(transaction),
                    String.format(
                            "₱%,.2f",
                            transaction.getAmount()
                    )
            });
        }
    }
    private String getTransactionDetails(Transaction transaction) {

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
    private void loadHistoryTransactions(
            List<Transaction> transactions
    ) {

        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel)
                        historyTable.getModel();

        model.setRowCount(0);

        for (Transaction transaction : transactions) {

            model.addRow(new Object[]{
                    formatDate(transaction),
                    getTransactionDetails(transaction),
                    String.format(
                            "₱%,.2f",
                            transaction.getAmount()
                    )
            });
        }
    }
    private String formatDate(Transaction transaction) {

        return transaction.getDate()
                .format(
                        java.time.format.DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss")
                );
    }


    private void changeMpin() {

        JPasswordField newMpinField = new JPasswordField();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        panel.add(
                new JLabel("Enter your new MPIN:"),
                BorderLayout.NORTH
        );

        panel.add(
                newMpinField,
                BorderLayout.CENTER
        );

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Change MPIN",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String newMpin =
                new String(newMpinField.getPassword()).trim();

        // Validate MPIN
        if (!newMpin.matches("\\d{4}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "MPIN must contain exactly 4 digits.",
                    "Invalid MPIN",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Ask for mobile number or email
        JTextField identifierField = new JTextField();

        JPanel verificationPanel =
                new JPanel(new BorderLayout(5, 5));

        verificationPanel.add(
                new JLabel("Enter your mobile number or email:"),
                BorderLayout.NORTH
        );

        verificationPanel.add(
                identifierField,
                BorderLayout.CENTER
        );

        int verificationResult = JOptionPane.showConfirmDialog(
                this,
                verificationPanel,
                "Verify Account",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (verificationResult != JOptionPane.OK_OPTION) {
            return;
        }

        String identifier =
                identifierField.getText().trim();

        if (identifier.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your mobile number or email.",
                    "Verification Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            boolean changed = authService.changeMpin(
                    user.getId(),
                    identifier,
                    newMpin
            );

            if (changed) {

                JOptionPane.showMessageDialog(
                        this,
                        "Your MPIN has been changed successfully.",
                        "MPIN Changed",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "The mobile number or email does not match your account.",
                        "Verification Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to change MPIN:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    private void setupCashInField() {

        ((javax.swing.text.AbstractDocument)
                cashinamountField.getDocument())
                .setDocumentFilter(
                        new javax.swing.text.DocumentFilter() {

                            @Override
                            public void insertString(
                                    javax.swing.text.DocumentFilter.FilterBypass fb,
                                    int offset,
                                    String string,
                                    javax.swing.text.AttributeSet attr)
                                    throws javax.swing.text.BadLocationException {

                                if (string != null
                                        && string.matches("\\d*")) {

                                    super.insertString(
                                            fb,
                                            offset,
                                            string,
                                            attr
                                    );
                                }
                            }

                            @Override
                            public void replace(
                                    javax.swing.text.DocumentFilter.FilterBypass fb,
                                    int offset,
                                    int length,
                                    String text,
                                    javax.swing.text.AttributeSet attrs)
                                    throws javax.swing.text.BadLocationException {

                                if (text != null
                                        && text.matches("\\d*")) {

                                    super.replace(
                                            fb,
                                            offset,
                                            length,
                                            text,
                                            attrs
                                    );
                                }
                            }
                        }
                );
    }
    private void processCashIn() {

        String amountText =
                cashinamountField.getText().trim();

        if (amountText.isEmpty()) {

            errorcashinlabel.setText(
                    "Please enter a cash-in amount."
            );

            cashinamountField.requestFocus();

            return;
        }

        double amount;

        try {

            amount = Double.parseDouble(amountText);

        } catch (NumberFormatException e) {

            errorcashinlabel.setText(
                    "Please enter a valid amount."
            );

            cashinamountField.requestFocus();

            return;
        }

        if (amount <= 0) {

            errorcashinlabel.setText(
                    "Amount must be greater than zero."
            );

            cashinamountField.requestFocus();

            return;
        }

        try {

            /*
             * CashInService handles:
             * 1. Database balance update
             * 2. Transaction insertion
             * 3. In-memory balance update
             */
            cashInService.cashIn(
                    user,
                    amount
            );

            /*
             * Update balance displayed on screen.
             */
            balanceText.setText(
                    String.format(
                            "₱%,.2f",
                            user.getBalance()
                    )
            );

            /*
             * Refresh transaction tables.
             */
            loadTransactions();

            /*
             * Clear fields.
             */
            cashinamountField.setText("");
            errorcashinlabel.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    String.format(
                            "Cash-in successful!\n\n" +
                                    "Amount: ₱%,.2f\n" +
                                    "New Balance: ₱%,.2f",
                            amount,
                            user.getBalance()
                    ),
                    "Cash In Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            errorcashinlabel.setText(
                    e.getMessage()
            );

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Cash-In Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (RuntimeException e) {

            errorcashinlabel.setText(
                    "Cash-in failed."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to process cash-in:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
    private void setupTransferFields() {

        javax.swing.text.DocumentFilter numbersOnlyFilter =
                new javax.swing.text.DocumentFilter() {

                    @Override
                    public void insertString(
                            javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset,
                            String string,
                            javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {

                        if (string != null
                                && string.matches("\\d*")) {

                            super.insertString(
                                    fb,
                                    offset,
                                    string,
                                    attr
                            );
                        }
                    }

                    @Override
                    public void replace(
                            javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset,
                            int length,
                            String text,
                            javax.swing.text.AttributeSet attrs)
                            throws javax.swing.text.BadLocationException {

                        if (text != null
                                && text.matches("\\d*")) {

                            super.replace(
                                    fb,
                                    offset,
                                    length,
                                    text,
                                    attrs
                            );
                        }
                    }
                };

        ((javax.swing.text.AbstractDocument)
                mobileField.getDocument())
                .setDocumentFilter(numbersOnlyFilter);

        ((javax.swing.text.AbstractDocument)
                cashoutamountField.getDocument())
                .setDocumentFilter(numbersOnlyFilter);
    }
    private void processTransfer() {

        String receiverMobile =
                mobileField.getText().trim();

        String amountText =
                cashoutamountField.getText().trim();

        /*
         * Validate mobile number.
         */
        if (receiverMobile.isEmpty()) {

            errorcashoutLabel.setText(
                    "Please enter the receiver's mobile number."
            );

            mobileField.requestFocus();

            return;
        }

        if (!receiverMobile.matches("\\d{11}")) {

            errorcashoutLabel.setText(
                    "Mobile number must contain exactly 11 digits."
            );

            mobileField.requestFocus();

            return;
        }

        /*
         * Prevent transferring to yourself.
         */
        if (receiverMobile.equals(user.getNumber())) {

            errorcashoutLabel.setText(
                    "You cannot transfer money to your own account."
            );

            mobileField.requestFocus();

            return;
        }

        /*
         * Validate amount.
         */
        if (amountText.isEmpty()) {

            errorcashoutLabel.setText(
                    "Please enter a transfer amount."
            );

            cashoutamountField.requestFocus();

            return;
        }

        double amount;

        try {

            amount = Double.parseDouble(amountText);

        } catch (NumberFormatException e) {

            errorcashoutLabel.setText(
                    "Please enter a valid amount."
            );

            cashoutamountField.requestFocus();

            return;
        }

        /*
         * Amount must be greater than zero.
         */
        if (amount <= 0) {

            errorcashoutLabel.setText(
                    "Amount must be greater than zero."
            );

            cashoutamountField.requestFocus();

            return;
        }

        /*
         * Check sender balance before processing.
         */
        if (amount > user.getBalance()) {

            errorcashoutLabel.setText(
                    "Insufficient balance."
            );

            JOptionPane.showMessageDialog(
                    this,
                    String.format(
                            "You cannot transfer ₱%,.2f.\n\n" +
                                    "Your current balance is ₱%,.2f.",
                            amount,
                            user.getBalance()
                    ),
                    "Insufficient Balance",
                    JOptionPane.ERROR_MESSAGE
            );

            cashoutamountField.requestFocus();

            return;
        }

        try {

            /*
             * Process transfer.
             */
            boolean success =
                    transferService.transfer(
                            user,
                            receiverMobile,
                            amount
                    );

            if (success) {

                /*
                 * Update displayed balance.
                 */
                balanceText.setText(
                        String.format(
                                "₱%,.2f",
                                user.getBalance()
                        )
                );

                /*
                 * Refresh recent and history transactions.
                 */
                loadTransactions();

                /*
                 * Clear fields.
                 */
                mobileField.setText("");
                cashoutamountField.setText("");
                errorcashoutLabel.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        String.format(
                                "Transfer successful!\n\n" +
                                        "To: %s\n" +
                                        "Amount: ₱%,.2f\n" +
                                        "Remaining Balance: ₱%,.2f",
                                receiverMobile,
                                amount,
                                user.getBalance()
                        ),
                        "Transfer Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (IllegalArgumentException e) {

            errorcashoutLabel.setText(
                    e.getMessage()
            );

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Transfer Failed",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (RuntimeException e) {

            errorcashoutLabel.setText(
                    "Transfer failed."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to process transfer:\n"
                            + e.getMessage(),
                    "Transfer Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {

            User testUser = new User(
                    1,
                    "She Mendoza",
                    "09123456789",
                    "she@gmail.com",
                    "1234",
                    5000.00,
                    "user"
            );

            new AccountDashboard(testUser);
        });
    }

}
