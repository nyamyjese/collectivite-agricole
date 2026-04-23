package com.example.collectivite.service;

import com.example.collectivite.dto.AccountResponse;
import com.example.collectivite.dto.BankAccountResponse;
import com.example.collectivite.dto.CreateBankAccountRequest;
import com.example.collectivite.dto.CreateMobileMoneyAccountRequest;
import com.example.collectivite.dto.CreatePaymentRequest;
import com.example.collectivite.dto.MobileMoneyAccountResponse;
import com.example.collectivite.entity.Account;
import com.example.collectivite.entity.AccountType;
import com.example.collectivite.entity.BankAccount;
import com.example.collectivite.entity.MobileMoneyAccount;
import com.example.collectivite.repository.AccountRepository;
import com.example.collectivite.repository.BankAccountRepository;
import com.example.collectivite.repository.MobileMoneyAccountRepository;
import com.example.collectivite.validator.AccountValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountService {

    private final AccountRepository            accountRepository;
    private final BankAccountRepository        bankAccountRepository;
    private final MobileMoneyAccountRepository mobileMoneyAccountRepository;
    private final AccountValidator             accountValidator;

    public AccountService(AccountRepository accountRepository,
                          BankAccountRepository bankAccountRepository,
                          MobileMoneyAccountRepository mobileMoneyAccountRepository,
                          AccountValidator accountValidator) {
        this.accountRepository            = accountRepository;
        this.bankAccountRepository        = bankAccountRepository;
        this.mobileMoneyAccountRepository = mobileMoneyAccountRepository;
        this.accountValidator             = accountValidator;
    }

    public AccountResponse createFund(CreatePaymentRequest request) {

        accountValidator.confirmCreatePayment(request);

        Account fund = new Account();
        fund.setCollectivityId(request.getCollectivityId());
        fund.setFederation(request.isFederation());
        fund.setAccountType(AccountType.CASH);
        fund.setTitular(request.getTitular());
        fund.setBalance(BigDecimal.ZERO);
        fund.setCurrency("MGA");
        fund.setCreationDate(LocalDate.now());

        fund = accountRepository.save(fund);

        return toAccountResponse(fund, "Fund created successfully.");
    }

    public BankAccountResponse createBankAccount(CreateBankAccountRequest request) {

        accountValidator.validateCreateBankAccount(request);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCollectivityId(request.getCollectivityId());
        bankAccount.setFederation(request.isFederation());
        bankAccount.setAccountType(AccountType.BANK);
        bankAccount.setTitular(request.getTitular());
        bankAccount.setBalance(BigDecimal.ZERO);
        bankAccount.setCurrency("MGA");
        bankAccount.setCreationDate(LocalDate.now());
        bankAccount.setBank(request.getBank());
        bankAccount.setAccountNumber(
                request.getAccountNumber().replaceAll("\\s", ""));

        bankAccount = bankAccountRepository.save(bankAccount);

        return toBankAccountResponse(bankAccount, "Bank account created successfully.");
    }

    public MobileMoneyAccountResponse createMobileMoneyAccount(
            CreateMobileMoneyAccountRequest request) {

        accountValidator.validateCreateMobileMoneyAccount(request);

        MobileMoneyAccount mobileMoneyAccount = new MobileMoneyAccount();
        mobileMoneyAccount.setCollectivityId(request.getCollectivityId());
        mobileMoneyAccount.setFederation(request.isFederation());
        mobileMoneyAccount.setAccountType(AccountType.MOBILE_MONEY);
        mobileMoneyAccount.setTitular(request.getTitular());
        mobileMoneyAccount.setBalance(BigDecimal.ZERO);
        mobileMoneyAccount.setCurrency("MGA");
        mobileMoneyAccount.setCreationDate(LocalDate.now());
        mobileMoneyAccount.setMobileMoneyService(request.getMobileMoneyService());
        mobileMoneyAccount.setPhoneNumber(request.getPhoneNumber());

        mobileMoneyAccount = mobileMoneyAccountRepository.save(mobileMoneyAccount);

        return toMobileMoneyAccountResponse(mobileMoneyAccount,
                "Mobile money account created successfully.");
    }

    private AccountResponse toAccountResponse(Account a, String message) {
        AccountResponse r = new AccountResponse();
        r.setId(a.getId());
        r.setCollectivityId(a.getCollectivityId());
        r.setFederation(a.isFederation());
        r.setAccountType(a.getAccountType());
        r.setTitular(a.getTitular());
        r.setBalance(a.getBalance());
        r.setCurrency(a.getCurrency());
        r.setCreationDate(a.getCreationDate());
        r.setMessage(message);
        return r;
    }

    private BankAccountResponse toBankAccountResponse(BankAccount a, String message) {
        BankAccountResponse r = new BankAccountResponse();
        r.setId(a.getId());
        r.setCollectivityId(a.getCollectivityId());
        r.setFederation(a.isFederation());
        r.setAccountType(a.getAccountType());
        r.setTitular(a.getTitular());
        r.setBalance(a.getBalance());
        r.setCurrency(a.getCurrency());
        r.setCreationDate(a.getCreationDate());
        r.setBank(a.getBank());
        r.setAccountNumber(a.getAccountNumber());
        r.setBankCode(a.getBankCode());
        r.setBranchCode(a.getBranchCode());
        r.setInternalAccountNumber(a.getInternalAccountNumber());
        r.setKeyRib(a.getKeyRib());
        r.setMessage(message);
        return r;
    }

    private MobileMoneyAccountResponse toMobileMoneyAccountResponse(
            MobileMoneyAccount a, String message) {
        MobileMoneyAccountResponse r = new MobileMoneyAccountResponse();
        r.setId(a.getId());
        r.setCollectivityId(a.getCollectivityId());
        r.setFederation(a.isFederation());
        r.setAccountType(a.getAccountType());
        r.setTitular(a.getTitular());
        r.setBalance(a.getBalance());
        r.setCurrency(a.getCurrency());
        r.setCreationDate(a.getCreationDate());
        r.setMobileMoneyService(a.getMobileMoneyService());
        r.setPhoneNumber(a.getPhoneNumber());
        r.setMessage(message);
        return r;
    }
}