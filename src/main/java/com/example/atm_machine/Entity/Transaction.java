package com.example.atm_machine.Entity;

import java.time.LocalDateTime;

public class Transaction {
//    properties
    private int transactionId;
    private double amount;
    private String description;
    private LocalDateTime dateTime;
    private Account account;
    private ETransactionType transactionType;
    private EStatus eStatus;

//    constructor null
    public Transaction() {;}
//    constructor
    public Transaction(int transactionId, double amount, String description, LocalDateTime dateTime, Account account, ETransactionType transactionType, EStatus eStatus) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
        this.account = account;
        this.transactionType = transactionType;
        this.eStatus = eStatus;
    }

//    getter setter
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ETransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public EStatus getEStatus() {
        return eStatus;
    }

    public void setEStatus(EStatus eStatus) {
        this.eStatus = eStatus;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", cardNumber=" + account.getCardNumber() +
                ", transactionType=" + transactionType.getType() +
                ", eStatus=" + eStatus.getStatus() +
                '}';
    }

}
