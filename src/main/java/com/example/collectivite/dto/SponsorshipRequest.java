package com.example.collectivite.dto;

public class SponsorshipRequest {
    private Integer sponsorshipId;
    private String relation;

    public SponsorshipRequest(Integer sponsorshipId, String relation) {
        this.sponsorshipId = sponsorshipId;
        this.relation = relation;
    }

    public Integer getSponsorshipId() {
        return sponsorshipId;
    }

    public void setSponsorshipId(Integer sponsorshipId) {
        this.sponsorshipId = sponsorshipId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
