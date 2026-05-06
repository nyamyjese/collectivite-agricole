package com.example.collectivite.dto;

import com.example.collectivite.enums.PaymentMode;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {
    private Integer paymentId;
    private Integer memberId;
    private String memberName;
    private BigDecimal amount;
    private PaymentMode mode;
    private String reference;
    private LocalDate paymentDate;

    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }
    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentMode getMode() { return mode; }
    public void setMode(PaymentMode mode) { this.mode = mode; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
