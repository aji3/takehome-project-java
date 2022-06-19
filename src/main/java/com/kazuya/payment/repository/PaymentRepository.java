package com.kazuya.payment.repository;

import com.kazuya.payment.dto.PaymentTransaction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PaymentRepository {
  private Map<String, PaymentTransaction> MOCK_DB = new HashMap<>();

  public void registerPaymentTransaction(PaymentTransaction paymentTransaction) {
    MOCK_DB.put(paymentTransaction.getPaymentIntentId(), paymentTransaction);
  }

  public Optional<PaymentTransaction> getPaymentTransaction(String paymentIntentId) {
    return Optional.ofNullable(MOCK_DB.get(paymentIntentId));
  }

  public void updatePaymentTransaction(PaymentTransaction paymentTransaction) {
    MOCK_DB.put(paymentTransaction.getPaymentIntentId(), paymentTransaction);
  }
}
