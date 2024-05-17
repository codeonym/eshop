package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String userName;
    private String userImage;
    private String userUid;
    private String userEmail;

    private Map<String, ShipmentDetails> userShipmentAddress;
    private String userPhone;
    private Cart userCart;
    private Map<String, Order> userOrders;
    private List<PaymentMethod> userPaymentMethods;
    private Wishlist userWishlist;
    private List<CreditCard> userCreditCard;

    public User() {
        this.userName = "";
        this.userImage = "";
        this.userUid = "";
        this.userEmail = "";
        this.userShipmentAddress = new HashMap<String, ShipmentDetails>();
        this.userPhone = "";
        this.userCart = new Cart();
        this.userOrders = new HashMap<String, Order>();
        this.userPaymentMethods = new ArrayList<PaymentMethod>();
        this.userWishlist = new Wishlist();
        this.userCreditCard = new ArrayList<CreditCard>();
    }
    public User(String userName, String userImage, String userUid, String userEmail, Map<String, ShipmentDetails> userShipmentAddress, String userPhone, Cart userCart, HashMap<String, Order> userOrders, List<PaymentMethod> userPaymentMethods, Wishlist userWishlist, List<CreditCard> userCreditCard) {
        this.userName = userName;
        this.userImage = userImage;
        this.userUid = userUid;
        this.userEmail = userEmail;
        this.userShipmentAddress = userShipmentAddress;
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

    public Map<String, ShipmentDetails> getUserShipmentAddress() {
        return userShipmentAddress;
    }

    public void setUserShipmentAddress(Map<String, ShipmentDetails> userShipmentAddress) {
        this.userShipmentAddress = userShipmentAddress;
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

    public Map<String, Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Map<String, Order> userOrders) {
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