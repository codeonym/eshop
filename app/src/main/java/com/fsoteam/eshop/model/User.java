package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String userImage;
    private String userUid;
    private String userEmail;
    private String userAddress;
    private String userPhone;
    private Cart userCart;
    private List<Order> userOrders;
    private List<PaymentMethod> userPaymentMethods;
    private Wishlist userWishlist;
    private List<CreditCard> userCreditCard;

    public User() {
        this.userName = "";
        this.userImage = "";
        this.userUid = "";
        this.userEmail = "";
        this.userAddress = "";
        this.userPhone = "";
        this.userCart = new Cart();
        this.userOrders = new ArrayList<>();
        this.userPaymentMethods = new ArrayList<>();
        this.userWishlist = new Wishlist();
        this.userCreditCard = new ArrayList<>();
    }
    public User(String userName, String userImage, String userUid, String userEmail, String userAddress, String userPhone, Cart userCart, List<Order> userOrders, List<PaymentMethod> userPaymentMethods, Wishlist userWishlist, List<CreditCard> userCreditCard) {
        this.userName = userName;
        this.userImage = userImage;
        this.userUid = userUid;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userCart = userCart;
        this.userOrders = userOrders;
        this.userPaymentMethods = userPaymentMethods;
        this.userWishlist = userWishlist;
        this.userCreditCard = userCreditCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Cart getUserCart() {
        return userCart;
    }

    public void setUserCart(Cart userCart) {
        this.userCart = userCart;
    }

    public List<Order> getUserOrders() {
        return userOrders;
    }
    public boolean addUserOrder(Order order) {
        return this.userOrders.add(order);
    }

    public boolean removeUserOrder(Order order) {
        return this.userOrders.remove(order);
    }

    public void setUserOrders(List<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public List<PaymentMethod> getUserPaymentMethods() {
        return userPaymentMethods;
    }

    public void setUserPaymentMethods(List<PaymentMethod> userPaymentMethods) {
        this.userPaymentMethods = userPaymentMethods;
    }
    public boolean addUserPaymentMethod(PaymentMethod paymentMethod) {
        return this.userPaymentMethods.add(paymentMethod);
    }
    public boolean removeUserPaymentMethod(PaymentMethod paymentMethod) {
        return this.userPaymentMethods.remove(paymentMethod);
    }

    public Wishlist getUserWishlist() {
        return userWishlist;
    }

    public void setUserWishlist(Wishlist userWishlist) {
        this.userWishlist = userWishlist;
    }

    public List<CreditCard> getUserCreditCard() {
        return userCreditCard;
    }
    public boolean addUserCreditCard(CreditCard creditCard) {
        return this.userCreditCard.add(creditCard);
    }
    public boolean removeUserCreditCard(CreditCard creditCard) {
        return this.userCreditCard.remove(creditCard);
    }

    public void setUserCreditCard(List<CreditCard> userCreditCard) {
        this.userCreditCard = userCreditCard;
    }
}