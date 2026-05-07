package com.example.collectivite.entity;

import java.time.LocalDate;

public class Membership {
    private String id;
    private String memberId;
    private String collectivityId;
    private Poste poste;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership() {}

    public Membership(String id, String memberId, String collectivityId,
                      Poste poste, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.poste = poste;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() { return endDate == null; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public Poste getPoste() { return poste; }
    public void setPoste(Poste poste) { this.poste = poste; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}