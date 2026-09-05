package com.bankApp.bankingApp;

import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.service.AuthService;
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

    private final AuthService authService = new AuthService();

    private int loginAttempts = 0;

    private static final int MAX_ATTEMPTS = 3;

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

        loginBtn.addActionListener(e -> loginUser());

        forgotBtn.addActionListener(e -> forgotMpin());
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
        addTextOutline(mobilenumLabel, new Color(0,60,170), 1);
        addTextOutline(mpinLabel, new Color(0,60,170), 1);

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

    private void loginUser() {

        // Already locked
        if (loginAttempts >= MAX_ATTEMPTS) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have exceeded the maximum number of login attempts.",
                    "Login Locked",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String mobileNumber = mobilenumField.getText().trim();
        String pin = mpinField.getText().trim();

        // Empty fields
        if (mobileNumber.isEmpty() || pin.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your mobile number and MPIN.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Validate mobile number
        if (!mobileNumber.matches("\\d{11}")) {

            loginAttempts++;

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid mobile number.\n\n" +
                            "Attempts remaining: "
                            + (MAX_ATTEMPTS - loginAttempts),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            checkLoginAttempts();
            return;
        }

        // Validate MPIN
        if (!pin.matches("\\d{4}")) {

            loginAttempts++;

            JOptionPane.showMessageDialog(
                    this,
                    "MPIN must contain exactly 4 digits.\n\n" +
                            "Attempts remaining: "
                            + (MAX_ATTEMPTS - loginAttempts),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            checkLoginAttempts();
            return;
        }

        try {

            User user = authService.login(
                    mobileNumber,
                    pin
            );

            if (user != null) {

                if ("admin".equalsIgnoreCase(user.getRole())) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful!",
                            "Welcome Admin",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    new AdminDashboard();
                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful!",
                            "Welcome",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    new AccountDashboard();
                }






                dispose();

            } else {

                loginAttempts++;

                if (loginAttempts >= MAX_ATTEMPTS) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Incorrect mobile number or MPIN.\n\n" +
                                    "You have reached the maximum of 3 attempts.\n" +
                                    "Login is now blocked for this session.",
                            "Login Locked",
                            JOptionPane.ERROR_MESSAGE
                    );

                    loginBtn.setEnabled(false);
                    mobilenumField.setEnabled(false);
                    mpinField.setEnabled(false);

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Incorrect mobile number or MPIN.\n\n" +
                                    "Attempts remaining: "
                                    + (MAX_ATTEMPTS - loginAttempts),
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login failed:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    private void checkLoginAttempts() {

        if (loginAttempts >= MAX_ATTEMPTS) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have reached the maximum of 3 failed attempts.\n" +
                            "Login is now blocked for this session.",
                    "Login Locked",
                    JOptionPane.ERROR_MESSAGE
            );

            loginBtn.setEnabled(false);
            mobilenumField.setEnabled(false);
            mpinField.setEnabled(false);
        }
    }

    private void forgotMpin() {

        JTextField emailInput = new JTextField();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        panel.add(
                new JLabel("Enter your registered email:"),
                BorderLayout.NORTH
        );

        panel.add(
                emailInput,
                BorderLayout.CENTER
        );

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Forgot MPIN",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String email = emailInput.getText().trim();

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email address.",
                    "Forgot MPIN",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            String mpin = authService.getMpinByEmail(email);

            if (mpin != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Your MPIN is: " + mpin,
                        "MPIN Recovery",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No account was found with that email address.",
                        "Forgot MPIN",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to retrieve MPIN:\n" + e.getMessage(),
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
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
