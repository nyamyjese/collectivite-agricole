package com.example.collectivite.dto;

public class SponsorshipRequest {
    private String sponsorshipId;
    private String relation;

    public SponsorshipRequest(String sponsorshipId, String relation) {
        this.sponsorshipId = sponsorshipId;
        this.relation = relation;
    }

    public String getSponsorshipId() {
        return sponsorshipId;
    }

    public void setSponsorshipId(String sponsorshipId) {
        this.sponsorshipId = sponsorshipId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
