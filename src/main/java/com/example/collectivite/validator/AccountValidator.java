package com.example.collectivite.validator;

import com.example.collectivite.dto.CreateBankAccountRequest;
import com.example.collectivite.dto.CreateMobileMoneyAccountRequest;
import com.example.collectivite.dto.CreatePaymentRequest;
import com.example.collectivite.exception.AccountException;
import com.example.collectivite.repository.AccountRepository;
import com.example.collectivite.repository.BankAccountRepository;
import com.example.collectivite.repository.MobileMoneyAccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private static final int ACCOUNT_NUMBER_LENGTH = 23;

    private final AccountRepository accountRepository;
    private final BankAccountRepository bankAccountRepository;
    private final MobileMoneyAccountRepository mobileMoneyAccountRepository;

    public AccountValidator(AccountRepository accountRepository,
                            BankAccountRepository bankAccountRepository,
                            MobileMoneyAccountRepository mobileMoneyAccountRepository) {
        this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.mobileMoneyAccountRepository = mobileMoneyAccountRepository;
    }

    public void confirmCreatePayment (CreatePaymentRequest request){
        List<String> errors = new ArrayList<>();

        if(request.getTitular() == null || request.getTitular().isEmpty()){
            errors.add("Titular is required");
        }

        if(!request.isFederation() && request.getCollectivityId() == null){
            errors.add("Collectivity is required");
        }

        if(accountRepository.fundExist(
                request.getCollectivityId(), request.isFederation())){
            errors.add("Collectivity already exists");
        }

        if (!errors.isEmpty()) {
            throw new AccountException(errors);
        }
    }

    public void validateCreateBankAccount (CreateBankAccountRequest request){
        List<String> errors = new ArrayList<>();
        if(request.getTitular() == null || request.getTitular().isBlank()){
            errors.add("Titular is required");
        }

        if(request.getBank() == null){
            errors.add("Bank name is required");
        }

        if(request.getAccountNumber() == null || request.getAccountNumber().isBlank()){
            errors.add("Account number is required");
        }

        if(!request.isFederation() && request.getCollectivityId() == null){
            errors.add("Collectivity id is required");
        }

        if (request.getAccountNumber() != null) {
            String num = request.getAccountNumber().replaceAll("\\s", "");
            if(!num.matches("\\d{" + ACCOUNT_NUMBER_LENGTH + "}")){
                errors.add("The bank account number must contain exactly"
                + ACCOUNT_NUMBER_LENGTH + " numbers "
                + "(format : BBBBB GGGGG CCCCCCCCCCC KK)."
                + "Included : " + num.length() + "characters");
            }
            else {
                if(bankAccountRepository.existsByAccountNumber(num)){
                    errors.add("The bank account number already exists");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new AccountException(errors);
        }
    }

    public void validateCreateMobileMoneyAccount (CreateMobileMoneyAccountRequest request){
        List<String> errors = new ArrayList<>();

        if(request.getTitular() == null || request.getTitular().isBlank()){
            errors.add("Titular is required");
        }

        if(request.getMobileMoneyService() == null){
            errors.add("Mobile Money Service is required."+ "(Orange money , Mvola or Airtel money");
        }

        if (request.getPhoneNumber() == null ||  request.getPhoneNumber().isBlank()) {
            errors.add("Phone number is required");
        }

        if(!request.isFederation() && request.getCollectivityId() == null){
            errors.add("Collectivity id is required");
        }

        if(request.getPhoneNumber() != null && mobileMoneyAccountRepository.existsByPhoneNumber(request.getPhoneNumber())){
            errors.add("Phone number already exists");
        }

        if(!errors.isEmpty()) {
            throw new AccountException(errors);
        }
    }
}
