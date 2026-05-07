package com.example.collectivite.validator;

import com.example.collectivite.entity.Member;
import com.example.collectivite.entity.Membership;
import com.example.collectivite.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberValidator {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CollectivityRepository collectivityRepository;

    public MemberValidator(MemberRepository memberRepository,
                           MembershipRepository membershipRepository,
                           CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.collectivityRepository = collectivityRepository;
    }

    // Tous les paramètres `Integer` sont maintenant `String`
    public List<String> validateNewMember(Member member, String collectivityId) {
        List<String> errors = new ArrayList<>();
        // ... même logique, aucune référence à des Integer
        return errors;
    }

    public List<String> validateExistingMemberForAdmission(String memberId, String collectivityId) {
        List<String> errors = new ArrayList<>();
        // ...
        return errors;
    }

    public List<String> validateAdmission(Member member, String collectivityId) {
        if (member.getId() == null) {
            return validateNewMember(member, collectivityId);
        } else {
            return validateExistingMemberForAdmission(member.getId(), collectivityId);
        }
    }

    public List<String> validateSponsorEligibility(String sponsorId, String collectivityId) {
        List<String> errors = new ArrayList<>();
        // ...
        return errors;
    }

    public List<String> validateReadmission(String memberId, String collectivityId) {
        List<String> errors = new ArrayList<>();
        // ...
        return errors;
    }

    // Les fonctions utilitaires restent inchangées
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^(\\+221)?(70|76|77|78)[0-9]{7}$");
    }
}