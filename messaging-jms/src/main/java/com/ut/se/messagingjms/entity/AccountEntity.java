package com.ut.se.messagingjms.entity;

public class AccountEntity {

    private final String accountNo;

    private int amount;

    public AccountEntity(String accountNo, int amount) {
        this.accountNo = accountNo;
        this.amount = amount;
    }

    public String getAccountNo() { return this.accountNo; }

    public int getAmount() { return this.amount; }

    public void setAmount(int amount) { this.amount = amount; }
}