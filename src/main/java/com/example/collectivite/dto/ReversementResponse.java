package com.example.collectivite.dto;

import java.time.LocalDate;
import java.util.List;

public class ReversementResponse {

    private String collectivityId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPeriodicContributions;
    private double reversalPercentage;
    private int totalReversedToFederation;
    private List<ReversementDetail> details;

    // Classe interne pour le détail
    public static class ReversementDetail {
        private String contributionId;
        private LocalDate collectionDate;
        private int contributionAmount;
        private int reversedAmount;


        public String getContributionId() { return contributionId; }
        public void setContributionId(String contributionId) { this.contributionId = contributionId; }

        public LocalDate getCollectionDate() { return collectionDate; }
        public void setCollectionDate(LocalDate collectionDate) { this.collectionDate = collectionDate; }

        public int getContributionAmount() { return contributionAmount; }
        public void setContributionAmount(int contributionAmount) { this.contributionAmount = contributionAmount; }

        public int getReversedAmount() { return reversedAmount; }
        public void setReversedAmount(int reversedAmount) { this.reversedAmount = reversedAmount; }
    }


    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getTotalPeriodicContributions() { return totalPeriodicContributions; }
    public void setTotalPeriodicContributions(int totalPeriodicContributions) { this.totalPeriodicContributions = totalPeriodicContributions; }

    public double getReversalPercentage() { return reversalPercentage; }
    public void setReversalPercentage(double reversalPercentage) { this.reversalPercentage = reversalPercentage; }

    public int getTotalReversedToFederation() { return totalReversedToFederation; }
    public void setTotalReversedToFederation(int totalReversedToFederation) { this.totalReversedToFederation = totalReversedToFederation; }

    public List<ReversementDetail> getDetails() { return details; }
    public void setDetails(List<ReversementDetail> details) { this.details = details; }
}