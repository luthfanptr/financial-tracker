# Expense and Income Tracker Application

---

# 1. Project Overview

**Expense and Income Tracker** adalah aplikasi desktop sederhana yang dibuat menggunakan **Java Swing** dengan penyimpanan data menggunakan **MySQL**.  
Aplikasi ini membantu pengguna mencatat transaksi keuangan berupa **Income (pendapatan)** dan **Expense (pengeluaran)** serta menampilkan ringkasan kondisi keuangan secara langsung.

Aplikasi menyediakan fitur untuk:

- Menambahkan transaksi
- Menghapus transaksi
- Menampilkan daftar transaksi
- Menampilkan total income
- Menampilkan total expense
- Menampilkan total balance

Aplikasi dirancang sebagai **desktop application yang ringan dan berjalan secara lokal**.

---

# 2. Objectives

Tujuan pengembangan aplikasi ini:

1. Membuat sistem pencatatan transaksi keuangan sederhana.
2. Mempermudah pengguna memonitor kondisi keuangan mereka.
3. Mengimplementasikan GUI desktop menggunakan Java Swing.
4. Mengintegrasikan aplikasi Java dengan database menggunakan JDBC.

---

# 3. Scope

Lingkup sistem meliputi:

- Pencatatan transaksi keuangan
- Penyimpanan transaksi dalam database
- Menampilkan transaksi dalam tabel
- Perhitungan otomatis:
  - Total Income
  - Total Expense
  - Total Balance

Sistem **tidak mencakup**:

- Multi user system
- Sistem login
- Cloud synchronization
- Export laporan
- Mobile version

---

# 4. System Architecture

Arsitektur aplikasi menggunakan pendekatan sederhana berbasis komponen:

```
User Interface (Java Swing)
        │
        │
Application Logic
        │
        │
DAO Layer (TransactionDAO)
        │
        │
Database (MySQL)
```

## Komponen Utama

| Component | Description |
|----------|-------------|
| Application | Main class yang mengatur UI dan alur program |
| Transaction | Model data transaksi |
| TransactionDAO | Mengambil dan menyimpan data dari database |
| TransactionValuesCalculation | Menghitung total income, expense, dan balance |
| DatabaseConnection | Mengelola koneksi database |

---

# 5. Functional Requirements

## 5.1 Add Transaction

Sistem harus memungkinkan pengguna menambahkan transaksi baru.

**Input**

- Transaction Type (Income / Expense)
- Description
- Amount

**Proses**

1. User mengisi form transaksi
2. Sistem menyimpan data ke database
3. Tabel transaksi diperbarui
4. Panel summary diperbarui

**Output**

Transaksi baru muncul di tabel transaksi.

---

## 5.2 Remove Transaction

Sistem harus memungkinkan pengguna menghapus transaksi.

**Proses**

1. User memilih baris pada tabel
2. User menekan tombol **Remove Transaction**
3. Sistem menghapus data dari database
4. Tabel diperbarui
5. Summary diperbarui

---

## 5.3 Display Transaction List

Sistem harus menampilkan semua transaksi dalam tabel.

| Column | Description |
|------|-------------|
| ID | ID transaksi |
| Type | Income atau Expense |
| Description | Deskripsi transaksi |
| Amount | Nilai transaksi |

---

## 5.4 Display Financial Summary

Sistem harus menampilkan 3 panel utama:

| Panel | Description |
|------|-------------|
| Expense | Total seluruh pengeluaran |
| Income | Total seluruh pendapatan |
| Total | Selisih antara income dan expense |

---

## 5.5 Data Persistence

Semua transaksi harus disimpan dalam database sehingga data tetap ada saat aplikasi ditutup dan dijalankan kembali.

---

# 6. Non-Functional Requirements

## 6.1 Usability

- Antarmuka harus sederhana dan mudah digunakan.
- User dapat menambah atau menghapus transaksi dengan mudah.

## 6.2 Performance

- Aplikasi harus memuat data transaksi dengan cepat (<2 detik).

## 6.3 Reliability

- Data transaksi harus tersimpan secara permanen di database.

## 6.4 Maintainability

Struktur kode dipisahkan menjadi:

- UI Layer
- Business Logic
- Data Access Layer

---

# 7. Database Design

## Table: `transaction_table`

| Field | Type | Description |
|------|------|-------------|
| id | INT (PK) | ID transaksi |
| transaction_type | VARCHAR | Income / Expense |
| description | VARCHAR | Deskripsi transaksi |
| amount | DOUBLE | Nilai transaksi |

---

# 8. Data Flow

## Add Transaction

```
User Input
   ↓
Add Transaction Dialog
   ↓
addTransaction()
   ↓
Insert ke Database
   ↓
Refresh Table
   ↓
Update Summary Panel
```

---

## Remove Transaction

```
User memilih row
      ↓
Remove Transaction Button
      ↓
DELETE dari database
      ↓
Refresh Table
      ↓
Update Summary Panel
```

---

# 9. Techstack

| Technology | Purpose |
|-----------|--------|
| Java | Bahasa pemrograman utama |
| Java Swing | GUI Framework |
| MySQL | Database |
| JDBC | Database connection |
| IntelliJ / NetBeans / VSCode | Development IDE |

---

# 10. Scope of Work

Aplikasi ini memiliki beberapa keterbatasan sebagai berikut:

1. Aplikasi hanya berjalan pada **desktop environment** dan tidak mendukung platform web atau mobile.
2. Sistem hanya mendukung **satu pengguna (single user)** tanpa fitur autentikasi atau login.
3. Aplikasi tidak menyediakan fitur **edit transaksi**, hanya dapat menambah dan menghapus data.
4. Tidak tersedia fitur **pencarian atau filter transaksi**.
5. Aplikasi tidak mendukung **export data** ke format lain seperti PDF atau Excel.
6. Tidak terdapat fitur **backup atau restore database secara otomatis**.
7. Tampilan grafik atau analisis visual keuangan belum tersedia.
8. Sistem tidak mendukung **sinkronisasi data online** atau cloud storage.

---

# 11. Future Improvements

Pengembangan yang dapat ditambahkan di masa depan:

- Edit Transaction
- Search Transaction
- Filter Income / Expense
- Grafik keuangan
- Export ke Excel / PDF
- Login system
- Multi user support
- Web version

