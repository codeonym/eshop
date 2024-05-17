package com.fsoteam.eshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Offer {
    private String offerId;
    private String offerTitle;
    private String offerDescription;
    private String offerImage;
    private long offerStartDate;
    private long offerEndDate;
    private int offerDiscount;
    private List<Product> offerItems;
    private String offerBadge;
    public Offer() {
        this.offerDiscount = 0;
        this.offerId = UUID.randomUUID().toString();
        this.offerTitle = "";
        this.offerDescription = "";
        this.offerImage = "";
        this.offerBadge = "";
        this.offerItems = new ArrayList<>();
        this.offerStartDate = 0;
        this.offerEndDate = 0;

    }
    public Offer(String offerId, int offerDiscount, String offerTitle,List<Product> offerItems,String offerBadge, String offerDescription, String offerImage, Long offerStartDate, Long offerEndDate) {
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerDiscount = offerDiscount;
        this.offerItems = offerItems;
        this.offerDescription = offerDescription;
        this.offerImage = offerImage;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
        this.offerBadge = offerBadge;
    }

    public int getOfferDiscount() {
        return offerDiscount;
    }

    public void setOfferDiscount(int offerDiscount) {
        this.offerDiscount = offerDiscount;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferBadge() {
        return offerBadge;
    }

    public void setOfferBadge(String offerBadge) {
        this.offerBadge = offerBadge;
    }

    public void setOfferStartDate(long offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public void setOfferEndDate(long offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public List<Product> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(List<Product> offerItems) {
        this.offerItems = offerItems;
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

    public Long getOfferEndDate() {
        return offerEndDate;
    }

}
