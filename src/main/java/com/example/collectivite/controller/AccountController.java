package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.AccountResponse;
import com.example.collectivite.dto.BankAccountResponse;
import com.example.collectivite.dto.CreateBankAccountRequest;
import com.example.collectivite.dto.CreateMobileMoneyAccountRequest;
import com.example.collectivite.dto.CreatePaymentRequest;
import com.example.collectivite.dto.MobileMoneyAccountResponse;
import com.example.collectivite.repository.AccountRepository;
import com.example.collectivite.repository.BankAccountRepository;
import com.example.collectivite.repository.MobileMoneyAccountRepository;
import com.example.collectivite.service.AccountService;
import com.example.collectivite.validator.AccountValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController() {
        DBConnection db = DBConnection.getInstance();

        AccountRepository            accountRepo      = new AccountRepository(db);
        BankAccountRepository        bankAccountRepo  = new BankAccountRepository(db, accountRepo);
        MobileMoneyAccountRepository mobileMoneyRepo  = new MobileMoneyAccountRepository(db, accountRepo);

        AccountValidator accountValidator = new AccountValidator(
                accountRepo, bankAccountRepo, mobileMoneyRepo);

        this.accountService = new AccountService(
                accountRepo, bankAccountRepo, mobileMoneyRepo, accountValidator);
    }

    @PostMapping("/cash")
    public ResponseEntity<AccountResponse> createFund(
            @RequestBody CreatePaymentRequest request) {

        AccountResponse response = accountService.createFund(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/bank")
    public ResponseEntity<BankAccountResponse> createBankAccount(
            @RequestBody CreateBankAccountRequest request) {

        BankAccountResponse response = accountService.createBankAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/mobile_money")
    public ResponseEntity<MobileMoneyAccountResponse> createMobileMoneyAccount(
            @RequestBody CreateMobileMoneyAccountRequest request) {

        MobileMoneyAccountResponse response =
                accountService.createMobileMoneyAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}