package com.example.collectivite.entity;

import java.time.LocalDate;

public class Contribution {
    private String id;
    private String memberId;
    private String collectivityId;
    private String type;
    private int amount;
    private String paymentMode;
    private String transactionReference;
    private LocalDate collectionDate;
    private int federationReversedAmount;
    private String status;


    public Contribution() {}


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getCollectivityId() {
        return collectivityId;
    }
    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
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
    public int getFederationReversedAmount() {
        return federationReversedAmount;
    }
    public void setFederationReversedAmount(int federationReversedAmount) {
        this.federationReversedAmount = federationReversedAmount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}