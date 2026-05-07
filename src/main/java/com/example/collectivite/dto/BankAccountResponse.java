package com.example.collectivite.dto;

import com.example.collectivite.entity.Bank;

public class BankAccountResponse extends AccountResponse {
    private Bank bank;
    private String accountNumber;
    private String bankCode;
    private String branchCode;
    private String internalAccountNumber;
    private String keyRib;

    public BankAccountResponse(){
        super();
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getInternalAccountNumber() {
        return internalAccountNumber;
    }

    public void setInternalAccountNumber(String internalAccountNumber) {
        this.internalAccountNumber = internalAccountNumber;
    }

    public String getKeyRib() {
        return keyRib;
    }

    public void setKeyRib(String keyRib) {
        this.keyRib = keyRib;
    }
}
