package com.bankApp.bankingApp;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginForm extends JFrame {
    private JLabel walleticonlabel;
    private JPanel textPanel;
    private JLabel mlbbText;
    private JLabel titleText;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JPanel contentPanel;
    private JLabel mobilenumLabel;
    private JLabel mpinLabel;
    private JTextField mobilenumField;
    private JTextField mpinField;
    private JLabel loginLabel;
    private JButton mainBtn;
    private JPanel loginPanel;
    private JButton forgotBtn;

    LoginForm() {
        setTitle("MLBB - Login");
        setContentPane(loginPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        loginBtn.putClientProperty("JButton.buttonType", "roundRect");
        forgotBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JButton.buttonType", "roundRect");
        mainBtn.putClientProperty("JButton.buttonType", "roundRect");
        signUpBtn.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mobilenumField.putClientProperty("JComponent.roundRect", true);
        mobilenumField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mpinField.putClientProperty("JComponent.roundRect", true);
        mpinField.putClientProperty("JComponent.outline", new Color(21, 101, 192));
        mainBtn.addActionListener(e -> {
            new MainDashboard();
            dispose();
        });
        signUpBtn.addActionListener(e -> {
            new RegistrationForm();
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

        backgroundPanel.add(loginPanel, BorderLayout.CENTER);

        loginPanel.setOpaque(false);

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
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
