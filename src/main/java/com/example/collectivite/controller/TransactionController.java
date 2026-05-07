package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.TransactionResponse;
import com.example.collectivite.repository.*;
import com.example.collectivite.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TransactionController {

    private final PaymentService paymentService;

    public TransactionController() {
        DBConnection db = DBConnection.getInstance();
        PaymentRepository paymentRepo = new PaymentRepository(db);
        MemberRepository memberRepo = new MemberRepository(db);
        MembershipRepository membershipRepo = new MembershipRepository(db);
        CollectivityRepository collectivityRepo = new CollectivityRepository(db);
        this.paymentService = new PaymentService(paymentRepo, memberRepo, membershipRepo, collectivityRepo);
    }

    @GetMapping("/collectivities/{collectivityId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable String collectivityId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionResponse> transactions = paymentService.getCollectivityTransactions(collectivityId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}
