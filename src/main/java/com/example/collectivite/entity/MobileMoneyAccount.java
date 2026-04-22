package com.example.collectivite.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MobileMoneyAccount extends Account {
    private MobileMoneyService mobileMoneyService;
    private String phoneNumber;

    public MobileMoneyAccount(){
        super();
        setAccountType(AccountType.MOBILE_MONEY);
    }

    public MobileMoneyAccount(Integer id ,
                              Integer collectivityId,
                              boolean isFederation ,
                              String titular ,
                              BigDecimal balance ,
                              String currency ,
                              LocalDate creationDate ,
                              MobileMoneyService mobileMoneyService,
                              String phoneNumber){
        super(id, collectivityId , isFederation,
                AccountType.MOBILE_MONEY, titular, balance, currency, creationDate);
        this.mobileMoneyService = mobileMoneyService;
        this.phoneNumber = phoneNumber;
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
