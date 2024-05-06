package com.fsoteam.eshop.model;

import android.os.Build;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private String productName;
    private String productId;
    private float productPrice;
    private String productDes;
    private float productRating;
    private String productDisCount;
    private boolean productHave;
    private String productBrand;
    private String productImage;
    private Category productCategory;
    private String productNote;
    private int productSales;
    private long productAddDate;
    private int productQuantity;
    public Product() {
        this.productName = "";
        this.productId = "";
        this.productPrice = 0.0f;
        this.productDes = "";
        this.productRating = 0;
        this.productDisCount = "";
        this.productHave = false;
        this.productBrand = "";
        this.productImage = "";
        this.productCategory = new Category();
        this.productNote = "";
        this.productQuantity = 0;
        this.productSales = 0;
        this.productAddDate = 0;
    }

    public Product(String productName, String productId, float productPrice, String productDes, float productRating, String productDisCount, boolean productHave, String productBrand, String productImage, Category productCategory, String productNote, int productQuantity, int productSales, long productAddDate) {
        this.productName = productName;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productDes = productDes;
        this.productRating = productRating;
        this.productDisCount = productDisCount;
        this.productHave = productHave;
        this.productBrand = productBrand;
        this.productImage = productImage;
        this.productCategory = productCategory;
        this.productNote = productNote;
        this.productQuantity = productQuantity;
        this.productSales = productSales;
        this.productAddDate = productAddDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public float getProductRating() {
        return productRating;
    }

    public void setProductRating(float productRating) {
        this.productRating = productRating;
    }

    public String getProductDisCount() {
        return productDisCount;
    }

    public void setProductDisCount(String productDisCount) {
        this.productDisCount = productDisCount;
    }

    public boolean isProductHave() {
        return productHave;
    }

    public void setProductHave(boolean productHave) {
        this.productHave = productHave;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductNote() {
        return productNote;
    }

    public void setProductNote(String productNote) {
        this.productNote = productNote;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductSales() {
        return productSales;
    }

    public void setProductSales(int productSales) {
        this.productSales = productSales;
    }

    public long getProductAddDate() {
        return productAddDate;
    }

    public void setProductAddDate(long productAddDate) {
        this.productAddDate = productAddDate;
    }
}