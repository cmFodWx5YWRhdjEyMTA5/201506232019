package com.techline.rideshare.entities;

public class UserInfo {
    private String firstname;
    private String lastname;
    private String fullname;
    private String phone;
    private String email;
    private String type;
    private String balance;
    private String user;
    private String pass;
    private String promo_code;
    private String formatted_date;
    private String accountNumber;
    private String status;

    public UserInfo() {
    }

    public UserInfo(String firstname, String lastname, String fullname, String phone,
                    String email, String type, String balance, String user, String pass,
                    String promo_code, String formatted_date, String accountNumber,
                    String status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.balance = balance;
        this.user = user;
        this.pass = pass;
        this.promo_code = promo_code;
        this.formatted_date = formatted_date;
        this.accountNumber = accountNumber;
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getFormatted_date() {
        return formatted_date;
    }

    public void setFormatted_date(String formatted_date) {
        this.formatted_date = formatted_date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
