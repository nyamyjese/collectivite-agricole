package com.example.collectivite.dto;

import com.example.collectivite.enums.MobileBankingService;

public class CreateMobileMoneyAccountRequest {
    private Integer collectivityId;
    private boolean isFederation;
    private String titular;
    private MobileBankingService mobileBankingService;
    private String phoneNumber;

    public CreateMobileMoneyAccountRequest() {}

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

    public MobileBankingService getMobileMoneyService() {
        return mobileBankingService;
    }

    public void setMobileMoneyService(MobileBankingService mobileBankingService) {
        this.mobileBankingService = mobileBankingService;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
