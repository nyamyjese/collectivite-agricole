package com.example.collectivite.dto;

import com.example.collectivite.enums.PaymentMode;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberPaymentRequest {
    private BigDecimal amount;
    private PaymentMode mode;
    private String reference;
    private LocalDate paymentDate;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentMode getMode() { return mode; }
    public void setMode(PaymentMode mode) { this.mode = mode; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
