# e-commerce application

## Getting started

It requires JDK11 or above.
Run following command to run the application.
```
gradlew bootRun
```

## A paragraph or two about your solution: how does it work? Which Stripe APIs does it use?

### Sequence

1. When checkout screen opens, the screen calls `/api/payment/create-payment-intent` API with selected item id in post body.
2. `/api/payment/create-payment-intent` API calculates total amount and call Stripe `Create Payment Intent` API and get payment intent id.
3. 

### Stripe APIs

- Create a PaymentIntent
- Confirm a PaymentIntent
- Retrieve a PaymentIntent

## A paragraph or two about how you approached this problem: which docs did you use to complete the project? What challenges did you encounter?

### Approach

I used Stripe Elements approach, which gives flexibility to customer but requires more workload for development. (https://stripe.com/docs/payments/online-payments)

### Documents

As a whole, quick start explains well on how to implement PCI compliant payment, so it was straight forward to run an end to end flow.
(https://stripe.com/docs/payments/quickstart)

In addition to quick start, I have referred to the following documents.

- UI: 
    - https://stripe.com/docs/js/element/payment_element
- API: 
    - https://stripe.com/docs/api/payment_intents
    - https://stripe.com/docs/api/errors
- Testing
    - https://stripe.com/docs/testing

One type of document which I couldn't find was detailed explanation on Java class `com.stripe.model.PaymentIntent`.
So I looked into the class to understand how it works.


## A paragraph or two about how you might extend this if you were building a more robust instance of the same application.

### For more robustness

It depends on what kind of user experience that this e-commerce app wants to provide, so let's take below assumptions:
- Stock for selected products to be checked before creating PI
- No matter how user behaves on web site, prevent user to get charged twice
- 


- Use database and persist PaymentIntent with status when create-payment-intent API is called
- When 
- Implement webhook to get latest payment status to properly handle a case where customer closes browser before success screen appears.
