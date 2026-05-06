package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.AdmitMemberRequest;
import com.example.collectivite.dto.MemberResponse;
import com.example.collectivite.exception.AdmissionException;
import com.example.collectivite.repository.*;
import com.example.collectivite.service.MemberAdmissionService;
import com.example.collectivite.validator.AdmissionValidator;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberAdmissionService admissionService;

    public MemberController() {
        DBConnection db = DBConnection.getInstance();

        MemberRepository      memberRepo      = new MemberRepository(db);
        CollectivityRepository collectivityRepo = new CollectivityRepository(db);
        MembershipRepository  membershipRepo  = new MembershipRepository(db);
        SponsorshipRepository sponsorshipRepo  = new SponsorshipRepository(db);
        PaymentRepository     paymentRepo     = new PaymentRepository(db);

        AdmissionValidator admissionValidator = new AdmissionValidator(
                memberRepo, membershipRepo, collectivityRepo);

        this.admissionService = new MemberAdmissionService(
                memberRepo,
                sponsorshipRepo,
                membershipRepo,
                paymentRepo,
                collectivityRepo,
                db,
                admissionValidator
        );
    }

    @PostMapping
    public ResponseEntity<?> admitMember(@RequestBody AdmitMemberRequest request) {
        try {
            if (request.getCollectiviteId() == null) {
                throw new BadRequestException("Collectivity id is required");
            }
            MemberResponse response = admissionService.admitMember(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AdmissionException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}