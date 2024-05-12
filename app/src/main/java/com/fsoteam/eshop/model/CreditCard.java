package com.fsoteam.eshop.model;

import java.util.UUID;

public class CreditCard {
    private String cardId;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryDate;
    private String cardCVV;

    public CreditCard() {
        this.cardId = UUID.randomUUID().toString();
        this.cardNumber = "";
        this.cardHolderName = "";
        this.cardExpiryDate = "";
        this.cardCVV = "";
    }
    public CreditCard(String cardId, String cardNumber, String cardHolderName, String cardExpiryDate, String cardCVV) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}