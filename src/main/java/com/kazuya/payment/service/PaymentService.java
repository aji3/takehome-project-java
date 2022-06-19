package com.kazuya.payment.service;

import com.kazuya.payment.adapter.StripePaymentAdapter;
import com.kazuya.payment.dto.CreatePaymentIntentRequest;
import com.kazuya.product.service.ProductService;
import com.kazuya.product.service.dto.ProductDto;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

  private StripePaymentAdapter paymentAdapter;
  private ProductService productService;

  public PaymentService(StripePaymentAdapter paymentAdapter, ProductService productService) {
    this.paymentAdapter = paymentAdapter;
    this.productService = productService;
  }

  public PaymentIntent startPayment(CreatePaymentIntentRequest request) {
    PaymentIntent paymentIntent = createPaymentIntent(request);
    return paymentIntent;
  }

  public PaymentIntent getPaymentIntent(String paymentIntentId) {
    PaymentIntent paymentIntent = paymentAdapter.fetchPaymentIntent(paymentIntentId);
    return paymentIntent;
  }

  private PaymentIntent createPaymentIntent(CreatePaymentIntentRequest request) {
    PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
            .setAmount(calculateTotalAmount(request))
            .setCurrency("usd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build())
            .build();

    return this.paymentAdapter.createPaymentIntent(params);
  }

  private long calculateTotalAmount(CreatePaymentIntentRequest request) {
    return request.getItems().stream()
        .map(this::calculateItemAmount)
        .collect(Collectors.summingLong(Long::longValue));
  }

  private long calculateItemAmount(CreatePaymentIntentRequest.Item item) {
    Optional<ProductDto> productDtoOptional = productService.getProductById(item.getItemId());
    if (productDtoOptional.isPresent()) {
      return productDtoOptional.get().getAmount() * item.getCount();
    } else {
      return 0L;
    }
  }
}
