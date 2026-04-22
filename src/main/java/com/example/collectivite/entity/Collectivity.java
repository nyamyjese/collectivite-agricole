package com.example.collectivite.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Collectivity {
    private Integer id;
    private String uniqueNumber;
    private String uniqueName;
    private String specialty;
    private LocalDate creationDate;
    private String city;
    private BigDecimal annualContribution;
    private LocalDate authorizationDate;

    public Collectivity() {}

    public Collectivity(Integer id,
                        String uniqueNumber,
                        String uniqueName,
                        String specialty,
                        LocalDate creationDate,
                        String city,
                        BigDecimal annualContribution,
                        LocalDate authorizationDate) {
        this.id = id;
        this.uniqueNumber = uniqueNumber;
        this.uniqueName = uniqueName;
        this.specialty = specialty;
        this.creationDate = creationDate;
        this.city = city;
        this.annualContribution = annualContribution;
        this.authorizationDate = authorizationDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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
}