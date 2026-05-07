package com.example.collectivite.dto;

import com.example.collectivite.dto.MemberRequest;
import java.math.BigDecimal;
import java.util.List;

public class CreateCollectivityRequest {
    private String uniqueNumber;
    private String uniqueName;
    private String specialty;
    private String city;
    private BigDecimal annualContribution;
    private List<MemberRequest> initialMembers;

    public String getUniqueNumber() { return uniqueNumber; }
    public void setUniqueNumber(String uniqueNumber) { this.uniqueNumber = uniqueNumber; }

    public String getUniqueName() { return uniqueName; }
    public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public BigDecimal getAnnualContribution() { return annualContribution; }
    public void setAnnualContribution(BigDecimal annualContribution) { this.annualContribution = annualContribution; }

    public List<MemberRequest> getInitialMembers() { return initialMembers; }
    public void setInitialMembers(List<MemberRequest> initialMembers) { this.initialMembers = initialMembers; }
}