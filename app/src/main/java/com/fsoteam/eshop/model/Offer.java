package com.fsoteam.eshop.model;

import java.time.LocalDateTime;

public class Offer {
    private String offerId;
    private String offerTitle;
    private String offerDescription;
    private String offerImage;
    private long offerStartDate;
    private long offerEndDate;
    public Offer() {
        this.offerId = "";
        this.offerTitle = "";
        this.offerDescription = "";
        this.offerImage = "";
        this.offerStartDate = 0;
        this.offerEndDate = 0;
    }
    public Offer(String offerId, String offerTitle, String offerDescription, String offerImage, Long offerStartDate, Long offerEndDate) {
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerDescription = offerDescription;
        this.offerImage = offerImage;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public Long getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(Long offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public Long getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(Long offerEndDate) {
        this.offerEndDate = offerEndDate;
    }
}
