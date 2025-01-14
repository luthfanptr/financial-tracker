package controller;

import database.DatabaseConnection;
import models.Keuangan;
import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeuanganController {

    // Mendapatkan total pengeluaran berdasarkan kategori dan username
    public double getTotalByCategory(String category, User user) {
        double total = 0;
        String sql = "SELECT SUM(money) FROM add_expenditure WHERE category = ? AND username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setString(2, user.getUsername());  // Menggunakan username dari objek User
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // Mendapatkan riwayat transaksi berdasarkan username
    public List<Keuangan> getTransaksiByUsername(String username) {
        List<Keuangan> transaksiList = new ArrayList<>();
        String sql = "SELECT * FROM add_expenditure WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);  
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Keuangan keuangan = new Keuangan(
                        rs.getString("purpose"),
                        rs.getDouble("money"),
                        rs.getString("date"),
                        rs.getString("category"),
                        rs.getString("username")
                );
                transaksiList.add(keuangan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaksiList;
    }

    // Menambahkan pengeluaran baru ke database
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
    

    // Mendapatkan kategori total berdasarkan username untuk category_table
    public void getCategoryTotals(User user) {
        String sql = "SELECT * FROM category_table WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());  // Menggunakan username dari objek User
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double gadgets = rs.getDouble("gadgets");
                double clothes = rs.getDouble("clothes");
                double books = rs.getDouble("books");
                double grocery = rs.getDouble("grocery");

                // Update totals category
                System.out.println("Gadgets: " + gadgets);
                System.out.println("Clothes: " + clothes);
                System.out.println("Books: " + books);
                System.out.println("Grocery: " + grocery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Menambahkan saldo ke akun pengguna
    public void addBalance(User user, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, user.getUsername());  // Menggunakan username dari objek User
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
            stmt.setString(2, user.getUsername());  // Menggunakan username dari objek User
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
