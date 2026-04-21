package com.example.collectivite.dto;

import com.example.collectivite.model.Gender;
import com.example.collectivite.model.ModePayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AdmitMemberRequest {
    private Integer collectiviteId;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String adress;
    private String profession;
    private String phone;
    private String email;

    private List<SponsorshipRequest> sponsorshipRequests;

    private BigDecimal amountPaid;
    private ModePayment modePayment;
    private String referenceTransaction;

    public Integer getCollectiviteId() {
        return collectiviteId;
    }

    public void setCollectiviteId(Integer collectiviteId) {
        this.collectiviteId = collectiviteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SponsorshipRequest> getSponsorshipRequests() {
        return sponsorshipRequests;
    }

    public void setSponsorshipRequests(List<SponsorshipRequest> sponsorshipRequests) {
        this.sponsorshipRequests = sponsorshipRequests;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public ModePayment getModePayment() {
        return modePayment;
    }

    public void setModePayment(ModePayment modePayment) {
        this.modePayment = modePayment;
    }

    public String getReferenceTransaction() {
        return referenceTransaction;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }
}
