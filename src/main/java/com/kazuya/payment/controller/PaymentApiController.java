package com.kazuya.payment.controller;

import com.kazuya.payment.dto.CreatePaymentIntentRequest;
import com.kazuya.payment.dto.CreatePaymentIntentResponse;
import com.kazuya.payment.service.PaymentService;
import com.stripe.model.PaymentIntent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentApiController {

  private PaymentService paymentService;

  public PaymentApiController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping(value = "/create-payment-intent", produces = "application/json")
  public CreatePaymentIntentResponse createPaymentIntent(@RequestBody CreatePaymentIntentRequest request) {
    PaymentIntent paymentIntent = paymentService.startPayment(request);
    return new CreatePaymentIntentResponse(paymentIntent.getClientSecret());
  }
}
