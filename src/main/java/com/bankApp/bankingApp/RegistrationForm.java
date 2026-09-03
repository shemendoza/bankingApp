package com.bankApp.bankingApp;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RegistrationForm extends JFrame {
    private JLabel walleticonlabel;
    private JLabel mlbbText;
    private JLabel titleText;
    private JLabel signupLabel;
    private JButton signUpBtn;
    private JButton loginBtn;
    private JPanel registerPanel;
    private JPanel textPanel;
    private JTextField fullnameField;
    private JLabel fullnameLabel;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel mobilenumLabel;
    private JPanel contentPanel;
    private JLabel mpinLabel;
    private JLabel confirmmpinLabel;
    private JTextField mobilenumField;
    private JTextField mpinField;
    private JTextField confirmmpinField;
    private JButton mainBtn;

    RegistrationForm() {
        setTitle("MLBB - Main Dashboard");
        setContentPane(registerPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        loginBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JButton.buttonType", "roundRect");
        mainBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        fullnameField.putClientProperty("JComponent.roundRect", true);
        fullnameField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        emailField.putClientProperty("JComponent.roundRect", true);
        emailField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mobilenumField.putClientProperty("JComponent.roundRect", true);
        mobilenumField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mpinField.putClientProperty("JComponent.roundRect", true);
        mpinField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        confirmmpinField.putClientProperty("JComponent.roundRect", true);
        confirmmpinField.putClientProperty("JComponent.outline", new Color(21, 101, 192));

        mainBtn.addActionListener(e -> {
            new MainDashboard();
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

        backgroundPanel.add(registerPanel, BorderLayout.CENTER);

        registerPanel.setOpaque(false);

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
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}

