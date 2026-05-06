package com.example.collectivite.validator;

import com.example.collectivite.entity.Member;
import com.example.collectivite.entity.Membership;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MemberRepository;
import com.example.collectivite.repository.MembershipRepository;

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

    public List<String> validateNewMember(Member member, Integer collectivityId) {
        List<String> errors = new ArrayList<>();

        if (member == null) {
            errors.add("Member cannot be null");
            return errors;
        }

        if (member.getName() == null || member.getName().trim().isEmpty()) {
            errors.add("Last name is required");
        } else if (member.getName().length() < 2) {
            errors.add("Last name must have at least 2 characters");
        }

        if (member.getFirstName() == null || member.getFirstName().trim().isEmpty()) {
            errors.add("First name is required");
        } else if (member.getFirstName().length() < 2) {
            errors.add("First name must have at least 2 characters");
        }

        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            errors.add("Email is required");
        } else if (!isValidEmail(member.getEmail())) {
            errors.add("Invalid email format");
        } else if (memberRepository.existsByEmail(member.getEmail())) {
            errors.add("A member with this email already exists");
        }

        if (member.getPhone() == null || member.getPhone().trim().isEmpty()) {
            errors.add("Phone number is required");
        } else if (!isValidPhoneNumber(member.getPhone())) {
            errors.add("Invalid phone number format (ex: 771234567 or +221771234567)");
        }

        if (member.getBirthDate() == null) {
            errors.add("Birth date is required");
        } else if (member.getBirthDate().isAfter(LocalDate.now())) {
            errors.add("Birth date cannot be in the future");
        } else if (member.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            errors.add("Member must be at least 18 years old");
        }

        if (member.getGender() == null) {
            errors.add("Gender is required");
        }

        if (member.getAdress() == null || member.getAdress().trim().isEmpty()) {
            errors.add("Address is required");
        }

        if (member.getProfession() == null || member.getProfession().trim().isEmpty()) {
            errors.add("Profession is required");
        }

        if (collectivityId == null) {
            errors.add("Collectivity ID is required");
        } else if (!collectivityRepository.existsById(collectivityId)) {
            errors.add("Collectivity not found with ID: " + collectivityId);
        }

        return errors;
    }

    public List<String> validateExistingMemberForAdmission(Integer memberId, Integer collectivityId) {
        List<String> errors = new ArrayList<>();

        if (memberId == null) {
            errors.add("Member ID is required");
            return errors;
        }

        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isEmpty()) {
            errors.add("Member not found with ID: " + memberId);
            return errors;
        }

        Member member = memberOpt.get();
        if (member.getStatus() != com.example.collectivite.entity.MemberStatus.ACTIVE) {
            errors.add("Member is not active");
        }

        Optional<Membership> activeMembership = membershipRepository.findActiveByMember(memberId);
        if (activeMembership.isPresent() && activeMembership.get().getCollectivityId().equals(collectivityId)) {
            errors.add("Member already has an active membership in this collectivity");
        }

        if (collectivityId == null) {
            errors.add("Collectivity ID is required");
        } else if (!collectivityRepository.existsById(collectivityId)) {
            errors.add("Collectivity not found with ID: " + collectivityId);
        }

        return errors;
    }

    public List<String> validateAdmission(Member member, Integer collectivityId) {
        if (member.getId() == null) {
            return validateNewMember(member, collectivityId);
        } else {
            return validateExistingMemberForAdmission(member.getId(), collectivityId);
        }
    }

    public List<String> validateSponsorEligibility(Integer sponsorId, Integer collectivityId) {
        List<String> errors = new ArrayList<>();

        if (sponsorId == null) {
            errors.add("Sponsor ID is required");
            return errors;
        }

        Optional<Member> sponsorOpt = memberRepository.findById(sponsorId);
        if (sponsorOpt.isEmpty()) {
            errors.add("Sponsor member not found");
            return errors;
        }

        Optional<Membership> activeMembership = membershipRepository.findActiveByMember(sponsorId);
        if (activeMembership.isEmpty()) {
            errors.add("Sponsor must have an active membership");
        } else if (!activeMembership.get().getCollectivityId().equals(collectivityId)) {
            errors.add("Sponsor is not a member of this collectivity");
        } else {
            int months = memberRepository.getMembershipDurationInMonths(sponsorId);
            if (months < 6) {
                errors.add("Sponsor must be a member for at least 6 months (current: " + months + " months)");
            }
        }

        if (collectivityId != null && !collectivityRepository.existsById(collectivityId)) {
            errors.add("Collectivity not found");
        }

        return errors;
    }

    public List<String> validateReadmission(Integer memberId, Integer collectivityId) {
        List<String> errors = new ArrayList<>();

        if (memberId == null) {
            errors.add("Member ID is required");
            return errors;
        }

        if (!memberRepository.findById(memberId).isPresent()) {
            errors.add("Member not found");
        }

        Optional<Membership> active = membershipRepository.findActiveByMember(memberId);
        if (active.isPresent()) {
            errors.add("Member already has an active membership");
        }

        if (collectivityId == null) {
            errors.add("Collectivity ID is required");
        } else if (!collectivityRepository.existsById(collectivityId)) {
            errors.add("Collectivity not found");
        }

        return errors;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^(\\+221)?(70|76|77|78)[0-9]{7}$");
    }
}