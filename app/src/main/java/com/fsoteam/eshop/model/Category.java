package com.fsoteam.eshop.model;

public class Category {
    private String categoryId;
    private String name;
    private String categoryDescription;
    private String image;
    private int productCount;

    public Category() {
        this.categoryId = "";
        this.name = "";
        this.categoryDescription = "";
        this.image = "";
        this.productCount = 0;
    }

    public Category(String categoryId, String name, String categoryDescription, String image, int productCount) {
        this.categoryId = categoryId;
        this.name = name;
        this.categoryDescription = categoryDescription;
        this.image = image;
        this.productCount = productCount;
    }


    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
    public boolean isCategoryEmpty() {
        return this.name.isEmpty() || this.productCount == 0;
    }
}
