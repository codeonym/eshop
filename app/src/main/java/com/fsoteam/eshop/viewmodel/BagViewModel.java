package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.adapter.CartAdapter;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BagViewModel extends ViewModel {
    private MutableLiveData<List<OrderItem>> cartItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ShipmentDetails>> shippingAddressesLiveData = new MutableLiveData<>();

    private String userId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userRef = dbRef.child(DbCollections.USERS).child(userId);
    private DatabaseReference cartRef = dbRef.child(DbCollections.USERS).child(userId).child("userCart").child("cartItems");
    private DatabaseReference addressesRef = dbRef.child(DbCollections.USERS).child(userId).child("userShipmentAddress");

    public MutableLiveData<List<OrderItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public MutableLiveData<List<ShipmentDetails>> getShippingAddressesLiveData() {
        return shippingAddressesLiveData;
    }

    public void loadCartItems() {
        userRef.child("userCart").child("cartItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OrderItem> cartItems = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderItem orderItem = snapshot.getValue(OrderItem.class);
                    cartItems.add(orderItem);
                }
                cartItemsLiveData.setValue(cartItems);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    public void loadShippingAddresses() {
        addressesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShipmentDetails> shippingAddresses = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShipmentDetails shipmentDetails = snapshot.getValue(ShipmentDetails.class);
                    shippingAddresses.add(shipmentDetails);
                }
                shippingAddressesLiveData.setValue(shippingAddresses);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
    public void updateCartItem(OrderItem cartItem){
        cartRef.child(cartItem.getItemId()).setValue(cartItem);
    }

    public void removeCartItem(OrderItem cartItem){
        cartRef.child(cartItem.getItemId()).removeValue();
    }

    public void increaseQuantity(OrderItem cartItem){
        Product product = cartItem.getProduct();
        float availableQuantity = product.getProductQuantity() - product.getProductSales();
        if(cartItem.getQuantity() < product.getProductMaxPurchasePerUser() && availableQuantity > 0){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            updateCartItem(cartItem);
        }
    }

    public void decreaseQuantity(OrderItem cartItem){
        if(cartItem.getQuantity() > 1){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            updateCartItem(cartItem);
        }
    }
}