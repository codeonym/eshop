package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private String shopName;
    private String shopImage;
    private String shopAddress;
    private String shopWebsite;
    private List<Product> shopProducts;

    public Shop() {
        this.shopName = "";
        this.shopImage = "";
        this.shopAddress = "";
        this.shopProducts = new ArrayList<>();
        this.shopWebsite = "";
    }


    public Shop(String shopName, String shopImage, String shopAddress,String shopWebsite, List<Product> shopProducts) {
        this.shopName = shopName;
        this.shopImage = shopImage;
        this.shopAddress = shopAddress;
        this.shopWebsite = shopWebsite;
        this.shopProducts = shopProducts;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<Product> getShopProducts() {
        return shopProducts;
    }
    public boolean isShopEmpty() {
        return shopProducts.isEmpty();
    }
    public boolean addProduct(Product product) {
        return this.shopProducts.add(product);
    }
    public boolean removeProduct(Product product) {
        return this.shopProducts.remove(product);
    }
    public void setShopProducts(List<Product> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public String getShopWebsite() {
        return shopWebsite;
    }

    public void setShopWebsite(String shopWebsite) {
        this.shopWebsite = shopWebsite;
    }
}
