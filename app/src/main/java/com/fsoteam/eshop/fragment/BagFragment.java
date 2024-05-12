package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private DatabaseReference userRef = database.getReference(DbCollections.USERS).child(userId);
    private LinearLayout bottomCartLayout;
    private LinearLayout emptyBagMsgLayout;
    private TextView MybagText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

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

        return view;
    }

    private void fetchCartItems() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if(currentUser != null && currentUser.getUserCart() != null){
                    cartItems.clear();
                    cartItems.addAll(currentUser.getUserCart().getCartItems());

                    cartAdapter = new CartAdapter(getActivity(), cartItems);
                    cartRecView.setAdapter(cartAdapter);

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
                    cartAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("BagFragment", "Failed to read value.", error.toException());
            }
        });
    }
}