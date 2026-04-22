package com.example.collectivite.entity;

import java.time.LocalDate;

public class Collectivite {
    private Integer id;
    private String nom;
    private String localite;
    private String specialiteAgricole;
    private LocalDate dateCreation;
    private String statut;
    private int nombreMembres;
    private int montantCotisationsAnnuelles;

    public  Collectivite(Integer id,
                         String nom,
                         String localite,
                         String specialiteAgricole,
                         LocalDate dateCreation,
                         String statut,
                         int nombreMembres,
                         int montantCotisationsAnnuelles) {
        this.id = id;
        this.nom = nom;
        this.localite = localite;
        this.specialiteAgricole = specialiteAgricole;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.nombreMembres = nombreMembres;
        this.montantCotisationsAnnuelles = montantCotisationsAnnuelles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getSpecialiteAgricole() {
        return specialiteAgricole;
    }

    public void setSpecialiteAgricole(String specialiteAgricole) {
        this.specialiteAgricole = specialiteAgricole;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNombreMembres() {
        return nombreMembres;
    }

    public void setNombreMembres(int nombreMembres) {
        this.nombreMembres = nombreMembres;
    }

    public int getMontantCotisationsAnnuelles() {
        return montantCotisationsAnnuelles;
    }

    public void setMontantCotisationsAnnuelles(int montantCotisationsAnnuelles) {
        this.montantCotisationsAnnuelles = montantCotisationsAnnuelles;
    }
}