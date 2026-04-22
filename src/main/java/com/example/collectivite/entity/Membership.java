package com.example.collectivite.entity;

import java.time.LocalDate;

public class Membership {
    private Integer id ;
    private Integer memberId;
    private Integer collectivityId;
    private Poste poste;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership() {
    }

    public Membership(Integer id,
                      Integer memberId,
                      Integer collectivityId,
                      Poste poste,
                      LocalDate startDate,
                      LocalDate endDate) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.poste = poste;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() {
        return endDate ==  null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(Integer collectivityId) {
        this.collectivityId = collectivityId;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
