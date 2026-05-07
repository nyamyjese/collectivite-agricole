package com.example.collectivite.dto;

import java.time.LocalDate;

public class CreateContributionRequest {

    private String memberId;
    private String type;
    private int amount;
    private String paymentMode;
    private String transactionReference;
    private LocalDate collectionDate;


    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionReference() {
        return transactionReference;
    }
    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }
    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }
}