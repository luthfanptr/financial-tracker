package models;

public class RiwayatTransaksi {
    private String username;  // Menambahkan properti username
    private String purpose;
    private double money;
    private String date;
    private String category;

    // Constructor
    public RiwayatTransaksi(String username, String purpose, double money, String date, String category) {
        this.username = username;
        this.purpose = purpose;
        this.money = money;
        this.date = date;
        this.category = category;
    }

    // Getter dan Setter untuk username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter dan Setter untuk purpose
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    // Getter dan Setter untuk money
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    // Getter dan Setter untuk date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter dan Setter untuk category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
