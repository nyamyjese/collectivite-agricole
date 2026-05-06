package com.example.collectivite.entity;

import com.example.collectivite.enums.AccountType;
import com.example.collectivite.enums.MobileBankingService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MobileMoneyAccount extends Account {
    private MobileBankingService mobileBankingService;
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
                              MobileBankingService mobileBankingService,
                              String phoneNumber){
        super(id, collectivityId , isFederation,
                AccountType.MOBILE_MONEY, titular, balance, currency, creationDate);
        this.mobileBankingService = mobileBankingService;
        this.phoneNumber = phoneNumber;
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
