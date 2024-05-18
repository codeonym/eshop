package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
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

public class ShippingAddressViewModel extends ViewModel {
    private MutableLiveData<List<ShipmentDetails>> shipmentDetailsLiveData = new MutableLiveData<>();
    private String userUid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userRef = databaseReference.child(DbCollections.USERS);

    public MutableLiveData<List<ShipmentDetails>> getShipmentDetailsLiveData() {
        return shipmentDetailsLiveData;
    }

    public void loadShipmentDetails() {
        databaseReference.child(DbCollections.USERS).child(userUid).child("userShipmentAddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShipmentDetails> shipmentDetailsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShipmentDetails shipmentDetails = snapshot.getValue(ShipmentDetails.class);
                    shipmentDetailsList.add(shipmentDetails);
                }
                shipmentDetailsLiveData.setValue(shipmentDetailsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
    public void removeShipmentDetails(String shipmentId) {
        userRef.child(userUid).child("userShipmentAddress").child(shipmentId).removeValue();
    }
}