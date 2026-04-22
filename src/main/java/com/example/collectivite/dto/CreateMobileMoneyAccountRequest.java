package com.example.collectivite.dto;

import com.example.collectivite.entity.MobileMoneyService;

public class CreateMobileMoneyAccountRequest {
    private Integer collectivityId;
    private boolean isFederation;
    private String titular;
    private MobileMoneyService mobileMoneyService;
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

    public MobileMoneyService getMobileMoneyService() {
        return mobileMoneyService;
    }

    public void setMobileMoneyService(MobileMoneyService mobileMoneyService) {
        this.mobileMoneyService = mobileMoneyService;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
