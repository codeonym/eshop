package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShippingAddressViewModel extends ViewModel {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String userUid = FirebaseAuth.getInstance().getUid();

    public void addShippingAddress(ShipmentDetails shipmentDetails) {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userShipmentAddress").child(shipmentDetails.getShipmentId()).setValue(shipmentDetails);
    }
}