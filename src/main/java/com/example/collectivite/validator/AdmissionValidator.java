package com.example.collectivite.validator;

import com.example.collectivite.dto.AdmitMemberRequest;
import com.example.collectivite.dto.SponsorshipRequest;
import com.example.collectivite.entity.Member;
import com.example.collectivite.entity.Membership;
import com.example.collectivite.entity.Poste;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MemberRepository;
import com.example.collectivite.repository.MembershipRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdmissionValidator {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CollectivityRepository collectivityRepository;

    public AdmissionValidator(MemberRepository memberRepository,
                              MembershipRepository membershipRepository,
                              CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<String> validate(AdmitMemberRequest request) {
        List<String> errors = new ArrayList<>();


        if (!collectivityRepository.existsById(request.getCollectiviteId())) {
            errors.add("Target collectivity does not exist");
            return errors;
        }

        List<SponsorshipRequest> sponsors = request.getSponsorshipRequests();
        if (sponsors == null || sponsors.size() < 2) {
            errors.add("At least two sponsors are required");
        } else {
            int fromTarget = 0;
            int fromOthers = 0;
            for (SponsorshipRequest sr : sponsors) {
                Integer sponsorId = sr.getSponsorshipId();
                Member sponsor = memberRepository.findById(sponsorId).orElse(null);
                if (sponsor == null) {
                    errors.add("Sponsor id " + sponsorId + " does not exist");
                    continue;
                }
                Membership sponsorMembership = membershipRepository.findActiveByMember(sponsorId).orElse(null);
                if (sponsorMembership == null || sponsorMembership.getPoste() != Poste.CONFIRMED_MEMBER) {
                    errors.add("Sponsor " + sponsorId + " is not a confirmed member");
                }
                int seniorityMonths = memberRepository.getMembershipDurationInMonths(sponsorId);
                if (seniorityMonths < 3) { // 90 days minimum
                    errors.add("Sponsor " + sponsorId + " has less than 90 days seniority");
                }
                if (sponsorMembership != null && sponsorMembership.getCollectivityId().equals(request.getCollectiviteId())) {
                    fromTarget++;
                } else {
                    fromOthers++;
                }
            }
            if (fromTarget == 0) {
                errors.add("At least one sponsor must belong to the target collectivity");
            }
            if (fromTarget < fromOthers) {
                errors.add("Number of sponsors from target collectivity must be at least equal to number from other collectivities");
            }
        }


        BigDecimal annualContribution = collectivityRepository.getAnnualContribution(request.getCollectiviteId());
        BigDecimal expectedAmount = new BigDecimal("50000").add(annualContribution);
        if (request.getAmountPaid() == null || request.getAmountPaid().compareTo(expectedAmount) < 0) {
            errors.add("Insufficient payment. Expected " + expectedAmount + " MGA (50,000 fees + " + annualContribution + " annual contribution)");
        }


        if (memberRepository.existsByEmail(request.getEmail())) {
            errors.add("Email already used by another member");
        }
        return errors;
    }
}