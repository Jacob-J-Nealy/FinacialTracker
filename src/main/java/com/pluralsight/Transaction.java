package com.pluralsight;

public class Transaction {

    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    // Constructor
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    /* //toString (Normal Generated toString)
    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    } */

    // String Builder ToString Strategy (Tiffany Obi)
    @Override
    public String toString() {

            StringBuilder transactionBuilder = new StringBuilder();
            transactionBuilder.append(date);
            transactionBuilder.append("|");
            transactionBuilder.append(time);
            transactionBuilder.append("|");
            transactionBuilder.append(description);
            transactionBuilder.append("|");
            transactionBuilder.append(vendor);
            transactionBuilder.append("|");
            transactionBuilder.append(amount);

            //transactionBuilder.toString();

            // advanced way
            return String.format("%s|%s|%s|%s|%s",date,time,description,vendor,amount);
        }

    // Getters & Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
