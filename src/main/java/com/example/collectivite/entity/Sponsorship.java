package com.example.collectivite.entity;

import java.time.LocalDate;

public class Sponsorship {
    private String id;
    private String candidateId;
    private String sponsorId;
    private String targetCommunityId;
    private String sponsorCommunityId;
    private String relation;
    private LocalDate referralDate;

    public Sponsorship() {}

    public Sponsorship(String id, String candidateId, String sponsorId, String targetCommunityId,
                       String sponsorCommunityId, String relation, LocalDate referralDate) {
        this.id = id;
        this.candidateId = candidateId;
        this.sponsorId = sponsorId;
        this.targetCommunityId = targetCommunityId;
        this.sponsorCommunityId = sponsorCommunityId;
        this.relation = relation;
        this.referralDate = referralDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }
    public String getSponsorId() { return sponsorId; }
    public void setSponsorId(String sponsorId) { this.sponsorId = sponsorId; }
    public String getTargetCommunityId() { return targetCommunityId; }
    public void setTargetCommunityId(String targetCommunityId) { this.targetCommunityId = targetCommunityId; }
    public String getSponsorCommunityId() { return sponsorCommunityId; }
    public void setSponsorCommunityId(String sponsorCommunityId) { this.sponsorCommunityId = sponsorCommunityId; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public LocalDate getReferralDate() { return referralDate; }
    public void setReferralDate(LocalDate referralDate) { this.referralDate = referralDate; }
}