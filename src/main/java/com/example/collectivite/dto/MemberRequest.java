package com.example.collectivite.dto;

import com.example.collectivite.entity.Poste;

public class MemberRequest {
    private String memberId;
    private Poste poste;

    public MemberRequest(String memberId, Poste poste) {
        this.memberId = memberId;
        this.poste =  poste;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }
}
