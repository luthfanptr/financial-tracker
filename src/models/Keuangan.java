package models;

public class Keuangan {
    private String purpose;
    private double money;
    private String date;
    private String category;
    private String username; // Replaced userId with username to match the new design

    // Constructor with all fields
    public Keuangan(String purpose, double money, String date, String category, String username) {
        this.purpose = purpose;
        this.money = money;
        this.date = date;
        this.category = category;
        this.username = username;
    }

    // Getters and setters
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
