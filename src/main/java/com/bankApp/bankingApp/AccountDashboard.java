package com.bankApp.bankingApp;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.*;
import java.util.Objects;


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
    private JLabel accountNumLabel;
    private JLabel accountnumberText;
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
    private JLabel quoteText;



    AccountDashboard() {


        setTitle("MLBB - Account Dashboard");
        setContentPane(accountPanel);
        setSize(380, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        historyBtn.putClientProperty("JButton.buttonType", "roundRect");
        recentBtn.putClientProperty("JButton.buttonType", "roundRect");

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

        addTextOutline(quoteText, Color.black, 1);


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

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }

        //Thread-safe way to launch Swing GUIs
        SwingUtilities.invokeLater(AccountDashboard::new);
    }


}
