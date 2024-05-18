package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckoutViewModel extends ViewModel {
    private String userId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    public Task<Void> submitOrder(ArrayList<OrderItem> cartItems, float sum, ArrayList<ShipmentDetails> shippingAddresses, int selectedAddressIndex) {
        Order order = new Order();

        if (selectedAddressIndex != -1) {
            order.setShipmentDetails(shippingAddresses.get(selectedAddressIndex));
        } else {
            // Handle the case where the selected address is not found in the list
        }

        order.setOrderProducts(cartItems);
        order.setOrderTotalAmount(sum);

        return dbRef.child(DbCollections.USERS).child(userId).child("userOrders").child(order.getOrderId()).setValue(order).onSuccessTask(task -> {
            // Clear the cart
            return dbRef.child(DbCollections.USERS).child(userId).child("userCart").child("cartItems").removeValue();
        });
    }
}