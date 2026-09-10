package com.bankApp.bankingApp;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.util.Objects;
import java.awt.*;

public class MainDashboard extends JFrame {
    private JPanel mainPanel;
    private JLabel walleticonlabel;
    private JLabel titleText;
    private JLabel titleLine;
    private JLabel mlbbText;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JLabel disclaimerLabel;

    MainDashboard() {
        setTitle("MLBB - Main Dashboard");
        setContentPane(mainPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        loginBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JComponent.outline", new Color(21, 101, 192));

        signUpBtn.addActionListener(e -> {
            new RegistrationForm();
            dispose();
        });
        loginBtn.addActionListener(e -> {
            new LoginForm();
            dispose();
        });
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

        // Paint background on mainPanel
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

        // Put your existing components panel on top
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        // Make the existing panel transparent
        mainPanel.setOpaque(false);

        // Use the new background panel as the content pane
        setContentPane(backgroundPanel);

        setVisible(true);

    }

    public static void main(String[] args) {

        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }

        //Thread-safe way to launch Swing GUIs
        SwingUtilities.invokeLater(MainDashboard::new);
    }
}
