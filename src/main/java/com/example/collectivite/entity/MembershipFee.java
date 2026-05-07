package com.example.collectivite.entity;

import java.math.BigDecimal;

public class MembershipFee {
    private String id;
    private String collectivityId;
    private String type;
    private BigDecimal amount;
    private String description;

    public MembershipFee() {}

    public MembershipFee(String id, String collectivityId, String type, BigDecimal amount, String description) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}