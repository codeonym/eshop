package com.fsoteam.eshop.fragment;

import android.os.Bundle;
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
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.viewmodel.BagViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class BagFragment extends Fragment {

    private RecyclerView cartRecView;
    private CartAdapter cartAdapter;
    private LottieAnimationView animationView;
    private TextView totalPriceBagFrag;
    private float sum;
    private LinearLayout bottomCartLayout;
    private LinearLayout emptyBagMsgLayout;
    private TextView MybagText;

    private BagViewModel bagViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        cartRecView = view.findViewById(R.id.cartRecView);
        animationView = view.findViewById(R.id.animationViewCartPage);
        totalPriceBagFrag = view.findViewById(R.id.totalPriceBagFrag);
        bottomCartLayout = view.findViewById(R.id.bottomCartLayout);
        emptyBagMsgLayout = view.findViewById(R.id.bag_emptyBagMsgLayout);
        MybagText = view.findViewById(R.id.MybagText);

        animationView.playAnimation();
        animationView.loop(true);
        bottomCartLayout.setVisibility(View.GONE);
        MybagText.setVisibility(View.GONE);
        emptyBagMsgLayout.setVisibility(View.VISIBLE);

        cartRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        bagViewModel = new ViewModelProvider(this).get(BagViewModel.class);
        bagViewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), cartItems -> {
            cartAdapter = new CartAdapter(getActivity(), (ArrayList<OrderItem>) cartItems, bagViewModel);
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
        });

        bagViewModel.loadCartItems();
        bagViewModel.loadShippingAddresses();

        // Find the checkout button
        Button checkoutButton = view.findViewById(R.id.checkOut_BagPage);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create a new CheckoutDialogFragment
                CheckoutDialogFragment dialogFragment = new CheckoutDialogFragment(getContext(), (ArrayList<OrderItem>) bagViewModel.getCartItemsLiveData().getValue(), sum, (ArrayList<ShipmentDetails>) bagViewModel.getShippingAddressesLiveData().getValue());

                // Show the dialog
                dialogFragment.show(getChildFragmentManager(), "CheckoutDialogFragment");
            }
        });

        return view;
    }
}