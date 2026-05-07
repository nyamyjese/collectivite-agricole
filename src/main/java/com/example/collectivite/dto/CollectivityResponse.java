package com.example.collectivite.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CollectivityResponse {
    private String id;
    private String uniqueNumber;
    private String uniqueName;
    private String specialty;
    private LocalDate creationDate;
    private String city;
    private BigDecimal annualContribution;
    private LocalDate authorizationDate;
    private List<MemberResponse> members;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUniqueNumber() { return uniqueNumber; }
    public void setUniqueNumber(String uniqueNumber) { this.uniqueNumber = uniqueNumber; }
    public String getUniqueName() { return uniqueName; }
    public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public BigDecimal getAnnualContribution() { return annualContribution; }
    public void setAnnualContribution(BigDecimal annualContribution) { this.annualContribution = annualContribution; }
    public LocalDate getAuthorizationDate() { return authorizationDate; }
    public void setAuthorizationDate(LocalDate authorizationDate) { this.authorizationDate = authorizationDate; }
    public List<MemberResponse> getMembers() { return members; }
    public void setMembers(List<MemberResponse> members) { this.members = members; }
}
