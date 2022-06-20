# e-commerce application

## Getting started

Requires JDK11 or above.

Run this command to run the application.
```
gradlew bootRun
```

## A paragraph or two about your solution: how does it work? Which Stripe APIs does it use?

### Sequence

![Sequence](/assets/images/sequence.png)

### Stripe APIs

- Create a PaymentIntent
- Confirm a PaymentIntent
- Retrieve a PaymentIntent

## A paragraph or two about how you approached this problem: which docs did you use to complete the project? What challenges did you encounter?

### Approach

I used Stripe Elements approach, which gives flexibility to customer but requires more workload for development. (https://stripe.com/docs/payments/online-payments)

### Documents

Quick start explains well on how to implement PCI compliant payment, so it was straight forward to run end to end flow.
(https://stripe.com/docs/payments/quickstart)

In addition to quick start, I have referred to the following documents.

- UI: 
    - https://stripe.com/docs/js/element/payment_element
- API: 
    - https://stripe.com/docs/api/payment_intents
    - https://stripe.com/docs/api/errors
- Testing
    - https://stripe.com/docs/testing
- Dashboard
    - https://stripe.com/docs/receipts

### Challenges

1. I couldn't find detail description for Java class `com.stripe.model.PaymentIntent`.
So I looked into the class to understand how it works.

## A paragraph or two about how you might extend this if you were building a more robust instance of the same application

Since this application doesn't use database, there are critical user experience issues. For example:
- No stock management, which could cause cancelling order after payment settled
- No purchase history, which could end up purchasing products multiple times

To deal with above issues, e-commerce application manages data like Stock, PurchaseHistory, etc., and records for these table need to be consistent with PaymentIntent.
Following flow shows example of how these data need to be synchronized.

![Sequence](/assets/images/datamanagement.png)
 
Assuming data is managed like this, webhook can be used for more robustness.
In above flow, there can be a case where payment settled but application doesn't know that.
It can be caused by user to close browser after credit card info sent but before success screen to open.
Webhook informs payment status to the application so that data can be kept consistent.
