package com.example.collectivite.dto;

import com.example.collectivite.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountResponse {
    private String id;
    private String collectivityId;
    private boolean isFederation;
    private AccountType accountType;
    private String titular;
    private BigDecimal balance;
    private String currency;
    private LocalDate creationDate;
    private String message;

    public AccountResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
