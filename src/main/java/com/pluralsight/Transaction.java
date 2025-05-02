package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // Constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }


    @Override
    public String toString() {

        // Transaction Builder String Builder
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
        return String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);
    }

    // Getters & Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}


