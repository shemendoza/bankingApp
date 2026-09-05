package com.bankApp.bankingApp;

import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.service.AuthService;
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

    private final AuthService authService = new AuthService();

    RegistrationForm() {
        setTitle("MLBB - Sign Up");
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
        loginBtn.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        signUpBtn.addActionListener(e -> registerUser());

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

        addTextOutline(fullnameLabel, new Color(0,60,170), 1);
        addTextOutline(emailLabel, new Color(0,60,170), 1);
        addTextOutline(mobilenumLabel, new Color(0,60,170), 1);
        addTextOutline(mpinLabel, new Color(0,60,170), 1);
        addTextOutline(confirmmpinLabel, new Color(0,60,170), 1);

        setVisible(true);
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
    private void registerUser() {

        String fullName = fullnameField.getText().trim();
        String email = emailField.getText().trim();
        String mobileNumber = mobilenumField.getText().trim();
        String mpin = mpinField.getText().trim();
        String confirmMpin = confirmmpinField.getText().trim();

        // Check empty fields
        if (fullName.isEmpty()
                || email.isEmpty()
                || mobileNumber.isEmpty()
                || mpin.isEmpty()
                || confirmMpin.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Check email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            emailField.requestFocus();
            return;
        }

        // Check mobile number
        if (!mobileNumber.matches("\\d{11}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Mobile number must contain exactly 11 digits.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            mobilenumField.requestFocus();
            return;
        }

        // Check MPIN
        if (!mpin.matches("\\d{4}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "MPIN must contain exactly 4 digits.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            mpinField.requestFocus();
            return;
        }

        // Check matching MPIN
        if (!mpin.equals(confirmMpin)) {

            JOptionPane.showMessageDialog(
                    this,
                    "MPIN and Confirm MPIN do not match.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            confirmmpinField.requestFocus();
            return;
        }

        // Create User object
        User user = new User(
                fullName,
                mobileNumber,
                email,
                mpin,
                "user"
        );

        try {

            String result = authService.register(user);

            if ("SUCCESS".equals(result)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                fullnameField.setText("");
                emailField.setText("");
                mobilenumField.setText("");
                mpinField.setText("");
                confirmmpinField.setText("");

                new LoginForm();
                dispose();

            } else if ("EMAIL_EXISTS".equals(result)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This email is already registered.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

                emailField.requestFocus();

            } else if ("MOBILE_EXISTS".equals(result)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This mobile number is already registered.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

                mobilenumField.requestFocus();
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed:\n" + e.getMessage(),
                    "Database Error",
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

        //Thread-safe way to launch Swing GUIs
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}

