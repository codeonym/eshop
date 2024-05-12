package com.fsoteam.eshop.model;

import java.util.UUID;

public class PaymentMethod {
    private String paymentMethodId;
    private String paymentMethodName;
    private String paymentMethodDetails;

    public PaymentMethod() {
        this.paymentMethodId = UUID.randomUUID().toString();
        this.paymentMethodName = "";
        this.paymentMethodDetails = "";
    }
    public PaymentMethod(String paymentMethodId, String paymentMethodName, String paymentMethodDetails) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentMethodDetails = paymentMethodDetails;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentMethodDetails() {
        return paymentMethodDetails;
    }

    public void setPaymentMethodDetails(String paymentMethodDetails) {
        this.paymentMethodDetails = paymentMethodDetails;
    }
}