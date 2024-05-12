package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product {
    private String productName;
    private String productId;
    private float productPrice;
    private String productDes;
    private float productRating;
    private String productDisCount;
    private float productPriceRaw;
    private boolean productHave;
    private String productBrand;
    private List<ProductImage> productImages;
    private String productThumbnail;
    private int likesCount;
    private String productModel;
    private Category productCategory;
    private String productSubCategory;
    private String productNote;
    private int productSales;
    private long productAddDate;
    private int productQuantity;
    private boolean isProductLiked;
    private String productImage;
    private int productMaxPurchasePerUser;
    private String productCurrency;
    public Product() {

        this.productModel = "";
        this.likesCount = 0;
        this.productName = "";
        this.productId = UUID.randomUUID().toString();
        this.productPrice = 0.0f;
        this.productSubCategory = "";
        this.productDes = "";
        this.productRating = 0;
        this.productDisCount = "";
        this.productPriceRaw = 0.0f;
        this.productHave = false;
        this.productBrand = "";
        this.productImages = new ArrayList<ProductImage>();
        this.productCategory = new Category();
        this.productNote = "";
        this.productQuantity = 0;
        this.productSales = 0;
        this.productAddDate = 0;
        this.productThumbnail = "";
        this.isProductLiked = false;
        this.productImage = "";
        this.productMaxPurchasePerUser = 1;
        this.productCurrency = "DH";
    }

    public Product(String productId, String productName, String productDes, String productBrand, String productModel,Category productCategory,String productSubCategory, String productImage, String productNote, float productPrice, float productPriceRaw, float productRating, String productDisCount, boolean productHave, int likesCount, ArrayList<ProductImage> productImages, String productThumbnail, int productQuantity, int productSales, long productAddDate, boolean isProductLiked, int productMaxPurchasePerUser, String productCurrency) {
        this.productName = productName;
        this.productModel = productModel;
        this.productPriceRaw = productPriceRaw;
        this.productId = productId;
        this.productThumbnail = productThumbnail;
        this.productPrice = productPrice;
        this.productDes = productDes;
        this.productRating = productRating;
        this.productDisCount = productDisCount;
        this.productHave = productHave;
        this.productBrand = productBrand;
        this.productImages = productImages;
        this.productCategory = productCategory;
        this.productNote = productNote;
        this.productQuantity = productQuantity;
        this.productSales = productSales;
        this.productAddDate = productAddDate;
        this.isProductLiked = isProductLiked;
        this.productImage = productImage;
        this.likesCount = likesCount;
        this.productMaxPurchasePerUser = productMaxPurchasePerUser;
        this.productCurrency = productCurrency;
        this.productSubCategory = productSubCategory;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public int getProductMaxPurchasePerUser() {
        return productMaxPurchasePerUser;
    }

    public void setProductMaxPurchasePerUser(int productMaxPurchasePerUser) {
        this.productMaxPurchasePerUser = productMaxPurchasePerUser;
    }

    public String getProductCurrency() {
        return productCurrency;
    }

    public void setProductCurrency(String productCurrency) {
        this.productCurrency = productCurrency;
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

    public boolean isProductLiked() {
        return isProductLiked;
    }

    public void setProductLiked(boolean productLiked) {
        isProductLiked = productLiked;
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

    public List<ProductImage> getProductImages() {
        return productImages;
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

    public float getProductPriceRaw() {
        return productPriceRaw;
    }

    public void setProductPriceRaw(float productPriceRaw) {
        this.productPriceRaw = productPriceRaw;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", productPrice=" + productPrice +
                ", productDes='" + productDes + '\'' +
                ", productRating=" + productRating +
                ", productDisCount='" + productDisCount + '\'' +
                ", productPriceRaw=" + productPriceRaw +
                ", productHave=" + productHave +
                ", productBrand='" + productBrand + '\'' +
                ", productImage=" + productImages +
                ", productThumbnail='" + productThumbnail + '\'' +
                ", likesCount=" + likesCount +
                ", productModel='" + productModel + '\'' +
                ", productCategory=" + productCategory +
                ", productNote='" + productNote + '\'' +
                ", productSales=" + productSales +
                ", productAddDate=" + productAddDate +
                ", productQuantity=" + productQuantity +
                '}';
    }
}