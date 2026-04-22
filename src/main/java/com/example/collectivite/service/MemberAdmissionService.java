package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.AdmitMemberRequest;
import com.example.collectivite.dto.MemberResponse;
import com.example.collectivite.dto.SponsorshipRequest;
import com.example.collectivite.entity.*;
import com.example.collectivite.exception.AdmissionException;
import com.example.collectivite.repository.*;
import com.example.collectivite.validator.AdmissionValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MemberAdmissionService {

    private final MemberRepository memberRepository;
    private final SponsorshipRepository sponsorshipRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final CollectivityRepository collectivityRepository;
    private final DBConnection dbConnection;
    private final AdmissionValidator validator;

    public MemberAdmissionService(MemberRepository memberRepository,
                                  SponsorshipRepository sponsorshipRepository,
                                  MembershipRepository membershipRepository,
                                  PaymentRepository paymentRepository,
                                  CollectivityRepository collectivityRepository,
                                  DBConnection dbConnection,
                                  AdmissionValidator validator) {
        this.memberRepository = memberRepository;
        this.sponsorshipRepository = sponsorshipRepository;
        this.membershipRepository = membershipRepository;
        this.paymentRepository = paymentRepository;
        this.collectivityRepository = collectivityRepository;
        this.dbConnection = dbConnection;
        this.validator = validator;
    }

    public MemberResponse admitMember(AdmitMemberRequest request) {
        List<String> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            throw new AdmissionException(errors);
        }

        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);


            Member newMember = new Member();
            newMember.setName(request.getName());
            newMember.setFirstName(request.getLastName());
            newMember.setBirthDate(request.getBirthDate());
            newMember.setGender(request.getGender());
            newMember.setAdress(request.getAdress());
            newMember.setProfession(request.getProfession());
            newMember.setPhone(request.getPhone());
            newMember.setEmail(request.getEmail());
            newMember.setJoinDate(LocalDate.now());
            newMember.setStatus(MemberStatus.ACTIVE);
            memberRepository.save(newMember);

            Membership membership = new Membership();
            membership.setMemberId(newMember.getId());
            membership.setCollectivityId(request.getCollectiviteId());
            membership.setPoste(Poste.JUNIOR_MEMBER);
            membership.setStartDate(LocalDate.now());
            membership.setEndDate(null);
            membershipRepository.save(membership);

            for (SponsorshipRequest sr : request.getSponsorshipRequests()) {
                Sponsorship s = new Sponsorship();
                s.setCandidateId(newMember.getId());
                s.setSponsorId(sr.getSponsorshipId());
                s.setTargetCommunityId(request.getCollectiviteId());
                s.setSponsorCommunityId(getSponsorCommunityId(sr.getSponsorshipId()));
                s.setRelation(sr.getRelation());
                s.setReferralDate(LocalDate.now());
                sponsorshipRepository.save(s);
            }

            Payment payment = new Payment();
            payment.setMemberId(newMember.getId());
            payment.setCollectivityId(request.getCollectiviteId());
            payment.setAmount(request.getAmountPaid());
            payment.setMode(request.getModePayment());
            payment.setReference(request.getReferenceTransaction());
            payment.setPaymentDate(LocalDate.now());
            paymentRepository.save(payment);

            conn.commit();

            MemberResponse response = new MemberResponse();
            response.setId(newMember.getId());
            response.setName(newMember.getName());
            response.setLastName(newMember.getFirstName());
            response.setBirthDate(newMember.getBirthDate());
            response.setAdress(newMember.getAdress());
            response.setProfession(newMember.getProfession());
            response.setPhone(newMember.getPhone());
            response.setEmail(newMember.getEmail());
            response.setJoinDate(newMember.getJoinDate());
            response.setStatus(MemberStatus.ACTIVE);
            response.setCollectiviteId(request.getCollectiviteId());
            response.setPoste(Poste.JUNIOR_MEMBER);
            response.setMessage("Admission successful");
            return response;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Transaction error while admitting member", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    private Integer getSponsorCommunityId(Integer sponsorId) {
        return membershipRepository.findActiveByMember(sponsorId)
                .map(Membership::getCollectivityId)
                .orElseThrow(() -> new RuntimeException("Sponsor has no active community"));
    }
}