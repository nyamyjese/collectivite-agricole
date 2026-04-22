package com.example.collectivite.validator;

import com.example.collectivite.dto.CreateCollectivityRequest;
import com.example.collectivite.dto.MemberRequest;
import com.example.collectivite.entity.Poste;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MemberRepository;
import com.example.collectivite.repository.MembershipRepository;

import java.util.ArrayList;
import java.util.List;

public class CollectivityCreationValidator {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CollectivityRepository collectivityRepository;

    public CollectivityCreationValidator(MemberRepository memberRepository,
                                         MembershipRepository membershipRepository,
                                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<String> validate(CreateCollectivityRequest request) {
        List<String> errors = new ArrayList<>();

        if (collectivityRepository.existsByUniqueNumber(request.getUniqueNumber())) {
            errors.add("Unique number already used");
        }
        if (collectivityRepository.existsByUniqueName(request.getUniqueName())) {
            errors.add("Unique name already used");
        }

        List<MemberRequest> members = request.getInitialMembers();
        if (members == null || members.size() < 10) {
            errors.add("At least 10 initial members required");
        } else {
            long seniorCount = members.stream()
                    .map(m -> memberRepository.findById(m.getMemberId()))
                    .filter(java.util.Optional::isPresent)
                    .filter(m -> memberRepository.getMembershipDurationInMonths(m.get().getId()) >= 6)
                    .count();
            if (seniorCount < 5) {
                errors.add("At least 5 members must have seniority >= 6 months");
            }

            boolean hasPresident = members.stream().anyMatch(m -> m.getPoste() == Poste.PRESIDENT);
            boolean hasVicePresident = members.stream().anyMatch(m -> m.getPoste() == Poste.PRESIDENT_ADJOINT);
            boolean hasTreasurer = members.stream().anyMatch(m -> m.getPoste() == Poste.TREASURER);
            boolean hasSecretary = members.stream().anyMatch(m -> m.getPoste() == Poste.SECRETARY);
            if (!hasPresident) errors.add("President position must be assigned");
            if (!hasVicePresident) errors.add("Vice-president position must be assigned");
            if (!hasTreasurer) errors.add("Treasurer position must be assigned");
            if (!hasSecretary) errors.add("Secretary position must be assigned");

            long distinctMembersForSpecific = members.stream()
                    .filter(m -> m.getPoste().specificPoste())
                    .map(MemberRequest::getMemberId)
                    .distinct()
                    .count();
            long specificPosteCount = members.stream()
                    .filter(m -> m.getPoste().specificPoste())
                    .count();
            if (distinctMembersForSpecific != specificPosteCount) {
                errors.add("A member cannot hold multiple specific positions");
            }
        }
        return errors;
    }
}