package com.fsoteam.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.adapter.ShippingAddressAdapter;
import com.fsoteam.eshop.fragment.ProfileFragment;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddressActivity extends AppCompatActivity {

    private RecyclerView cartRecView;
    private TextView myAddressTitle_ShippingPage;
    private FloatingActionButton addAddressButton;
    private DatabaseReference databaseReference;
    private LottieAnimationView animationView;
    private String userUid = FirebaseAuth.getInstance().getUid();
    private List<ShipmentDetails> shipmentDetailsList;
    private LinearLayout emptyAddressMsgLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        cartRecView = findViewById(R.id.cartRecView);
        addAddressButton = findViewById(R.id.addAddress_ShippingPage);
        myAddressTitle_ShippingPage = findViewById(R.id.myAddressTitle_ShippingPage);
        emptyAddressMsgLayout = findViewById(R.id.emptyAddressLayout);
        animationView = findViewById(R.id.animationViewAddressPage);

        animationView.playAnimation();
        animationView.loop(true);
        myAddressTitle_ShippingPage.setVisibility(View.GONE);
        emptyAddressMsgLayout.setVisibility(View.VISIBLE);

        // Set the layout manager to the RecyclerView
        cartRecView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list that will hold the shipment details
        shipmentDetailsList = new ArrayList<>();

        // Get a reference to the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Attach a listener to the database reference
        databaseReference.child(DbCollections.USERS).child(userUid).child("userShipmentAddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the list before adding new data
                shipmentDetailsList.clear();

                // Loop through the snapshot and add the shipment details to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShipmentDetails shipmentDetails = snapshot.getValue(ShipmentDetails.class);
                    shipmentDetailsList.add(shipmentDetails);
                }


                ShippingAddressAdapter adapter = new ShippingAddressAdapter(shipmentDetailsList, ShippingAddressActivity.this);
                cartRecView.setAdapter(adapter);

                if (shipmentDetailsList.isEmpty()) {
                    animationView.playAnimation();
                    animationView.loop(true);
                    myAddressTitle_ShippingPage.setVisibility(View.GONE);
                    emptyAddressMsgLayout.setVisibility(View.VISIBLE);
                } else {
                    emptyAddressMsgLayout.setVisibility(View.GONE);
                    myAddressTitle_ShippingPage.setVisibility(View.VISIBLE);
                    animationView.pauseAnimation();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        addAddressButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShippingAddressActivity.this, AddShippingAddressActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShippingAddressActivity.this, MainActivity.class);
        intent.putExtra("fragment", "profile");
        startActivity(intent);
        finish();
    }
}