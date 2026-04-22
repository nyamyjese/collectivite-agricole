package com.example.collectivite.dto;

import com.example.collectivite.entity.Contribution;
import java.time.LocalDate;

public class ContributionResponse {
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

    // Constructeur par défaut
    public ContributionResponse() {}

    // Méthode statique pour convertir une entité en DTO
    public static ContributionResponse fromEntity(Contribution contribution) {
        ContributionResponse response = new ContributionResponse();
        response.setId(contribution.getId());
        response.setMemberId(contribution.getMemberId());
        response.setCollectivityId(contribution.getCollectivityId());
        response.setType(contribution.getType());
        response.setAmount(contribution.getAmount());
        response.setPaymentMode(contribution.getPaymentMode());
        response.setTransactionReference(contribution.getTransactionReference());
        response.setCollectionDate(contribution.getCollectionDate());
        response.setFederationReversedAmount(contribution.getFederationReversedAmount());
        response.setStatus(contribution.getStatus());
        return response;
    }

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