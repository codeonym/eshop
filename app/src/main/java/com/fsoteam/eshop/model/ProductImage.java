package com.fsoteam.eshop.model;

import java.util.UUID;

public class ProductImage {
    private String image;
    private String imageId;

    public ProductImage() {
        this.imageId = UUID.randomUUID().toString();
        this.image = "";
    }

    public ProductImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "image='" + image + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
