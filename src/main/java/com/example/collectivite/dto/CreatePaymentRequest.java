package com.example.collectivite.dto;

public class CreatePaymentRequest {
    private Integer collectivityId ;
    private boolean isFederation ;
    private String titular;

    public CreatePaymentRequest(){}

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

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
