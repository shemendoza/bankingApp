package com.bankApp.bankingApp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String number;
    private String email;
    private String pin;
    private double balance;
    private String role;

    private final List<Transaction> transactionList;

    public User() {
        this.balance = 0.0;
        this.transactionList = new ArrayList<>();
    }

    public User(
            String name,
            String number,
            String email,
            String pin,
            String role
    ) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.pin = pin;
        this.balance = 0.0;
        this.role = role;
        this.transactionList = new ArrayList<>();
    }

    public User(
            int id,
            String name,
            String number,
            String email,
            String pin,
            double balance,
            String role
    ) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.pin = pin;
        this.balance = balance;
        this.role = role;
        this.transactionList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addBalance(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        balance += amount;
    }

    public void deductBalance(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        if (amount > balance) {
            throw new IllegalArgumentException(
                    "Insufficient balance."
            );
        }

        balance -= amount;
    }

    public List<Transaction> getTransactionList() {
        return Collections.unmodifiableList(transactionList);
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null."
            );
        }

        transactionList.add(transaction);
    }
}