package com.kazuya.payment.adapter;

import com.kazuya.app.exception.StandardException;
import com.kazuya.app.util.SecretsConfig;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentAdapter {

  public StripePaymentAdapter() {
    Stripe.apiKey = SecretsConfig.getInstance().getApiKey();
  }

  public PaymentIntent createPaymentIntent(PaymentIntentCreateParams params) {
    try {
      return PaymentIntent.create(params);
    } catch (StripeException e) {
      throw new StandardException(e.getStatusCode(), e.getMessage(), e);
    }
  }

  public PaymentIntent fetchPaymentIntent(String paymentIntentId) {
    try {
      return PaymentIntent.retrieve(paymentIntentId);
    } catch (StripeException e) {
      throw new StandardException(e.getStatusCode(), e.getMessage(), e);
    }
  }
}
