package controller;

import database.DatabaseConnection;
import models.Keuangan;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeuanganController {

    // Mendapatkan saldo pengguna dari tabel users
    public double getUserBalance(User user) {
        double balance = 0.0;
        String sql = "SELECT balance FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    // Mendapatkan total pengeluaran berdasarkan kategori dan username
    public double getTotalByCategory(String category, User user) {
        double total = 0.0;
        String sql = "SELECT SUM(money) FROM add_expenditure WHERE category = ? AND username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setString(2, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1); // Mengambil nilai dari kolom pertama (SUM)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // Mendapatkan riwayat transaksi berdasarkan objek User
    public List<Keuangan> getTransactionHistory(User user) {
        List<Keuangan> transactionHistory = new ArrayList<>();
        String sql = "SELECT * FROM add_expenditure WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername()); // Menggunakan username dari objek User
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Keuangan transaction = new Keuangan(
                        rs.getString("purpose"),
                        rs.getDouble("money"),
                        rs.getString("date"),
                        rs.getString("category"),
                        rs.getString("username")
                );
                transactionHistory.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionHistory;
    }

    // Menambahkan pengeluaran baru ke database //! berhasil
    public void addExpense(String username, String purpose, double money, String date, String category) {
        String query = "INSERT INTO add_expenditure (username, purpose, money, date, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, purpose);
            stmt.setDouble(3, money);
            stmt.setString(4, date);
            stmt.setString(5, category);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //! Menambahkan jumlah total pengeluaran di kategori panel atas
    public void initializeCategoryForUser(String username) {
        String query = "INSERT IGNORE INTO category_table (username, gadgets, clothes, books, grocery) VALUES (?, 0, 0, 0, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    // Menambahkan saldo ke akun pengguna
    public void addBalance(User user, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengurangi saldo akun pengguna
    public void deductBalance(User user, double amount) {
        String sql = "UPDATE users SET balance = balance - ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan kategori total berdasarkan username untuk category_table
    public Map<String, Double> getCategoryTotals(User user) {
        Map<String, Double> categoryTotals = new HashMap<>();
        String sql = "SELECT * FROM category_table WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoryTotals.put("Gadgets", rs.getDouble("gadgets"));
                categoryTotals.put("Clothes", rs.getDouble("clothes"));
                categoryTotals.put("Books", rs.getDouble("books"));
                categoryTotals.put("Grocery", rs.getDouble("grocery"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryTotals;
    }

    // Menambahkan saldo pada kategori tertentu untuk pengguna
    public void updateCategoryTotal(String username, String category, double money) {
        String query = "UPDATE category_table SET " + category.toLowerCase() + " = " + category.toLowerCase() + " + ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, money);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUserBalance(String username, double newBalance) { //! update saldo ke db
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET balance = ? WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, newBalance);
                ps.setString(2, username);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getUserBalance(String username) { //! load jumlah saldo ke db
        double balance = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        balance = rs.getDouble("balance");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
    
    
}
