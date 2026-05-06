package com.example.collectivite.enums;

public enum MemberOccupation {
    PRESIDENT, VICE_PRESIDENT, SECRETARY, TREASURER, SENIOR, JUNIOR;

    public boolean specificPoste(){
        return this == PRESIDENT
                || this == VICE_PRESIDENT
                || this == TREASURER
                || this == SECRETARY;
    }
}
