package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.*;
import com.example.collectivite.entity.*;
import com.example.collectivite.exception.BadRequestException;
import com.example.collectivite.exception.ResourceNotFoundException;
import com.example.collectivite.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CollectivityRepository collectivityRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          MemberRepository memberRepository,
                          MembershipRepository membershipRepository,
                          CollectivityRepository collectivityRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.collectivityRepository = collectivityRepository;
    }


    public PaymentResponse recordMemberPayment(Integer memberId, MemberPaymentRequest request) {
        Membership active = membershipRepository.findActiveByMember(memberId)
                .orElseThrow(() -> new BadRequestException("Member has no active membership"));
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Payment amount must be positive");
        }
        if (request.getMode() == null) {
            throw new BadRequestException("Payment mode is required");
        }
        if (request.getPaymentDate() == null) {
            throw new BadRequestException("Payment date is required");
        }

        Payment payment = new Payment();
        payment.setMemberId(memberId);
        payment.setCollectivityId(active.getCollectivityId());
        payment.setAmount(request.getAmount());
        payment.setMode(request.getMode());
        payment.setReference(request.getReference());
        payment.setPaymentDate(request.getPaymentDate());
        payment = paymentRepository.save(payment);

        PaymentResponse resp = new PaymentResponse();
        resp.setId(payment.getId());
        resp.setMemberId(payment.getMemberId());
        resp.setCollectivityId(payment.getCollectivityId());
        resp.setAmount(payment.getAmount());
        resp.setMode(payment.getMode());
        resp.setReference(payment.getReference());
        resp.setPaymentDate(payment.getPaymentDate());
        return resp;
    }


    public List<TransactionResponse> getCollectivityTransactions(Integer collectivityId,
                                                                 LocalDate startDate,
                                                                 LocalDate endDate) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new ResourceNotFoundException("Collectivity not found");
        }
        List<Payment> payments = paymentRepository.findByCollectivityId(collectivityId);

        List<TransactionResponse> result = new ArrayList<>();
        for (Payment p : payments) {
            if (startDate != null && p.getPaymentDate().isBefore(startDate)) continue;
            if (endDate != null && p.getPaymentDate().isAfter(endDate)) continue;

            Member member = memberRepository.findById(p.getMemberId()).orElse(null);
            String memberName = member != null ? member.getFirstName() + " " + member.getName() : "Unknown";

            TransactionResponse tr = new TransactionResponse();
            tr.setPaymentId(p.getId());
            tr.setMemberId(p.getMemberId());
            tr.setMemberName(memberName);
            tr.setAmount(p.getAmount());
            tr.setMode(p.getMode());
            tr.setReference(p.getReference());
            tr.setPaymentDate(p.getPaymentDate());
            result.add(tr);
        }
        return result;
    }

    public List<AccountResponse> getFinancialAccounts(Integer collectivityId, LocalDate at) {

        AccountRepository accountRepo = new AccountRepository(DBConnection.getInstance());
        List<Account> accounts = accountRepo.findByCollectivite(collectivityId);
        List<AccountResponse> responses = new ArrayList<>();
        for (Account a : accounts) {
            AccountResponse ar = toAccountResponse(a);
            responses.add(ar);
        }
        return responses;
    }

    private AccountResponse toAccountResponse(Account a) {
        AccountResponse ar = new AccountResponse();
        ar.setId(a.getId());
        ar.setCollectivityId(a.getCollectivityId());
        ar.setFederation(a.isFederation());
        ar.setAccountType(a.getAccountType());
        ar.setTitular(a.getTitular());
        ar.setBalance(a.getBalance());
        ar.setCurrency(a.getCurrency());
        ar.setCreationDate(a.getCreationDate());
        return ar;
    }
}
