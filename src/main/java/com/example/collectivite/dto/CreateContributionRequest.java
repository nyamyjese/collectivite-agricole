package com.example.collectivite.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateContributionRequest {
    @NotBlank(message = "Member ID is required")
    private String memberId;

    @NotBlank(message = "Type is required")
    private String type; // PERIODIQUE_MENSUELLE, PERIODIQUE_ANNUELLE, PONCTUELLE

    @Min(value = 1, message = "Amount must be positive")
    private int amount;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode; // ESPECE, VIREMENT_BANCAIRE, MOBILE_MONEY

    private String transactionReference; // optional for ESPECE

    @NotNull(message = "Collection date is required")
    private LocalDate collectionDate;

    // Getters et setters
    // ...
}