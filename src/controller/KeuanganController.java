package controller;

import models.Keuangan;
import java.util.ArrayList;
import java.util.List;

public class KeuanganController {
    private List<Keuangan> semuaTransaksi; // Menyimpan semua transaksi
    private double totalGadget;
    private double totalClothes;
    private double totalBooks;
    private double totalGrocery;

    public KeuanganController() {
        semuaTransaksi = new ArrayList<>();
        totalGadget = 0;
        totalClothes = 0;
        totalBooks = 0;
        totalGrocery = 0;
    }

    // Menambah transaksi baru
    public void addTransaksi(String purpose, double money, String date, String category) {
        Keuangan transaksiBaru = new Keuangan(purpose, money, date, category);
        semuaTransaksi.add(transaksiBaru);

        // Update total berdasarkan kategori
        switch (category.toLowerCase()) {
            case "gadget":
                totalGadget += money;
                break;
            case "clothes":
                totalClothes += money;
                break;
            case "books":
                totalBooks += money;
                break;
            case "grocery":
                totalGrocery += money;
                break;
        }
    }

    // Mengambil semua transaksi
    public List<Keuangan> getSemuaTransaksi() {
        return semuaTransaksi;
    }

    // Mendapatkan total pengeluaran kategori gadget
    public double getTotalGadget() {
        return totalGadget;
    }

    // Mendapatkan total pengeluaran kategori clothes
    public double getTotalClothes() {
        return totalClothes;
    }

    // Mendapatkan total pengeluaran kategori books
    public double getTotalBooks() {
        return totalBooks;
    }

    // Mendapatkan total pengeluaran kategori grocery
    public double getTotalGrocery() {
        return totalGrocery;
    }

    // Menghitung total pengeluaran
    public double getTotalPengeluaran() {
        return semuaTransaksi.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase("pengeluaran"))
                .mapToDouble(Keuangan::getMoney)
                .sum();
    }

    // Menghitung total pemasukan
    public double getTotalPemasukan() {
        return semuaTransaksi.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase("pemasukan"))
                .mapToDouble(Keuangan::getMoney)
                .sum();
    }
}
