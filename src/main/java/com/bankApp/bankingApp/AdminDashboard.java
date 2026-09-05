package com.bankApp.bankingApp;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
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
    private JPanel transactionsperuserpanel;
    private JComboBox userBox;
    private JScrollPane transactionsperuserScrollPanel;
    private JTable transactionsperuserTable;
    private JTable userTable;
    private JScrollPane userScrollPane;
    private JPanel logoutPanel;


    AdminDashboard(){
    setTitle("MLBB - Admin Dashboard");
    setContentPane(adminPanel);
    setSize(380, 600);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);


    logoutBtn.putClientProperty("JButton.buttonType", "roundRect");

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
        // View selection listener
        viewBox.addActionListener(e -> updateViewPanel());

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

        backgroundPanel.add(adminPanel, BorderLayout.CENTER);

        adminPanel.setOpaque(false);

    setContentPane(backgroundPanel);

        updateViewPanel();


    setVisible(true);
}
    private void updateViewPanel() {

        String selected = String.valueOf(viewBox.getSelectedItem());

        // Hide all panels
        usersPanel.setVisible(false);
        transactionsPanel.setVisible(false);
        transactionsperuserpanel.setVisible(false);

        // Disable all panels
        setPanelEnabled(usersPanel, false);
        setPanelEnabled(transactionsPanel, false);
        setPanelEnabled(transactionsperuserpanel, false);

        // Hide userBox by default
        userBox.setVisible(false);


        // View All Users
        if ("View All Users".equalsIgnoreCase(selected)) {

            usersPanel.setVisible(true);
            setPanelEnabled(usersPanel, true);

        }

        // View All Transactions
        else if ("View All Transactions".equalsIgnoreCase(selected)) {

            transactionsPanel.setVisible(true);
            setPanelEnabled(transactionsPanel, true);

        }

        // View Transactions per user
        else if ("View Transactions per user".equalsIgnoreCase(selected)) {

            transactionsperuserpanel.setVisible(true);
            setPanelEnabled(transactionsperuserpanel, true);

            userBox.setVisible(true);
        }


        viewPanel.revalidate();
        viewPanel.repaint();
    }
    private void setPanelEnabled(JPanel panel, boolean enabled) {

        panel.setEnabled(enabled);

        for (Component component : panel.getComponents()) {

            component.setEnabled(enabled);

            if (component instanceof JPanel) {
                setPanelEnabled(
                        (JPanel) component,
                        enabled
                );
            }
        }
    }

public static void main(String[] args) {
    try {
        FlatDarkLaf.setup();
    } catch (Exception e) {
        System.err.println("Failed to initialize FlatLaf");
    }

    //Thread-safe way to launch Swing GUIs
    SwingUtilities.invokeLater(AdminDashboard::new);
}


}

