package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.utils.DbCollections;
import com.fsoteam.eshop.utils.OrderStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetailsViewModel extends ViewModel {

    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String userUid = FirebaseAuth.getInstance().getUid();

    public MutableLiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    public void loadOrder(String orderId) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order != null) {
                    orderLiveData.setValue(order);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void confirmReceived(String orderId) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").child(orderId).child("orderStatus").setValue(OrderStatus.CONFIRMED.toString());
    }

    public void cancelOrder(String orderId) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").child(orderId).child("orderStatus").setValue(OrderStatus.CANCELLED.toString());
    }

    public void returnOrder(String orderId) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").child(orderId).child("orderStatus").setValue(OrderStatus.REFUND_REQUESTED.toString());
    }
}
