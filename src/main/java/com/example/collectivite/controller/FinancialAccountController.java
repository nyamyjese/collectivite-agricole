package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.AccountResponse;
import com.example.collectivite.repository.*;
import com.example.collectivite.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FinancialAccountController {

    private final PaymentService paymentService;

    public FinancialAccountController() {
        DBConnection db = DBConnection.getInstance();
        PaymentRepository paymentRepo = new PaymentRepository(db);
        MemberRepository memberRepo = new MemberRepository(db);
        MembershipRepository membershipRepo = new MembershipRepository(db);
        CollectivityRepository collectivityRepo = new CollectivityRepository(db);
        this.paymentService = new PaymentService(paymentRepo, memberRepo, membershipRepo, collectivityRepo);
    }

    @GetMapping("/collectivities/{collectivityId}/financialAccounts")
    public ResponseEntity<List<AccountResponse>> getFinancialAccounts(
            @PathVariable Integer collectivityId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {

        List<AccountResponse> accounts = paymentService.getFinancialAccounts(collectivityId, at);
        return ResponseEntity.ok(accounts);
    }
}
