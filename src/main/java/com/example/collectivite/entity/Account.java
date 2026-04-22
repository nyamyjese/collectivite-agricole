package com.example.collectivite.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private Integer id;
    private Integer collectivityId ;
    private boolean isFederation;
    private AccountType accountType;
    private String titular;
    private BigDecimal balance;
    private String currency;
    private LocalDate creationDate;

    public Account(){
        this.currency = "MGA";
        this.balance = BigDecimal.ZERO;
        this.creationDate = LocalDate.now();
        this.isFederation = false;
    }

    public Account(Integer id,
                   Integer collectivityId,
                   boolean isFederation,
                   AccountType accountType,
                   String titular,
                   BigDecimal balance,
                   String currency,
                   LocalDate creationDate) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.isFederation = isFederation;
        this.accountType = accountType;
        this.titular = titular;
        this.balance = balance;
        this.currency = currency;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(Integer collectivityId) {
        this.collectivityId = collectivityId;
    }

    public boolean isFederation() {
        return isFederation;
    }

    public void setFederation(boolean federation) {
        isFederation = federation;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
