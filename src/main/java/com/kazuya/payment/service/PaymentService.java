package com.kazuya.payment.service;

import com.kazuya.payment.adapter.StripePaymentAdapter;
import com.kazuya.payment.dto.CreatePaymentIntentRequest;
import com.kazuya.payment.dto.PaymentStatus;
import com.kazuya.payment.dto.PaymentTransaction;
import com.kazuya.payment.repository.PaymentRepository;
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
  private PaymentRepository paymentRepository;

  public PaymentService(
      StripePaymentAdapter paymentAdapter,
      ProductService productService,
      PaymentRepository paymentRepository) {
    this.paymentAdapter = paymentAdapter;
    this.productService = productService;
    this.paymentRepository = paymentRepository;
  }

  public PaymentIntent startPayment(CreatePaymentIntentRequest request) {
    PaymentIntent paymentIntent = createPaymentIntent(request);
//    PaymentTransaction paymentTransaction =
//        new PaymentTransaction(
//            paymentIntent.getClientSecret(), paymentIntent.getId(), paymentIntent.getAmount());
//    registerTransaction(paymentTransaction);
    return paymentIntent;
  }

  public PaymentIntent updatePaymentTransactionAfterConfirm(String paymentIntentId) {
    PaymentIntent paymentIntent = paymentAdapter.fetchPaymentIntent(paymentIntentId);
    Optional<PaymentTransaction> transaction =
            paymentRepository.getPaymentTransaction(paymentIntentId);
    if (transaction.isPresent()) {
      if ("succeeded".equals(paymentIntent.getStatus())) {
        transaction.get().setStatus(PaymentStatus.COMPLETED);
      } else {
        // need to know how redirect status is returned for error case
      }
    } else {
      throw new IllegalArgumentException("Illegal payment intent id specified.");
    }
    paymentRepository.updatePaymentTransaction(transaction.get());
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

  private void registerTransaction(PaymentTransaction transaction) {
    paymentRepository.registerPaymentTransaction(transaction);
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
