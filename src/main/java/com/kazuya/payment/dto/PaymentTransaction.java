package com.kazuya.payment.dto;

public class PaymentTransaction {
  private String clientSecret;
  private String paymentIntentId;
  private long totalAmount;
  private PaymentStatus status;

  public PaymentTransaction(String clientSecret, String paymentIntentId, long totalAmount) {
    this.clientSecret = clientSecret;
    this.paymentIntentId = paymentIntentId;
    this.totalAmount = totalAmount;
    this.status = PaymentStatus.IN_PROGRESS;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public String getPaymentIntentId() {
    return paymentIntentId;
  }

  public void setPaymentIntentId(String paymentIntentId) {
    this.paymentIntentId = paymentIntentId;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }
}
