package com.example.collectivite.entity;

import com.example.collectivite.enums.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private Integer id;
    private Integer memberId;
    private Integer collectivityId;
    private BigDecimal amount;
    private PaymentMode mode;
    private String reference;
    private LocalDate paymentDate;

    public Payment() {}

    public Payment(Integer id, Integer memberId, Integer collectivityId,
                   BigDecimal amount, PaymentMode mode, String reference,
                   LocalDate paymentDate) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.amount = amount;
        this.mode = mode;
        this.reference = reference;
        this.paymentDate = paymentDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }

    public Integer getCollectivityId() { return collectivityId; }
    public void setCollectivityId(Integer collectivityId) { this.collectivityId = collectivityId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentMode getMode() { return mode; }
    public void setMode(PaymentMode mode) { this.mode = mode; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}