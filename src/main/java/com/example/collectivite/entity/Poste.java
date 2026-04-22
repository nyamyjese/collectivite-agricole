package com.example.collectivite.entity;

public enum Poste {
    PRESIDENT,
    PRESIDENT_ADJOINT,
    TREASURER,
    SECRETARY,
    CONFIRMED_MEMBER,
    JUNIOR_MEMBER;

    public boolean specificPoste(){
        return this == PRESIDENT
                || this == PRESIDENT_ADJOINT
                || this == TREASURER
                || this == SECRETARY;
    }
}
