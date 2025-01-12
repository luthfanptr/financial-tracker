package view;

import models.User;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.geom.RoundRectangle2D;

public class DashboardFrame extends JFrame {
    private JLabel lblUsername, lblBalance;
    private JTextField txtAmount, txtPurpose, txtMoney, txtDate;
    private JRadioButton rbBooks, rbGrocery, rbClothes, rbGadgets;
    private JTable tblHistory;
    private DefaultTableModel tableModel;
    private JButton btnAddMoney;
    private double booksTotal = 0, groceryTotal = 0, clothesTotal = 0, gadgetsTotal = 0;
    private User user;

    public DashboardFrame(User user) {
        this.user = user;
        setTitle("Personal Finance Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color to dark blue
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(Color.decode("#0a1d37")); // Dark blue
        setContentPane(backgroundPanel);

        // Custom rounded border
        //!AbstractBorder roundedBorder = new RoundedBorder(15);

        // Left Panel: Profile, Balance, Add Money
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPanel.setOpaque(false);

        lblUsername = new JLabel("Username: " + user.getUsername());
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("SansSerif", Font.BOLD, 16));

        lblBalance = new JLabel("Balance: Rp. " + user.getBalance());
        lblBalance.setForeground(Color.WHITE);
        lblBalance.setFont(new Font("SansSerif", Font.BOLD, 16));

        txtAmount = createRoundedTextField();
        btnAddMoney = createRoundedButton("Add Money");

        btnAddMoney.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(txtAmount.getText());
                user.setBalance(user.getBalance() + amount);
                lblBalance.setText("Balance: Rp. " + user.getBalance());
                txtAmount.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        leftPanel.add(createSectionLabel("PROFILE"));
        leftPanel.add(lblUsername);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createSectionLabel("MY BALANCE"));
        leftPanel.add(lblBalance);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(createSectionLabel("ENTER THE AMOUNT"));
        leftPanel.add(txtAmount);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnAddMoney);

        add(leftPanel, BorderLayout.WEST);

        // Top Panel: Total Expenditure by Category
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows for categories
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        topPanel.setOpaque(false);

        // Create and add category panels with rounded borders
        JPanel panelBooks = createCategoryPanel("BOOKS", booksTotal);
        JPanel panelGrocery = createCategoryPanel("GROCERY", groceryTotal);
        JPanel panelClothes = createCategoryPanel("CLOTHES", clothesTotal);
        JPanel panelGadgets = createCategoryPanel("GADGETS", gadgetsTotal);

        topPanel.add(panelBooks);
        topPanel.add(panelGrocery);
        topPanel.add(panelClothes);
        topPanel.add(panelGadgets);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Transaction History with Border and Gradient Background
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // Label above the border
        JLabel lblHistory = new JLabel("Recent Transactions History", SwingConstants.CENTER);
        lblHistory.setForeground(Color.WHITE);

        String[] columnNames = {"Purpose", "Money", "Date", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblHistory = new JTable(tableModel);
        tblHistory.setBackground(Color.decode("#14213D")); // warna dasar history
        tblHistory.setForeground(Color.decode("#E5E5E5"));// warna text untuk history
        tblHistory.setGridColor(Color.decode("#FCA311")); // warna border history
        tblHistory.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tblHistory.setRowHeight(25);

        // Create a custom panel with gradient background
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setOpaque(false);
        historyPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FCA311"), 2, true)); // Oranye border untuk keseluruhan
        historyPanel.add(createGradientPanel(tblHistory), BorderLayout.CENTER); // Gradient background for the table

        centerPanel.add(lblHistory, BorderLayout.NORTH); // Label above the border
        centerPanel.add(historyPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Right Panel remains unchanged
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setOpaque(false);

        txtPurpose = createRoundedTextField();
        txtMoney = createRoundedTextField();
        txtDate = createRoundedTextField();

        rbBooks = createRoundedRadioButton("Books");
        rbGrocery = createRoundedRadioButton("Grocery");
        rbClothes = createRoundedRadioButton("Clothes");
        rbGadgets = createRoundedRadioButton("Gadgets");

        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(rbBooks);
        categoryGroup.add(rbGrocery);
        categoryGroup.add(rbClothes);
        categoryGroup.add(rbGadgets);

        JButton btnAddExpense = createRoundedButton("Add Expense");
        btnAddExpense.addActionListener(e -> {
            String purpose = txtPurpose.getText();
            double money;
            String date = txtDate.getText();
            String category = "";

            if (rbBooks.isSelected()) category = "Books";
            else if (rbGrocery.isSelected()) category = "Grocery";
            else if (rbClothes.isSelected()) category = "Clothes";
            else if (rbGadgets.isSelected()) category = "Gadgets";

            try {
                money = Double.parseDouble(txtMoney.getText());
                if (money > user.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                user.setBalance(user.getBalance() - money);
                lblBalance.setText("Balance: Rp. " + user.getBalance());

                tableModel.addRow(new Object[]{purpose, money, date, category});

                switch (category) {
                    case "Books" -> booksTotal += money;
                    case "Grocery" -> groceryTotal += money;
                    case "Clothes" -> clothesTotal += money;
                    case "Gadgets" -> gadgetsTotal += money;
                }

                // Update category panels with the new totals
                updateCategoryPanel(panelBooks, "BOOKS", booksTotal);
                updateCategoryPanel(panelGrocery, "GROCERY", groceryTotal);
                updateCategoryPanel(panelClothes, "CLOTHES", clothesTotal);
                updateCategoryPanel(panelGadgets, "GADGETS", gadgetsTotal);

                txtPurpose.setText("");
                txtMoney.setText("");
                txtDate.setText("");
                categoryGroup.clearSelection();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        rightPanel.add(createSectionLabel("Purpose:"));
        rightPanel.add(txtPurpose);
        rightPanel.add(createSectionLabel("Money:"));
        rightPanel.add(txtMoney);
        rightPanel.add(createSectionLabel("Date:"));
        rightPanel.add(txtDate);
        rightPanel.add(createSectionLabel("Category:"));
        rightPanel.add(rbBooks);
        rightPanel.add(rbGrocery);
        rightPanel.add(rbClothes);
        rightPanel.add(rbGadgets);
        rightPanel.add(btnAddExpense);

        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel createCategoryPanel(String category, double total) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new RoundedBorder(15));
        
        JLabel lblCategory = new JLabel(category);
        lblCategory.setForeground(Color.WHITE);
        lblCategory.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JLabel lblTotal = new JLabel("Rp. " + total);
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        panel.add(lblCategory);
        panel.add(lblTotal);
        
        return panel;
    }

    private void updateCategoryPanel(JPanel panel, String category, double total) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel lbl = (JLabel) comp;
                if (lbl.getText().startsWith("Rp.")) {
                    lbl.setText("Rp. " + total);
                    break;
                }
            }
        }
    }

    private JTextField createRoundedTextField() {
        JTextField textField = new JTextField(10);
        textField.setBorder(new RoundedBorder(15));
        textField.setMargin(new Insets(5, 10, 5, 10));
        textField.setPreferredSize(new Dimension(200, 40));
        return textField;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setBorder(new RoundedBorder(15));
        button.setBackground(Color.decode("#FCA311"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private JRadioButton createRoundedRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBackground(Color.decode("#1B263B"));
        radioButton.setForeground(Color.decode("#FCA311"));
        return radioButton;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    private JPanel createGradientPanel(JTable table) {
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#1D3557"), 0, getHeight(), Color.decode("#14213D"));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        gradientPanel.setPreferredSize(new Dimension(600, 300));
        return gradientPanel;
    }

    public static void main(String[] args) {
        User user = new User(1, "user", "password", 500000);
        new DashboardFrame(user);
    }

    // Custom Border for Rounded Corners
    class RoundedBorder extends AbstractBorder {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.decode("#FCA311"));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }
    }
}
