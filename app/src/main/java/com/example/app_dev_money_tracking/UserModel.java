package com.example.app_dev_money_tracking;

public class UserModel {
    private int id;
    private String email;
    private String password;
    private int balance;
    private int admin;

    public UserModel(int id, String email, String password, int balance, int admin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.admin=admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
