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
        setTitle("FinTrack - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        userController = new UserController();

        // Fields
        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User user = userController.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new DashboardFrame(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(btnLogin);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
