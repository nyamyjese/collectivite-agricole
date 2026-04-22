package com.example.collectivite.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankAccount extends Account {
    private Bank bank;
    private String accountNumber;

    public BankAccount() {
        super();
        setAccountType(AccountType.BANK);
    }

    public BankAccount(Integer id ,
                       Integer collectivityId ,
                       boolean isFederation ,
                       String titular,
                       BigDecimal balance,
                       String currency,
                       LocalDate creationDate ,
                       Bank bank ,
                       String accountNumber) {
        super(id, collectivityId , isFederation ,
                AccountType.BANK, titular, balance, currency, creationDate);
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public String bankAccount(){
        return accountNumber != null ? accountNumber.substring(0,5) : null;
    }

    public String getBranchCode (){
        return accountNumber != null ? accountNumber.substring(5,10) : null;
    }

    public String getInternalAccountNumber (){
        return accountNumber != null ? accountNumber.substring(10,21) : null;
    }

    public String keyRib (){
        return accountNumber != null ? accountNumber.substring(21,23) : null;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
