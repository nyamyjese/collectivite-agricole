package com.example.collectivite.entity;

import java.time.LocalDate;

public class Sponsorship {
    private Integer id;
    private Integer candidateId;
    private Integer sponsorId;
    private Integer targetCommunityId;
    private Integer sponsorCommunityId;
    private String relation;
    private LocalDate referralDate;

    public Sponsorship(Integer id,
                       Integer candidateId,
                       Integer sponsorId,
                       Integer targetCommunityId,
                       Integer sponsorCommunityId,
                       String relation,
                       LocalDate referralDate) {
        this.id = id;
        this.candidateId = candidateId;
        this.sponsorId = sponsorId;
        this.targetCommunityId = targetCommunityId;
        this.sponsorCommunityId = sponsorCommunityId;
        this.relation = relation;
        this.referralDate = referralDate;
    }

    public Sponsorship() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getTargetCommunityId() {
        return targetCommunityId;
    }

    public void setTargetCommunityId(Integer targetCommunityId) {
        this.targetCommunityId = targetCommunityId;
    }

    public Integer getSponsorCommunityId() {
        return sponsorCommunityId;
    }

    public void setSponsorCommunityId(Integer sponsorCommunityId) {
        this.sponsorCommunityId = sponsorCommunityId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDate referralDate) {
        this.referralDate = referralDate;
    }
}
