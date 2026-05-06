package com.example.collectivite.dto;

import com.example.collectivite.enums.MobileBankingService;

public class MobileMoneyAccountResponse extends AccountResponse {
    private MobileBankingService mobileBankingService;
    private String phoneNumber;

    public MobileMoneyAccountResponse() {
        super();
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
