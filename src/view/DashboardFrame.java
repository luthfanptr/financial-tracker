package view;

import models.User;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardFrame extends JFrame {
    private JLabel lblUsername, lblBalance;
    private JTextField txtAmount, txtPurpose, txtMoney, txtDate;
    private JRadioButton rbBooks, rbGrocery, rbClothes, rbGadgets;
    private JTable tblHistory;
    private DefaultTableModel tableModel;
    private JButton btnAddMoney, btnAddExpense;
    private double booksTotal = 0, groceryTotal = 0, clothesTotal = 0, gadgetsTotal = 0;
    private User user;

    public DashboardFrame(User user) {
        this.user = user;
        setTitle("Personal Finance Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(Color.decode("#87CEFA"));

        // Custom rounded border
        AbstractBorder roundedBorder = new RoundedBorder(15);

        // Panel kiri (Profile, Balance, Add Money)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Color.decode("#87CEFA"));

        lblUsername = new JLabel("Username: " + user.getUsername());
        lblBalance = new JLabel("Balance: Rp. " + user.getBalance());
        txtAmount = new JTextField(10);
        txtAmount.setBorder(roundedBorder);
        btnAddMoney = new JButton("Add Money");
        btnAddMoney.setBorder(roundedBorder);

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

        leftPanel.add(new JLabel("PROFILE"));
        leftPanel.add(lblUsername);
        leftPanel.add(new JLabel("MY BALANCE"));
        leftPanel.add(lblBalance);
        leftPanel.add(new JLabel("ENTER THE AMOUNT"));
        leftPanel.add(txtAmount);
        leftPanel.add(btnAddMoney);

        add(leftPanel, BorderLayout.WEST);

        // Panel tengah (Tabel History)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(Color.decode("#87CEFA"));

        JLabel lblHistory = new JLabel("Recent Transactions History");
        lblHistory.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = {"Purpose", "Money", "Date", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblHistory = new JTable(tableModel);
        tblHistory.setBorder(roundedBorder);

        centerPanel.add(lblHistory, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(tblHistory), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Panel atas (Kategori)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.decode("#87CEFA"));

        JButton btnGadgets = new JButton("GADGETS: Rp. 0");
        btnGadgets.setBorder(roundedBorder);
        JButton btnClothes = new JButton("CLOTHES: Rp. 0");
        btnClothes.setBorder(roundedBorder);
        JButton btnBooks = new JButton("BOOKS: Rp. 0");
        btnBooks.setBorder(roundedBorder);
        JButton btnGrocery = new JButton("GROCERY: Rp. 0");
        btnGrocery.setBorder(roundedBorder);

        topPanel.add(btnGadgets);
        topPanel.add(btnClothes);
        topPanel.add(btnBooks);
        topPanel.add(btnGrocery);

        add(topPanel, BorderLayout.NORTH);

        // Panel kanan (Add Expenditure)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 1, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(Color.decode("#87CEFA"));

        txtPurpose = new JTextField(10);
        txtPurpose.setBorder(roundedBorder);
        txtMoney = new JTextField(10);
        txtMoney.setBorder(roundedBorder);
        txtDate = new JTextField(10);
        txtDate.setBorder(roundedBorder);

        rbBooks = new JRadioButton("Books");
        rbBooks.setBackground(Color.decode("#87CEFA"));
        rbGrocery = new JRadioButton("Grocery");
        rbGrocery.setBackground(Color.decode("#87CEFA"));
        rbClothes = new JRadioButton("Clothes");
        rbClothes.setBackground(Color.decode("#87CEFA"));
        rbGadgets = new JRadioButton("Gadgets");
        rbGadgets.setBackground(Color.decode("#87CEFA"));

        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(rbBooks);
        categoryGroup.add(rbGrocery);
        categoryGroup.add(rbClothes);
        categoryGroup.add(rbGadgets);

        btnAddExpense = new JButton("Add Expense");
        btnAddExpense.setBorder(roundedBorder);

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

                // Tambahkan ke tabel
                tableModel.addRow(new Object[]{purpose, money, date, category});
                txtPurpose.setText("");
                txtMoney.setText("");
                txtDate.setText("");
                categoryGroup.clearSelection();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        rightPanel.add(new JLabel("Purpose:"));
        rightPanel.add(txtPurpose);
        rightPanel.add(new JLabel("Money:"));
        rightPanel.add(txtMoney);
        rightPanel.add(new JLabel("Date:"));
        rightPanel.add(txtDate);
        rightPanel.add(new JLabel("Category:"));
        rightPanel.add(rbBooks);
        rightPanel.add(rbGrocery);
        rightPanel.add(rbClothes);
        rightPanel.add(rbGadgets);
        rightPanel.add(btnAddExpense);

        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        User user = new User(1, "testuser", "testpass", 1000000);
        new DashboardFrame(user);
    }
}

// Custom Border for Rounded Corners
class RoundedBorder extends AbstractBorder {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
    }
}
