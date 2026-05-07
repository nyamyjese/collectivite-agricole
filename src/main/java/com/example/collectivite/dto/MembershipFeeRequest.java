package com.example.collectivite.dto;

import java.math.BigDecimal;

public class MembershipFeeRequest {
    private String type;
    private BigDecimal amount;
    private String description;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
