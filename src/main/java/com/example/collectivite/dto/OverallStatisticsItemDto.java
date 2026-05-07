package com.example.collectivite.dto;

public class OverallStatisticsItemDto {
    private CollectivityInformationDto collectivityInformation;
    private int newMembersNumber;
    private double overallMemberCurrentDuePercentage;

    public OverallStatisticsItemDto() {}
    public OverallStatisticsItemDto(CollectivityInformationDto collectivityInformation, int newMembersNumber, double overallMemberCurrentDuePercentage) {
        this.collectivityInformation = collectivityInformation;
        this.newMembersNumber = newMembersNumber;
        this.overallMemberCurrentDuePercentage = overallMemberCurrentDuePercentage;
    }

    public CollectivityInformationDto getCollectivityInformation() { return collectivityInformation; }
    public void setCollectivityInformation(CollectivityInformationDto collectivityInformation) { this.collectivityInformation = collectivityInformation; }
    public int getNewMembersNumber() { return newMembersNumber; }
    public void setNewMembersNumber(int newMembersNumber) { this.newMembersNumber = newMembersNumber; }
    public double getOverallMemberCurrentDuePercentage() { return overallMemberCurrentDuePercentage; }
    public void setOverallMemberCurrentDuePercentage(double overallMemberCurrentDuePercentage) { this.overallMemberCurrentDuePercentage = overallMemberCurrentDuePercentage; }
}