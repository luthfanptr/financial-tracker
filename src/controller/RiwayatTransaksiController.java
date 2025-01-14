package controller;

import models.RiwayatTransaksi;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class RiwayatTransaksiController {

    private static final String URL = "jdbc:mysql://localhost:3306/fintrack_test"; 
    private static final String USER = "username"; // Ganti dengan username database Anda
    private static final String PASSWORD = "password"; // Ganti dengan password database Anda

    // Menyimpan riwayat transaksi ke database
    public void saveRiwayatTransaksi(RiwayatTransaksi riwayatTransaksi) {
        String query = "INSERT INTO riwayat_transaksi (username, purpose, money, date, category) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, riwayatTransaksi.getUsername());  // Menggunakan username dari objek RiwayatTransaksi
            stmt.setString(2, riwayatTransaksi.getPurpose());
            stmt.setDouble(3, riwayatTransaksi.getMoney());
            stmt.setString(4, riwayatTransaksi.getDate());
            stmt.setString(5, riwayatTransaksi.getCategory());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil riwayat transaksi berdasarkan username dan menambahkannya ke DefaultTableModel
    public void loadRiwayatTransaksi(String username, DefaultTableModel tableModel) {
        String query = "SELECT purpose, money, date, category FROM riwayat_transaksi WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);  // Menggunakan username sebagai parameter
            ResultSet rs = stmt.executeQuery();

            // Bersihkan model tabel sebelum menambahkan data baru
            tableModel.setRowCount(0);

            while (rs.next()) {
                String purpose = rs.getString("purpose");
                double money = rs.getDouble("money");
                String date = rs.getString("date");
                String category = rs.getString("category");

                // Menambahkan data ke model tabel
                tableModel.addRow(new Object[]{purpose, money, date, category});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
