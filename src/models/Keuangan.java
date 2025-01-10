package models;

public class Keuangan {
    private String purpose;
    private double money;
    private String date;
    private String category;

    public Keuangan(String purpose, double money, String date, String category) {
        this.purpose = purpose;
        this.money = money;
        this.date = date;
        this.category = category;
    }

    // Getters and setters
    public String getPurpose() { return purpose; }
    public double getMoney() { return money; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
}

