package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdersViewModel extends ViewModel {
    private MutableLiveData<List<Order>> ordersLiveData = new MutableLiveData<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String userUid = FirebaseAuth.getInstance().getUid();

    public MutableLiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public void loadOrders() {

        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").orderByChild("orderDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    orders.add(order);
                }
                Collections.reverse(orders);
                ordersLiveData.setValue(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

    }
    public void filterOrders(String status) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userOrders").orderByChild("orderDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null && order.getOrderStatus().toString().equals(status)) {
                        orders.add(order);
                    }
                }

                Collections.reverse(orders);
                ordersLiveData.setValue(orders);
                ordersLiveData.setValue(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
