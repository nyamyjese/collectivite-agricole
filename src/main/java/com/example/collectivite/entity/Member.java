package com.example.collectivite.entity;

import java.time.LocalDate;

public class Member {
    private Integer id;
    private String name;
    private String firstName;
    private LocalDate birthDate;
    private Gender gender;
    private String adress;
    private String profession;
    private String phone;
    private String email;
    private LocalDate joinDate;
    private MemberStatus status;

    public Member(Integer id,
                  String name,
                  String firstName,
                  LocalDate birthDate,
                  Gender gender,
                  String adress,
                  String profession,
                  String phone,
                  String email,
                  LocalDate joinDate,
                  MemberStatus status) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.adress = adress;
        this.profession = profession;
        this.phone = phone;
        this.email = email;
        this.joinDate = joinDate;
        this.status = status;
    }

    public Member() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }
}

