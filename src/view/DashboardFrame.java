package view;

import controller.KeuanganController;
import models.Keuangan;
import models.User;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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

        JPanel panelBooks = createCategoryPanel("BOOKS", booksTotal);
        JPanel panelGrocery = createCategoryPanel("GROCERY", groceryTotal);
        JPanel panelClothes = createCategoryPanel("CLOTHES", clothesTotal);
        JPanel panelGadgets = createCategoryPanel("GADGETS", gadgetsTotal);

        topPanel.add(panelBooks);
        topPanel.add(panelGrocery);
        topPanel.add(panelClothes);
        topPanel.add(panelGadgets);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Transaction History
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel lblHistory = new JLabel("Recent Transactions History", SwingConstants.CENTER);
        lblHistory.setForeground(Color.WHITE);

        String[] columnNames = {"Purpose", "Money", "Date", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblHistory = new JTable(tableModel);
        tblHistory.setBackground(Color.decode("#14213D"));
        tblHistory.setForeground(Color.decode("#E5E5E5"));
        tblHistory.setGridColor(Color.decode("#FCA311"));
        tblHistory.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tblHistory.setRowHeight(25);

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setOpaque(false);
        historyPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FCA311"), 2, true));
        historyPanel.add(new JScrollPane(tblHistory), BorderLayout.CENTER);

        centerPanel.add(lblHistory, BorderLayout.NORTH);
        centerPanel.add(historyPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Right Panel: Add Expense
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
        btnAddExpense.addActionListener(e -> { //? Sukses connect db 
            String purpose = txtPurpose.getText();
            double money;
            String date = txtDate.getText();
            String category = "";
        
            // Menentukan kategori yang dipilih
            if (rbBooks.isSelected()) category = "Books";
            else if (rbGrocery.isSelected()) category = "Grocery";
            else if (rbClothes.isSelected()) category = "Clothes";
            else if (rbGadgets.isSelected()) category = "Gadgets";
        
            try {
                // Memastikan input uang valid
                money = Double.parseDouble(txtMoney.getText());
                if (money > user.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Mengurangi saldo pengguna
                user.setBalance(user.getBalance() - money);
                lblBalance.setText("Balance: Rp. " + user.getBalance());
        
                // Menambahkan transaksi ke dalam add_expenditure
                KeuanganController keuanganController = new KeuanganController();
                keuanganController.addExpense(user.getUsername(), purpose, money, date, category);
        
                // Memperbarui kategori di category_table
                keuanganController.updateCategoryTotal(user.getUsername(), category, money);
        
                // Menambahkan transaksi ke dalam tableModel (untuk ditampilkan di tabel)
                tableModel.addRow(new Object[]{
                    purpose,   // Menambahkan Purpose
                    money,     // Menambahkan Money
                    date,      // Menambahkan Date
                    category   // Menambahkan Category
                });
        
                // Reset field input setelah transaksi ditambahkan
                txtPurpose.setText("");
                txtMoney.setText("");
                txtDate.setText("");
                categoryGroup.clearSelection();
        
                // Memberikan feedback kepada pengguna
                JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            } catch (NumberFormatException ex) {
                // Menangani error jika input tidak valid
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }); //? sukses connect db
        

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

        loadCategoryTotals();
        loadTransactionHistory();

        setVisible(true);
    }

    private void loadCategoryTotals() {
        KeuanganController keuanganController = new KeuanganController();
        booksTotal = keuanganController.getTotalByCategory("Books", user);
        groceryTotal = keuanganController.getTotalByCategory("Grocery", user);
        clothesTotal = keuanganController.getTotalByCategory("Clothes", user);
        gadgetsTotal = keuanganController.getTotalByCategory("Gadgets", user);

        updateCategoryPanel(createCategoryPanel("BOOKS", booksTotal), "BOOKS", booksTotal);
    }

    private void loadTransactionHistory() {
        KeuanganController controller = new KeuanganController();
        List<Keuangan> transaksi = controller.getTransaksiByUsername(user.getUsername());
        tableModel.setRowCount(0); // Clear existing rows
        for (Keuangan transaksiItem : transaksi) {
            tableModel.addRow(new Object[]{
                transaksiItem.getPurpose(),
                transaksiItem.getMoney(),
                transaksiItem.getDate(),
                transaksiItem.getCategory()
            });
        }
    }

    private JPanel createCategoryPanel(String title, double total) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#14213D"));
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#FCA311"), 2, true));
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel lblTotal = new JLabel("Rp. " + total, SwingConstants.CENTER);
        lblTotal.setForeground(Color.decode("#FCA311"));
        lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblTotal, BorderLayout.CENTER);
        return panel;
    }

    private void updateCategoryPanel(JPanel panel, String title, double total) {
        JLabel lblTitle = (JLabel) panel.getComponent(0);
        JLabel lblTotal = (JLabel) panel.getComponent(1);
        lblTitle.setText(title);
        lblTotal.setText("Rp. " + total);
    }

    private JTextField createRoundedTextField() {
        JTextField textField = new JTextField();
        textField.setBorder(new EmptyBorder(5, 10, 5, 10));
        textField.setBackground(Color.decode("#E5E5E5"));
        textField.setForeground(Color.decode("#0a1d37"));
        return textField;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#FCA311"));
        button.setForeground(Color.WHITE);
        button.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.decode("#FCA311"));
                g.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
            }
        });
        button.setFocusPainted(false);
        return button;
    }

    private JRadioButton createRoundedRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBackground(Color.decode("#0a1d37"));
        radioButton.setForeground(Color.WHITE);
        return radioButton;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text, JLabel.LEFT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }
}
