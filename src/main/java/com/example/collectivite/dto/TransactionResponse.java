package com.example.collectivite.dto;

import com.example.collectivite.entity.ModePayment;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {
    private String paymentId;
    private String memberId;
    private String memberName;
    private BigDecimal amount;
    private ModePayment mode;
    private String reference;
    private LocalDate paymentDate;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public ModePayment getMode() {return mode; }
    public void setMode(ModePayment mode) {this.mode = mode;}
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
