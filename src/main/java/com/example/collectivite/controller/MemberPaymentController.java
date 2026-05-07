package com.example.collectivite.controller;
import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.MemberPaymentRequest;
import com.example.collectivite.dto.PaymentResponse;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MemberRepository;
import com.example.collectivite.repository.MembershipRepository;
import com.example.collectivite.repository.PaymentRepository;
import com.example.collectivite.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberPaymentController {

    private final PaymentService paymentService;

    public MemberPaymentController() {
        DBConnection db = DBConnection.getInstance();
        PaymentRepository paymentRepo = new PaymentRepository(db);
        MemberRepository memberRepo = new MemberRepository(db);
        MembershipRepository membershipRepo = new MembershipRepository(db);
        CollectivityRepository collectivityRepo = new CollectivityRepository(db);
        this.paymentService = new PaymentService(paymentRepo, memberRepo, membershipRepo, collectivityRepo);
    }

    @PostMapping("/members/{memberId}/payments")
    public ResponseEntity<PaymentResponse> recordPayment(@PathVariable String memberId,
                                                         @RequestBody MemberPaymentRequest request) {
        PaymentResponse resp = paymentService.recordMemberPayment(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}