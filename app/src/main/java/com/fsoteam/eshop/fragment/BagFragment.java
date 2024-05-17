package com.fsoteam.eshop.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.adapter.CartAdapter;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Cart;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.fsoteam.eshop.viewmodel.CartViewModel;
import com.fsoteam.eshop.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BagFragment extends Fragment {

    private RecyclerView cartRecView;
    private CartAdapter cartAdapter;
    private LottieAnimationView animationView;
    private TextView totalPriceBagFrag;
    private ArrayList<OrderItem> cartItems;
    private float sum;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String userId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference dbRef = database.getReference();
    private DatabaseReference userRef = dbRef.child(DbCollections.USERS).child(userId);
    private DatabaseReference addressesRef = dbRef.child(DbCollections.USERS).child(userId).child("userShipmentAddress");
    private LinearLayout bottomCartLayout;
    private ArrayList<ShipmentDetails> shippingAddresses;
    private LinearLayout emptyBagMsgLayout;
    private TextView MybagText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);
        shippingAddresses = new ArrayList<>();
        cartRecView = view.findViewById(R.id.cartRecView);
        animationView = view.findViewById(R.id.animationViewCartPage);
        totalPriceBagFrag = view.findViewById(R.id.totalPriceBagFrag);
        bottomCartLayout = view.findViewById(R.id.bottomCartLayout);
        emptyBagMsgLayout = view.findViewById(R.id.bag_emptyBagMsgLayout);
        MybagText = view.findViewById(R.id.MybagText);
        cartItems = new ArrayList<>();

        animationView.playAnimation();
        animationView.loop(true);
        bottomCartLayout.setVisibility(View.GONE);
        MybagText.setVisibility(View.GONE);
        emptyBagMsgLayout.setVisibility(View.VISIBLE);

        cartRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchCartItems();
        // Find the checkout button
        Button checkoutButton = view.findViewById(R.id.checkOut_BagPage);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create a new CheckoutDialogFragment
                CheckoutDialogFragment dialogFragment = new CheckoutDialogFragment(getContext(), cartItems, sum, shippingAddresses);

                // Show the dialog
                dialogFragment.show(getChildFragmentManager(), "CheckoutDialogFragment");
            }
        });

        return view;
    }

    private void fetchCartItems() {
        userRef.child("userCart").child("cartItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItems.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderItem orderItem = snapshot.getValue(OrderItem.class);
                    cartItems.add(orderItem);
                }

                if (cartAdapter == null) {
                    cartAdapter = new CartAdapter(getActivity(), cartItems);
                    cartRecView.setAdapter(cartAdapter);
                } else {
                    cartAdapter.notifyDataSetChanged();
                }

                if (cartItems.isEmpty()) {
                    animationView.playAnimation();
                    animationView.loop(true);
                    bottomCartLayout.setVisibility(View.GONE);
                    MybagText.setVisibility(View.GONE);
                    emptyBagMsgLayout.setVisibility(View.VISIBLE);
                } else {
                    emptyBagMsgLayout.setVisibility(View.GONE);
                    bottomCartLayout.setVisibility(View.VISIBLE);
                    MybagText.setVisibility(View.VISIBLE);
                    animationView.pauseAnimation();
                }

                String currency = "DH";
                sum = 0.0f;
                for (OrderItem item : cartItems) {
                    sum += item.getQuantity() * item.getProduct().getProductPrice();
                    currency = item.getProduct().getProductCurrency();
                }
                totalPriceBagFrag.setText(sum + currency);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("BagFragment", "Failed to read value.", error.toException());
            }
        });
        addressesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                shippingAddresses.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShipmentDetails shipmentDetails = snapshot.getValue(ShipmentDetails.class);
                    shippingAddresses.add(shipmentDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("BagFragment", "Failed to read value.", error.toException());
            }
        });
    }
}