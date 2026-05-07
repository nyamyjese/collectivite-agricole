package com.example.collectivite.dto;

import com.example.collectivite.entity.ModePayment;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponse {
    private String id;
    private String memberId;
    private String collectivityId;
    private BigDecimal amount;
    private ModePayment mode;
    private String reference;
    private LocalDate paymentDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ModePayment getMode() {
        return mode;
    }

    public void setMode(ModePayment mode) {
        this.mode = mode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
