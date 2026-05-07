package com.example.collectivite.dto;

import java.math.BigDecimal;

public class MembershipFeeResponse {
    private String id;
    private String collectivityId;
    private String type;
    private BigDecimal amount;
    private String description;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
