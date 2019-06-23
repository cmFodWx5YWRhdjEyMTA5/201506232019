package com.techline.rideshare.entities;

public class Transaction {
    private String tran_id;
    private String driver_name;
    private String pass_name;
    private String tran_date;
    private String source;
    private String destination;
    private String amount_due;
    private String amount_paid;
    private String amount_deducted;
    private String payment_type;
    private String channel;
    private String channel_trans_id;
    private String accountNumber;


    public Transaction() {
    }

    public Transaction(String tran_id, String driver_name, String pass_name,
                       String tran_date, String source, String destination, String amount_due,
                       String amount_paid, String amount_deducted, String payment_type,
                       String channel, String channel_trans_id, String accountNumber) {
        this.tran_id = tran_id;
        this.driver_name = driver_name;
        this.pass_name = pass_name;
        this.tran_date = tran_date;
        this.source = source;
        this.destination = destination;
        this.amount_due = amount_due;
        this.amount_paid = amount_paid;
        this.amount_deducted = amount_deducted;
        this.payment_type = payment_type;
        this.channel = channel;
        this.channel_trans_id = channel_trans_id;
        this.accountNumber = accountNumber;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getPass_name() {
        return pass_name;
    }

    public void setPass_name(String pass_name) {
        this.pass_name = pass_name;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(String amount_due) {
        this.amount_due = amount_due;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getAmount_deducted() {
        return amount_deducted;
    }

    public void setAmount_deducted(String amount_deducted) {
        this.amount_deducted = amount_deducted;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_trans_id() {
        return channel_trans_id;
    }

    public void setChannel_trans_id(String channel_trans_id) {
        this.channel_trans_id = channel_trans_id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
