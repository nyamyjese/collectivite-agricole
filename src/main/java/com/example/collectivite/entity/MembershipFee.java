package com.example.collectivite.entity;

public class MembershipFee {
    private Integer id;
    private Integer collectivityId;
    private String type;
    private java.math.BigDecimal amount;
    private String description;

    public MembershipFee() {}

    public MembershipFee(Integer id, Integer collectivityId, String type, java.math.BigDecimal amount, String description) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCollectivityId() { return collectivityId; }
    public void setCollectivityId(Integer collectivityId) { this.collectivityId = collectivityId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public java.math.BigDecimal getAmount() { return amount; }
    public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}