package com.example.collectivite.dto;

import com.example.collectivite.enums.MemberOccupation;

public class MemberRequest {
    private Integer memberId;
    private MemberOccupation memberOccupation;

    public MemberRequest(Integer memberId, MemberOccupation memberOccupation) {
        this.memberId = memberId;
        this.memberOccupation = memberOccupation;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public MemberOccupation getPoste() {
        return memberOccupation;
    }

    public void setPoste(MemberOccupation memberOccupation) {
        this.memberOccupation = memberOccupation;
    }
}
