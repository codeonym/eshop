package com.fsoteam.eshop.model;

import java.util.UUID;

public class ShipmentDetails {

    private String shipmentId;
    private String shipmentTitle;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverCity;
    private String receiverCountry;
    private String receiverZipCode;
    private String receiverEmail;

    public ShipmentDetails() {
        this.shipmentId = UUID.randomUUID().toString();
        this.shipmentTitle = "";
        this.receiverName = "";
        this.receiverPhone = "";
        this.receiverAddress = "";
        this.receiverCity = "";
        this.receiverCountry = "";
        this.receiverZipCode = "";
        this.receiverEmail = "";
    }
    public ShipmentDetails(String shipmentId, String shipmentTitle, String receiverName, String receiverPhone, String receiverAddress, String receiverCity, String receiverCountry, String receiverZipCode, String receiverEmail) {
        this.shipmentId = shipmentId;
        this.shipmentTitle = shipmentTitle;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.receiverCity = receiverCity;
        this.receiverCountry = receiverCountry;
        this.receiverZipCode = receiverZipCode;
        this.receiverEmail = receiverEmail;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentTitle() {
        return shipmentTitle;
    }

    public void setShipmentTitle(String shipmentTitle) {
        this.shipmentTitle = shipmentTitle;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
