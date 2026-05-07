package com.example.collectivite.dto;

public class UpdateCollectivityInformationRequest {
    private String uniqueNumber;
    private String uniqueName;

    public String getUniqueNumber() { return uniqueNumber; }
    public void setUniqueNumber(String uniqueNumber) { this.uniqueNumber = uniqueNumber; }

    public String getUniqueName() { return uniqueName; }
    public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }
}
