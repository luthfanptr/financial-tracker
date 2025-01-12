package view;

import controller.UserController;
import models.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserController userController;

    public LoginFrame() {
        // Frame properties
        setTitle("Money Tracker - Login");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(true);

        // Main panel with gradient-like background color
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = Color.decode("#000428"); // Dark blue
                Color color2 = Color.decode("#004e92"); // Light blue
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        
        // Title Label
        JLabel lblTitle = new JLabel("MONEY TRACKER");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(100, 30, 200, 40);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle);

        // Subtitle
        JLabel lblSubtitle = new JLabel("Effortlessly manage your finances.");
        lblSubtitle.setForeground(Color.WHITE);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setBounds(50, 320, 300, 20);
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblSubtitle);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(50, 90, 300, 200);
        loginPanel.setBackground(new Color(255, 255, 255, 60));
        loginPanel.setLayout(null);
        mainPanel.add(loginPanel);

        // Username Label
        JLabel lblUsername = new JLabel("USERNAME");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
        lblUsername.setBounds(20, 20, 110, 20);
        lblUsername.setForeground(Color.WHITE);
        loginPanel.add(lblUsername);

        // Username TextField
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setBounds(20, 40, 260, 30);
        loginPanel.add(txtUsername);

        // Password Label
        JLabel lblPassword = new JLabel("PASSWORD");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        lblPassword.setBounds(20, 80, 110, 20); // Fixed typo from 11x0 to 110
        lblPassword.setForeground(Color.WHITE);
        loginPanel.add(lblPassword);

        // Password Field
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setBounds(20, 100, 260, 30);
        loginPanel.add(txtPassword);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(Color.decode("#FF5722"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(20, 150, 260, 30);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            userController = new UserController(); // Inisialisasi user controller

            User user = userController.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new DashboardFrame(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(btnLogin);

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
