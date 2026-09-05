package com.bankApp.bankingApp.model;

import java.time.LocalDateTime;

public class Transaction {

    private int id;
    private String userNumber;
    private String type;
    private double amount;
    private LocalDateTime date;
    private int userId;

    public Transaction() {
    }

    public Transaction(
            String userNumber,
            String type,
            double amount,
            LocalDateTime date,
            int userId
    ) {
        this.userNumber = userNumber;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.userId = userId;
    }

    public Transaction(
            int id,
            String userNumber,
            String type,
            double amount,
            LocalDateTime date,
            int userId
    ) {
        this.id = id;
        this.userNumber = userNumber;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userNumber='" + userNumber + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }
}