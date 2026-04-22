package com.example.collectivite.dto;

import com.example.collectivite.entity.MobileMoneyService;

public class MobileMoneyAccountResponse extends AccountResponse {
    private MobileMoneyService mobileMoneyService;
    private String phoneNumber;

    public MobileMoneyAccountResponse() {
        super();
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
