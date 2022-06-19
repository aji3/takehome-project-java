package com.kazuya.payment.controller;

import com.kazuya.payment.dto.PaymentTransaction;
import com.kazuya.payment.service.PaymentService;
import com.kazuya.product.service.ProductService;
import com.kazuya.product.service.dto.ProductDto;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PaymentUiController {

  private ProductService productService;
  private PaymentService paymentService;

  public PaymentUiController(ProductService productService, PaymentService paymentService) {
    this.productService = productService;
    this.paymentService = paymentService;
  }

  @GetMapping("/checkout")
  public String startCheckout(@RequestParam String item, Model model) {
    Optional<ProductDto> productDtoOptional = productService.getProductById(Long.valueOf(item));
    if (productDtoOptional.isEmpty()) {
      model.addAttribute("message", "Unsupported item number");
      return "error.html";
    } else {
      ProductDto productDto = productDtoOptional.get();
      model.addAttribute("itemId", productDto.getId());
      model.addAttribute("title", productDto.getName());
      model.addAttribute("amount", productDto.getAmount());
    }
    return "checkout.html";
  }

  @GetMapping("/success.html")
  public String success(@RequestParam("payment_intent") String paymentIntent, Model model) {
    PaymentIntent transaction = paymentService.updatePaymentTransactionAfterConfirm(paymentIntent);
    model.addAttribute("paymentIntentId", paymentIntent);
    model.addAttribute("totalAmount", transaction.getAmount());
    return "success.html";
  }
}
