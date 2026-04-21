package com.example.collectivite.dto;

import com.example.collectivite.model.Poste;

public class MemberRequest {
    private Integer memberId;
    private Poste poste;

    public MemberRequest(Integer memberId, Poste poste) {
        this.memberId = memberId;
        this.poste = poste;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }
}
