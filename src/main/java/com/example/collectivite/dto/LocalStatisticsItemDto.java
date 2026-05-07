package com.example.collectivite.dto;

public class LocalStatisticsItemDto {
    private MemberDescriptionDto memberDescription;
    private double earnedAmount;
    private double unpaidAmount;

    public LocalStatisticsItemDto() {}
    public LocalStatisticsItemDto(MemberDescriptionDto memberDescription, double earnedAmount, double unpaidAmount) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
    }

    public MemberDescriptionDto getMemberDescription() { return memberDescription; }
    public void setMemberDescription(MemberDescriptionDto memberDescription) { this.memberDescription = memberDescription; }
    public double getEarnedAmount() { return earnedAmount; }
    public void setEarnedAmount(double earnedAmount) { this.earnedAmount = earnedAmount; }
    public double getUnpaidAmount() { return unpaidAmount; }
    public void setUnpaidAmount(double unpaidAmount) { this.unpaidAmount = unpaidAmount; }
}